package oe.cav.bean.logic.bus;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


public interface BussDao {

	String _HTTP_INFO_CONTENT_TYPE = "application/octet-stream; charset=GB2312";

	/**
	 * ��������
	 * 
	 * @param bus
	 *            �ò����ɾ����ʵ������ָ��,���������������
	 * @return
	 * 
	 * @return bus Map , key=table value= real tablenmae <br>
	 *         Key=ds value=real ds from xml ds <br>
	 *         Key=columnidlist value= each columned <br>
	 *         Key=columnvaluelist value= each column value which user input
	 *         Key=typelist value= each column value type <br>
	 *         key=primarykey value=[][3] columnid,columntype,columnvalue
	 */
	boolean create(TCsBus bus);

	/**
	 * ��������
	 * 
	 * @param list :
	 *            list�е�Ԫ���Ƕ���
	 * @return
	 */
	boolean creates(List list);

	/**
	 * ����������
	 * 
	 * @param bus
	 *            �ò����ɾ����ʵ������ָ��,���������������
	 * @return
	 */
	boolean update(TCsBus bus);

	/**
	 * ��������
	 * 
	 * @param list :
	 *            list�е�Ԫ���Ƕ���
	 * @return
	 */
	boolean updates(List bus);

	/**
	 * ������ɾ��
	 * 
	 * @param bus
	 *            �ò����ɾ����ʵ������ָ��,���������������
	 * @return
	 */
	boolean drop(TCsBus bus);

	/**
	 * ����ɾ��
	 * 
	 * @param list :
	 *            list�е�Ԫ���Ƕ���
	 * @return
	 */
	boolean drops(List list);

	/**
	 * ��������
	 * 
	 * @param bus
	 *            �ò������ڲ�ѯ����������,�ɾ����ʵ������ָ��,���������������
	 * @param condition
	 *            ��������
	 * @param type
	 *            �������� �ο�BusTextendInfo�е� _TYPE_* ����
	 * @param res
	 * @return
	 */
	boolean export(TCsBus bus, String condition, String type,
			HttpServletResponse res);

	/**
	 * �ֶε�λ���ƶ�
	 * 
	 * @param formcode
	 * @param lsh
	 * @param up
	 * @param participant
	 * @return
	 */
	boolean move(String formcode, String lsh, boolean up, String participant);

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
	public List queryObjects(TCsBus obj, int from, int to);

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
	public List queryObjects(TCsBus obj, int from, int to, String conditionPre);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(TCsBus obj);

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
	public long queryObjectsNumber(TCsBus obj, String conditionPre);

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(TCsBus obj);

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
	public List queryObjects(TCsBus obj, String conditionPre);

	/**
	 * װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public TCsBus loadObject(String formcode, Serializable key);

}
