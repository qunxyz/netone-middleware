/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 行政区划业务接口
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public interface AreaService {

	/**
	 * 查询行政区划
	 * 
	 * @param request
	 * @param response
	 */
	public void queryArea(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 保存或更新行政区划
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateArea(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 取得一个行政区划
	 * 
	 * @param request
	 * @param response
	 */
	public void getArea(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 删除行政区划
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteArea(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询行政区划树
	 * 
	 * @param request
	 * @param response
	 */
	public void findAreaTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查找行政区划与组织机构的关联关系
	 * 
	 * @param request
	 * @param response
	 */
	public void findAreaADepartmentRelation(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导出行政区划信息
	 * 
	 * @param request
	 * @param response
	 */
	public void exportAreaInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导入行政区划信息更新
	 * 
	 * @param request
	 * @param response
	 */
	public void importAreaInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 关联行政区划与公司
	 * 
	 * @param request
	 * @param response
	 */
	public void relateAreaADepartment(HttpServletRequest request,
			HttpServletResponse response);

}
