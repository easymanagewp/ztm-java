package cn.weiyunmei.entity.advertisement;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.weiyunmei.entity.enterprise.Enterprise;
import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 广告信息
 * @author wangpeng
 *
 */
@Entity
@Table(name="advertisement")
public class Advertisement extends BaseEntity {
	
	private String title;					// 标题
	private String icon;					// 广告图标
	private String simpleDescript;			// 简要描述
	private AdvertisementType type;			// 广告类型
	private long moneyCount;				// 总奖金
	private long money;						// 单任务奖励
	private int companyProportion;			// 公司分成比例
	private int userProportion;				// 用户分成比例
	private long startTime;					// 任务开始时间
	private long endTime;					// 任务结束时间
	private Enterprise releaseEnterprise;	// 任务发布企业
	private String executionFlow;			// 任务执行流程
	private String details;					// 任务详情
	private String executionAddress;		// 任务执行地址
	private int isExamine;					// 是否人工审核
	private int status;						// 任务状态
	
	private List<AdvertisementData> advertisementDatas;	// 任务相关数据
	
	@Column(length=200)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(length=200)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(length=200)
	public String getSimpleDescript() {
		return simpleDescript;
	}
	public void setSimpleDescript(String simpleDescript) {
		this.simpleDescript = simpleDescript;
	}
	
	@ManyToOne
	@JoinColumn(name="type")
	public AdvertisementType getType() {
		return type;
	}
	public void setType(AdvertisementType type) {
		this.type = type;
	}
	
	public long getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(long moneyCount) {
		this.moneyCount = moneyCount;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	
	public int getCompanyProportion() {
		return companyProportion;
	}
	public void setCompanyProportion(int companyProportion) {
		this.companyProportion = companyProportion;
	}
	public int getUserProportion() {
		return userProportion;
	}
	public void setUserProportion(int userProportion) {
		this.userProportion = userProportion;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	@ManyToOne
	@JoinColumn(name="release_enterprise")
	public Enterprise getReleaseEnterprise() {
		return releaseEnterprise;
	}
	public void setReleaseEnterprise(Enterprise releaseEnterprise) {
		this.releaseEnterprise = releaseEnterprise;
	}
	
	@Lob
	public String getExecutionFlow() {
		return executionFlow;
	}
	public void setExecutionFlow(String executionFlow) {
		this.executionFlow = executionFlow;
	}
	@Lob
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Column(length=200)
	public String getExecutionAddress() {
		return executionAddress;
	}
	public void setExecutionAddress(String executionAddress) {
		this.executionAddress = executionAddress;
	}
	
	public int getIsExamine() {
		return isExamine;
	}
	public void setIsExamine(int isExamine) {
		this.isExamine = isExamine;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@OneToMany(mappedBy="advertisement")
	public List<AdvertisementData> getAdvertisementDatas() {
		return advertisementDatas;
	}
	public void setAdvertisementDatas(List<AdvertisementData> advertisementDatas) {
		this.advertisementDatas = advertisementDatas;
	}
	
	
	
	
}
