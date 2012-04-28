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
	 * ��ҳ��ѯ��Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void queryShoppingForPage(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ������Ϣ
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void loadShopping(HttpServletRequest request, HttpServletResponse response);
	
	void audit(HttpServletRequest request, HttpServletResponse response);
	/**
	 * �������ݼ���ϸ
	 * @param request
	 * @param response
	 */
	void saveShoppingAndDetail(HttpServletRequest request,HttpServletResponse response);
	
	void delete(HttpServletRequest request,HttpServletResponse response);
	
	void queryShoppingDetail(HttpServletRequest request,HttpServletResponse response);
	/**
	 * ��ȡ��������Ϣ������SELECT
	 * @param request
	 * @param response
	 */
	void loadWFInfoToSelect(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * ��������¼�
	 * @param request
	 * @param response
	 */
	void rollBackEvent(HttpServletRequest request,HttpServletResponse response);

}
