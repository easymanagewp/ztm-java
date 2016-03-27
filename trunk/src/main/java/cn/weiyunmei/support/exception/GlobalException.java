package cn.weiyunmei.support.exception;

public class GlobalException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;	// 错误消息
	private String code;	// 错误码
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public GlobalException(String message, String code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	
	
}
