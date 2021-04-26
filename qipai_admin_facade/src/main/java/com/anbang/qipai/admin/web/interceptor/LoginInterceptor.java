package com.anbang.qipai.admin.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.anbang.qipai.admin.cqrs.c.service.AdminAuthService;

/**
 * 管理员登录拦截器
 * 
 * @author 林少聪 2018.5.31
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private AdminAuthService adminAuthService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getParameter("token");
		if (token != null) {
			String adminId = adminAuthService.getAdminIdBySessionId(token);
			if (adminId != null) {
				return true;
			}
		}
		// response.sendRedirect("http://scs.3cscy.com/dist/index.html");
		return false;
        //return true;
	}

}
