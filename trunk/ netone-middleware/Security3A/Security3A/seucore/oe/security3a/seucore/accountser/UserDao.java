package oe.security3a.seucore.accountser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import oe.security3a.seucore.obj.Clerk;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public interface UserDao {

	String _USER_STATUS_DEL = "0";
	String _USER_STATUS_OK = "1";

	/**
	 * ��½��֤
	 * 
	 * @param code �û�������
	 * @param name �û���
	 * @param pass ����
	 * @return clerk ����operationinfo�ֶ��Ƿ�����֤�Ĵ�����Ϣ
	 */
	public Clerk validationUser(String code, String name, String pass);

	/**
	 * �ƶ���Ա
	 * 
	 * @param ouOri
	 *            ԭʼ���ŵ�OU
	 * @param ouAim
	 *            Ŀ�겿�ŵ�OU
	 * @return
	 */
	public boolean moveUserDept(String loginname, String ouOri, String ouAim);

	/**
	 * ע��
	 * 
	 * @param name
	 * @return
	 */
	public boolean logout(String name);
	
	
	//��ӵķ���
	
	public boolean checkSecrity(Object obj);
	public boolean create(Object obj);
	public boolean creates(List objs);
	public boolean drop(Object obj);
	public boolean drops(List objs);
	public void refreshObject(Object objClass, Serializable key);
	public boolean serial(Object obj);
	public boolean update(Object obj);
	public boolean updates(List objs);
	
	/**
	 * loadObject�����Ĳ����仯����Class����code 
	 * @param code ������
	 * @param key  �ؼ���
	 * @return
	 */
	public Object loadObject(String code, Serializable key);
	
	
	public List queryObjects(Object obj, Map comparisonKey);
	public List queryObjects(Object obj, Map comparisonKey, String conditionPre);
	public List queryObjects(Object obj, Map comparisonKey, int from, int to) ;
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) ;
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) ;
	public long queryObjectsNumber(Object obj, Map comparisonKey);

}
