package com.jl.common.workflow.worklist;

import java.util.List;

/**
 * ��������������ר��
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface WorklistViewIfc {
	/**
	 * ���������Ĵ�������
	 * 
	 * @param clientid
	 *            �ͻ�id
	 * @param appname
	 *            Ӧ����
	 * @param mode
	 *            ҵ��ģʽtrue ���졢����,false ����
	 * @param from
	 * @param size
	 * @param listtype
	 *            �������͡�01 ���졢02�Ѱ쵫δ�鵵 ��03 �Ѱ��ѹ鵵
	 * @param query
	 *            ��ѯ�ֶζ���
	 * @return ��ֵ�����������ж�Ӧ�ļ�¼ֵ
	 * @throws Exception
	 */
	List<DataObj> worklist(String clientid, String appname, boolean mode,
			int from, int size, String listtype, QueryColumn query)
			throws Exception;
	/**
	 * ���������Ĵ�������(�����ر���lsh)
	 * 
	 * @param clientid
	 *            �ͻ�id
	 * @param appname
	 *            Ӧ����
	 * @param mode
	 *            ҵ��ģʽtrue ���졢����,false ����
	 * @param from
	 * @param size
	 * @param listtype
	 *            �������͡�01 ���졢02�Ѱ쵫δ�鵵 ��03 �Ѱ��ѹ鵵
	 * @param query
	 *            ��ѯ�ֶζ���
	 * @return ��ֵ�����������ж�Ӧ�ļ�¼ֵ
	 * @throws Exception
	 */
	List<String> worklistOnlyLsh(String clientid, String appname, boolean mode,
			int from, int size, String listtype, QueryColumn query)
			throws Exception;

	/**
	 * ���������Ĵ�����������
	 * 
	 * @param clientid
	 *            �ͻ�id
	 * @param appname
	 *            Ӧ����
	 * 
	 * @param mode
	 *            ҵ��ģʽtrue ���졢����,false ����
	 * 
	 * @param listtype
	 *            �������͡�01 ���졢02�Ѱ쵫δ�鵵 ��03 �Ѱ��ѹ鵵
	 * @param query
	 *            ��ѯ�ֶζ���
	 * @return
	 * @throws Exception
	 */
	int count(String clientid, String appname, boolean mode, String listtype,
			QueryColumn query) throws Exception;
	/**
	 * ���������Ĵ�����������������lsh��
	 * 
	 * @param clientid
	 *            �ͻ�id
	 * @param appname
	 *            Ӧ����
	 * 
	 * @param mode
	 *            ҵ��ģʽtrue ���졢����,false ����
	 * 
	 * @param listtype
	 *            �������͡�01 ���졢02�Ѱ쵫δ�鵵 ��03 �Ѱ��ѹ鵵
	 * @param query
	 *            ��ѯ�ֶζ���
	 * @return
	 * @throws Exception
	 */
	int countOnlyLsh(String clientid, String appname, boolean mode, String listtype,
			QueryColumn query) throws Exception;
	/**
	 * ���в�ѯ�ֶ�
	 * 
	 * @param appname
	 *            Ӧ����
	 * @return
	 */
	List<QueryColumn> listQueryColumn(String appname) throws Exception;

	/**
	 * װ�ز�ѯ�ֶ�
	 * 
	 * @param appname
	 *            Ӧ����
	 * @param index
	 *            �ֶ�������
	 * @return
	 */
	QueryColumn loadQueryColumn(String appname, int index) throws Exception;

	/**
	 * װ�ز�ѯ�ֶ����
	 * 
	 * @param appname
	 *            Ӧ����
	 * @param columnid
	 *            �ֶ�����
	 * @return
	 */
	int fetchQueryColumnIndex(String appname, String columnid) throws Exception;

}
