package cn.weiyunmei.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import cn.weiyunmei.support.exception.GlobalException;
import cn.weiyunmei.utils.BeanPropertiesUtils;
import cn.weiyunmei.utils.Random;

@Controller
@RequestMapping("user")
public class UserController extends RestController<User> {
	
	@Override
	public View update(User entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User oldUser = this.getBaseDao().findById(entity.getId());
		String[] igPro = BeanPropertiesUtils.getEmptyProperties(entity);
		ArrayUtils.add(igPro, "password");
		BeanUtils.copyProperties(entity, oldUser,igPro);
		this.getBaseDao().update(oldUser);
		return new RestView(oldUser);
	}
	
	@Override
	public View add(User entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mobile = entity.getMobile();
		QueryContainer qc = new QueryContainer();
		qc.addCondition("mobile='"+mobile+"'");
		long count = this.getBaseDao().countByQueryContainer(qc);
		// 验证手机号是否注册
		if(count>0){
			throw new GlobalException("手机号已经被注册", "UCAx0001");
		}
		// 生成推荐码
		while(true){
			String randomCode = Random.getRandomCode();
			qc = new QueryContainer();
			qc.addCondition("code='"+randomCode+"'");
			count = this.getBaseDao().countByQueryContainer(qc);
			if(count > 0){
				continue;
			}else{
				entity.setCode(randomCode);
				break;
			}
		}
		
		// 邀请人
		if(entity.getParent()!=null && StringUtils.isNotBlank(entity.getParent().getCode())){
			qc = new QueryContainer();
			qc.addCondition("code='"+entity.getParent().getCode()+"'");
			List<User> parent = this.getBaseDao().findByQueryContainer(qc);
			if(CollectionUtils.isEmpty(parent)){
				entity.setParent(null);
			}else{
				entity.setParent(parent.get(0));
			}
		}
		
		return super.add(entity, request, response);
	}
	
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
	
	@Override
	public View page(User entity, QueryContainer qc, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取指定人员下的一级粉丝列表
		if(entity.getParent()!=null && StringUtils.isNotBlank(entity.getParent().getId())){
			qc.addCondition("parent.id='"+entity.getParent().getId()+"'");
		}
		return super.page(entity, qc, request, response);
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
		List<User> users = this.getBaseDao().findByQueryContainer(qc);
		if(CollectionUtils.isEmpty(users)){
			throw new GlobalException("用户名或密码错误", "UCx0002");
		}else{
			return new RestView(users.get(0),null);
		}
	}
	
	@RequestMapping(value="password.do",method=RequestMethod.PUT,params={"mobile","newPassword"})
	public View setPassword(
			@RequestParam("mobile")String mobile,
			@RequestParam("newPassword")String newPassword
			){
		QueryContainer qc = new QueryContainer();
		qc.addCondition("mobile='"+mobile+"'");
		List<User> users = this.getBaseDao().findByQueryContainer(qc);
		if(CollectionUtils.isEmpty(users)){
			throw new GlobalException("手机号不存在","Ux0005");
		}
		User user = users.get(0);
		user.setPassword(newPassword);
		this.getBaseDao().update(user);
		return new RestView(user);
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
			@RequestParam(value="wechatId")String wechatId,
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
			this.getBaseDao().update(user);
			return new RestView(user,null);
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


