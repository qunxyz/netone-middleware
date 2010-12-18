package oe.security3a.seucore.roleser;

import java.util.List;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.obj.Permission;
import oe.security3a.seucore.obj.Role;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;
import oe.security3a.seucore.roleser.RoleService;


/**
 * ��ɫ����ӿ�ʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class RoleServiceImpl implements RoleService {

	/**
	 * �����Դ�����dao����
	 * 
	 * @return RoleDao
	 */
	public RoleDao fetchDao() {
		return (RoleDao) SeudaoEntry.iv("roleDao");
	}
	
	/**
	 * �����Դ�����dao����
	 * 
	 * @return RoleDao
	 * 
	 */
	public RoleDao fetchDao(String pointto){
		if(pointto!=null && !pointto.equals("")){
			if(pointto.equals("roleDaopermission")){
				return (RoleDao) SeudaoEntry.iv("roleDaopermission");
			}
			if(pointto.equals("roleDaouser")){
				return (RoleDao) SeudaoEntry.iv("roleDaouser");
			}
			if(pointto.equals("roleDaorole")){
				return (RoleDao) SeudaoEntry.iv("roleDaorole");
			}
		}
		return null;
	}
	
	/**
	 * ����ɫ���Ƿ����ĳȨ��
	 * 
	 * @param role
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	public boolean checkContainPermission(Role role, Permission permission)
			throws Exception {
		return false;
	}

	/**
	 * ��ý�ɫȨ��
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List fetchAvaliablePermission(Role role) throws Exception {
		return null;
	}

	/**
	 * ��ȡ��ɫ��Ȩ����Ϣ
	 * 
	 * @param Roleid
	 * @return
	 * @throws Exception
	 */
	public List<UmsRolepermission> fetchRoleUmsRolepermission(String Roleid) throws Exception {
		return null;
	}

}
