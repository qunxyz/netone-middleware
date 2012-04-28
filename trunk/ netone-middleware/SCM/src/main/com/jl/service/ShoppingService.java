/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 *
 */
public interface ShoppingService {
	/**
	 * 分页查询信息
	 * 
	 * @param request
	 * @param response
	 */
	public void queryShoppingForPage(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 加载信息
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void loadShopping(HttpServletRequest request, HttpServletResponse response);
	
	void audit(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 保存内容及明细
	 * @param request
	 * @param response
	 */
	void saveShoppingAndDetail(HttpServletRequest request,HttpServletResponse response);
	
	void delete(HttpServletRequest request,HttpServletResponse response);
	
	void queryShoppingDetail(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 获取工作流信息，传至SELECT
	 * @param request
	 * @param response
	 */
	void loadWFInfoToSelect(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 处理回退事件
	 * @param request
	 * @param response
	 */
	void rollBackEvent(HttpServletRequest request,HttpServletResponse response);

}
