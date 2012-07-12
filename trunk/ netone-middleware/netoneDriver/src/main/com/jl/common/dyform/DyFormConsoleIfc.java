package com.jl.common.dyform;

import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;

import com.jl.common.workflow.TWfActive;

/**
 * ��̬������̨
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface DyFormConsoleIfc {

	String[][] _HTML_LIST = { { "00", "ͨ��" }, { "01", "����/���" },
			{ "02", "ʱ��hh:mm:ss" }, { "03", "����YYYY-MM-DD" },
			{ "04", "����ʱ��YYYY-MM-DD hh:mm:ss" }, { "05", "���" },
			{ "06", "�ʼ���ַ" }, { "10", "�б���Ϣ" }, { "11", "�б���ϢK-V" },
			{ "12", "IP��ַ" }, { "13", "���ı�" }, {"14", "�ļ�" }, {"15", "ͼƬ" }, {"16", "��ť" }, { "17", "����Դѡ��" },
			{ "18", "����Դѡ��" }, { "20", "PORTAL��" }, { "21", "����ĵ�" },
			{ "22", "��֯��Ա" }, { "23", "��֯��Ա��ѡ" }, { "24", "��ǰ�û�" },
			{ "25", "��������" }, { "26", "��ѡ��" }, { "27", "��֯������ѡ" },
			{ "28", "��֯������ѡ" } , {"29", "URL" } ,  {"30", "��ѡ��" } ,{"31","���radio"},{"32","������"}};;

	String _EVENT_INIT = "init_event";
	String _EVENT_FOCUS = "focus_event";
	String _EVENT_LOSEFOCUS = "losefocus_event";
	String _TYPE_XYOFFSET = "xyoffset";
	String _TYPE_SQLTYPE = "sqltype";

	/**
	 * װ�ر�
	 * 
	 * @param formid
	 *            ��Ψһ��ʶ��
	 * @return
	 */
	DyForm loadForm(String formid) throws Exception;

	/**
	 * װ�ر��ֶ�
	 * 
	 * @param formid
	 * @return
	 */
	List<DyFormColumn> fetchColumnList(String formid) throws Exception;
	
	/**
	 * װ�ر��ֶ�(���ʱʹ�ò�Ҫȥ�����б�ű�ִ��)
	 * 
	 * @param formid
	 * @return  
	 */
	List<DyFormColumn> fetchColumnListForDesign(String formid) throws Exception;

	/**
	 * װ�ر��ֶ�
	 * 
	 * @param formid
	 * @return
	 */
	DyFormColumn loadColumn(String formid, String columnid) throws Exception;

	/**
	 * �������������
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	String addData(String formid, DyFormData bus) throws Exception;

	/**
	 * �޸ı�����
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	boolean modifyData(String formid, DyFormData bus) throws Exception;

	/**
	 * ɾ������
	 * 
	 * @param formid
	 * @param id
	 * @return
	 */
	boolean deleteData(String formid, String id) throws Exception;

	/**
	 * �߼�ɾ����
	 * 
	 * @param formid
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteDataByLogic(String formid, String id) throws Exception;

	/**
	 * ��ѯ����
	 * 
	 * @param bus
	 * @param form
	 *            ��ʼ��¼
	 * @param size
	 *            ��¼��
	 * @param condition
	 * @return
	 */
	List queryData(DyFormData bus, int form, int size, String condition)
			throws Exception;

	/**
	 * װ������
	 * 
	 * @param id
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	DyFormData loadData(String formcode, String bussid) throws Exception;

	/**
	 * ����װ������
	 * 
	 * @param id
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	DyFormData loadDataS(String formcode, String bussid) throws Exception;

	/**
	 * ��ѯ���������ļ�¼
	 * 
	 * @param bus
	 * @param condition
	 * @return
	 * @throws RemoteException
	 */
	int queryDataNum(DyFormData bus, String condition) throws Exception;

	/**
	 * ����ɾ��
	 * 
	 * @param formcode
	 *            ��code
	 * @param fatherlsh
	 *            ���ڵ�id������Ƕ��������׽ڵ�idΪ1
	 * @return ɾ��������
	 */
	int deleteAll(String formcode, String fatherlsh) throws Exception;

	/**
	 * �������
	 * 
	 * @param formcode
	 *            ��code
	 * @param fatherlsh
	 *            ���ڵ�id������Ƕ��������׽ڵ�idΪ1
	 * @param data
	 *            ����¼����
	 * @return
	 */
	String[] addAll(List<DyFormData> data) throws Exception;

	/**
	 * ����Ȩ
	 * 
	 * @param form
	 *            ������
	 * @param usercode
	 *            �û�����
	 * @param TWfActive
	 *            ���Ϣ
	 * @return
	 */
	void permission(DyForm form, String usercode, TWfActive act)
			throws Exception;

	/**
	 * ��ѯ�ֶ�װ��(�����б�չʾ�ֶ�)
	 * 
	 * @param �򵥲�ѯmodel=0���߼���ѯmodel=1���ֶ��б�model=2���ֶ��б����model=3
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnX(String formcode, String model,String lsh) throws Exception;
	
	/**
	 * ��ѯ�ֶ�װ��(���ڲ�ѯչʾ�ֶ�)
	 * 
	 * @param �򵥲�ѯmodel=0���߼���ѯmodel=1���ֶ��б�model=2���ֶ��б����model=3
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnQ(String formcode,String lsh) throws Exception;
}
