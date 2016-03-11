package cn.weiyunmei.entity.enterprise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.entity.advertisement.Advertisement;
import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 企业扣款记录
 * @author wangpeng
 *
 */
@Entity
@Table(name="enterprise_consume_log")
public class EnterpriseConsumeLog extends BaseEntity {
	
	private long money;						// 扣款金额
	private String remark;					// 备注
	private Enterprise enterprise;			// 扣款企业
	private Advertisement advertisement;	// 扣款原因（广告）
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
	
	@ManyToOne
	@JoinColumn(name="enterprise_id")
	public Enterprise getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	@ManyToOne
	@JoinColumn(name="advertisement_id")
	public Advertisement getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}
	
	
	
}
