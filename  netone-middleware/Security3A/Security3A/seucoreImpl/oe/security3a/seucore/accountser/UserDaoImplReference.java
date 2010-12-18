package oe.security3a.seucore.accountser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.TCsUser;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class UserDaoImplReference {
	
	
	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);
	


	public static List<Clerk> buildClerkList(List list)  {
		List<Clerk> clerklist = new ArrayList<Clerk>();
		if (list != null && list.size() > 0) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object user = null;
				Clerk clerk = new Clerk();
				try {
					
					user = iter.next();
				
					clerk.setPassword(BeanUtils.getProperty(user, "passwordx"));// 密码
					clerk.setName(BeanUtils.getProperty(user, "name"));// 姓名
					clerk.setNaturalname(BeanUtils.getProperty(user, "description"));// 姓名拼音
					clerk.setDescription(BeanUtils.getProperty(user, "usercode"));// 帐号
					clerk.setCompany(BeanUtils.getProperty(user, "bussiness"));// 开发组
					clerk.setProvince(BeanUtils.getProperty(user, "major"));// 项目组
					clerk.setPhoneNO(BeanUtils.getProperty(user, "phone"));// 移动电话
					clerk.setEmail(BeanUtils.getProperty(user, "email"));// 邮件;
					clerk.setRemark(BeanUtils.getProperty(user, "otherinfo"));// 备注
					clerk.setExtendattribute(BeanUtils.getProperty(user, "extendinfo"));// 部门Naturalname
					clerk.setDeptment(BeanUtils.getProperty(user, "systemid"));// 组织单位
					
//					clerk.setOfficeNO(BeanUtils.getProperty(user, "ids"));//设置隶属于  即四位代码
					
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				try {
					if (BeanUtils.getProperty(user, "systemid") != null
							&& BeanUtils.getProperty(user, "systemid").length() > 0) {
						UmsProtectedobject up = (UmsProtectedobject) OrmerEntry
								.fetchOrmer().fetchQuerister().loadObject(
										UmsProtectedobject.class,
										BeanUtils.getProperty(user, "systemid"));
						clerk.setFaxNO(up.getName());// 部门名称
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						System.err.println("部门不存在:" + BeanUtils.getProperty(user, "systemid"));
						clerk.setFaxNO(BeanUtils.getProperty(user, "systemid"));// 部门
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				clerklist.add(clerk);
			}
		}
		return clerklist;
	}

	public static List<Object> buildTCsUserList(List list) {
		List<Object> userlist = new ArrayList<Object>();
		if (list != null && list.size() > 0) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Clerk clerk = (Clerk) iter.next();
				
				String jndix = "_ACCOUNT_" + clerk.getOfficeNO();
				String ds = messages.getString(jndix);
				
				
				
				Object user = null;
				try {
					user = new TCsUser();
					BeanUtils.setProperty(user, "usercode", clerk.getDescription());
					BeanUtils.setProperty(user, "name",clerk.getName() );
					BeanUtils.setProperty(user, "description",clerk.getNaturalname() );
					BeanUtils.setProperty(user, "bussiness",clerk.getCompany() );
					BeanUtils.setProperty(user, "major",clerk.getProvince() );
					BeanUtils.setProperty(user, "phone",clerk.getPhoneNO() );
					BeanUtils.setProperty(user, "email",clerk.getEmail() );
					BeanUtils.setProperty(user, "otherinfo", clerk.getRemark());
					BeanUtils.setProperty(user, "systemid",clerk.getDeptment() );
					BeanUtils.setProperty(user, "extendinfo",clerk.getExtendattribute() );
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotEmpty(clerk.getPassword())) {
					try {
						BeanUtils.setProperty(user, "passwordx",clerk.getPassword() );
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				userlist.add(user);
			}
		}
		return userlist;
	}

	public static Object buildTCsUser(Clerk clerk, Map map)
			throws IllegalAccessException, InvocationTargetException {
		if (clerk == null) {
			return null;
		}
		String jndix = "_ACCOUNT_" + clerk.getOfficeNO();
		String ds = messages.getString(jndix);		
		Object user = null;
		try {
			
			user = new TCsUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置密码
		if (StringUtils.isNotEmpty(clerk.getPassword())) {
			BeanUtils.setProperty(user, "passwordx", clerk.getPassword());
		}
		// 设置部门
		if (clerk.getDeptment() != null) {
			if (StringUtils.isNotEmpty(clerk.getDeptment())) {
				BeanUtils.setProperty(user, "systemid", clerk.getDeptment());
			}
		}
		// 设置用户名
		if (StringUtils.isNotEmpty(clerk.getName())) {
			BeanUtils.setProperty(user, "name", clerk.getName());
		}
		// 设置用户Naturalname
		if (StringUtils.isNotEmpty(clerk.getNaturalname())) {
			BeanUtils.setProperty(user, "description", clerk.getNaturalname());
		}
		// 设置用户描述
		if (StringUtils.isNotEmpty(clerk.getDescription())) {
			BeanUtils.setProperty(user, "usercode", clerk.getDescription());
		}
		// 设置用户隶属公司
		if (StringUtils.isNotEmpty(clerk.getCompany())) {
			BeanUtils.setProperty(user, "bussiness", clerk.getCompany());
		}
		// 设置用户的所属组
		if (StringUtils.isNotEmpty(clerk.getProvince())) {
			BeanUtils.setProperty(user, "major", clerk.getProvince());

		}
		// 设置用户的移动账户
		if (StringUtils.isNotEmpty(clerk.getPhoneNO())) {
			BeanUtils.setProperty(user, "phone", clerk.getPhoneNO());

		}
		// 设置用户的邮件
		if (StringUtils.isNotEmpty(clerk.getEmail())) {
			BeanUtils.setProperty(user, "email", clerk.getEmail());

		}
		// 设置用户的补充信息
		if (StringUtils.isNotEmpty(clerk.getRemark())) {
			BeanUtils.setProperty(user, "otherinfo", clerk.getRemark());

		}
		// 设置用户的扩展属性
		if (StringUtils.isNotEmpty(clerk.getExtendattribute())) {
			BeanUtils.setProperty(user, "extendinfo", clerk
					.getExtendattribute());

		}
		// 设置用户的组织，这个组织是物理组织，后台通过这个值来决定采用哪个数据库中的账户表
		// clerk.getCity();

		BeanUtils.setProperty(user, "statusinfo", UserDao._USER_STATUS_OK);

		if (map != null) {
			map.put("extendinfo", (String) map.get("extendattribute"));
		}
		System.out.println(user.toString());
		return user;

	}

}
