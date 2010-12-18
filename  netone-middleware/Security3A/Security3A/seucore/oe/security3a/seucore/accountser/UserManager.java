package oe.security3a.seucore.accountser;

import java.util.List;

import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;


/**
 * �û�ҵ��Ӧ��
 * 
 * @author chen.jia.xun
 * 
 */
public interface UserManager {

	/**
	 * �û���ѯ�ӿڣ�
	 * 
	 * @param condi
	 *            �������������Ϊnull,��ʾ��ѯ���е��û�,
	 * @param from
	 *            ����Ϊnull
	 * @param to
	 *            ����Ϊnull
	 * @return List<UserInfo>
	 * @throws Exception
	 */
	public List<Clerk> searchUser(String condi, Integer from, Integer to) throws Exception;

	/**
	 * ��ѯ�û����������
	 * 
	 * @param condi
	 * @return
	 * @throws Exception
	 */
	public int searchUserCount(String condi) throws Exception;

	/**
	 * �ж��û��Ƿ��Ѿ�����
	 * 
	 * @param loginname
	 * @param code ������
	 * @return
	 * @throws Exception
	 */
	public boolean isUserExist(String code, String loginname) throws Exception;

	/**
	 * �½��û�
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean createUser(User user) throws Exception;

	/**
	 * ɾ���û�
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean delUser(User user) throws Exception;

	/**
	 * �޸��û�
	 * 
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public boolean modifyUser(User user) throws Exception;

	/**
	 * ��ȡ�û����еĽ�ɫ
	 * 
	 * @param code	�û���������
	 * @param userid �û���ID
	 * @return List<Role>
	 * @throws Exception
	 */
	public List<UmsRole> getUserRoles(String code, String userid) throws Exception;

	/**
	 * ��ȡ�û��������û���
	 * 
	 * @param userid
	 * @return List<Group>
	 * @throws Exception
	 */
	public List<UmsProtectedobject> getUserGroups(String userid) throws Exception;

	/**
	 * ��������<br>
	 * ע: �÷�������Ҫ���ȶ��Žӿڷ���Ϣ���û�
	 * 
	 * @param code �û�������
	 * @param clerk Clerk
	 * @return
	 */
	public boolean resetPassword(String code, Clerk clerk);

	
	/**
	 * 
	 * �����û���ɫ�Ĺ�ϵ
	 * @param code �û�������
	 * @param userid �û�id
	 * @param role ��ɫ�б�
	 */
	public void roleRelationupdate(String code, String userid, List<UmsRole> role);

	/**
	 * �����û����Ź�ϵ
	 * 
	 * @param dept
	 */
	public void groupRelationupdate(String userid, List<UmsProtectedobject> dept);

	/**
	 * ��½��֤
	 * 
	 * @param code �û�������
	 * @param name �û���
	 * @param pass ����
	 * @return clerk ����operationinfo�ֶ��Ƿ�����֤�Ĵ�����Ϣ
	 */
	public Clerk validationUserOpe(String code, String name, String pass);

	/**
	 * �ƶ���Ա
	 * 
	 * @param ouOri
	 *            ԭʼ���ŵ�OU
	 * @param ouAim
	 *            Ŀ�겿�ŵ�OU
	 * @return
	 */
	public boolean moveUserDeptOpe(String loginname, String ouOri, String ouAim);

	/**
	 * ע��
	 * 
	 * @param name
	 * @return
	 */
	public boolean logoutOpe(String name);

}
