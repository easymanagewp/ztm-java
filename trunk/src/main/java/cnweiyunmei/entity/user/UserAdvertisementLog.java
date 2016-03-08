package cn.weiyunmei.entity.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 用户任务日志 - 执行任务的日志
 * @author wangpeng
 */
@Entity
@Table(name="user_advertisement_log")
public class UserAdvertisementLog extends BaseEntity {
	
	private UserAdvertisement userAdvertisement;	// 用户接受的广告
	private int status;								// 当前执行状态
	private String remark;							// 备注
	
	@ManyToOne
	@JoinColumn(name="user_advertisement_id")
	public UserAdvertisement getUserAdvertisement() {
		return userAdvertisement;
	}
	public void setUserAdvertisement(UserAdvertisement userAdvertisement) {
		this.userAdvertisement = userAdvertisement;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
