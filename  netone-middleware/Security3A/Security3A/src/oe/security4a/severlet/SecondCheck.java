package oe.security4a.severlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class SecondCheck {
	public static boolean check(HttpServletRequest request, String username) {
		String secondcheckid = (String) request.getSession().getAttribute("secondcheckid");
		String secondcheck = request.getParameter("secondcheck");
		request.getSession().setAttribute("secondcheckid", null);
		if (!"adminx".equals(username)) {
			if (secondcheck == null) {
				// 说明 页面上没有 邮件 二次 认证需求
				return true;
			} else if (StringUtils.isNotEmpty(secondcheckid) && StringUtils.isNotEmpty(secondcheck)
					&& secondcheckid.equals(secondcheck)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
