package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ClientService {
	/**
	 * 保存或修改一个信息
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void saveOrUpdateClientInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 根据用户编号返回对应客户信息
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void loadClientInfoById(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 根据查询条件查询客户信息
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void queryClientInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 获取客户信息 回车选择事件及初始加载组织机构信息
	 * 
	 * @param request
	 * @param response
	 */
	void getClientInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 查找客户级别树
	 * 
	 * @param request
	 * @param response
	 */
	void findClientLevelTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查找客户级别的客户关联信息
	 * 
	 * @author Don 2010-1-6
	 * @param request
	 * @param response
	 */
	void loadClientLevelOfRelation(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 批量设置客户级别
	 * 
	 * @author Don 2010-1-6
	 * @param request
	 * @param response
	 */
	void updateClientLevel(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 客户级别节点管理【添加、修改、删除】
	 * 
	 * @author Don 2010-1-6
	 * @param request
	 * @param response
	 */
	void saveOrUpdateOrDeleteClientLevelNode(HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 经销商基本信息导出
	 * 			【EXCEL\HTML\PDF\CVS\】
	 * @param request
	 * @param response
	 */
	void export(HttpServletRequest request, HttpServletResponse response);
}
