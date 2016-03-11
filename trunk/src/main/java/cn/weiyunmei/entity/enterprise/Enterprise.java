package cn.weiyunmei.entity.enterprise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 企业(广告主)信息
 * @author wangpeng
 */
@Entity
@Table(name="enterprise")
public class Enterprise extends BaseEntity {
	
	private String loginName;		// 登录名称
	private String password;		// 登录密码
	private String name;			// 企业名称
	private String email;			// 企业邮箱
	private String contacts;		// 联系人
	private String phone;			// 联系电话
	private String paymentCard;		// 银行卡信息
	private String alipay;			// 支付宝账户
	private String businessLicense;	// 营业执照
	private String identityCardFace;// 身份证正面
	private String identityCardBack;// 身份证背面
	private long money;				// 账户余额
	private long frozenMoney;		// 冻结金额
	private long consumeMoney;		// 累计消费金额
	
	@Column(length=45,name="login_name")
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(length=45)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(length=45)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=45)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length=45)
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Column(length=18)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(length=45,name="payment_card")
	public String getPaymentCard() {
		return paymentCard;
	}
	public void setPaymentCard(String paymentCard) {
		this.paymentCard = paymentCard;
	}
	
	@Column(length=45,name="alipay")
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	
	@Column(length=200,name="business_lincense")
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	
	@Column(length=200,name="identity_card_face")
	public String getIdentityCardFace() {
		return identityCardFace;
	}
	public void setIdentityCardFace(String identityCardFace) {
		this.identityCardFace = identityCardFace;
	}
	
	@Column(length=200,name="identity_card_back")
	public String getIdentityCardBack() {
		return identityCardBack;
	}
	public void setIdentityCardBack(String identityCardBack) {
		this.identityCardBack = identityCardBack;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	@Column(name="frozen_money")
	public long getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(long frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	@Column(name="consume_money")
	public long getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(long consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	
	
	
	
}
