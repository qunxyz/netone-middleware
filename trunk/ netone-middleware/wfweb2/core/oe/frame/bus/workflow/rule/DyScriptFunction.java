package oe.frame.bus.workflow.rule;

import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;

public interface DyScriptFunction extends ScriptFunction {

	/**
	 * ɾ������
	 * 
	 * @param recordid
	 *            �� lsh:formcode
	 * @return
	 */
	boolean deleteData(String recordid);

	/**
	 * ��ѯ����
	 * 
	 * @param name
	 *            ��̬����naturalname
	 * @param bus
	 * @param form
	 * @param to
	 * @param condition
	 * @return
	 */
	List queryData(String name, TCsBus bus, int form, int to, String condition);

	/**
	 * ��ѯ���������ļ�¼
	 * 
	 * @param bus
	 * @param condition
	 * @return
	 * @throws RemoteException
	 */
	int queryDataNum(String name, TCsBus bus, String condition);

	/**
	 * ����һ��Ӧ��ʵ��,ʹ��formcode�����naturalname
	 * 
	 * @param formcode
	 * @return lsh
	 */
	String newInstanceByCode(String formcode);

}
