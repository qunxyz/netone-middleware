/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用程序接口
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-8-13 下午02:17:12
 * @history
 */
public interface AppService {

	public void findPartTree(HttpServletRequest request,
			HttpServletResponse response);

	public void savePartAProductRelation(HttpServletRequest request,
			HttpServletResponse response);

	public void findCategoriesTree(HttpServletRequest request,
			HttpServletResponse response);

	public void findProductSetByPC(HttpServletRequest request,
			HttpServletResponse response);

	public void findProductSetByParentId(HttpServletRequest request,
			HttpServletResponse response);

	public void relatePartAProduct(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 同步产品有卖，想卖数据
	 * 
	 * @param request
	 * @param response
	 */
	public void syncProductData(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导出网点产品
	 * 
	 * @param request
	 * @param response
	 */
	public void exportPartAndProduct(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询销售出库明细
	 * 
	 * @param request
	 * @param response
	 */
	public void querySellOutStorageDetail(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询销售透视图
	 * 
	 * @param request
	 * @param response
	 */
	public void querySellPivotTable(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询网点图片管理
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPointManage(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 更新网点理货评分
	 * 
	 * @param request
	 * @param response
	 */
	public void updateNetPoint(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryHGReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
