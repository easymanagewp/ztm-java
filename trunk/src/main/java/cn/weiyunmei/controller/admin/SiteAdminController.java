package cn.weiyunmei.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.admin.SiteAdmin;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.controller.RestController;

@RequestMapping(value="admin")
public class SiteAdminController extends RestController<SiteAdmin>{

	@RequestMapping(method=RequestMethod.GET,params={"loginName","password"})
	public View getByLoginNameAndPassword(){
		
		return new RestView(null);
	}
	
}
