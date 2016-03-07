package cn.weiyunmei.spring;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cn.weiyunmei.spring.view.RestView;

public class MappingExceptionResolver extends SimpleMappingExceptionResolver  {

	 @Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e) {
		ModelAndView mav = new ModelAndView();
		
		mav.setView(new RestView(null,e));
		
		return mav;
	}

}
