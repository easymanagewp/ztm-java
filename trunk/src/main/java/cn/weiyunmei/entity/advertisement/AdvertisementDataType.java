package cn.weiyunmei.entity.advertisement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 广告提交数据，数据类型
 * @author wangpeng
 *
 */
@Entity
@Table(name="advertisement_data_type")
public class AdvertisementDataType extends BaseEntity {
	private String name;
	private String code;
	@Column(length=45)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=45)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
