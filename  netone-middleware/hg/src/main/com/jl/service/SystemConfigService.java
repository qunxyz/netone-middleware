package com.jl.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenlx created by 2010年12月2日
 */
public interface SystemConfigService {

	/**
	 * 查询 系统配置信息
	 * 
	 * @param request
	 * @param response
	 */
	void queryInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 新增/更新 系统配置信息
	 * 
	 * @param request
	 * @param response
	 */
	void saveOrUpdate(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 加载 系统配制信息
	 * 
	 * @param request
	 * @param response
	 */
	void loadInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 删除 系统配制信息
	 * 
	 * @param request
	 * @param response
	 */
	void deleteInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 加载 配置信息至下拉列表
	 * 
	 * @param request
	 * @param response
	 */
	void loadSystemConfigToSelect(HttpServletRequest request,HttpServletResponse response);

}
