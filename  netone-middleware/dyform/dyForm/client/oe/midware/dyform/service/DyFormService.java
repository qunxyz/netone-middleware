package oe.midware.dyform.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.form.TCsForm;

/**
 * ��Ӧ�÷���,�÷������ṩ�˱��Ļ�ȡ,����Ӧ��ҵ�����ݵĹ���
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface DyFormService extends Remote {
	/**
	 * װ�ر�
	 * 
	 * @param formid
	 *            ��Ψһ��ʶ��
	 * @return
	 */
	TCsForm loadForm(String formid) throws RemoteException;

	/**
	 * װ�ر�
	 * 
	 * @param formid
	 * @return
	 */
	TCsForm loadFormUrl(String url) throws RemoteException;

	/**
	 * װ�ر��ֶ�
	 * 
	 * @param formid
	 * @return
	 */
	List fetchColumnList(String formid) throws RemoteException;

	/**
	 * װ�ر��ֶ�
	 * 
	 * @param url
	 * @return
	 */
	List fetchColumnListUrl(String url) throws RemoteException;

	/**
	 * �������������
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	String addData(String formid, TCsBus bus) throws RemoteException;

	/**
	 * �޸ı�����
	 * 
	 * @param formid
	 * @param bus
	 * @return
	 */
	boolean modifyData(TCsBus bus) throws RemoteException;

	/**
	 * ɾ������
	 * 
	 * @param formid
	 * @param id
	 * @return
	 */
	boolean deleteData(String formid, String id) throws RemoteException;

	/**
	 * ��ѯ����
	 * 
	 * @param bus
	 * @param form
	 * @param to
	 * @param condition
	 * @return
	 */
	List queryData(TCsBus bus, int form, int to, String condition)
			throws RemoteException;

	/**
	 * װ������
	 * 
	 * @param id
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	TCsBus loadData(String id, String formcode) throws RemoteException;

	/**
	 * ��ѯ���������ļ�¼
	 * 
	 * @param bus
	 * @param condition
	 * @return
	 * @throws RemoteException
	 */
	int queryDataNum(TCsBus bus, String condition) throws RemoteException;

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
	public Map fetchTitleInfos(String formcode) throws RemoteException;
}
