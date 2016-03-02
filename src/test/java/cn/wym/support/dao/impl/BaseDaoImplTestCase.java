package cn.wym.support.dao.impl;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import cn.wym.support.entity.BaseEntity;

public class BaseDaoImplTestCase {

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
		
		TestEntity entity = new TestEntity();
		entity.setId("0001");
		EasyMock.expect(entityManager.find(baseDao.getEntityClazz(),"0001")).andReturn(entity).times(2);
		EasyMock.replay(entityManager);
		
		baseDao.setEntityManager(entityManager);
		Assert.assertNotNull(baseDao.findById("0001"));
		Assert.assertEquals(baseDao.findById("0001").getId(), "0001");
	}
	
	
	
	@Entity
	public static class TestEntity extends BaseEntity{
		
	}
	
	@Entity(name="__testNamedEntity")
	public static class TestNamedEntity extends BaseEntity{
		
	}
	
	public static class TestBaseDaoImpl extends BaseDaoImpl<TestEntity>{
		
	}
	
	public static class TestNamedBaseDaoImpl extends BaseDaoImpl<TestNamedEntity>{
		
	}
	
}
