package com.github.losemy.sso.client.util;

import com.github.losemy.sso.client.Constant;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie操作工具
 * 
 * @author Joe
 */
public class CookieUtils {

	private CookieUtils() {
	}

	/**
	 * 按名称获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || "".equals(name)) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

	/**
	 * 清空cookie
	 * @param request
	 */
	public static void invalidate(HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, Constant.SSO_TOKEN_NAME, null);
	}
}
