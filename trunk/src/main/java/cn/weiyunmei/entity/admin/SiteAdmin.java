package cn.weiyunmei.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.weiyunmei.support.entity.BaseEntity;

/**
 * 网站管理员
 * @author wangpeng
 */
@Entity
@Table(name="site_admin")
public class SiteAdmin extends BaseEntity {

	private String loginName;	// 登录名
	private String password;	// 密码
	
	@Column(length=45,nullable=false,name="login_name")
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(length=45,nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
