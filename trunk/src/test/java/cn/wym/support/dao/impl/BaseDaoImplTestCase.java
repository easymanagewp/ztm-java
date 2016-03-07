package cn.wym.support.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import cn.weiyunmei.support.container.PagerResultContainer;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.dao.impl.BaseDaoImpl;
import cn.weiyunmei.support.entity.BaseEntity;

public class BaseDaoImplTestCase {

	List<TestEntity> entityList = new ArrayList<TestEntity>();
	TestEntity entity = new TestEntity();
	TestEntity entity2 = new TestEntity();
	{
		entity.setId("0001");
		entity2.setId("0002");
		entityList.add(entity);
		entityList.add(entity2);
	}

	/**
	 * 初始化 - 构造方法测试
	 */
	@Test
	public void initTestCase() throws Exception {
		TestBaseDaoImpl baseDao = new TestBaseDaoImpl();

		Assert.assertEquals(baseDao.getEntityName(), "TestEntity");
		Assert.assertEquals(baseDao.getEntityClazz(), TestEntity.class);

		TestNamedBaseDaoImpl namedBaseDao = new TestNamedBaseDaoImpl();
		Assert.assertEquals(namedBaseDao.getEntityName(), "__testNamedEntity");
		Assert.assertEquals(namedBaseDao.getEntityClazz(), TestNamedEntity.class);
	}

	/**
	 * 测试findById方法
	 */
	@Test
	public void findByIdTestCase() throws Exception {
		TestBaseDaoImpl baseDao = new TestBaseDaoImpl();
		EntityManager entityManager = EasyMock.createMock(EntityManager.class);

		EasyMock.expect(entityManager.find(baseDao.getEntityClazz(), "0001")).andReturn(entity).times(2);
		EasyMock.replay(entityManager);

		baseDao.setEntityManager(entityManager);
		Assert.assertNotNull(baseDao.findById("0001"));
		Assert.assertEquals(baseDao.findById("0001").getId(), "0001");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findAllTestCase() throws Exception {
		TestBaseDaoImpl baseDao = new TestBaseDaoImpl();

		TypedQuery<TestEntity> query = EasyMock.createMock(TypedQuery.class);
		EasyMock.expect(query.getResultList()).andReturn(entityList).times(3);
		EasyMock.replay(query);

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(
				entityManager.createQuery("FROM " + baseDao.getEntityName() + " WHERE isDel=false", baseDao.getEntityClazz()))
				.andReturn(query).times(3);
		EasyMock.replay(entityManager);

		baseDao.setEntityManager(entityManager);

		Assert.assertEquals(2, baseDao.findAll().size());
		Assert.assertEquals("0001", baseDao.findAll().get(0).getId());
		Assert.assertEquals("0002", baseDao.findAll().get(1).getId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findByQueryContainerTestCase() throws Exception {
		TestBaseDaoImpl baseDao = new TestBaseDaoImpl();

		TypedQuery<TestEntity> query = EasyMock.createMock(TypedQuery.class);
		EasyMock.expect(query.getResultList()).andReturn(entityList).times(3);
		EasyMock.replay(query);

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.createQuery(
				"FROM " + baseDao.getEntityName() + " WHERE isDel=false AND name=11 AND age=12", baseDao.getEntityClazz()))
				.andReturn(query).times(3);
		EasyMock.replay(entityManager);

		baseDao.setEntityManager(entityManager);

		QueryContainer qc = new QueryContainer();
		qc.addCondition("name=11");
		qc.addCondition("age=12");
		Assert.assertEquals(2, baseDao.findByQueryContainer(qc).size());
		Assert.assertEquals("0001", baseDao.findByQueryContainer(qc).get(0).getId());
		Assert.assertEquals("0002", baseDao.findByQueryContainer(qc).get(1).getId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findPagerTestCase() throws Exception {
		TestBaseDaoImpl baseDao = new TestBaseDaoImpl();

		TypedQuery<TestEntity> query = EasyMock.createMock(TypedQuery.class);
		List<TestEntity> entityList2 = new ArrayList<TestEntity>();
		entityList2.add(entity);
		EasyMock.expect(query.getResultList()).andReturn(entityList2);
		EasyMock.expect(query.setMaxResults(1)).andReturn(query);
		EasyMock.expect(query.setFirstResult(0)).andReturn(query);
		EasyMock.replay(query);

		TypedQuery<Long> pagerQuery = EasyMock.createMock(TypedQuery.class);
		EasyMock.expect(pagerQuery.getSingleResult()).andReturn(new Long(2));
		EasyMock.replay(pagerQuery);
		
		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager
				.createQuery("FROM " + baseDao.getEntityName() + " WHERE isDel=false AND name=11 AND age=12", baseDao.getEntityClazz()))
				.andReturn(query);
		EasyMock.expect(entityManager
				.createQuery("SELECT COUNT(id) FROM "+baseDao.getEntityName()+" WHERE isDel=false AND name=11 AND age=12"))
				.andReturn(pagerQuery);
		EasyMock.replay(entityManager);

		baseDao.setEntityManager(entityManager);
		
		QueryContainer qc = new QueryContainer();
		qc.addCondition("name=11");
		qc.addCondition("age=12");
		qc.setPageNum(-1);
		qc.setPageSize(1);
		PagerResultContainer resultContainer = baseDao.findPager(qc);
		
		Assert.assertEquals(2, resultContainer.getTotalRows());
		Assert.assertEquals(2, resultContainer.getTotalPage());
		Assert.assertEquals(1, resultContainer.getResult().size());
		Assert.assertEquals("0001", resultContainer.getResult().get(0).getId());
	}

	@Entity
	public static class TestEntity extends BaseEntity {

	}

	@Entity(name = "__testNamedEntity")
	public static class TestNamedEntity extends BaseEntity {

	}

	public static class TestBaseDaoImpl extends BaseDaoImpl<TestEntity> {

	}

	public static class TestNamedBaseDaoImpl extends BaseDaoImpl<TestNamedEntity> {

	}

}
