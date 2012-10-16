package oe.frame.bus.workflow.rule;

import java.rmi.RemoteException;

public interface WfScriptFunction extends ScriptFunction {

	/**
	 * ��ʼ�����̣������ָ̻�����ʼ״̬
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @return boolean
	 */
	public void initProc(String runtimeid) throws RemoteException;

	/**
	 * ɾ�����̣����������̵�ȫ��������Ϣ
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @return boolean
	 */
	public void dropProc(String runtimeid) throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param currentworklist
	 *            ��ǰ�ʵ��
	 */
	public void commitAct(String workcode) throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param currentworklist
	 *            ��ǰ�ʵ��
	 * @param ѡ���Ŀ��
	 */
	public void commitActAndPoint(String workcode, String actid)
			throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param runtime
	 *            ����ʵ������(��Ҫ����currentPointActivity)
	 * @return boolean
	 */
	public void commitActAndPoints(String workcode, String[] actid)
			throws RemoteException;

	/**
	 * Ϊ���Ӧ��,��:��,����......
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	void worklistAppBind(String workcode, String apptype, String appvalue)
			throws RemoteException;

	/**
	 * ���ƻ��״̬
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	void updateWorklistStatus(String workcode, String status);

	/**
	 * ʹ�ù������Ự,�Ự������������������ݣ����ǻỰ�Ƿ�Ԥ�ȶ����,֧�ֵ����ݷ�Χ��������Ը���
	 * 
	 * @param name
	 * @param value
	 */
	void setSession(String name, Object value);

	/**
	 * ʹ�ù������Ự,�Ự�������������������,���ǻỰ�Ƿ�Ԥ�ȶ����,֧�ֵ����ݷ�Χ��������Ը���
	 * 
	 * @param name
	 * @param value
	 */
	Object getSession(String name);
	
	/**
	 * ���������ر�������չ����
	 * 
	 * @param id
	 * @param paramname
	 * @param extname
	 * @return
	 */
	String getext(String id,String paramname,String extname);
	
	/**
	 * ����workcode��õ�ǰ�û�
	 * @param workcode
	 * @return
	 */
	String[] getUser(String workcode);
	/**
	 * ������̽ڵ���ύ��
	 * @param workcode
	 * @return
	 */
	String getCommiter(String workcode);

}
