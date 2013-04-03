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
	 * ��ѯ����ͼƬ����2
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPointManage2(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ������Ʒ��
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetpointExtendInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���������������
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
	 * ��ѯ����
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPoint(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ����
	 * 
	 * @param request
	 * @param response
	 */
	public void queryNetPointGrid(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���� Ʒ����Ϣ����������ʽչʾ
	 * 
	 * @param request
	 * @param response
	 */
	public void findProTree(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ���Ʒ�ƣ�������Ӧ���㡢���Ա��Ƶ�ʵ���Ϣ
	 * 
	 * @param request
	 * @param response
	 */
	public void findOutletSetByLshId(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ����δѡ��������Ϣ��
	 * 
	 * @param request
	 * @param response
	 */
	public void loadOutletsInfo(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * �������������Ϣ ���� Ʒ��ID �� ����ID
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOutlets(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��������������Ϣ ����tallId ���ñ�ID
	 * 
	 * @param request
	 * @param response
	 */
	public void updateTimes(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ɾ������������Ϣ ���� tallId ���ñ�ID
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteOutlets(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��������������Ϣ��
	 * 
	 * @param request
	 * @param response
	 */
	public void exportAppOBR(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��������
	 * 
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateStoreJson(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ɾ��
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteStoreJson(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��ѯ
	 * 
	 * @param request
	 * @param response
	 */
	public void queryStoreJson(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ����LSH����
	 * 
	 * @param request
	 * @param response
	 */
	public void loadStoreJson(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * �������۷�Ʊ����ͨ��
	 * @param request
	 * @param response
	 */
	public void saveIcsale(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * һ�������������۷�Ʊ����ͨ��
	 * @param request
	 * @param response
	 */
	public void saveAllIcsale(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * ���۷�Ʊ��ѯ
	 * @param request
	 * @param response
	 */
	public void queryIcsale(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * ����������Ϣ
	 * @param request
	 * @param response
	 */
	public void exportsellinfo(HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 * ��������֪ͨ��Ϣ
	 * @param request
	 * @param response
	 */
	public void exportDeliveryInfo(HttpServletRequest request,
			HttpServletResponse response);
}
