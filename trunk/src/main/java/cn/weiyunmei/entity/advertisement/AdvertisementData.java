package cn.weiyunmei.entity.advertisement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 广告任务提交数据定义
 * @author wangpeng
 *
 */
@Entity
@Table(name="advertisement_data")
public class AdvertisementData extends BaseEntity {
	private String label;					// 数据标签
	private String name;					// 数据名称
	private AdvertisementDataType type;		// 数据类型
	private Advertisement advertisement;	// 所属任务
	
	@Column(length=10)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Column(length=200)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name="type")
	public AdvertisementDataType getType() {
		return type;
	}
	public void setType(AdvertisementDataType type) {
		this.type = type;
	}
	
	@ManyToOne
	@JoinColumn(name="advertisement_id")
	@JSONField(serialize=false)
	public Advertisement getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}
	
	
}
