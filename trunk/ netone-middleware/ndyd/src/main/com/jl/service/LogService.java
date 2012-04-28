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
	 * 查询日志
	 * 
	 * @param request
	 * @param response
	 */
	void queryLog(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 查询日志2 jquery datagrid体验应用
	 * 
	 * @param request
	 * @param response
	 */
	void queryLog2(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 查询日志-按天
	 * 
	 * @param request
	 * @param response
	 */
	void queryDailyLog(HttpServletRequest request, HttpServletResponse response);
}
