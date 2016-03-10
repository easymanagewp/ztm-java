package cn.weiyunmei.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.user.UserAdvertisement;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/user/advertisement")
public class UserAdvertisementController extends RestController<UserAdvertisement> {

	/**
	 * 根据状态获取已完成的任务总数量
	 * @param status	// 任务状态
	 * @param userId	// 用户id
	 * @return
	 */
	@RequestMapping(value="count",method=RequestMethod.GET,params={"status","user.id"})
	public View getAdvertisementCountOfStatus(
			@RequestParam("status")int status,
			@RequestParam("user.id")String userId){
		
		return null;
	}
	
	/**
	 * 提交任务
	 */
	@RequestMapping(value="{userId}/{advertisementId}.do",method=RequestMethod.PUT,params={"data","status=complete"})
	public View complete(
			@PathVariable("userId")String userId,
			@PathVariable("advertisementId")String advertisementId,
			@RequestParam("data")String data){
		return null;
	}
	
	/**
	 * 接受任务
	 */
	@RequestMapping(value="{userId}/{advertisementId}.do",method=RequestMethod.PUT,params={"status=start"})
	public View complete(
			@PathVariable("userId")String userId,
			@PathVariable("advertisementId")String advertisementId){
		
		return null;
	}
}
