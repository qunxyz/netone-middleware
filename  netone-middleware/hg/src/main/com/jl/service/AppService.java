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
	 * 查询网点图片管理2
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPointManage2(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询网点下品牌
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetpointExtendInfo(HttpServletRequest request,
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

	/**
	 * 查询网点
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPoint(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询网点
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPointGrid(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 加载 品牌信息，以树级方式展示
	 * 
	 * @param request
	 * @param response
	 */
	public void findProTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 点击品牌，加载相应网点、理货员、频率等信息
	 * 
	 * @param request
	 * @param response
	 */
	public void findOutletSetByLshId(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 加载未选择网点信息。
	 * 
	 * @param request
	 * @param response
	 */
	public void loadOutletsInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 保存网点关联信息 根据 品牌ID 和 网点ID
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOutlets(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 更新网点配置信息 根据tallId 配置表ID
	 * 
	 * @param request
	 * @param response
	 */
	public void updateTimes(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 删除网点配置信息 根据 tallId 配置表ID
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteOutlets(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导出网点配置信息表
	 * 
	 * @param request
	 * @param response
	 */
	public void exportAppOBR(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateStoreJson(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteStoreJson(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 */
	public void queryStoreJson(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 根据LSH加载
	 * 
	 * @param request
	 * @param response
	 */
	public void loadStoreJson(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * 生成销售发票（普通）
	 * @param request
	 * @param response
	 */
	public void saveIcsale(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * 一键生成所有销售发票（普通）
	 * @param request
	 * @param response
	 */
	public void saveAllIcsale(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * 销售发票查询
	 * @param request
	 * @param response
	 */
	public void queryIcsale(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * 导出销售信息
	 * @param request
	 * @param response
	 */
	public void exportsellinfo(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * 导出发货通知信息
	 * @param request
	 * @param response
	 */
	public void exportDeliveryInfo(HttpServletRequest request,
			HttpServletResponse response);
}
