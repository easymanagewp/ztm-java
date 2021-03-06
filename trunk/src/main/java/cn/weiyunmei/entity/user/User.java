package cn.weiyunmei.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 自媒体
 * @author wangpeng
 *
 */
@Entity
@Table(name="user")
public class User extends BaseEntity {
	
	private String icon;			// 头像
	private String name;			// 姓名
	private String sex;				// 性别
	private String mobile;			// 手机号
	private String wechatId;		// 微信号openId
	private String wechatName;		// 微信名称
	private long birthday;			// 生日
	private String address;			// 地址
	private User parent;			// 邀请者
	private long totalMoney;		// 累计收益
	private long money;				// 账户余额
	private String password;		// 登录密码
	private String code;			// 用户推荐码
	
	@Column(length=200)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(length=45)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=5)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(length=20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name="wechat_id",length=45)
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String weichatId) {
		this.wechatId = weichatId;
	}
	
	@Column(name="wechat_name",length=45)
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String weichatName) {
		this.wechatName = weichatName;
	}
	public long getBirthday() {
		return birthday;
	}
	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}
	@Column(length=200)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}
	
	@Column(name="total_money")
	public long getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	
	// 密码不进行输出
	@JSONField(serialize=false)
	@Column(length=45)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
}
