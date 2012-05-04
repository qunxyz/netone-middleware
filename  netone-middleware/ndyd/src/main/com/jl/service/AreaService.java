/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��������ҵ��ӿ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-2-15
 * @history
 */
public interface AreaService {

	/**
	 * ��ѯ��������
	 * 
	 * @param request
	 * @param response
	 */
	public void queryArea(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ����������������
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateArea(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ȡ��һ����������
	 * 
	 * @param request
	 * @param response
	 */
	public void getArea(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ɾ����������
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteArea(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ����������
	 * 
	 * @param request
	 * @param response
	 */
	public void findAreaTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ����������������֯�����Ĺ�����ϵ
	 * 
	 * @param request
	 * @param response
	 */
	public void findAreaADepartmentRelation(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��������������Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void exportAreaInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��������������Ϣ����
	 * 
	 * @param request
	 * @param response
	 */
	public void importAreaInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �������������빫˾
	 * 
	 * @param request
	 * @param response
	 */
	public void relateAreaADepartment(HttpServletRequest request,
			HttpServletResponse response);

}
