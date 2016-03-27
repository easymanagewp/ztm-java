package cn.weiyunmei.controller.advertisement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import cn.weiyunmei.dao.advertisement.AdvertisementDataDao;
import cn.weiyunmei.dao.advertisement.AdvertisementDataTypeDao;
import cn.weiyunmei.dao.advertisement.AdvertisementTypeDao;
import cn.weiyunmei.dao.enterprise.EnterpriseDao;
import cn.weiyunmei.dao.user.UserAdvertisementDao;
import cn.weiyunmei.entity.advertisement.Advertisement;
import cn.weiyunmei.entity.advertisement.AdvertisementData;
import cn.weiyunmei.entity.advertisement.AdvertisementDataType;
import cn.weiyunmei.entity.advertisement.AdvertisementType;
import cn.weiyunmei.entity.enterprise.Enterprise;
import cn.weiyunmei.entity.user.UserAdvertisement;
import cn.weiyunmei.spring.view.RestView;
import cn.weiyunmei.support.container.PagerResultContainer;
import cn.weiyunmei.support.container.QueryContainer;
import cn.weiyunmei.support.controller.RestController;
import cn.weiyunmei.support.exception.GlobalException;
import cn.weiyunmei.utils.Cn2Spell;

@Controller
@RequestMapping("advertisement")
public class AdvertisementController extends RestController<Advertisement> {
	
	@Autowired private UserAdvertisementDao userAdvertisementDao;
	@Autowired private AdvertisementTypeDao advertisementTypeDao;
	@Autowired private EnterpriseDao enterpriseDao;
	@Autowired private AdvertisementDataTypeDao advertisementDataTypeDao;
	@Autowired private AdvertisementDataDao advertisementDataDao;
	
	
	
	// 获取任务详情（如果任务为正在进行的状态，则显示任务接受人数和任务完成人数）
	@Override
	public View findById(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Advertisement advertisement = this.getBaseDao().findById(id);
		if(advertisement.getStatus() == Advertisement.STATUS_EXECUTE){
			// 接受任务的人数
			QueryContainer qc = new QueryContainer();
			qc.addCondition("advertisement.id='"+advertisement.getId()+"'");
			qc.addCondition("status="+UserAdvertisement.STATUS_STARTED);
			long startCount = userAdvertisementDao.countByQueryContainer(qc);
			
			// 审核中的人数
			qc = new QueryContainer();
			qc.addCondition("advertisement.id='"+advertisement.getId()+"'");
			qc.addCondition("status="+UserAdvertisement.STATUS_COMPLETE);
			long completeCount = userAdvertisementDao.countByQueryContainer(qc);
			
			// 已完成任务的人数
			qc = new QueryContainer();
			qc.addCondition("advertisement.id='"+advertisement.getId()+"'");
			qc.addCondition("status="+UserAdvertisement.STATUS_END);
			long endCount = userAdvertisementDao.countByQueryContainer(qc);
			
			advertisement.setStartCount(startCount);
			advertisement.setCompleteCount(completeCount);
			advertisement.setEndCount(endCount);
		}
		return new RestView(advertisement);
	}
	
	// 创建任务
	@Override
	public View add(Advertisement entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 设置typeId
		{
			String typeId = entity.getType()!=null ? entity.getType().getId() : null;
			if(StringUtils.isNotBlank(typeId)){
				AdvertisementType advertisementType = advertisementTypeDao.findById(entity.getType().getId());
				entity.setType(advertisementType);
			}else{
				throw new GlobalException("请选择任务类型","ACAx0001");
			}
		}
		
		// 设置发布企业
		{
			String enterpriseId = entity.getReleaseEnterprise()!= null?entity.getReleaseEnterprise().getId() : null;
			if(StringUtils.isNotBlank(enterpriseId)){
				Enterprise enterprise = enterpriseDao.findById(enterpriseId);
				entity.setReleaseEnterprise(enterprise);
			}else{
				throw new GlobalException("请选择任务发布企业","ACAx0001");
			}
		}
		
		// 设置任务状态
		entity.setStatus(Advertisement.STATUS_CREATE);
		// 设置剩余金额
		entity.setSurplusMoney(entity.getMoneyCount());
		
		// 停止原因和审核失败原因设为空，防止通过接口强制注入数据
		entity.setStopRemark(null);
		entity.setFailRemark(null);
		
		// 保存结果
		View view = super.add(entity, request, response);
		
		// 设置预定义提交的任务数据
		String advertisementDatasStr = entity.getAdvertisementDatasStr();
		String[] advertisementDatasArr = advertisementDatasStr.split(",");
		for(int iIndex=0;iIndex<advertisementDatasArr.length;iIndex++){
			String advertisementDataTypeId = advertisementDatasArr[iIndex].split("\\|")[1];
			String advertisementDataLabel = advertisementDatasArr[iIndex].split("\\|")[0];
			AdvertisementDataType dataType = advertisementDataTypeDao.findById(advertisementDataTypeId);
			AdvertisementData advertisementData = new AdvertisementData();
			advertisementData.setLabel(advertisementDataLabel);
			advertisementData.setType(dataType);
			advertisementData.setName(Cn2Spell.converterToSpell(advertisementDataLabel));
			advertisementData.setAdvertisement(entity);
			advertisementDataDao.save(advertisementData);
		}
		
		return view;
	}
	
