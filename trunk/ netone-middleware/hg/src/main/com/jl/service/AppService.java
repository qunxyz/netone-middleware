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

}
