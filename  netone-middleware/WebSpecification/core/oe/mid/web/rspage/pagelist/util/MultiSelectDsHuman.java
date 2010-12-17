package oe.mid.web.rspage.pagelist.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.Role;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsUser2role;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.beanutils.BeanUtils;



/**
 * ����ѡ���������Դʵ��,������Ҫ���ݸ�AjaxServiceSvl?class= ����ʹ�� <br>
 * ������Ҫ�������Աѡ���ʵ��
 * 
 * @author chen.jia.xun
 * 
 */
public class MultiSelectDsHuman implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap map)
			throws Exception {
		
		
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();
		
		
		ResourceRmi rsrmi = null;
		StringBuffer sb = new StringBuffer();

		String selectColumn = request.getParameter("selectvalue");
		if (selectColumn == null || selectColumn.equals("")) {
			selectColumn = "naturalname";
		}

		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = new Clerk();
			clerk.setOfficeNO(code);
			clerk.setDeptment(map.getParameter("id"));
			List<Clerk> list = rsrmi.fetchClerk(code, clerk, null, null);
			for (Clerk cl : list) {
				StringBuffer but = new StringBuffer();
				Role[] role = cl.getRoles();
				if (role != null && role.length > 0) {
					for (int i = 0; i < role.length; i++) {
						but.append("[" + role[i].getName() + "]");
					}
				}
				sb.append("name=" + cl.getName() + but + ","); // ����
				String naturalname = null;
				try {
					naturalname = BeanUtils.getProperty(cl, selectColumn);
				} catch (Exception e) {
					naturalname = cl.getNaturalname();
				}
				sb.append("naturalname=" + naturalname + ";"); // }

				// sb.append("id=" + cl.getId() + ",");
				// sb.append("province="
				// + StringUtil.getNotNullStr(cl.getProvince()) + ","); // ��Ա����
				// sb.append("faxNO=" + StringUtil.getNotNullStr(cl.getFaxNO())
				// + ","); // ����
				// sb.append("phoneNO="
				// + StringUtil.getNotNullStr(cl.getPhoneNO()) + ","); // �ƶ��绰
				// sb.append("company="
				// + StringUtil.getNotNullStr(cl.getCompany()) + ","); // ְ��
				// sb.append("description="
				// + StringUtil.getNotNullStr(cl.getDescription()) + ";"); // �ʺ�
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private String roleInfo(String code, Clerk clerk) {
		User2Role xxx = new User2Role();
		xxx.setUserid(clerk.getNaturalname());
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			List rolex = rsrmi.fetchUser2role(code, xxx, null, "");
			StringBuffer but = new StringBuffer();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
