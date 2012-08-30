/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����/��˾
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-8 ����11:35:03
 * @history
 */
public interface DepartmentService {

	/**
	 * ��ѯ����/��˾
	 * 
	 * @param request
	 * @param response
	 */
	public void queryDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �������²���/��˾
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ȡ��һ������/��˾
	 * 
	 * @param request
	 * @param response
	 */
	public void getDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ɾ������/��˾
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteDepartment(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ����/��˾��
	 * 
	 * @param request
	 * @param response
	 */
	public void findDepartmentTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ����/��˾��X
	 * 
	 * @param request
	 * @param response
	 */
	public void findDepartmentTreeX(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��������/��˾��Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void exportDepartmentInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���벿��/��˾��Ϣ����
	 * 
	 * @param request
	 * @param response
	 */
	public void importDepartmentInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �ж�ҵ������
	 * 
	 * @param id
	 *            Ŀ¼ID departmentId
	 * @return
	 */
	public String checkBussType(String id);

	/**
	 * �����ͼ��Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void importDAreaInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ʼ����չ����
	 * 
	 * @param request
	 * @param response
	 */
	public void initExtData();

	/**
	 * ���û�������
	 * 
	 * @param request
	 * @param response
	 */
	public void fobidFunction(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ͬ��K3���ŵ����ű�
	 * 
	 * @param request
	 * @param response
	 */
	public void syncDeptFromK3(HttpServletRequest request,
			HttpServletResponse response);

}