	// 提交任务到审核
	@RequestMapping(value="{id}.do",method=RequestMethod.PUT,params={"status=examine"})
	public View updateToExamine(@PathVariable(value="id")String advertisementId){
		
		Advertisement advertisement = this.getBaseDao().findById(advertisementId);
		// 只有新创建的任务才可以提交到审核阶段，否则无法提交
		if(advertisement.getStatus()!=Advertisement.STATUS_CREATE){
			throw new GlobalException("任务已经提交到审核或者正在执行，无法继续提交","ACUTEx0001");
		}
		
		advertisement.setStatus(Advertisement.STATUS_EXAMINE);
		this.getBaseDao().update(advertisement);
		
		return new RestView(null);
	}
	
	// 审核通过
	@RequestMapping(value="{id}.do",method=RequestMethod.PUT,params={"status=execute"})
	public View updateToExecute(@PathVariable(value="id")String advertisementId){
		Advertisement advertisement = this.getBaseDao().findById(advertisementId);
		// 只有审核中的任务才可以进行审核，并且设置为通过，开始在平台发布，否则无法提交
		if(advertisement.getStatus()!=Advertisement.STATUS_EXAMINE){
			throw new GlobalException("任务已经审核或者正在执行，无法继续提交","ACUTEx0002");
		}
		
		// 更改状态为执行中
		advertisement.setStatus(Advertisement.STATUS_EXECUTE);
		this.getBaseDao().update(advertisement);
		
		return new RestView(null);
	}
	
	// 审核未通过
	@RequestMapping(value="{id}.do",method=RequestMethod.PUT,params={"status=fail","failRemark"})
	public View updateToFail(@PathVariable(value="id")String advertisementId,
			@RequestParam(value="failRemark")String failRemark){
		Advertisement advertisement = this.getBaseDao().findById(advertisementId);
		
		// 只有审核中的任务才可以进行审核，并且设置为未通过，否则无法提交
		if(advertisement.getStatus()!=Advertisement.STATUS_EXAMINE){
			throw new GlobalException("任务已经审核或者正在执行，无法继续提交","ACUTFx0001");
		}
		
		// 更高状态为未通过
		advertisement.setStatus(Advertisement.STATUS_FAIL);
		advertisement.setFailRemark(failRemark);
		this.getBaseDao().update(advertisement);
		
		return new RestView(null);
	}
	
	// 停止任务
	@RequestMapping(value="{id}.do",method=RequestMethod.PUT,params={"status=stop","stopRemark"})
	public View updateToStop(@PathVariable("id")String advertisementId,
			@RequestParam("stopRemark")String stopRemark){
		Advertisement advertisement = this.getBaseDao().findById(advertisementId);
		
		advertisement.setStatus(Advertisement.STATUS_STOP);
		advertisement.setStopRemark(stopRemark);
		
		this.getBaseDao().update(advertisement);
		
		return new RestView(null);
	}
	
