/**
 * 
 */
package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �û�ҵ�����ӿ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24 ����02:47:31
 * @history
 */
public interface UserService {

	/**
	 * ��ѯ�������ݣ�����Է�ҳ��ʽչ��
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void queryInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ��ѯ�������ݣ�����Է�ҳ��ʽչ��
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void queryInfoX(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ������޸�һ����Ϣ
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void saveOrUpdate(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ����������Ϣ
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void loadInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ɾ�����ݣ�������<br>
	 * ��ע��ͨ��Ajax�ύҪɾ�����������ϣ�����ʵ�弯�ϣ���������������뵽DAO����ɾ��
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void deleteInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ͬ��K3ְԱ���û�����
	 * 
	 * @param request
	 * @param response
	 */
	void syncUserFromK3(HttpServletRequest request, HttpServletResponse response);

}
