package com.jl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ClientService {
	/**
	 * ������޸�һ����Ϣ
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void saveOrUpdateClientInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �����û���ŷ��ض�Ӧ�ͻ���Ϣ
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void loadClientInfoById(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���ݲ�ѯ������ѯ�ͻ���Ϣ
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	void queryClientInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ȡ�ͻ���Ϣ �س�ѡ���¼�����ʼ������֯������Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	void getClientInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * ���ҿͻ�������
	 * 
	 * @param request
	 * @param response
	 */
	void findClientLevelTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���ҿͻ�����Ŀͻ�������Ϣ
	 * 
	 * @author Don 2010-1-6
	 * @param request
	 * @param response
	 */
	void loadClientLevelOfRelation(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �������ÿͻ�����
	 * 
	 * @author Don 2010-1-6
	 * @param request
	 * @param response
	 */
	void updateClientLevel(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �ͻ�����ڵ������ӡ��޸ġ�ɾ����
	 * 
	 * @author Don 2010-1-6
	 * @param request
	 * @param response
	 */
	void saveOrUpdateOrDeleteClientLevelNode(HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * �����̻�����Ϣ����
	 * 			��EXCEL\HTML\PDF\CVS\��
	 * @param request
	 * @param response
	 */
	void export(HttpServletRequest request, HttpServletResponse response);
}
