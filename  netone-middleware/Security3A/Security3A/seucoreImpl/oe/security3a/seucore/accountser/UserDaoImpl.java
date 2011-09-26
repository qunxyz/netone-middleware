package oe.security3a.seucore.accountser;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.frame.orm.OrmerEntry;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.TCsUser;
import oe.security3a.sso.LoginInfo;
import oe.security3a.sso.util.Encryption;
import oe.security3a.sso.util.Md5;
import oe.security4a.severlet.MD5Util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 用户Dao实现(关于UmsUser2dept)
 * 
 * @author ni.he.qing
 * 
 */
public class UserDaoImpl implements UserDao, LoginInfo {

	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

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
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();

		String jndix = "_ACCOUNT_" + code;

		if (existKey(jndix)) {
			String ds = messages.getString(jndix);
			try {
				Object quser = new TCsUser();
				String condition = " and usercode='" + clerk.getDescription()
						+ "'";
				long exist = OrmerEntry.fetchOrmer(ds).fetchQuerister()
						.queryObjectsNumber(quser, null, condition);
				if (exist == 1) {
					// throw new RuntimeException("用户名:" +
					// clerk.getDescription()
					// + " 已存在 !");
					return false;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			String key = messages.getString("EncrypKey");
			String inispassword = messages.getString("initpassword");

			String encryptionMode = "default";
			try {
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
				encryptionMode=cupm.fetchConfig("EncryptionMode");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if ("md5".equals(encryptionMode)) {
				try {
					clerk.setPassword(MD5Util.MD5_UTF16LE(inispassword));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				clerk.setPassword(Encryption.encry(inispassword, key, true));
			}

			Object user = null;
			try {
				user = UserDaoImplReference.buildTCsUser(clerk, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return OrmerEntry.fetchOrmer(ds).fetchSerializer().create(user);
		} else {
			throw new RuntimeException("保存:" + clerk.getDescription() + "出错！");
		}
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
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);

		String delmode = messages.getString("delmode");
		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("real".equals(delmode)) { // 物理删除
			return OrmerEntry.fetchOrmer(ds).fetchSerializer().drop(user);
		} else { // 逻辑删除
			try {
				BeanUtils.setProperty(user, "statusinfo",
						UserDao._USER_STATUS_DEL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user);
		}

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
		String code = clerk.getOfficeNO();

		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);

		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user);
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
			String jndix = "_ACCOUNT_" + code;
			String ds = messages.getString(jndix);
			TCsUser tcsuser = new TCsUser();
			String userid = key.toString();
			String conditionPre = "and usercode = '" + userid + "'";
			Object user = null;
			List list = OrmerEntry.fetchOrmer(ds).fetchQuerister()
					.queryObjects(tcsuser, null, conditionPre);
			if (!list.isEmpty()) {
				user = list.get(0);
			}
			List<Object> buildlist = new ArrayList<Object>();
			if (user != null) {
				buildlist.add(user);
				return UserDaoImplReference.buildClerkList(buildlist).get(0);
			}
			return null;

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
		return this.queryObjects(obj, comparisonKey, -1, -1, conditionPre);
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
		String code = clerk.getOfficeNO();
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, comparisonKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conditionPre == null) {
			conditionPre = "";
		}
		conditionPre += " and statusinfo!='" + this._USER_STATUS_DEL + "'";
		if (from == -1 || to == -1) {
			return UserDaoImplReference.buildClerkList(OrmerEntry
					.fetchOrmer(ds).fetchQuerister().queryObjects(user,
							comparisonKey, conditionPre));
		} else {
			return UserDaoImplReference.buildClerkList(OrmerEntry
					.fetchOrmer(ds).fetchQuerister().queryObjects(user,
							comparisonKey, from, to, conditionPre));
		}

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
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		if (obj == null) {
			return -1;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, comparisonKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conditionPre == null) {
			conditionPre = "";
		}
		conditionPre += " and statusinfo!='" + this._USER_STATUS_DEL + "'";
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjectsNumber(
				user, comparisonKey, conditionPre);
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
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		return this.queryObjectsNumber(obj, comparisonKey, "");
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
	 * 
	 * @param code
	 *            用户隶属于
	 * @param name
	 *            用户名
	 * @param pass
	 *            密码
	 * @return clerk 其中operationinfo字段是返回验证的错误信息 算法：<br>
	 *         1）根据传入的name，即cn值，用默认用户登陆查询，获得SearchResult属性 2）从SearchResult中获得dn串
	 *         3）用获得的dn串和传入的密码pass重新连接ldap，若能连上则验证成功，反之失败
	 */
	public Clerk validationUser(String code, String name, String pass) {
		Clerk clerk = new Clerk();
		if (StringUtils.isEmpty(pass)) {
			clerk.setOperationinfo(_ERROR_3[0]);
			return clerk;
		}

		String jndix = "_ACCOUNT_" + code;

		if (existKey(jndix)) {
			try {
				String ds = messages.getString(jndix);
				Object quser = new TCsUser();
				String condition = " and usercode='" + name + "'";
				long exist = OrmerEntry.fetchOrmer(ds).fetchQuerister()
						.queryObjectsNumber(quser, null, condition);

				if (exist == 0) {
					clerk.setOperationinfo(_ERROR_5[0]);
					return clerk;

				} else {
					Object user = OrmerEntry.fetchOrmer(ds).fetchQuerister()
							.loadObject(TCsUser.class, name);
					String key = messages.getString("EncrypKey");

					String oripassword = Encryption.encry(BeanUtils
							.getProperty(user, "passwordx"), key, false);
					if (pass.equals(oripassword)) {
						List<Object> list = new ArrayList<Object>();
						list.add(user);
						clerk = UserDaoImplReference.buildClerkList(list)
								.get(0);
						return clerk;
					} else {
						clerk.setOperationinfo(_ERROR_6[0]);
						return clerk;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				clerk.setOperationinfo(_ERROR_6[0]);
				return clerk;
			}
		} else {
			clerk.setOperationinfo(_ERROR_7[0]);
			return clerk;
		}

	}

	public boolean moveUserDept(String loginname, String ouOri, String ouAim) {
		return true;
	}
}
