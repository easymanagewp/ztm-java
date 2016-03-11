package cn.weiyunmei.controller.user;

import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
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
import cn.weiyunmei.dao.user.UserDao;
import cn.weiyunmei.entity.advertisement.Advertisement;
import cn.weiyunmei.entity.advertisement.AdvertisementData;
import cn.weiyunmei.entity.user.User;
import cn.weiyunmei.entity.user.UserAdvertisement;
import cn.weiyunmei.entity.user.UserAdvertisementData;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/user/advertisement")
public class UserAdvertisementController extends RestController<UserAdvertisement> {

	@Autowired private AdvertisementDataDao advertisementDataDao;
	@Autowired private AdvertisementDao advertisementDao;
	@Autowired private UserAdvertisementDataDao userAdvertisementDataDao;
	@Autowired private UserDao userDao;
	
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
	@RequestMapping(value="{userId}/{advertisementId}.do",method=RequestMethod.PUT,params={"data","status=complete"})
	public View complete(
			@PathVariable("userId")String userId,
			@PathVariable("advertisementId")String advertisementId,
			@RequestParam("data")String data){ /* {name:value,name:value} - base64 */
		//User user = userDao.findById(userId);
		QueryContainer qc = new QueryContainer();
		qc.addCondition("advertisement.id='"+advertisementId+"'");
		qc.addCondition("user.id='"+userId+"'");
		List<UserAdvertisement> userAdvertisements = this.getBaseDao().findByQueryContainer(qc);
		UserAdvertisement userAdvertisement = userAdvertisements.get(0);
//		if(CollectionUtils.isNotEmpty(userAdvertisements)){
//			
//		}
		
		/*
		 * 解密data
		 * */
		byte[] dataBytes = data.getBytes();
		Base64 base64 = new Base64();
		dataBytes = base64.decode(dataBytes);
		String dataJsonStr = new String(dataBytes);
		JSONObject dataJson = JSON.parseObject(dataJsonStr);
		
		Set<String> names = dataJson.keySet();
		for(String name : names){
			String value = dataJson.getString(name);
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
		
		userAdvertisement.setStatus(UserAdvertisement.STATUS_COMPLETE);
		this.getBaseDao().update(userAdvertisement);
		
		return new RestView(null,null);
	}
	
	/**
	 * 接受任务
	 */
	@RequestMapping(value="{userId}/{advertisementId}.do",method=RequestMethod.PUT,params={"status=start"})
	public View complete(
			@PathVariable("userId")String userId,
			@PathVariable("advertisementId")String advertisementId){
		User user = userDao.findById(userId);
		Advertisement advertisement = advertisementDao.findById(advertisementId);
		UserAdvertisement userAdvertisement = new UserAdvertisement();
		userAdvertisement.setAdvertisement(advertisement);
		userAdvertisement.setUser(user);
		userAdvertisement.setStatus(UserAdvertisement.STATUS_STARTED);
		this.getBaseDao().save(userAdvertisement);
		return new RestView(null,null);
	}
}
