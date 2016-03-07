package cn.wym.support.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.wym.spring.view.RestView;
import cn.wym.support.container.PagerResultContainer;
import cn.wym.support.container.QueryContainer;
import cn.wym.support.dao.BaseDao;
import cn.wym.support.entity.BaseEntity;

public class RestController<E extends BaseEntity> {

	@Autowired
	private BaseDao<E> baseDao;

	public BaseDao<E> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<E> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * <strong>创建信息: </strong>Roy Wang 2015年7月7日 上午9:47:13 <br>
	 * <strong>概要 : </strong> <br>
	 * List <strong>修改记录 : </strong> <br>
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public View list(E entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<E> resultList = null;
		resultList = getBaseDao().findAll();
		return new RestView(resultList, null);
	}

	// paging
	@RequestMapping(method = RequestMethod.GET, params = { "paging=true" })
	public View page(E entity, QueryContainer qc, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PagerResultContainer result = null;
		result = getBaseDao().findPager(qc);
		return new RestView(result, null);
	}

	// findById
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public View findById(@PathVariable(value = "id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		E result = null;
		result = getBaseDao().findById(id);
		return new RestView(result, null);
	}

	// add
	@RequestMapping(method = RequestMethod.POST)
	public View add(E entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		getBaseDao().save(entity);
		return new RestView(entity, null);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public View update(E entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		getBaseDao().update(entity);
		return new RestView(entity, null);
	}

	@RequestMapping(method = RequestMethod.DELETE, params = { "ids" })
	public View delete4Batch(@RequestParam("ids") String ids, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(ids!=null){
			Serializable[] idsArr = ids.split(",");
			getBaseDao().delete(idsArr);
		}
		return new RestView(null, null);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public View delete(@PathVariable("id") String id,HttpServletRequest reqest,HttpServletResponse response) throws Exception {
		getBaseDao().delete(id);
		return new RestView(null, null);
	}

	@RequestMapping(value = "transform", method = RequestMethod.DELETE, params = { "isDel", "ids" })
	public View delete4BatchUpdate(@RequestParam("ids") String ids, @RequestParam("isDel") String isDel,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(ids!=null){
			Serializable[] idsArr = ids.split(",");
			getBaseDao().update2Del(idsArr);
		}
		
		return new RestView(null, null);
	}

	@RequestMapping(value = "transform/{id}", method = RequestMethod.DELETE, params = { "isDel" })
	public View delete4Update(@PathVariable("id") String id, @RequestParam("isDel") String isDel,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		getBaseDao().update2Del(id);
		return new RestView(null, null);
	}

}
