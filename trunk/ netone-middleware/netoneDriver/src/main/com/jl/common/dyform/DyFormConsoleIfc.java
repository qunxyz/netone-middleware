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
	 * ��Ա��༭����������̻����У��ύ��������Ա����ô����������Ҫ����Ȩ��
	 * @param bussid ҵ�����ݵ�lsh
	 * @param participant ������
	 * @return true��ʾ���Ա༭ 
	 */
	boolean whenFlowPageEdit(String bussid,String participant)throws Exception;

	/**
	 * װ�ر�
	 * 
	 * @param formcode
	 *            ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @return
	 */
	DyForm loadForm(String formcode) throws Exception;

	/**
	 * װ�ر��ֶ�
	 * 
	 * @param formcode   ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @return
	 */
	List<DyFormColumn> fetchColumnList(String formcode) throws Exception;
	
	/**
	 * װ�ر��ֶ�(�������ʱʹ�ã�����񲻻��ҵ��ű�)
	 * 
	 * @param formcode   ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @return   
	 */
	List<DyFormColumn> fetchColumnListForDesign(String formcode) throws Exception;

	/**
	 * װ�ر��ֶ�
	 * 
	 * @param formcode   ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param columnid ���ֶ�id,�ڱ��������ļ��е� columnid
	 * @return
	 */
	DyFormColumn loadColumn(String formcode, String columnid) throws Exception;

	/**
	 * �������������
	 * 
	 * @param formcode  ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param bus ������ 
	 * ������ο�:
	 * DyFormData bus=new DyFormData();
	 * bus.setParticipant("userid");//���ݵĴ�����Ա
	 * bus.setFatherlsh("1");//������ӱ�Ϊ1��������Ҫ���븸����lsh
	 * @return ����������ɹ� ���� ����¼��ΨһID 
	 */
	String addData(String formcode, DyFormData bus) throws Exception;

	/**
	 * �޸ı�����
	 * 
	 * @param formcode  ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param bus һ�������ݶ���ע��ö������ֹ�����ģ�ͨ����ͨ��loaddata���������

	 * @return
	 */
	boolean modifyData(String formcode, DyFormData bus) throws Exception;

	/**
	 * ɾ������
	 * 
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param lsh ����lsh
	 * 
	 * @return 
	 */
	boolean deleteData(String formcode, String lsh) throws Exception;

	/**
	 * �߼�ɾ����
	 * 
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param lsh ����lsh
	 * @return
	 * @throws Exception
	 */
	boolean deleteDataByLogic(String formcode, String lsh) throws Exception;

	/**
	 * ��ѯ����
	 * 
	 * @param bus һ�������ݶ���,����ǿ��ֶ�ϵͳ���Զ�ת��Ϊ��ѯ��������
	 * @param from
	 *            ��ʼ��¼
	 * @param size
	 *            ��¼��
	 * @param condition ���ŵ�SQL��where������
	 * @return
	 */
	List<DyFormData> queryData(DyFormData bus, int from, int size, String condition)
			throws Exception;

	/**
	 * װ������
	 * 
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param lsh ����lsh
	 * @return ����ƽṹ����
	 * @throws RemoteException 
	 */
	DyFormData loadData(String formcode, String lsh) throws Exception;

	/**
	 * ����װ������
	 * 
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param lsh ����lsh
	 * @return
	 * @throws RemoteException
	 */
	DyFormData loadDataS(String formcode, String lsh) throws Exception;

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
	 *            ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param fatherlsh
	 *            ���ڵ�id������Ƕ��������׽ڵ�idΪ1
	 * @return ɾ��������
	 */
	int deleteAll(String formcode, String fatherlsh) throws Exception;

	/**
	 * �������
	 * 
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
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnX(String formcode, String model) throws Exception;
	
	/**
	 * ��ѯ�ֶ�װ��(���ڲ�ѯչʾ�ֶ�)
	 * 
	 * @param �򵥲�ѯmodel=0���߼���ѯmodel=1���ֶ��б�model=2���ֶ��б����model=3
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @return
	 * @throws RemoteException
	 */
	List<DyFormColumn> queryColumnQ(String formcode) throws Exception;
	
	/**
	 * ��������������Ӧ�ã�ͨ����Ȩ�û��������κ�ʱ��ȥ�޸�����ֶΣ����۸ñ�
	 * �ǹ鵵������δ�鵵��
	 * @param formcode ��Ψһ��ʶ�룬�ڱ��������ļ��е�UUID�ֶ�
	 * @param participant
	 * @return
	 */
	String[] manageColumn(String formcode,String participant) throws Exception;
	
}
