package oe.midware.dyform.service;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;

/**
 * ����Ʒ���
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, oesee@139.com<br>
 *         tel:15860836998<br>
 */
public interface DyFormDesignService extends Remote {
	/**
	 * ������ͷ,������KEY�Զ�����,����ͨ������ֵ���
	 * 
	 * @return String[]={tablenane,formcode}
	 */
	String[] create(TCsForm form, String belongname) throws RemoteException;

	/**
	 * ����ֶ�
	 * 
	 * @param column
	 * @return
	 */
	String addColumn(TCsColumn column) throws RemoteException;

	/**
	 * ɾ���ֶ�
	 * 
	 * @param column
	 * @return
	 */
	String dropColumn(TCsColumn column) throws RemoteException;

	/**
	 * �޸��ֶ�
	 * 
	 * @param column
	 * @return
	 */
	String updateColumn(TCsColumn column) throws RemoteException;

	/**
	 * �����޸�XML�е��ֶ�����,������
	 * 
	 * @param column
	 * @return
	 * @throws RemoteException
	 */
	public String updateColumnView(TCsColumn column) throws RemoteException;

	/**
	 * ͨ������Դ��table����DyObj����,���table�ǿ�����ͳ�Ʊ�,��ʹ��create(),addColumn()�ȷ�������������
	 * ��table
	 * 
	 * @param ds
	 * @param sql
	 * @return
	 */
	DyObj fromSQL(String ds, String table) throws RemoteException;

	/**
	 * ͨ����̬������DyObj����
	 * 
	 * @return
	 */
	DyObj fromDy(String formcode) throws RemoteException;

	/**
	 * ��DyObj���󴴽���XML
	 * 
	 * @param obj
	 * @return
	 */
	String fromDyObj(DyObj obj) throws RemoteException;

	/**
	 * ����level��ֵ��ñ�
	 * 
	 * @param level

	 * @return
	 */
	public List listDyObjFromLevel(String level)
			throws RemoteException;

	public boolean updateForm(TCsForm form) throws RemoteException;

	public boolean dropForm(TCsForm form) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            Object ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(TCsForm obj, int from, int to)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            Object ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * @param conditionPre
	 *            String ��������,����SQL92��׼����Where�Ĳ�ѯ����,��ѯ�ֶ����ɲο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(TCsForm obj, int from, int to, String conditionPre)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(TCsForm obj) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(TCsForm obj, String conditionPre)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsForm obj) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsForm obj, String conditionPre)
			throws RemoteException;

	/**
	 * ����ֶε�λ��
	 * 
	 * @param formcode
	 * @return
	 */
	long getNextIndexValue(String formcode) throws RemoteException;

	/**
	 * �����һ���ֶ�
	 * 
	 * @param formcode
	 * @return
	 */
	String getNextColumn(String formcode) throws RemoteException;

	/**
	 * �ƶ��ֶε�λ��
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean moveupColumn(String formcode, String columnid, String participant)
			throws RemoteException;

	/**
	 * �ƶ��ֶε�λ��
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean movedownColumn(String formcode, String columnid, String participant)
			throws RemoteException;

	/**
	 * ��ʼ���ֶε�λ��
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean resizeColumnIndexValue(String formcode, String participant)
			throws RemoteException;

	/**
	 * �����ֶ�����
	 * 
	 * @param type
	 * @return
	 */
	String[] parseViewType(String type) throws RemoteException;

	/**
	 * װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public TCsColumn loadColumn(String formcode, Serializable key)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            Object ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(TCsColumn obj, int from, int to)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            Object ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * @param conditionPre
	 *            String ��������,����SQL92��׼����Where�Ĳ�ѯ����,��ѯ�ֶ����ɲο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(TCsColumn obj, int from, int to,
			String conditionPre) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(TCsColumn obj) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(TCsColumn obj, String conditionPre)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsColumn obj) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param url
	 *            ���ݱ���URL������е��ֶ�
	 * 
	 * @return List ��������
	 */
	public List queryObjectsUrl(String url) throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsColumn obj, String conditionPre)
			throws RemoteException;

	/**
	 * ��ñ���������Ϣ
	 * 
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	public String formDescription(String formcode) throws RemoteException;
}
