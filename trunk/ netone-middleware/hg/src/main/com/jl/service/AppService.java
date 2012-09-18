/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ӧ�ó���ӿ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-8-13 ����02:17:12
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
	 * ͬ����Ʒ��������������
	 * 
	 * @param request
	 * @param response
	 */
	public void syncProductData(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���������Ʒ
	 * 
	 * @param request
	 * @param response
	 */
	public void exportPartAndProduct(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ���۳�����ϸ
	 * 
	 * @param request
	 * @param response
	 */
	public void querySellOutStorageDetail(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ����͸��ͼ
	 * 
	 * @param request
	 * @param response
	 */
	public void querySellPivotTable(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ����ͼƬ����
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPointManage(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ����������������
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