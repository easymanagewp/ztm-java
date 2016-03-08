package cn.weiyunmei.entity.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private String weichatId;		// 微信号openId
	private String weichatName;		// 微信名称
	private long birthday;			// 生日
	private String address;			// 地址
	private User parent;			// 邀请者
	private long totalMoney;		// 累计收益
	private long money;				// 账户余额
	private String password;		// 登录密码
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWeichatId() {
		return weichatId;
	}
	public void setWeichatId(String weichatId) {
		this.weichatId = weichatId;
	}
	public String getWeichatName() {
		return weichatName;
	}
	public void setWeichatName(String weichatName) {
		this.weichatName = weichatName;
	}
	public long getBirthday() {
		return birthday;
	}
	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
