package oe.security3a.seucore.roleser;

import java.util.List;

import oe.security3a.seucore.obj.Application;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRolepermission;


/**
 * ������ɫ�Ľӿ�
 * 
 * @author hls
 * 
 */
public interface RoleManager {
	/**
	 * ���ӵ�иý�ɫ���û�
	 * 
	 * @param code ������
	 * @param roleId
	 * @return
	 */
	public List<Clerk> fetchUser(String code,String roleId);

	/**
	 * ��ý�ɫ����Ȩ
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UmsRolepermission> fetchPermission(String roleId);

	/**
	 * ��ý�ɫ������ϵͳ
	 * 
	 * @param roleId
	 * @return
	 */
	public UmsApplication fetchApplication(String roleId);

	/**
	 * ��ñ�roleId�̳еĽ�ɫ(��ǰֻ֧�ֵ��Ӽ̳�)
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ���̳еĽ�ɫ
	 * @return
	 */
	public UmsRole fetchExtendedRole(String roleId);

	/**
	 * ��ñ�roleId�����Ľ�ɫ
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ�������Ľ�ɫ
	 * @return
	 */
	public List<UmsRole> fetchIncludibleRole(String roleId);

	/**
	 * ��ñ�roleId�����Ľ�ɫ
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ�������Ľ�ɫ
	 * @return
	 */
	public List<UmsRole> fetchAssociatedRole(String roleId);

	/**
	 * ���roleId�ľۺϽ�ɫԪ��
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UmsRole> fetchPolymericRole(String roleId);
	
	/**
	 * �ƶ���ɫ
	 * 
	 * @param ouOri
	 *            ԭʼ���ŵ�OU
	 * @param ouAim
	 *            Ŀ�겿�ŵ�OU
	 * @return
	 */
	public boolean moveRoleDeptOpe(String roleid, String ouOri, String ouAim);

}
