package cn.wym.interceptor;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class AuthorityInterceptorTestCase {
	
	@Test
	public void initTestCase() throws IOException{
		AuthorityInterceptor authorityInterceptor = new AuthorityInterceptor();
		Assert.assertEquals(authorityInterceptor.getClientKeys().keySet().size(), 1);
	}
	
	
	
}
