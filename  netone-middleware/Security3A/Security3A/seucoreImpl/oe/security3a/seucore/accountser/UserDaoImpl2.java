package oe.security3a.seucore.accountser;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.directory.SearchControls;

import oe.frame.orm.OrmerEntry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.TCsUser;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.resourceser.ProtectedObjectService;
import oe.security3a.sso.util.Encryption;
import oe.security3a.sso.util.Md5;

/**
 * 用户Dao实现(LDAP)
 * 
 * @author ni.he.qing
 * 
 */
public class UserDaoImpl2 implements UserDao {

	static Md5 md5 = new Md5();

	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

	/**
	 * 判断合成的key是否在jndi文件中存在
	 * 
	 * @param key
	 * @return
	 */
	private boolean existKey(String key) {

		Enumeration<String> enumer = messages.getKeys();
		Boolean result = false;
		while (enumer.hasMoreElements() && !result) {
			if (key.equals(enumer.nextElement())) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 安全检察，在对象中加入用户信息
	 * 
	 * @param obj
	 * @return
	 */
	public boolean checkSecrity(Object obj) {
		throw new RuntimeException("该方法没有实现");
	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            UmsApplication
	 * @return boolean
	 */
	public boolean create(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		Clerk clerk = (Clerk) obj;
//		Clerk user = new Clerk();
//		user.setDescription(clerk.getDescription());
//		user.setOfficeNO(clerk.getOfficeNO());
//
//		String code = clerk.getOfficeNO();
//		String jndix = "_ACCOUNT_" + code;
//
//		if (existKey(jndix)) {
//			String ds = messages.getString(jndix);
//			try {
//				long exist = queryObjectsNumber(user, null, null);
//				if (exist == 1) {
//					throw new RuntimeException("用户名:" + clerk.getDescription()
//							+ " 已存在 !");
//				}
//				if (StringUtils.isNotEmpty(clerk.getDeptment())) {
//					UmsProtectedobject upo = (UmsProtectedobject) pos
//							.fetchDao().loadObject(UmsProtectedobject.class,
//									clerk.getDeptment());
//					if (upo != null) {
//						clerk.setDeptment("ou=" + upo.getNaturalname() + ","
//								+ upo.getParentdir());
//					}
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			clerk.setPassword(md5.calcMD5(messages.getString("initpassword")));
//			return OrmerEntry.fetchOrmer(ds).fetchSerializer().create(clerk);
//		} else {
//			throw new RuntimeException("保存:" + clerk.getDescription() + "出错！");
//		}
		
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		Clerk user = new Clerk();
		user.setDescription(clerk.getDescription());
		long exist = queryObjectsNumber(user, null, null);
		if (exist == 1) {
			throw new RuntimeException("用户名:" + clerk.getDescription()
					+ " 已存在 !");
		}
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
		}
		clerk.setPassword(md5.calcMD5(messages.getString("initpassword")));
		return OrmerEntry.fetchOrmer().fetchSerializer().create(clerk);
	}

	/**
	 * 批量创建对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean creates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Clerk clerk = (Clerk) iter.next();
			if (!this.create(clerk)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            UmsApplication
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		/**
		 * if (obj == null) { return false; } Clerk clerk = (Clerk) obj; String
		 * code = clerk.getOfficeNO(); String jndix = "_ACCOUNT_" + code; String
		 * ds = messages.getString(jndix);
		 * 
		 * Object user = null; try { user =
		 * UserDaoImplReference.buildTCsUser(clerk, null);
		 * BeanUtils.setProperty(user, "statusinfo", UserDao._USER_STATUS_DEL); }
		 * catch (Exception e) { e.printStackTrace(); }
		 * 
		 * return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user);
		 */
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		//这个文件是ldap使用，这里loadObject的第一个参数没有使用，在具体查询时默认是Clerk.class
		clerk = (Clerk) loadObject("", clerk.getDescription());
		return OrmerEntry.fetchOrmer().fetchSerializer().drop(clerk);
	}

	/**
	 * 批量删除对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Clerk clerk = (Clerk) iter.next();
			if (!this.drop(clerk)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 刷新对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * @param key
	 *            Serializable 对象ID
	 */
	public void refreshObject(Object objClass, Serializable key) {
		throw new RuntimeException("该方法没有实现");
	}

	public boolean serial(Object obj) {
		throw new RuntimeException("该方法没有实现");
	}

	/**
	 * 更新对象
	 * 
	 * @param obj
	 *            UmsApplication
	 * @return boolean
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().update(clerk);
	}

	/**
	 * 批量更新对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Clerk clerk = (Clerk) iter.next();
			if (!this.update(clerk)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * 
	 * 
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public Object loadObject(String code, Serializable key) {
		try {
			Clerk clerk = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister()
					.loadObject(Clerk.class, key);
			UmsProtectedobject up = (UmsProtectedobject) OrmerEntry
					.fetchOrmer().fetchQuerister().loadObject(
							UmsProtectedobject.class, clerk.getDeptment());
			clerk.setFaxNO(up.getName());
			return clerk;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            UmsApplication
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		return queryObjects(obj, comparisonKey, null);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            UmsApplication
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey, String conditionPre) {
		if (obj == null) {
			return null;
		}
		Clerk clerk = (Clerk) obj;
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String
					.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		return UserDaoImplReference2.buildClerkList(OrmerEntry.fetchOrmer()
				.fetchQuerister().queryObjects(clerk, comparisonKey,
						conditionPre));
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            UmsApplication
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to) {
		return queryObjects(obj, comparisonKey, from, to, null);
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            UmsApplication
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准不含Where的查询条件,查询字段名可参考查询对象中的get/set中的属性
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) {
		if (obj == null) {
			return null;
		}
		Clerk clerk = (Clerk) obj;
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String
					.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		return UserDaoImplReference2.buildClerkList(OrmerEntry.fetchOrmer()
				.fetchQuerister().queryObjects(clerk, comparisonKey, from, to,
						conditionPre));
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param Object
	 *            UmsApplication
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		List list = queryObjects(obj, comparisonKey);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            UmsApplication
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	/**
	 * 注销
	 * 
	 * @param name
	 * @return
	 */
	public boolean logout(String name) {
		return false;
	}

	/**
	 * 登陆验证
	 * @param code 用户四位代码 ，LDAP中没有使用
	 * @param name 用户名
	 * @param pass 密码
	 * @return clerk 其中operationinfo字段是返回验证的错误信息 算法：<br>
	 *         1）根据传入的name，即cn值，用默认用户登陆查询，获得SearchResult属性 2）从SearchResult中获得dn串
	 *         3）用获得的dn串和传入的密码pass重新连接ldap，若能连上则验证成功，反之失败
	 */
	public Clerk validationUser(String code, String name, String pass) {

		Clerk clerk = new Clerk();
		if (StringUtils.isEmpty(pass)) {
			clerk.setOperationinfo("密码不能为空!");
			return clerk;
		}
		Clerk user = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(Clerk.class, name);
		Md5 md5 = new Md5();
		if (user == null) {
			clerk.setOperationinfo("用户不存在！");
			return clerk;
		} else if (md5.calcMD5(pass).equals(user.getPassword())) {
			return user;
		} else {
			clerk.setOperationinfo("用户密码错误！");
			return clerk;
		}
	}

	public boolean moveUserDept(String loginname, String ouOri, String ouAim) {
		// try {
		// String[] str = new String[2];
		// str[0] = "uid=" + loginname + "," + ouOri;
		// if (StringUtils.isNotEmpty(ouAim)) {
		// UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
		// .loadObject(UmsProtectedobject.class, ouAim);
		// if (upo != null) {
		// ouAim = "uid=" + loginname + ",ou=" + upo.getNaturalname()
		// + "," + upo.getParentdir();
		// }
		// }
		// str[1] = ouAim;
		// if (!str[0].equals(str[1])) {
		// UmsUser2role uuserrole = new UmsUser2role();
		// uuserrole.setUserid(str[0]);
		// List list = OrmerEntry.fetchOrmer().fetchQuerister()
		// .queryObjects(uuserrole, null);
		// OrmerEntry.fetchOrmer().fetchSerializer().update(str);
		// if (list != null && !list.isEmpty()) {
		// List<UmsUser2role> tmplist = new ArrayList<UmsUser2role>();
		// for (Iterator iterator = list.iterator(); iterator
		// .hasNext();) {
		// UmsUser2role ur = (UmsUser2role) iterator.next();
		// ur.setUserid(str[1]);
		// tmplist.add(ur);
		// }
		// OrmerEntry.fetchOrmer().fetchSerializer().updates(tmplist);
		// }
		// }
		// return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new RuntimeException(e.getMessage());
		// }
		return true;
	}
}
