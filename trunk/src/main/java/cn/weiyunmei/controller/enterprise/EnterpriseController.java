package cn.weiyunmei.controller.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.enterprise.Enterprise;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController extends RestController<Enterprise> {

	/**
	 * 根据企业用户名和密码获取企业信息
	 * @param loginName 企业登录名
	 * @param password	企业登录密码
	 */
	@RequestMapping(method=RequestMethod.GET,params={"loginName","password"})
	public View findByLoginNameAndPassword(
			@RequestParam(value="loginName")String loginName,
			@RequestParam(value="password")String password
			){
		
		return null;
	}
	
}
