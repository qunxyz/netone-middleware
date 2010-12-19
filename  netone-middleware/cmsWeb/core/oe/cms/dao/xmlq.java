package oe.cms.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ���ݿ��ѯ����
 * 
 * @author chen.jia.xun
 * 
 */
public interface xmlq {

	/**
	 * ������������Ķ��������
	 * 
	 * @param obj
	 *            Object
	 *            �����ò�ѯ�����Ķ��󣬸ö��������com.rjsoft.masterflow.core.runtimepools �� ��
	 *            ��com.rjsoft.masterflow.core.staticpools ���е��κζ���
	 * 
	 * @return Long ��¼����
	 */
	public long  findXmlNumber(Object obj);

	/**
	 * ��ò�ѯ�����б�
	 * 
	 * @param obj
	 *            Object
	 *            �����ò�ѯ�����Ķ��󣬸ö��������com.rjsoft.masterflow.core.runtimepools �� ��
	 *            ��com.rjsoft.masterflow.core.staticpools ���е��κζ���
	 * 
	 * @return List ��������
	 */
	public List  findXmls(Object obj);
	
	/**
	 * ��ò�ѯ�����б�
	 * 
	 * @param obj
	 *            Object
	 *            �����ò�ѯ�����Ķ��󣬸ö��������com.rjsoft.masterflow.core.runtimepools �� ��
	 *            ��com.rjsoft.masterflow.core.staticpools ���е��κζ���
	 * 
	 * @return List ��������
	 */
	public List  findXmls(Object obj,Map conditionkey);


	/**
	 * װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public Object loadObject(Class objClass, Serializable key);

}
