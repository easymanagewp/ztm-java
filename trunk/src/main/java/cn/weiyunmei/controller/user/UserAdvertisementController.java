package cn.weiyunmei.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.weiyunmei.dao.advertisement.AdvertisementDao;
import cn.weiyunmei.dao.advertisement.AdvertisementDataDao;
import cn.weiyunmei.dao.user.UserAdvertisementDataDao;
import cn.weiyunmei.dao.user.UserAdvertisementLogDao;
import cn.weiyunmei.dao.user.UserDao;
import cn.weiyunmei.entity.advertisement.Advertisement;
import cn.weiyunmei.entity.advertisement.AdvertisementData;
import cn.weiyunmei.entity.user.User;
import cn.weiyunmei.entity.user.UserAdvertisement;
import cn.weiyunmei.entity.user.UserAdvertisementData;
import cn.weiyunmei.entity.user.UserAdvertisementLog;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;
import cn.weiyunmei.support.exception.GlobalException;

@Controller
@RequestMapping("/user/advertisement")
public class UserAdvertisementController extends RestController<UserAdvertisement> {

	@Autowired private AdvertisementDataDao advertisementDataDao;
	@Autowired private AdvertisementDao advertisementDao;
	@Autowired private UserAdvertisementDataDao userAdvertisementDataDao;
	@Autowired private UserDao userDao;
	@Autowired private UserAdvertisementLogDao userAdvertisementLogDao;
	
	@Override
	public View page(UserAdvertisement entity, QueryContainer qc, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int status = entity.getStatus();
		qc.addCondition("status="+status);
		if(entity.getAdvertisement()!=null && StringUtils.isBlank(entity.getAdvertisement().getId())){
			qc.addCondition("advertisement.id='"+entity.getAdvertisement().getId()+"'");
		}
		return super.page(entity, qc, request, response);
	}
	
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
		QueryContainer qc = new QueryContainer();
		qc.addCondition("status="+status);
		qc.addCondition("user.id='"+userId+"'");
		List<UserAdvertisement> userAdvertisements = getBaseDao().findByQueryContainer(qc);
		
		return new RestView(userAdvertisements.size(),null);
	}
	
	/**
	 * 提交任务
	 */
	@RequestMapping(value="{userId}/{advertisementId}/complete.do",method=RequestMethod.PUT,params={"data"})
	public View complete(
			@PathVariable("userId")String userId,
			@PathVariable("advertisementId")String advertisementId,
			@RequestParam("data")String data){ /* {name:value,name:value} - base64 */
		// 获取用户和广告信息
		QueryContainer qc = new QueryContainer();
		qc.addCondition("advertisement.id='"+advertisementId+"'");
		qc.addCondition("user.id='"+userId+"'");
		List<UserAdvertisement> userAdvertisements = this.getBaseDao().findByQueryContainer(qc);
		UserAdvertisement userAdvertisement = userAdvertisements.get(0);
		
		/*
		 * 解密data
		 * */
		byte[] dataBytes = data.getBytes();
		Base64 base64 = new Base64();
		dataBytes = base64.decode(dataBytes);
		String dataJsonStr = new String(dataBytes);
		Map<String,String> dataJson = new HashMap<String,String>();
		String[] dataJsonArr = dataJsonStr.split(",");
		for(String key : dataJsonArr){
			dataJson.put(key.split(":")[0], key.split(":")[1]);
		}
		//JSONObject dataJson = JSON.parseObject(dataJsonStr);
		
		// 解析data，保存提交数据
		Set<String> names = dataJson.keySet();
		for(String name : names){
			String value = dataJson.get(name);
			qc = new QueryContainer();
			qc.addCondition("name='"+name+"'");
			qc.addCondition("advertisement.id='"+advertisementId+"'");
			List<AdvertisementData> advertisementDatas = advertisementDataDao.findByQueryContainer(qc);
			if(CollectionUtils.isNotEmpty(advertisementDatas)){
				AdvertisementData advertisementData = advertisementDatas.get(0);
				UserAdvertisementData uda = new UserAdvertisementData();
				uda.setAdvertisementData(advertisementData);
				uda.setUserAdvertisement(userAdvertisement);
				uda.setValue(value);
				userAdvertisementDataDao.save(uda);
			}
		}
		
		// 更新任务状态
		userAdvertisement.setStatus(UserAdvertisement.STATUS_COMPLETE);
		this.getBaseDao().update(userAdvertisement);
		
		// 保存任务执行日志
		UserAdvertisementLog userAdvertisementLog = new UserAdvertisementLog();
		userAdvertisementLog.setStatus(UserAdvertisement.STATUS_COMPLETE);
		userAdvertisementLog.setUserAdvertisement(userAdvertisement);
		userAdvertisementLog.setRemark("提交任务 - "+userAdvertisement.getAdvertisement().getTitle());
		userAdvertisementLogDao.save(userAdvertisementLog);
		
		return new RestView(null,null);
	}
	
	/**
	 * 接受任务
	 */
	@RequestMapping(value="{userId}/{advertisementId}.do",method=RequestMethod.PUT,params={"status=start"})
	public View complete(
			@PathVariable("userId")String userId,
			@PathVariable("advertisementId")String advertisementId){
		// 验证用户是否已经接受过此任务
		QueryContainer qc = new QueryContainer();
		qc.addCondition("user.id='"+userId+"'");
		qc.addCondition("advertisement.id='"+advertisementId+"'");
		long count = this.getBaseDao().countByQueryContainer(qc);
		if(count>0){
			throw new GlobalException("该任务已经接受过，请勿重复接受","UDx0001");
		}
		
		// 获取用户信息和任务信息
		User user = userDao.findById(userId);
		Advertisement advertisement = advertisementDao.findById(advertisementId);
		// 创建用户任务信息
		UserAdvertisement userAdvertisement = new UserAdvertisement();
		userAdvertisement.setAdvertisement(advertisement);
		userAdvertisement.setUser(user);
		userAdvertisement.setStatus(UserAdvertisement.STATUS_STARTED);
		this.getBaseDao().save(userAdvertisement);
		// 保存任务日志
		UserAdvertisementLog userAdvertisementLog = new UserAdvertisementLog();
		userAdvertisementLog.setStatus(UserAdvertisement.STATUS_STARTED);
		userAdvertisementLog.setUserAdvertisement(userAdvertisement);
		userAdvertisementLog.setRemark("接受任务-"+advertisement.getTitle());
		userAdvertisementLogDao.save(userAdvertisementLog);
		return new RestView(null,null);
	}
	
	/**
	 * 获取广告状态
	 */
	@RequestMapping(value="status.do",method=RequestMethod.GET)
	public View getAdvertisementStatus(
			@RequestParam(value="user.id")String userId,
			@RequestParam(value="advertisementIds")String advertisementIds
			){
		if(StringUtils.isNotBlank(advertisementIds)){
			Map<String,Integer> status = new HashMap<String,Integer>();
			String[] advertisementIdArr = advertisementIds.split(",");
			for(String advertisementId : advertisementIdArr){
				QueryContainer qc = new QueryContainer();
				qc.addCondition("advertisement.id='"+advertisementId+"'");
				qc.addCondition("user.id='"+userId+"'");
				List<UserAdvertisement> userAdvertisements = this.getBaseDao().findByQueryContainer(qc);
				if(CollectionUtils.isNotEmpty(userAdvertisements)){
					status.put(userAdvertisements.get(0).getAdvertisement().getId(), userAdvertisements.get(0).getStatus());
				}
			}
			return new RestView(status,null);
		}
		
		return new RestView(null,null);
	}
}
