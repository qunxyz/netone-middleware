/**
 * 类说明
 * @author 丘桂昌 
 * @version  1.0 
   @time 创建时间：
 * 
 */
package com.jl.common;

import javax.servlet.http.HttpServletRequest;

public class JspUtil {

	private static String BASEPATH = ""; // web根目录

	public static String getBasePath(HttpServletRequest request) {
		BASEPATH = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		return BASEPATH;
	}
}
