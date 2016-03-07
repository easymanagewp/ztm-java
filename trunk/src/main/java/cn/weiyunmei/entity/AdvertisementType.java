package cn.weiyunmei.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 广告类型
 * @author wangpeng
 */
@Entity
@Table(name="advertisement_type")
public class AdvertisementType extends BaseEntity {
	private String name;		// 广告类型名称
	private String remark;		// 广告类型说明
	
	@Column(length=45)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
