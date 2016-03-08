package cn.weiyunmei.entity.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.entity.advertisement.Advertisement;
import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 用户广告信息 - 自媒体所接受的广告信息
 * @author wangpeng
 */
@Entity
@Table(name="user_advertisement")
public class UserAdvertisement extends BaseEntity {
	
	private Advertisement advertisement;	// 广告主体
	private int status;						// 状态
	private User user;						// 所属用户
	
	@ManyToOne
	@JoinColumn(name="advertisement_id")
	public Advertisement getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name="user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
