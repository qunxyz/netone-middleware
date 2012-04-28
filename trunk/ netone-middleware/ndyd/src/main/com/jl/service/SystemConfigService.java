package com.jl.service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenlx created by 2010��12��2��
 */
public interface SystemConfigService {

	/**
	 * ��ѯ ϵͳ������Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	void queryInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * ����/���� ϵͳ������Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	void saveOrUpdate(HttpServletRequest request,HttpServletResponse response);

	/**
	 * ���� ϵͳ������Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	void loadInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * ɾ�� ϵͳ������Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	void deleteInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * ���� ������Ϣ�������б�
	 * 
	 * @param request
	 * @param response
	 */
	void loadSystemConfigToSelect(HttpServletRequest request,HttpServletResponse response);

}
