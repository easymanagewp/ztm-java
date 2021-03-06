package cn.weiyunmei.interceptor.exception;

/**
 * 权限异常
 * @author wangpeng
 *
 */
public class AuthorityException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;
	
	public AuthorityException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
