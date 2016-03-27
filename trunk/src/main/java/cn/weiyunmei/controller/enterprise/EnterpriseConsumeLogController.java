package cn.weiyunmei.controller.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.enterprise.EnterpriseConsumeLog;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/enterprise/consume/log")
public class EnterpriseConsumeLogController extends RestController<EnterpriseConsumeLog> {

	@RequestMapping(value="{id}.do",method=RequestMethod.GET)
	public View count(@PathVariable("id")String id){
		QueryContainer qc = new QueryContainer();
		qc.addCondition("enterprise.id='"+id+"'");
		long count = this.getBaseDao().countByQueryContainer(qc);
		return new RestView(count);
	}
	
}
