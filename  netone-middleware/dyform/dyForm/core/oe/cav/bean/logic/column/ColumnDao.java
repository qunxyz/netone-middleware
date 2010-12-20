package oe.cav.bean.logic.column;

import java.io.Serializable;
import java.util.List;

public interface ColumnDao {

	String COLUMNID_PRE = "column";

	/**
	 * �����ֶ�
	 * 
	 * @param column
	 * @return
	 */
	String create(TCsColumn column);

	/**
	 * �����ֶ�
	 * 
	 * @param column
	 * @return
	 */
	String update(TCsColumn column);

	public String updateView(TCsColumn column);

	/**
	 * ɾ���ֶ�
	 * 
	 * @param column
	 * @return
	 */
	String drop(TCsColumn column);

	/**
	 * ����ֶε�λ��
	 * 
	 * @param formcode
	 * @return
	 */
	long getNextIndexValue(String formcode);

	/**
	 * �����һ���ֶ�
	 * 
	 * @param formcode
	 * @return
	 */
	String getNextColumn(String formcode);

	/**
	 * �ƶ��ֶε�λ��
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean moveupColumn(String formcode, String columnid, String participant);

	/**
	 * �ƶ��ֶε�λ��
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean movedownColumn(String formcode, String columnid, String participant);

	/**
	 * ��ʼ���ֶε�λ��
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean resizeColumnIndexValue(String formcode, String participant);

	/**
	 * �����ֶ�����
	 * 
	 * @param type
	 * @return
	 */
	String[] parseViewType(String type);

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
	public List queryObjects(TCsColumn obj, int from, int to);

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
			String conditionPre);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(TCsColumn obj);

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
	public long queryObjectsNumber(TCsColumn obj, String conditionPre);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsColumn obj);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param url
	 *            ���ݱ���URL������е��ֶ�
	 * 
	 * @return List ��������
	 */
	public List queryObjectsUrl(String url);

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
	public List queryObjects(TCsColumn obj, String conditionPre);

	/**
	 * װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public TCsColumn loadObject(String formcode, Serializable key);

}
