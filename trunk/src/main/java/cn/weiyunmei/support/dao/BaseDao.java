package cn.weiyunmei.support.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.weiyunmei.support.container.PagerResultContainer;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.entity.BaseEntity;

@Transactional
public interface BaseDao<E extends BaseEntity> {


	/**
	 * 获取实体名称
	 * @param 实体类
	 * @return 实体名称
	 */
	String getEntityName(Class<?> entityCls);

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	E findById(Serializable id);

	/**
	 * 查询全部
	 * @return 
	 */
	List<E> findAll();

	/**
	 * 根据查询容器查询
	 * @param qc
	 * @return
	 */
	List<E> findByQueryContainer(QueryContainer qc);
	
	/**
	 * 根据查询容器获取总记录数
	 * @param qc
	 * @return
	 */
	long countByQueryContainer(QueryContainer qc);

	/**
	 * 根据jpql语句查询
	 * @param jpql
	 * @return
	 */
	List<E> findByJPQL(String jpql);

	/**
	 * 根据查询容器查询分页数据
	 * @param qc
	 * @return
	 */
	PagerResultContainer findPager(QueryContainer qc);

	/**
	 * 更新实体
	 * @param e
	 */
	void update(E e);

	/**
	 * 假删除实体
	 * @param e
	 */
	void update2Del(Serializable... ids);
	
	/**
	 * 保存实体
	 * @param e
	 */
	void save(E e);

	/**
	 * 删除实体
	 * @param ids
	 */
	void delete(Serializable... ids);
}
