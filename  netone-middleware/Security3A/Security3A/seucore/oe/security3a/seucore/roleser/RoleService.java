package oe.security3a.seucore.roleser;

import java.util.List;

import oe.security3a.seucore.obj.Permission;
import oe.security3a.seucore.obj.Role;
import oe.security3a.seucore.obj.db.UmsRolepermission;


public interface RoleService {

	/**
	 * ��ý�ɫȨ��
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List fetchAvaliablePermission(Role role) throws Exception;

	/**
	 * ����ɫ���Ƿ����ĳȨ��
	 * 
	 * @param role
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	public boolean checkContainPermission(Role role, Permission permission)
			throws Exception;

	/**
	 * ��ȡ��ɫ��Ȩ����Ϣ
	 * 
	 * @param Roleid
	 * @return
	 * @throws Exception
	 */
	public List<UmsRolepermission> fetchRoleUmsRolepermission(String Roleid)
			throws Exception;

	/**
	 * �����Դ�����dao����
	 * 
	 * @return
	 */
	public RoleDao fetchDao();

	/**
	 * �����Դ�����dao����
	 * 
	 * @return
	 */
	public RoleDao fetchDao(String pointto);

}
