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
				
					clerk.setPassword(BeanUtils.getProperty(user, "passwordx"));// ����
					clerk.setName(BeanUtils.getProperty(user, "name"));// ����
					clerk.setNaturalname(BeanUtils.getProperty(user, "description"));// ����ƴ��
					clerk.setDescription(BeanUtils.getProperty(user, "usercode"));// �ʺ�
					clerk.setCompany(BeanUtils.getProperty(user, "bussiness"));// ������
					clerk.setProvince(BeanUtils.getProperty(user, "major"));// ��Ŀ��
					clerk.setPhoneNO(BeanUtils.getProperty(user, "phone"));// �ƶ��绰
					clerk.setEmail(BeanUtils.getProperty(user, "email"));// �ʼ�;
					clerk.setRemark(BeanUtils.getProperty(user, "otherinfo"));// ��ע
					clerk.setExtendattribute(BeanUtils.getProperty(user, "extendinfo"));// ����Naturalname
					clerk.setDeptment(BeanUtils.getProperty(user, "systemid"));// ��֯��λ
					
//					clerk.setOfficeNO(BeanUtils.getProperty(user, "ids"));//����������  ����λ����
					
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
						clerk.setFaxNO(up.getName());// ��������
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						System.err.println("���Ų�����:" + BeanUtils.getProperty(user, "systemid"));
						clerk.setFaxNO(BeanUtils.getProperty(user, "systemid"));// ����
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
		// ��������
		if (StringUtils.isNotEmpty(clerk.getPassword())) {
			BeanUtils.setProperty(user, "passwordx", clerk.getPassword());
		}
		// ���ò���
		if (clerk.getDeptment() != null) {
			if (StringUtils.isNotEmpty(clerk.getDeptment())) {
				BeanUtils.setProperty(user, "systemid", clerk.getDeptment());
			}
		}
		// �����û���
		if (StringUtils.isNotEmpty(clerk.getName())) {
			BeanUtils.setProperty(user, "name", clerk.getName());
		}
		// �����û�Naturalname
		if (StringUtils.isNotEmpty(clerk.getNaturalname())) {
			BeanUtils.setProperty(user, "description", clerk.getNaturalname());
		}
		// �����û�����
		if (StringUtils.isNotEmpty(clerk.getDescription())) {
			BeanUtils.setProperty(user, "usercode", clerk.getDescription());
		}
		// �����û�������˾
		if (StringUtils.isNotEmpty(clerk.getCompany())) {
			BeanUtils.setProperty(user, "bussiness", clerk.getCompany());
		}
		// �����û���������
		if (StringUtils.isNotEmpty(clerk.getProvince())) {
			BeanUtils.setProperty(user, "major", clerk.getProvince());

		}
		// �����û����ƶ��˻�
		if (StringUtils.isNotEmpty(clerk.getPhoneNO())) {
			BeanUtils.setProperty(user, "phone", clerk.getPhoneNO());

		}
		// �����û����ʼ�
		if (StringUtils.isNotEmpty(clerk.getEmail())) {
			BeanUtils.setProperty(user, "email", clerk.getEmail());

		}
		// �����û��Ĳ�����Ϣ
		if (StringUtils.isNotEmpty(clerk.getRemark())) {
			BeanUtils.setProperty(user, "otherinfo", clerk.getRemark());

		}
		// �����û�����չ����
		if (StringUtils.isNotEmpty(clerk.getExtendattribute())) {
			BeanUtils.setProperty(user, "extendinfo", clerk
					.getExtendattribute());

		}
		// �����û�����֯�������֯��������֯����̨ͨ�����ֵ�����������ĸ����ݿ��е��˻���
		// clerk.getCity();

		BeanUtils.setProperty(user, "statusinfo", UserDao._USER_STATUS_OK);

		if (map != null) {
			map.put("extendinfo", (String) map.get("extendattribute"));
		}
		System.out.println(user.toString());
		return user;

	}

}
