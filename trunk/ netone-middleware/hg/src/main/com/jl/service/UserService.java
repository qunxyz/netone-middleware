/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户业务管理接口
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24 下午02:47:31
 * @history
 */
public interface UserService {

	/**
	 * 查询集合数据，结果以分页形式展现
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void queryInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 查询集合数据，结果以分页形式展现
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void queryInfoX(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 保存或修改一个信息
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void saveOrUpdate(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 加载数据信息
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void loadInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 删除数据（批量）<br>
	 * 备注：通过Ajax提交要删除的主键集合，构造实体集合，并把这个参数传入到DAO进行删除
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	void deleteInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 同步K3职员到用户表中
	 * 
	 * @param request
	 * @param response
	 */
	void syncUserFromK3(HttpServletRequest request, HttpServletResponse response);

}
