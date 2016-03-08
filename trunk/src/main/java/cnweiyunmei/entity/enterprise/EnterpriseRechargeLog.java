package cn.weiyunmei.entity.enterprise;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 企业充值记录
 * @author wangpeng
 *
 */
@Entity
@Table(name="enterprise_recharge_log")
public class EnterpriseRechargeLog extends BaseEntity {
	
	private long money;						// 充值金额
	private String remark;					// 备注
	private Enterprise enterprise;			// 扣款企业
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
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
	
	
	
}
