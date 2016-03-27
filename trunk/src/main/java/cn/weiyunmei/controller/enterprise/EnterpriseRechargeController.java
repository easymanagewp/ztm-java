package cn.weiyunmei.controller.enterprise;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import cn.weiyunmei.dao.enterprise.EnterpriseDao;
import cn.weiyunmei.entity.enterprise.Enterprise;
import cn.weiyunmei.entity.enterprise.EnterpriseRechargeLog;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/enterprise/recharge/log")
public class EnterpriseRechargeController extends RestController<EnterpriseRechargeLog> {

	@Autowired private EnterpriseDao enterpriseDao;
	
	@Override
	public View add(EnterpriseRechargeLog entity, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Enterprise enterprise = enterpriseDao.findById(entity.getEnterprise().getId());
		entity.setEnterprise(enterprise);
		enterprise.setMoney(enterprise.getMoney()+entity.getMoney());
		enterpriseDao.update(enterprise);
		return super.add(entity, request, response);
	}
	
	@RequestMapping(value="{id}.do",method=RequestMethod.GET)
	public View count(@PathVariable("id")String id){
		QueryContainer qc = new QueryContainer();
		qc.addCondition("enterprise.id='"+id+"'");
		long count = this.getBaseDao().countByQueryContainer(qc);
		return new RestView(count);
	}
	
}
