package com.kh.yapx3.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * preHandle(전처리): DispatcherServlet이 핸들러메소드 호출전
 * postHandle(후처리): 핸들러메소드가 DispatcherServlet으로 리턴되는 타이밍
 * afterCompletion(뷰단처리후): view단처리가 완료된 후
 * 
 *
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler)
			throws Exception {
		logger.debug("======================= START ========================");
		logger.debug(request.getRequestURI());
		logger.debug("------------------------------------------------------");
		//super.preHandle() => 항상 true
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, 
						   HttpServletResponse response, 
						   Object handler,
						   ModelAndView modelAndView) 
							throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
		logger.debug("------------------------ VIEW -----------------------");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, 
								HttpServletResponse response, 
								Object handler, 
								Exception ex)
			throws Exception {
		
		logger.debug("_________________________ END ________________________");
		
		super.afterCompletion(request, response, handler, ex);
		
		
	}
	
	
}
