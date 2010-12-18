package oe.security3a.seucore.roleser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsUser2role;

import org.apache.commons.beanutils.BeanUtils;

/**
 * User2Role和UmsUser2Role之间的转化
 * 
 * @author wsz
 * 
 */
public class RoleDaoImplUserReference {

	// 把user2role 转化成对应的UmsUser2Role
	public static Object ConverToUmsUser2Role(Object obj) {

		Object user2role = null;
		try {
			String id = BeanUtils.getProperty(obj, "id");
			String userid = BeanUtils.getProperty(obj, "userid");
			String roleid = BeanUtils.getProperty(obj, "roleid");

			user2role = new UmsUser2role();
			if (id != null)
				BeanUtils.setProperty(user2role, "id", id);
			if (userid != null)
				BeanUtils.setProperty(user2role, "userid", userid);
			if (roleid != null)
				BeanUtils.setProperty(user2role, "roleid", roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user2role;
	}

	// 把UmsUser2Role_List转化成User2Role_List
	public static List<User2Role> ConverToUser2RoleList(String code, List list) {
		List<User2Role> user2roles = new ArrayList<User2Role>();
		if (list != null && list.size() > 0) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object obj = null;
				User2Role user2role = new User2Role();
				try {

					obj = iter.next();

					user2role.setCode(code);
					Long id = Long.valueOf(BeanUtils.getProperty(obj, "id"));
					user2role.setId(id);
					user2role.setRoleid(BeanUtils.getProperty(obj, "roleid"));
					user2role.setUserid(BeanUtils.getProperty(obj, "userid"));

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				user2roles.add(user2role);
			}
		}
		return user2roles;
	}
	

}
