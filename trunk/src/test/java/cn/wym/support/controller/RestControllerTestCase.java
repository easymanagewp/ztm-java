package cn.wym.support.controller;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.View;

import cn.wym.support.container.PagerResultContainer;
import cn.wym.support.container.QueryContainer;
import cn.wym.support.dao.BaseDao;
import cn.wym.support.entity.BaseEntity;

@SuppressWarnings("unchecked")
public class RestControllerTestCase {
	
	private BaseDao<BaseEntity> dao;
	private RestController<BaseEntity> controller;
	private QueryContainer qc = new QueryContainer();
	private BaseEntity entity = new BaseEntity();
	private String id = "001";
	
	@Test
	public void listTestCase() throws Exception{
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		EasyMock.expect(dao.findAll()).andReturn(new ArrayList<BaseEntity>());
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.list(null, null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void pageTestCase() throws Exception{
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		EasyMock.expect(dao.findPager(qc)).andReturn(new PagerResultContainer());
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.page(null,qc,null,null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void findByIdTestCase() throws Exception{
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		EasyMock.expect(dao.findById(id)).andReturn(new BaseEntity());
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.findById(id, null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void addTestCase() throws Exception{
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		dao.save(entity);
		EasyMock.expectLastCall();
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.add(entity, null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void updateTestCase() throws Exception{
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		dao.update(entity);
		EasyMock.expectLastCall();
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.update(entity, null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void delete4BatchTestCase() throws Exception {
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		dao.delete("222","333");
		EasyMock.expectLastCall();
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.delete4Batch(null, null, null);
		Assert.assertNotNull(view);
		
		view = controller.delete4Batch("222,333", null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void deleteTestCase() throws Exception {
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		dao.delete("");
		EasyMock.expectLastCall();
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.delete("", null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void delete4BatchUpdateTestCase() throws Exception {
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		dao.update2Del("111","222","333");
		EasyMock.expectLastCall();
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.delete4BatchUpdate(null,null, null, null);
		Assert.assertNotNull(view);
		
		view = controller.delete4BatchUpdate("111,222,333",null, null, null);
		Assert.assertNotNull(view);
	}
	
	@Test
	public void delete4UpdateTestCase() throws Exception {
		IMocksControl mc = EasyMock.createStrictControl();
		dao = mc.createMock(BaseDao.class);
		dao.update2Del("");
		EasyMock.expectLastCall();
		EasyMock.replay(dao);
		controller = new RestController<BaseEntity>();
		controller.setBaseDao(dao);
		View view = controller.delete4Update("",null, null, null);
		Assert.assertNotNull(view);
	}
	
	
}
