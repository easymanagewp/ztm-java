package cn.weiyunmei.entity.advertisement;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	public static final int STATUS_CREATE = 10;		// 任务 - 创建 - 已保存
	public static final int STATUS_EXAMINE = 15;	// 任务 - 提交 - 审核中
	public static final int STATUS_EXECUTE = 20;	// 任务 - 审核 - 执行中
	public static final int STATUS_COMPLETE = 30;	// 任务 - 执行 - 已完成
	public static final int STATUS_FAIL = 40;		// 任务 - 审核 - 审核未通过
	public static final int STATUS_STOP = 50;		// 任务 - 完结 - 强制结束
	
	private String title;					// 标题
	private String icon;					// 广告图标
	private String simpleDescript;			// 简要描述
	private AdvertisementType type;			// 广告类型
	private long moneyCount;				// 总奖金
	private long surplusMoney;				// 剩余奖金
	private long money;						// 单任务奖励
	private int companyProportion;			// 公司分成比例
	private int userProportion;				// 用户分成比例
	private long startTime;					// 任务开始时间
	private long endTime;					// 任务结束时间
	private Enterprise releaseEnterprise;	// 任务发布企业
	private String executionFlow;			// 任务执行流程
	private String details;					// 任务详情
	private String executionAddress;		// 任务执行地址
	private int isExamine = 0;				// 是否人工审核
	private Integer status;					// 任务状态
	private String failRemark;				// 审核未通过原因
	private String stopRemark;				// 任务停止原因
	
	// ------------- 非持久化字段
	private long startCount;				// 接受任务人数
	private long completeCount;				// 已提交任务人数
	private long endCount;					// 已完成任务人数
	private String advertisementDatasStr;	// 任务提交数据字符串
	
	
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
	
	@Column(length=200,name="simple_descript")
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
	
	@Column(name="money_count")
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
	
	@Column(name="company_proportion")
	public int getCompanyProportion() {
		return companyProportion;
	}
	public void setCompanyProportion(int companyProportion) {
		this.companyProportion = companyProportion;
	}
	
	@Column(name="user_proportion")
	public int getUserProportion() {
		return userProportion;
	}
	public void setUserProportion(int userProportion) {
		this.userProportion = userProportion;
	}
	
	@Column(name="start_time")
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="end_time")
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
	@Column(name="execution_flow")
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
	
	@Column(length=200,name="execution_address")
	public String getExecutionAddress() {
		return executionAddress;
	}
	public void setExecutionAddress(String executionAddress) {
		this.executionAddress = executionAddress;
	}
	
	@Column(name="is_examine")
	public int getIsExamine() {
		return isExamine;
	}
	public void setIsExamine(int isExamine) {
		this.isExamine = isExamine;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@OneToMany(mappedBy="advertisement")
	public List<AdvertisementData> getAdvertisementDatas() {
		return advertisementDatas;
	}
	public void setAdvertisementDatas(List<AdvertisementData> advertisementDatas) {
		this.advertisementDatas = advertisementDatas;
	}
	
	@Column(name="surplus_money")
	public long getSurplusMoney() {
		return surplusMoney;
	}
	public void setSurplusMoney(long surplusMoney) {
		this.surplusMoney = surplusMoney;
	}
	
	@Column(name="fail_remark")
	public String getFailRemark() {
		return failRemark;
	}
	public void setFailRemark(String failRemark) {
		this.failRemark = failRemark;
	}
	
	@Column(name="stop_remark")
	public String getStopRemark() {
		return stopRemark;
	}
	public void setStopRemark(String stopRemark) {
		this.stopRemark = stopRemark;
	}
	
	// --------------- 不持久化属性
	@Transient
	public long getStartCount() {
		return startCount;
	}
	public void setStartCount(long startCount) {
		this.startCount = startCount;
	}
	
	@Transient
	public long getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(long completeCount) {
		this.completeCount = completeCount;
	}
	
	@Transient
	public long getEndCount() {
		return endCount;
	}
	public void setEndCount(long endCount) {
		this.endCount = endCount;
	}
	
	@Transient
	public String getAdvertisementDatasStr() {
		return advertisementDatasStr;
	}
	public void setAdvertisementDatasStr(String advertisementDatasStr) {
		this.advertisementDatasStr = advertisementDatasStr;
	}
	
	
	
	
	
}
