/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 30, 2010 create by Don
 * @history
 */
public interface LogService {

	/**
	 * ��ѯ��־
	 * 
	 * @param request
	 * @param response
	 */
	void queryLog(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ��ѯ��־2 jquery datagrid����Ӧ��
	 * 
	 * @param request
	 * @param response
	 */
	void queryLog2(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ��ѯ��־-����
	 * 
	 * @param request
	 * @param response
	 */
	void queryDailyLog(HttpServletRequest request, HttpServletResponse response);
}
