/**
 * ��˵��
 * @author ���� 
 * @version  1.0 
   @time ����ʱ�䣺
 * 
 */
package com.jl.common;

import javax.servlet.http.HttpServletRequest;

public class JspUtil {

	private static String BASEPATH = ""; // web��Ŀ¼

	public static String getBasePath(HttpServletRequest request) {
		BASEPATH = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		return BASEPATH;
	}
}
