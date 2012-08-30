/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 部门/公司
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-8 上午11:35:03
 * @history
 */
public interface DepartmentService {

	/**
	 * 查询部门/公司
	 * 
	 * @param request
	 * @param response
	 */
	public void queryDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 保存或更新部门/公司
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 取得一个部门/公司
	 * 
	 * @param request
	 * @param response
	 */
	public void getDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 删除部门/公司
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询部门/公司树
	 * 
	 * @param request
	 * @param response
	 */
	public void findDepartmentTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询部门/公司树X
	 * 
	 * @param request
	 * @param response
	 */
	public void findDepartmentTreeX(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导出部门/公司信息
	 * 
	 * @param request
	 * @param response
	 */
	public void exportDepartmentInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导入部门/公司信息更新
	 * 
	 * @param request
	 * @param response
	 */
	public void importDepartmentInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 判断业务类型
	 * 
	 * @param id
	 *            目录ID departmentId
	 * @return
	 */
	public String checkBussType(String id);

	/**
	 * 导入地图信息
	 * 
	 * @param request
	 * @param response
	 */
	public void importDAreaInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 初始化扩展数据
	 * 
	 * @param request
	 * @param response
	 */
	public void initExtData();

	/**
	 * 禁用或开启功能
	 * 
	 * @param request
	 * @param response
	 */
	public void fobidFunction(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 同步K3部门到部门表
	 * 
	 * @param request
	 * @param response
	 */
	public void syncDeptFromK3(HttpServletRequest request,
			HttpServletResponse response);

}
