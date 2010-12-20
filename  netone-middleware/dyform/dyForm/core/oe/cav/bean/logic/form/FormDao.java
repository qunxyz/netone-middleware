package oe.cav.bean.logic.form;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface FormDao {

	String FORM_VIEWMODE_LIST = "1";// ��ͨ�б���ʾ
	String FORM_VIEWMODE_LISTSMALL = "2";// �б���ʾ����ģʽ
	String FORM_VIEWMODE_LISTVIEW = "3";// �б���ʾ�����쿴ģʽ
	String FORM_VIEWMODE_VIEW = "4";// ��ʾ��
	String FORM_VIEWMODE_CREATE = "5"; // ��ʾ������
	String FORM_VIEWMODE_LISTVIEWX = "6"; // ��ʾ������

	/**
	 * ������
	 * 
	 * �����߼���������������systemid�ķ���(systemid���������ݿ���,Ҳ������һ��xml�ĵ�)
	 * ����һ������ζ����systemid�ķ�����,����һ��Ԫ��(���ҳ־û�),��Ԫ�ص����Ե�Ч��TCsForm�����е�����
	 * 
	 * @param form
	 * @return
	 */
	public boolean create(TCsForm form, String belongname);

	/**
	 * ���±�,�Ա����Ե��޸�
	 * 
	 * @param form
	 * @return
	 */
	public boolean update(TCsForm form);

	/**
	 * ɾ����
	 * 
	 * @param form
	 * @return
	 */
	public boolean drop(TCsForm form);

	/**
	 * ��ñ��е�ȫ���ֶ�
	 * 
	 * ���е�ȫ���ֶ�,������Ҳ���Ǳ��е���Ԫ��,����������ݿ��,��ô����Ԫ�� ���Ǳ��е��ֶ�
	 * 
	 * @param url
	 * @return TCsColumn
	 */
	public List fetchColumnListUrl(String url);

	/**
	 * ��ñ��е�ȫ���ֶ�
	 * 
	 * ���е�ȫ���ֶ�,������Ҳ���Ǳ��е���Ԫ��,����������ݿ��,��ô����Ԫ�� ���Ǳ��е��ֶ�
	 * 
	 * @param formcode
	 * @return TCsColumn
	 */
	public List fetchColumnList(String formcode);

	/**
	 * ��ö�̬���ݵ��б����ͷ��Ϣ
	 * 
	 * 
	 * �㷨˵��: ��Form��xml��other�����л��[listinfo]��Ӧ������,���Ϊ����ôֵΪ1,2,3,4,5,6,7,8"
	 * Ȼ�����listinfo��ֵ(��Ӧ������λ��)ȥ���FileMapping����(����:1��Ӧ�ŵ�һ��,3��Ӧ�ŵ�����...��ù���ע��Ҫ��������,�����������Ӧ��FileMapping���󲻴���)
	 * ���ܻ�õ���Filemapping�����з���Map ����key�� ����ֵ,valueΪ{uuid,name}
	 * 
	 * 
	 * 
	 * @param formcode
	 * @return Map key:tcsColumn.indexvalue,
	 *         value:[]{tcsColumn.columnid,tcsColumn.columnname}
	 */
	public Map fetchTitleInfos(String formcode);

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
	public List queryObjects(TCsForm obj, int from, int to);

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
	public List queryObjects(TCsForm obj, int from, int to, String conditionPre);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(TCsForm obj);

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
	public long queryObjectsNumber(TCsForm obj, String conditionPre);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsForm obj);

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
	public List queryObjects(TCsForm obj, String conditionPre);

	/**
	 * װ�ض���
	 * 
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public TCsForm loadObject(String key);

	/**
	 * װ�ض���
	 * 
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public TCsForm loadObjectUrl(String key);

	/**
	 * ����ά�ȵ�ˮƽֵװ�ػ�ñ�
	 * 
	 * @param level
	 *            ˮƽֵ
	 * 
	 * @return
	 */
	public List listByLevel(String level);

	/**
	 * ��ñ���������Ϣ
	 * 
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	public String formDescription(String formcode) throws RemoteException;

}
