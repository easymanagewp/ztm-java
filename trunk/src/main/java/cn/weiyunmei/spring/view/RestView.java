package cn.weiyunmei.spring.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.View;

import cn.weiyunmei.interceptor.exception.AuthorityException;
import cn.weiyunmei.utils.JSONUtils;

public class RestView implements View {
	private Logger log = Logger.getLogger(RestView.class);

	private Object responseValue;
	private Exception exception;
	
	public RestView(Object responseValue){
		this(responseValue,null);
	}
	
	public RestView(Object responseValue,Exception e){
		this.responseValue = responseValue;
		this.exception = e;
	}
	
	@Override
	public String getContentType() {
		return "application/json;charset=utf-8";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResp resp = null;
		if( null == responseValue && null == this.exception){
			resp = new RestResp();
			resp.setStatus(RestResp.SUCCESS);
			resp.setMessage("操作成功");
		}else if( null == responseValue && null != this.exception){
			resp = handlerException();
		}else if(RestResp.class.isAssignableFrom(responseValue.getClass())){
			resp = (RestResp) responseValue;
		}else if( null != responseValue && null == this.exception){
			resp = new RestResp();
			resp.setStatus(RestResp.SUCCESS);
			resp.setMessage("操作成功");
			resp.setResult(this.responseValue);
		}
		String value = JSONUtils.toJSON(resp);
		response.setContentType(getContentType());
		PrintWriter pw = response.getWriter();
		String callback = request.getParameter("callback");
		if(StringUtils.isNotBlank(callback)){
			value = callback +"(" + value + ")";
		}
		pw.write(value);
	}

	private RestResp handlerException() {
		RestResp resp = new RestResp();
		if(AuthorityException.class.isAssignableFrom(this.exception.getClass())){
			AuthorityException authorityException = (AuthorityException) this.exception;
			int errCode = authorityException.getCode();
			resp.setMessage("错误码：["+errCode+"]，"+authorityException.getMessage());
		}else{
			log.error("不可预知的异常",this.exception);
			resp.setMessage("操作失败");
		}
		return resp;
	}

}
