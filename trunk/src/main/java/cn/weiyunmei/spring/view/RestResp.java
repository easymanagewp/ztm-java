package cn.weiyunmei.spring.view;

public class RestResp {
	public static final Integer FAIL = -1;
	public static final Integer SUCCESS = 0;
	public static final Integer EMPTY = 1;
	
	private Integer status = RestResp.FAIL;
	private String message;
	private Object result;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
