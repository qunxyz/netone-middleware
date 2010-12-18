package oe.security3a.seucore.accountser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.TCsUser;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;

import oe.security3a.seucore.resourceser.ProtectedObjectService;
import oe.security3a.seucore.roleser.RoleDaoImplUserReference;
import oe.security3a.seucore.roleser.RoleService;
import oe.security3a.sso.util.Encryption;

/**
 * �û�����ӿ�ʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class UserManagerImpl implements UserManager {

	private UserService userservice = (UserService) SeuserEntry
			.iv("userService");

	private RoleService roleservice = (RoleService) SeuserEntry
			.iv("roleService");

	private ProtectedObjectService protectedobjectservice = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

	/**
	 * �½��û�
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean createUser(User user) throws Exception {
		Clerk clerk = (Clerk) user;
		return userservice.fetchDao().create(
				UserDaoImplReference.buildTCsUser(clerk, null));
	}

	/**
	 * ɾ���û�
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean delUser(User user) throws Exception {
		Clerk clerk = (Clerk) user;
		return userservice.fetchDao().drop(
				UserDaoImplReference.buildTCsUser(clerk, null));
	}

	/**
	 * ��ȡ�û��������û���
	 * 
	 * @param userid
	 * @return List<UmsProtectedobject>
	 * @throws Exception
	 *             �㷨��<br>
	 *             1�����ݴ����userid����cnֵ����ѯUmsUser2dept�����deptid
	 *             2�����ݵõ���deptid�����ݿ�UmsProtectedobject��ȥ��ѯ,Ȼ��������Ԫ�ؼ��뼯��
	 */
	public List<UmsProtectedobject> getUserGroups(String userid)
			throws Exception {
		return null;
	}

	/**
	 * ��ȡ�û����еĽ�ɫ
	 * 
	 * @param userid
	 * @return List<UmsRole>
	 * @throws Exception
	 *             �㷨��<br>
	 *             1�����ݴ����cnֵ��ѯ���ݿ⣬���UmsUser2role���󼯺�
	 *             2����ÿ�������еĶ���id��װ�س�UmsRole�����ٰѽ�������¼����з���
	 */
	public List<UmsRole> getUserRoles(String code, String userid)
			throws Exception {
		List<UmsRole> rolelist = new ArrayList<UmsRole>();
		User2Role user2role = new User2Role();
		user2role.setCode(code);
		user2role.setUserid(userid);
		List list = roleservice.fetchDao("roleDaouser").queryObjects(user2role,
				null);
		List user2rolelist = RoleDaoImplUserReference.ConverToUser2RoleList(
				code, list);
		for (Iterator iter = user2rolelist.iterator(); iter.hasNext();) {
			User2Role userrole = (User2Role) iter.next();
			try {
				
				List<UmsRole> umsrolelist = new ArrayList<UmsRole>();
				UmsRole ur = new UmsRole();
				String conditionPre = "and id = '" + userrole.getRoleid() + "'";
				umsrolelist = roleservice.fetchDao().queryObjects(ur, null,
						conditionPre);
				if (umsrolelist != null && !umsrolelist.isEmpty()) {
					UmsRole ur1 = umsrolelist.get(0);
					rolelist.add(ur1);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return rolelist;
	}

	/**
	 * �ж��û��Ƿ��Ѿ�����
	 * 
	 * @param loginname
	 * @return
	 * @throws Exception
	 */
	public boolean isUserExist(String code, String loginname) throws Exception {

		Clerk clerk = new Clerk();
		clerk.setDescription(loginname);
		clerk.setOfficeNO(code);

		long number = userservice.fetchDao().queryObjectsNumber(clerk, null);
		if (number > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �޸��û�
	 * 
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public boolean modifyUser(User user) throws Exception {
		Clerk clerk = (Clerk) user;
		return userservice.fetchDao().update(
				UserDaoImplReference.buildTCsUser(clerk, null));
	}

	/**
	 * ��������<br>
	 * ע: �÷�������Ҫ���ȶ��Žӿڷ���Ϣ���û�
	 * 
	 * @param userid
	 * @return ��ʼ������
	 */
	public boolean resetPassword(String code, Clerk clerk) {
		String pass=clerk.getPassword();
		String inispassword = messages.getString("initpassword");
		if(pass!=null&&!pass.equals("")){
			inispassword=pass;
		}
		
		String key = messages.getString("EncrypKey");
		clerk.setPassword(Encryption.encry(inispassword, key, true));
		clerk.setOfficeNO(code);
		return userservice.fetchDao().update(clerk);
	}

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
	public List<Clerk> searchUser(String condi, Integer from, Integer to)
			throws Exception {
		return UserDaoImplReference.buildClerkList(userservice.fetchDao()
				.queryObjects(new TCsUser(), null, from.intValue(),
						to.intValue(), condi));
	}

	/**
	 * ��ѯ�û����������
	 * 
	 * @param condi
	 * @return
	 * @throws Exception
	 */
	public int searchUserCount(String condi) throws Exception {
		return (int) userservice.fetchDao().queryObjectsNumber(new TCsUser(),
				null, condi);
	}

	/**
	 * �����û����Ź�ϵ
	 * 
	 * @param dept
	 *            �㷨��<br>
	 *            1�����ݴ����useridֵ��ѯ���ݿ⣬������õ�����ȫ��ɾ��
	 *            2�����ݴ����UmsProtectedobject�������deptid�봫���userid�����µ�UmsUser2dept���󣬼��뼯���У���һ�𴴽�
	 */
	public void groupRelationupdate(String userid, List<UmsProtectedobject> dept) {

	}

	/**
	 * �����û���ɫ�Ĺ�ϵ
	 * 
	 * @param role
	 *            �㷨��<br>
	 *            1�����ݴ����useridֵ��ѯ���ݿ⣬������õ�����ȫ��ɾ��
	 *            2�����ݴ����UmsRole�������roleid�봫���userid�����µ�UmsUser2role���󣬼��뼯���У���һ�𴴽�
	 */
	public void roleRelationupdate(String code, String userid,
			List<UmsRole> role) {
		List<User2Role> ulist = new ArrayList<User2Role>();
		User2Role userrole = new User2Role();
		userrole.setUserid(userid);
		userrole.setCode(code);

		// ���ﷵ�ص�list�Ǻ����ݿ�����Ķ���û��code�ֶ�
		// �����ڵõ�����Ҫת����User2Role,�ٽ���ɾ��
		List list = roleservice.fetchDao("roleDaouser").queryObjects(userrole,
				null);
		List user2roles = null;
		if (list != null && list.size() > 0) {
			user2roles = RoleDaoImplUserReference.ConverToUser2RoleList(code,
					list);
		}
		if (user2roles != null && user2roles.size() > 0) {
			roleservice.fetchDao("roleDaouser").drops(user2roles);
		}
		if (role != null && role.size() > 0) {
			for (Iterator iter = role.iterator(); iter.hasNext();) {
				UmsRole uurole = (UmsRole) iter.next();
				String id = uurole.getId().toString();
				User2Role newUser2Role = new User2Role();
				newUser2Role.setRoleid(id);
				newUser2Role.setUserid(userid);
				newUser2Role.setCode(code);
				ulist.add(newUser2Role);
			}
			roleservice.fetchDao("roleDaouser").creates(ulist);
		}
	}

	public boolean logoutOpe(String name) {
		return userservice.fetchDao().logout(name);
	}

	public boolean moveUserDeptOpe(String loginname, String ouOri, String ouAim) {
		return userservice.fetchDao().moveUserDept(loginname, ouOri, ouAim);
	}

	public Clerk validationUserOpe(String code, String name, String pass) {
		return userservice.fetchDao().validationUser(code, name, pass);
	}
}