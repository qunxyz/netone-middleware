package oe.security3a.seucore.permission;

import java.rmi.Remote;

/**
 * ��Ȩ�ӿ�
 * 
 * @author hls
 */
public interface PermissionService extends Remote {

	/**
	 * ���½�ɫ��Ȩ
	 * 
	 * @param roleid
	 */
	public void reflashRolePermission(String roleid);

	/**
	 * �����û���ɫ
	 * 
	 * @param userid
	 */
	public void reflashUserRole(String userid);

	/**
	 * �жϽ�ɫ�Ƿ���ĳ����Դ��Ȩ�ޡ�
	 * 
	 * @param roleid
	 * @param podn
	 * @param action
	 * @return
	 */
	public boolean checkRolePermission(String roleid, String podn, String action)
			throws Exception;

	/**
	 * �жϽ�ɫ��Ȩ�ޣ���������ɫ����û�а����̳еĽ�ɫ
	 * @param roleid
	 * @param podn
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoleSelfPermission(String roleid, String podn, String action)
		throws Exception ;
	
	/**
	 * �ж��û��Ƿ���ĳ����Դ��Ȩ��
	 * 
	 * @param code	�û���������
	 * @param userid	�û�id
	 * @param podn
	 * @param action
	 * @return
	 */
	public boolean checkUserPermission(String code, String userid, String podn, String action)
			throws Exception;

	/**
	 * ����ȫ���Ļ���
	 * 
	 * @throws Exception
	 */
	public void reflashAll() throws Exception;

}
