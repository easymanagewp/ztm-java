package cn.weiyunmei.controller.enterprise;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.weiyunmei.dao.enterprise.EnterpriseRechargeLogDao;
import cn.weiyunmei.entity.enterprise.Enterprise;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.PagerResultContainer;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;
import cn.weiyunmei.support.exception.GlobalException;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController extends RestController<Enterprise> {

	@Autowired private EnterpriseRechargeLogDao enterpriseRechargeLogDao;
	
	@Override
	public View add(Enterprise entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String loginName = entity.getLoginName();
		String name = entity.getName();
		
		QueryContainer qc = new QueryContainer();
		qc.addCondition("loginName='"+loginName+"'");
		long count = this.getBaseDao().countByQueryContainer(qc);
		if(count>0){
			throw new GlobalException("登录名重复","Ex0002");
		}
		
		qc = new QueryContainer();
		qc.addCondition("name='"+name+"'");
		count = this.getBaseDao().countByQueryContainer(qc);
		if(count > 0){
			throw new GlobalException("企业名称重复","Ex0003");
		}
		
		return super.add(entity, request, response);
	}
	
	/**
	 * 根据企业用户名和密码获取企业信息
	 * @param loginName 企业登录名
	 * @param password	企业登录密码
	 */
	@RequestMapping(method=RequestMethod.GET,params={"loginName","password"})
	public View findByLoginNameAndPassword(
			@RequestParam(value="loginName")String loginName,
			@RequestParam(value="password")String password
			) throws Exception{
		
		QueryContainer qc = new QueryContainer();
		qc.addCondition("loginName='"+loginName+"'");
		qc.addCondition("password='"+password+"'");
		List<Enterprise> enterprise = this.getBaseDao().findByQueryContainer(qc);
		if(CollectionUtils.isEmpty(enterprise)){
			throw new GlobalException("登录名或者密码错误","Ex0001");
		}
		
		return new RestView(enterprise.get(0));
	}
	
	/**
	 * 获取企业充值记录
	 */
	@RequestMapping(value="{enterpriseId}/recharge/log.do",method=RequestMethod.GET)
	public View getRechargeLog(
			@PathVariable("enterpriseId")String enterpriseId,
			QueryContainer qc
			){
		qc.addCondition("enterprise.id='"+enterpriseId+"'");
		qc.addOrder("createTime", QueryContainer.ORDER_TYPE_DESC);
		PagerResultContainer pageResult = enterpriseRechargeLogDao.findPager(qc);
		return new RestView(pageResult);
	}
	
	@Override
	public View page(Enterprise entity,QueryContainer qc, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(StringUtils.isNotBlank(entity.getName())){
			qc.addCondition("name like '%"+entity.getName()+"%'");
		}
		
		return super.page(entity, qc, request, response);
	}
	
}
