package cn.weiyunmei.controller.user;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.weiyunmei.entity.user.User;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("user")
public class UserController extends RestController<User> {
	
	/**
	 * 获取用户账户余额
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="money/{id}.do",method=RequestMethod.GET)
	public View getMoney(@PathVariable(value="id")String userId){
		User user = this.getBaseDao().findById(userId);
		
		// 依据日志验证
		
		return new RestView(user.getMoney(),null);
	}
	
	/**
	 * 获取用户粉丝列表
	 */
	@RequestMapping(value="{id}/fans.do",method=RequestMethod.GET)
	public View fans(@PathVariable(value="id")String userId){
		QueryContainer qc = new QueryContainer();
		qc.addCondition("parent.id='"+userId+"'");
		List<User> fans = this.getBaseDao().findByQueryContainer(qc);
		return new RestView(fans,null);
	}
	
	/**
	 * 根据手机号和密码获取用户信息
	 */
	@RequestMapping(method=RequestMethod.GET,params={"mobile","password"})
	public View findByMobileAndPassword(
			@RequestParam("mobile")String mobile,
			@RequestParam("password")String password){
		QueryContainer qc = new QueryContainer();
		qc.addCondition("mobile='"+mobile+"'");
		qc.addCondition("password='"+password+"'");
		List<User> fans = this.getBaseDao().findByQueryContainer(qc);
		if(CollectionUtils.isEmpty(fans)){
			return new RestView(null,null);
		}else{
			return new RestView(fans.get(0),null);
		}
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="password.do",method=RequestMethod.PUT,params={"id","newPassword","oldPassword"})
	public View resetPassword(
			@RequestParam("id")String userId,
			@RequestParam("newPassword")String newPassword,
			@RequestParam("oldPassword")String oldPassword
			){
		User user = this.getBaseDao().findById(userId);
		if(StringUtils.equals(user.getPassword(), oldPassword)){
			user.setPassword(newPassword);
			this.getBaseDao().update(user);
		}
		
		return new RestView(null,null);
	}
	
	/**
	 * 微信登录/绑定/注册接口
	 */
	@RequestMapping(value="wechat.do",method=RequestMethod.POST,params={"wechatId","nickname","sex","province","city","headimgUrl"})
	public View getUserInfoByWechatId(
			@RequestParam(value="userId",required=false)String userId,	//存在则进行绑定
			@RequestParam(value="weichatId")String wechatId,
			@RequestParam(value="nickname")String nickname,
			@RequestParam(value="sex")String sex,
			@RequestParam(value="province")String province,
			@RequestParam(value="city")String city,
			@RequestParam(value="headimgUrl")String headimgUrl
			){
		if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(wechatId)){
			User user = this.getBaseDao().findById(userId);
			user.setWechatId(wechatId);
			user.setWechatName(nickname);
			return new RestView("",null);
		}else{
			// 根据wechatId获取用户
			QueryContainer qc = new QueryContainer();
			qc.addCondition("wechatId='"+wechatId+"'");
			List<User> users = this.getBaseDao().findByQueryContainer(qc);
			if(CollectionUtils.isNotEmpty(users)){
				// 有，返回用户信息
				return new RestView(users.get(0),null);
			}else{
				// 无，生成用户，返回用户信息
				User user = new User();
				user.setIcon(headimgUrl);
				user.setName(nickname);
				user.setSex(sex);
				user.setWechatId(wechatId);
				user.setWechatName(nickname);
				user.setAddress(province+"省"+city);
				this.getBaseDao().save(user);
				return new RestView(user,null);
			}
		}
	}
}


