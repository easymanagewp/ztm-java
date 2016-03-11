package cn.weiyunmei.support.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import cn.weiyunmei.support.container.ConditionContainer;
import cn.weiyunmei.support.container.OrderContainer;
import cn.weiyunmei.support.container.PagerResultContainer;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.dao.BaseDao;
import cn.weiyunmei.support.entity.BaseEntity;

public class BaseDaoImpl<E extends BaseEntity> implements BaseDao<E> {

	private EntityManager entityManager;
	private Class<E> entityClazz;
	protected String entityName;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * 设置实体管理器
	 * @param entityManager 实体管理器
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Class<E> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<E> entityClazz) {
		this.entityClazz = entityClazz;
	}

	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	/**
	 * 初始化基础数据，获取EntityClass和EntityName
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		@SuppressWarnings("rawtypes")
		Class<? extends BaseDaoImpl> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClazz = (Class<E>) parameterizedType[0];
			this.entityName = getEntityName(entityClazz);
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#getEntityName(java.lang.Class)
	 */
	@Override
	public String getEntityName(Class<?> entityCls){
		String entityName = entityCls.getSimpleName();
		Entity entityAnno = entityCls.getAnnotation(Entity.class);
		if(null != entityAnno){
			String entityName4Anno = entityAnno.name();
			if(StringUtils.isNotBlank(entityName4Anno)){
				entityName = entityName4Anno;
			}
		}
		return entityName;
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#findById(java.io.Serializable)
	 */
	@Override
	public E findById(Serializable id) {
		return this.getEntityManager().find(entityClazz, id);
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> findAll(){
		Query query = this.getEntityManager().createQuery(this.createBaseJPQL(), this.getEntityClazz());
		return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#findByQueryContainer(cn.wym.support.container.QueryContainer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> findByQueryContainer(QueryContainer qc){
		Query query = createQueryByQueryContainer(qc);
		return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#findByJPQL(java.lang.String)
	 */
	@Override
	public List<E> findByJPQL(String jpql){
		return this.createQueryByJPQL(jpql).getResultList();
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#findPager(cn.wym.support.container.QueryContainer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PagerResultContainer findPager(QueryContainer qc){
		Query query = createQueryByQueryContainer(qc);
		PagerResultContainer pagerResult = getPageInfo(qc);
		
		if(pagerResult.getTotalPage() < qc.getPageNum()){
			qc.setPageNum(pagerResult.getTotalPage());
		}else if(qc.getPageNum()<1){
			qc.setPageNum(1);
		}
		
		query.setMaxResults(qc.getPageSize());
		int firstRow = (qc.getPageNum() - 1) * qc.getPageSize();
		query.setFirstResult(firstRow);
		
		List<E> result = query.getResultList();
		pagerResult.setResult(result);
		return pagerResult;
	}

	private PagerResultContainer getPageInfo(QueryContainer qc) {
		PagerResultContainer pagerResult = new PagerResultContainer();
		String jpql = createJPQLByQueryContainer(qc);
		jpql = "SELECT COUNT(id) " + jpql;
		Query query = this.getEntityManager().createQuery(jpql);
		Long totalRows = (Long) query.getSingleResult();
		pagerResult.setTotalRows(totalRows);
		
		int totalPage = 1;
		if(totalRows%qc.getPageSize()>0){
			totalPage = (int) (totalRows / qc.getPageSize() +1);
		}else{
			totalPage = (int) (totalRows / qc.getPageSize());
		}
		
		pagerResult.setTotalPage(totalPage);
		
		return pagerResult;
	}

	private Query createQueryByQueryContainer(QueryContainer qc) {
		String jpql = createJPQLByQueryContainer(qc);
		return createQueryByJPQL(jpql);
	}

	private String createJPQLByQueryContainer(QueryContainer qc) {
		String jpql = this.createBaseJPQL();
		if(qc.hasCondition()){
			jpql = buildConditions(jpql,qc);
		}
		if(qc.hasOrder()){
			jpql = buildOrders(jpql,qc);
		}
		return jpql;
	}

	private TypedQuery<E> createQueryByJPQL(String jpql) {
		return this.getEntityManager().createQuery(jpql, this.getEntityClazz());
	}
	
	private String buildConditions(String jpql, QueryContainer qc) {
		StringBuffer sbf = new StringBuffer(jpql);
		List<ConditionContainer> conditions = qc.getConditions();
		for(ConditionContainer condition : conditions){
			sbf.append(" AND ")
				.append(condition.getCondition());
		}
		
		return sbf.toString();
	}
	
	private String buildOrders(String jpql, QueryContainer qc){
		StringBuffer sbf = new StringBuffer(jpql);
		sbf.append(" ORDER BY ");
		List<OrderContainer> orders = qc.getOrders();
		for(OrderContainer order : orders){
			sbf.append(" ")
				.append(order.getField())
				.append(" ")
				.append(order.getOrderType())
				.append(",");
		}
		return sbf.delete(0, sbf.length() - 1).toString();
	}

	private String createBaseJPQL() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("FROM ")
			.append(getEntityName())
			.append(" WHERE isDel=false");
		return sbf.toString();
	}

	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#update(E)
	 */
	@Override
	public void update(E e){
		this.getEntityManager().merge(e);
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#save(E)
	 */
	@Override
	public void save(E e){
		e.setCreateTime(new Date().getTime());
		this.getEntityManager().persist(e);
	}
	
	/* (non-Javadoc)
	 * @see cn.wym.support.dao.impl.BaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable... ids){
		for(Serializable id : ids){
			this.getEntityManager().remove(this.findById(id));
		}
	}

	@Override
	public void update2Del(Serializable... ids) {
		for(Serializable id : ids){
			E entity = this.findById(id);
			entity.setDel(true);
			this.update(entity);
		}
	}
	
	
	
	
}
