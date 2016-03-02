package cn.wym.support.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import cn.wym.support.container.ConditionContainer;
import cn.wym.support.container.OrderContainer;
import cn.wym.support.container.PagerResultContainer;
import cn.wym.support.container.QueryContainer;
import cn.wym.support.dao.BaseDao;
import cn.wym.support.entity.BaseEntity;

public class BaseDaoImpl<E extends BaseEntity> implements BaseDao<E>{

	private EntityManager entityManager;
	private Class<E> entityClazz;
	protected String entityName;
	
	/**
	 * 获取实体管理器
	 * @return
	 */
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
	
	/**
	 * 获取实体名称
	 * @param 实体类
	 * @return 实体名称
	 */
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
	
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public E findById(Serializable id) {
		return this.getEntityManager().find(entityClazz, id);
	}
	
	/**
	 * 查询全部
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<E> findAll(){
		Query query = this.getEntityManager().createQuery("", this.getEntityClazz());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findByQueryContainer(QueryContainer qc){
		Query query = createQueryByQueryContainer(qc);
		return query.getResultList();
	}
	
	public List<E> findByJPQL(String jpql){
		return this.createQueryByJPQL(jpql).getResultList();
	}
	
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
		jpql = "SELECT COUNT(id)" + jpql;
		Query query = this.createQueryByJPQL(jpql);
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
			.append(" WHERE 1=1");
		return sbf.toString();
	}

	/**
	 * 更新实体
	 * @param e
	 */
	public void update(E e){
		this.getEntityManager().merge(e);
	}
	
	/**
	 * 保存实体
	 * @param e
	 */
	public void save(E e){
		this.getEntityManager().persist(e);
	}
	
	/**
	 * 删除实体
	 * @param ids
	 */
	public void delete(Serializable... ids){
		for(Serializable id : ids){
			this.getEntityManager().remove(this.findById(id));
		}
	}
	
	
	
	
}
