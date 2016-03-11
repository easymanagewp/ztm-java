package cn.weiyunmei.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 提现记录
 * @author wangpeng
 *
 */
@Entity
@Table(name="user_cashing_log")
public class UserCashingLog extends BaseEntity {

	private long money;					// 提现金额
	private String remark;				// 备注
	private String orderNo;				// 提现流水号
	private String wechatOrderNo;		// 微信支付订单号
	private User user;					// 提现用户
	
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	
	@Column(length=45)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="order_no",length=45)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name="wechat_order_no",length=200)
	public String getWechatOrderNo() {
		return wechatOrderNo;
	}
	public void setWechatOrderNo(String wechatOrderNo) {
		this.wechatOrderNo = wechatOrderNo;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
