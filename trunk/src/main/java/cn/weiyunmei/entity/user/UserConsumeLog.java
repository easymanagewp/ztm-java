package cn.weiyunmei.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 用户收益记录
 * @author wangpeng
 *
 */
@Entity
@Table(name="user_consume_log")
public class UserConsumeLog extends BaseEntity {
	
	public static final int TYPE_FANS = 1;
	public static final int TYPE_ADVERTISEMENT = 0;

	private long money;								// 收益金额
	private String remark;							// 收益说明
	private User user;								// 收益人
	private UserAdvertisement userAdvertisement;	// 收益广告
	private int type;								// 收益类型 0：广告收入，1：粉丝奖励
	private User fans;								// 贡献粉丝
	
	
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	
	@Column(name="remark",length=45)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="user_advertisement_id")
	public UserAdvertisement getUserAdvertisement() {
		return userAdvertisement;
	}
	public void setUserAdvertisement(UserAdvertisement userAdvertisement) {
		this.userAdvertisement = userAdvertisement;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@ManyToOne
	@JoinColumn(name="fans_id")
	public User getFans() {
		return fans;
	}
	public void setFans(User fans) {
		this.fans = fans;
	}
	
	
	
	
}
