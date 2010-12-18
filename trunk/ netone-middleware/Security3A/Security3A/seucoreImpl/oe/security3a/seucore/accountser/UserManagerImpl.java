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
 * 用户管理接口实现
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
	 * 新建用户
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
	 * 删除用户
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
	 * 获取用户所属的用户组
	 * 
	 * @param userid
	 * @return List<UmsProtectedobject>
	 * @throws Exception
	 *             算法：<br>
	 *             1）根据传入的userid，即cn值，查询UmsUser2dept表，获得deptid
	 *             2）根据得到的deptid到数据库UmsProtectedobject里去查询,然后把里面的元素加入集合
	 */
	public List<UmsProtectedobject> getUserGroups(String userid)
			throws Exception {
		return null;
	}

	/**
	 * 获取用户具有的角色
	 * 
	 * @param userid
	 * @return List<UmsRole>
	 * @throws Exception
	 *             算法：<br>
	 *             1）根据传入的cn值查询数据库，获得UmsUser2role对象集合
	 *             2）用每个集合中的对象id来装载出UmsRole对象，再把结果放入新集合中返回
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
	 * 判断用户是否已经存在
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
	 * 修改用户
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
	 * 重置密码<br>
	 * 注: 该方法中需要调度短信接口发消息给用户
	 * 
	 * @param userid
	 * @return 初始化密码
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
	 * 用户查询接口，
	 * 
	 * @param condi
	 *            这里的条件可以为null,表示查询所有的用户,
	 * @param from
	 *            可以为null
	 * @param to
	 *            可以为null
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
	 * 查询用户结果的数量
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
	 * 更新用户部门关系
	 * 
	 * @param dept
	 *            算法：<br>
	 *            1）根据传入的userid值查询数据库，把所获得的数据全部删除
	 *            2）根据传入的UmsProtectedobject里包含的deptid与传入的userid构造新的UmsUser2dept对象，加入集合中，再一起创建
	 */
	public void groupRelationupdate(String userid, List<UmsProtectedobject> dept) {

	}

	/**
	 * 更新用户角色的关系
	 * 
	 * @param role
	 *            算法：<br>
	 *            1）根据传入的userid值查询数据库，把所获得的数据全部删除
	 *            2）根据传入的UmsRole里包含的roleid与传入的userid构造新的UmsUser2role对象，加入集合中，再一起创建
	 */
	public void roleRelationupdate(String code, String userid,
			List<UmsRole> role) {
		List<User2Role> ulist = new ArrayList<User2Role>();
		User2Role userrole = new User2Role();
		userrole.setUserid(userid);
		userrole.setCode(code);

		// 这里返回的list是和数据库关联的对象，没有code字段
		// 所以在得到后需要转化成User2Role,再进行删除
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