package cn.wym.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.wym.interceptor.exception.AuthorityException;

public class AuthorityInterceptorTestCase {
	
	private AuthorityInterceptor authorityInterceptor ;
	
	/**
	 * 初始化拦截器测试
	 */
	@Before
	public void initTestCase() throws IOException{
		authorityInterceptor = new AuthorityInterceptor();
		Assert.assertEquals(authorityInterceptor.getClientKeys().keySet().size(), 1);
	}
	
	/**
	 * 拦截器方法测试
	 */
	@Test
	public void preHandlerTestCase() throws Exception{
		/*
		 * clientkey和clientid都正确，执行preHandle返回true
		 */
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getParameter("_clientId_")).andReturn("SNDH-WWKH-LKNG-WWHY");
		EasyMock.expect(request.getParameter("_clientKey_")).andReturn("ASDHUSCBAMSNJKHSDJKASJK+1/SDAHJDKASJ");
		
		EasyMock.replay(request);
		
		Assert.assertTrue(authorityInterceptor.preHandle(request, null, null));
		
		/*
		 * clientId不存在，抛出 AuthorityException 异常，错误码为1
		 */
		request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getParameter("_clientId_")).andReturn(null);
		EasyMock.expect(request.getParameter("_clientKey_")).andReturn("ASDHUSCBAMSNJKHSDJKASJK+1/SDAHJDKASJ");
		EasyMock.replay(request);
		try{
			authorityInterceptor.preHandle(request, null, null);
		}catch(AuthorityException exception){
			Assert.assertEquals(exception.getCode(), 1);
		}
		
		/*
		 * clientKey不存在，抛出 AuthorityException 异常，错误码为1
		 */
		request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getParameter("_clientId_")).andReturn("SNDH-WWKH-LKNG-WWHY");
		EasyMock.expect(request.getParameter("_clientKey_")).andReturn(null);
		EasyMock.replay(request);
		try{
			authorityInterceptor.preHandle(request, null, null);
		}catch(AuthorityException exception){
			Assert.assertEquals(exception.getCode(), 1);
		}
		
		/*
		 * clientId错误，抛出 AuthorityException 异常，错误码为2
		 */
		request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getParameter("_clientId_")).andReturn("xxx");
		EasyMock.expect(request.getParameter("_clientKey_")).andReturn("ASDHUSCBAMSNJKHSDJKASJK+1/SDAHJDKASJ");
		EasyMock.replay(request);
		try{
			authorityInterceptor.preHandle(request, null, null);
		}catch(AuthorityException exception){
			Assert.assertEquals(exception.getCode(), 2);
		}
		
		/*
		 * clientKey错误，抛出 AuthorityException 异常，错误码为3
		 */
		request = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(request.getParameter("_clientId_")).andReturn("SNDH-WWKH-LKNG-WWHY");
		EasyMock.expect(request.getParameter("_clientKey_")).andReturn("xxx");
		EasyMock.replay(request);
		try{
			authorityInterceptor.preHandle(request, null, null);
		}catch(AuthorityException exception){
			Assert.assertEquals(exception.getCode(), 3);
		}
		
		
	}
	
}
