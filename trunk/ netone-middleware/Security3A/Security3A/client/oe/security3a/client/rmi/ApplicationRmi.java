package oe.security3a.client.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.db.UmsApplication;


/**
 * ��������,������Դ���������,���е���Դ�������ڲ�ͬ������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail: 56414429@qq.com, chenjiaxun@oesee.com<br>
 *         support by: http://www.oesee.com
 * 
 */
public interface ApplicationRmi extends Remote {

	/**
	 * ������Դ��
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public String create(UmsApplication obj) throws RemoteException;

	/**
	 * ɾ����Դ��
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean drop(Long id) throws RemoteException;

	/**
	 * ������Դ��
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(UmsApplication obj) throws RemoteException;

	/**
	 * ����ɾ����Դ��
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(Long[] ids) throws RemoteException;

	/**
	 * ����������Դ��
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) throws RemoteException;

	/**
	 * ��ѯ��Դ��, ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            Object ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(UmsApplication obj, Map comparisonKey, int from,
			int to) throws RemoteException;

	/**
	 * ��Դ��,���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            Object ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * @param conditionPre
	 *            String ��������,����SQL92��׼����Where�Ĳ�ѯ����,��ѯ�ֶ����ɲο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(UmsApplication obj, Map comparisonKey, int from,
			int to, String conditionPre) throws RemoteException;

	/**
	 * ��Դ��,���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(UmsApplication obj, Map comparisonKey)
			throws RemoteException;

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(UmsApplication obj, Map comparisonKey,
			String conditionPre) throws RemoteException;

	/**
	 * ��Դ��,���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @return List ��������
	 */
	public List queryObjects(UmsApplication obj, Map comparisonKey)
			throws RemoteException;

	/**
	 * ��Դ��,���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            ��ѯ���󣬿���ʹ������Ѿ������ݿ��е���ر��Ӧ��POJO����
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(UmsApplication obj, Map comparisonKey,
			String conditionPre) throws RemoteException;

	/**
	 * ��Դ��,װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 * 
	 * 
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public UmsApplication loadObject(Serializable key) throws RemoteException;
}
