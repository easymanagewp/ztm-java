package cn.weiyunmei.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.entity.advertisement.AdvertisementData;
import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 用户提交的任务数据
 * @author wangpeng
 * 
 */
@Entity
@Table(name="user_advertisement_data")
public class UserAdvertisementData extends BaseEntity {
	
	private AdvertisementData advertisementData;	// 提交的数据Key
	private UserAdvertisement userAdvertisement;	// 所属任务
	private String value;							// 提交的数据值
	
	@ManyToOne
	@JoinColumn(name="advertisement_data_id")
	public AdvertisementData getAdvertisementData() {
		return advertisementData;
	}
	public void setAdvertisementData(AdvertisementData advertisementData) {
		this.advertisementData = advertisementData;
	}
	
	@ManyToOne
	@JoinColumn(name="user_advertisement_id")
	public UserAdvertisement getUserAdvertisement() {
		return userAdvertisement;
	}
	public void setUserAdvertisement(UserAdvertisement userAdvertisement) {
		this.userAdvertisement = userAdvertisement;
	}
	
	@Column(length=100)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
