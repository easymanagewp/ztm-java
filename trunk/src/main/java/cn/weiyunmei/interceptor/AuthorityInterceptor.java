package cn.weiyunmei.interceptor;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.weiyunmei.interceptor.exception.AuthorityException;

/**
 * 对所有的接口进行验证，验证请求方的权限key是否可用
 * @author wangpeng
 *
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter{
	
	/*
	 * 所有客户端的id和key
	 */
	private final Properties clientKeys = new Properties();
	
	private static final String CLIENT_ID_NAME = "_clientId_";
	private static final String CLIENT_KEY_NAME = "_clientKey_";
	
	/**
	 * 加载所有客户端的id和key，以id=key的方式存放到clientKeys内
	 * @throws IOException
	 */
	public AuthorityInterceptor() throws IOException {
		try {
			clientKeys.load(AuthorityInterceptor.class.getClassLoader().getResourceAsStream("keys.properties"));
		} catch (IOException e) {
			throw new IOException("读取客户端id失败",e);
		}
	}
	
	public Properties getClientKeys() {
		return clientKeys;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String clientId = request.getParameter(CLIENT_ID_NAME);
		String clientKey = request.getParameter(CLIENT_KEY_NAME);
		
		/*
		 * 验证clientId和clientKey
		 */
		/*====================== 参数必须存在 =======================*/
		if(StringUtils.isBlank(clientId) || StringUtils.isBlank(clientKey)) {
			throw new AuthorityException(1, "请检查请求接口是否存在"+CLIENT_ID_NAME+"或"+CLIENT_KEY_NAME);
		}
		
		/*======================  ClientId必须合法 ================ */
		String storeClientKey = clientKeys.getProperty(clientId);
		if(StringUtils.isBlank(storeClientKey)){
			throw new AuthorityException(2, CLIENT_ID_NAME+"不存在");
		}
		
		/*======================  ClientKey必须合法 ============== */
		if(!StringUtils.equalsIgnoreCase(clientKey, storeClientKey)){
			throw new AuthorityException(3, "非法的"+CLIENT_KEY_NAME);
		}
		
		return super.preHandle(request, response, handler);
	}
	
}