	// 更新任务
	@Override
	public View update(Advertisement entity, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取任务信息
		Advertisement updateAdvertisement = this.getBaseDao().findById(entity.getId());
		
		// 此任务只适用于未提交审核的任务
		if(updateAdvertisement.getStatus()!=Advertisement.STATUS_CREATE){
			throw new GlobalException("任务已经提交审核或者正在执行，不可修改其信息", "ACUx0001");
		}
		
		// 剩余奖金，审核未通过备注，任务状态，任务停止备注不允许修改
		BeanUtils.copyProperties(entity, updateAdvertisement, "surplusMoney","failRemark","status","stopRemark","advertisementDatasStr");
		
		// 此修改接口只适用于未提交审核的任务，任务的剩余金额始终等于任务总奖金
		updateAdvertisement.setSurplusMoney(updateAdvertisement.getMoneyCount());
		
		// 更新广告类型
		if(!entity.getType().getId().equals(updateAdvertisement.getType().getId())){
			AdvertisementType advertisementType = advertisementTypeDao.findById(entity.getType().getId());
			updateAdvertisement.setType(advertisementType);
		}
		
		// 更新发布企业
		if(!entity.getReleaseEnterprise().getId().equals(updateAdvertisement.getReleaseEnterprise().getId())){
			Enterprise enterprise = enterpriseDao.findById(entity.getReleaseEnterprise().getId());
			updateAdvertisement.setReleaseEnterprise(enterprise);
		}
		
		this.getBaseDao().update(updateAdvertisement);
		
		// 删除原始数据
		QueryContainer qc = new QueryContainer();
		qc.addCondition("advertisement.id='"+updateAdvertisement.getId()+"'");
		List<AdvertisementData> advertisementDatas = advertisementDataDao.findByQueryContainer(qc);
		for(int iIndex=0;iIndex<advertisementDatas.size();iIndex++){
			AdvertisementData advertisementData = advertisementDatas.get(iIndex);
			advertisementDataDao.delete(advertisementData.getId());
		}
		
		// 设置预定义提交的任务数据
		String advertisementDatasStr = entity.getAdvertisementDatasStr();
		String[] advertisementDatasArr = advertisementDatasStr.split(",");
		for(int iIndex=0;iIndex<advertisementDatasArr.length;iIndex++){
			String advertisementDataTypeId = advertisementDatasArr[iIndex].split("\\|")[1];
			String advertisementDataLabel = advertisementDatasArr[iIndex].split("\\|")[0];
			AdvertisementDataType dataType = advertisementDataTypeDao.findById(advertisementDataTypeId);
			AdvertisementData advertisementData = new AdvertisementData();
			advertisementData.setLabel(advertisementDataLabel);
			advertisementData.setType(dataType);
			advertisementData.setName(Cn2Spell.converterToSpell(advertisementDataLabel));
			advertisementData.setAdvertisement(updateAdvertisement);
			advertisementDataDao.save(advertisementData);
		}
		
		return new RestView(null,null);
	}
	
	// 获取所有已上线的任务信息
	@RequestMapping(value="exec.do",method=RequestMethod.GET)
	public View getExecAdvertisements(Advertisement entity, QueryContainer qc,HttpServletRequest request,HttpServletResponse response) throws Exception{
		qc.addCondition("(status ="+Advertisement.STATUS_EXECUTE+" AND startTime<="+System.currentTimeMillis()+" AND endTime>="+System.currentTimeMillis()+") OR status="+Advertisement.STATUS_COMPLETE+")");
		return this.page(entity, qc, request, response);
	}
	
	@Override
	public View page(Advertisement entity, QueryContainer qc, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// 企业id过滤
		if(entity.getReleaseEnterprise()!=null && StringUtils.isNotBlank(entity.getReleaseEnterprise().getId())){
			qc.addCondition("releaseEnterprise.id='"+entity.getReleaseEnterprise().getId()+"'");
		}
		// 状态过滤
		if(entity.getStatus()!=null){
			qc.addCondition("status="+entity.getStatus());
		}
		
		PagerResultContainer prc = this.getBaseDao().findPager(qc);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Advertisement> advertisements = (List)prc.getResult();
		for(Advertisement advertisement : advertisements){
			advertisement = this.getBaseDao().findById(advertisement.getId());
			if(advertisement.getStatus() == Advertisement.STATUS_EXECUTE){
				// 接受任务的人数
				qc = new QueryContainer();
				qc.addCondition("advertisement.id='"+advertisement.getId()+"'");
				qc.addCondition("status="+UserAdvertisement.STATUS_STARTED);
				long startCount = userAdvertisementDao.countByQueryContainer(qc);
				
				// 审核中的人数
				qc = new QueryContainer();
				qc.addCondition("advertisement.id='"+advertisement.getId()+"'");
				qc.addCondition("status="+UserAdvertisement.STATUS_COMPLETE);
				long completeCount = userAdvertisementDao.countByQueryContainer(qc);
				
				// 已完成任务的人数
				qc = new QueryContainer();
				qc.addCondition("advertisement.id='"+advertisement.getId()+"'");
				qc.addCondition("status="+UserAdvertisement.STATUS_END);
				long endCount = userAdvertisementDao.countByQueryContainer(qc);
				
				advertisement.setStartCount(startCount);
				advertisement.setCompleteCount(completeCount);
				advertisement.setEndCount(endCount);
			}
		}
		
		return new RestView(prc);
	}
	
}
