package cn.weiyunmei.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.user.UserCashingLog;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/user/cashing/log")
public class UserCashingLogController extends RestController<UserCashingLog> {

	/**
	 * 获取用户提现明细
	 */
	@Override
	public View list(UserCashingLog entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = entity.getUser().getId();
		QueryContainer qc = new QueryContainer();
		qc.addCondition("user.id='"+userId+"'");
		List<UserCashingLog> userCashingLogs = getBaseDao().findByQueryContainer(qc);
		return new RestView(userCashingLogs, null);
	}
	
	
}
