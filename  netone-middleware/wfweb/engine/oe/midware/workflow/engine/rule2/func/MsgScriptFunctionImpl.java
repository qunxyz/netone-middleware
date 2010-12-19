package oe.midware.workflow.engine.rule2.func;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.rule.MsgScriptFunction;
import oe.frame.web.util.WebStr;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.SendMail;
import oe.rmi.message.UmsBussformworklist;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsUser2role;

/**
 * 基于消息的脚本服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class MsgScriptFunctionImpl implements MsgScriptFunction {

	private static Log _log = LogFactory.getLog(MsgScriptFunctionImpl.class);
	private String runtimeid;

	private String workcode;

	public void init(String runtimeid, String workcode) {
		this.workcode = workcode;
		this.runtimeid = runtimeid;
	}

	public void toMail(String mail, String title, String body, String append,
			String buss) {
		SendMail sendmail = null;
		try {
			sendmail = (SendMail) RmiEntry.iv("sendmail");
			sendmail.send(mail, title, body);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			_log.error(e1.getMessage());
		}
	}

	public void toMen(String code, String particiapnt, String title,
			String body, String append, String buss) {
		try {
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			String email = rs.loadClerk(code, particiapnt).getEmail();
			toMail(email, title, body, append, buss);
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
	}

	public void toRole(String code, String rolename, String title, String body,
			String append, String buss) {
		ResourceRmi resourceRmi;
		SendMail sendmail = null;
		try {
			sendmail = (SendMail) RmiEntry.iv("sendmail");
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsRole role = new UmsRole();
			role.setNaturalname(rolename);
			List list = resourceRmi.fetchRole(role, null, null);

			if (list != null && list.size() == 1) {
				UmsRole rolenow = ((UmsRole) list.get(0));

				User2Role usertorole = new User2Role();
				String condition = " and roleid in(" + rolenow.getId() + ")";

				List listMen = resourceRmi.fetchUser2role(code, usertorole,
						null, condition);

				for (Iterator iter = listMen.iterator(); iter.hasNext();) {
					UmsUser2role element = (UmsUser2role) iter.next();
					String usercode = element.getUserid();

					toMen(code, usercode, title, body, append, buss);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}

	public void toDept(String code, String deptname, String title, String body,
			String append, String buss) {

		ResourceRmi resourceRmi;

		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

			Clerk clerk = new Clerk();
			clerk.setExtendattribute(deptname);
			List listMen = resourceRmi.fetchClerk(code, clerk, null, null);

			for (Iterator iter = listMen.iterator(); iter.hasNext();) {
				Clerk element = (Clerk) iter.next();
				String mail = element.getEmail();

				toMail(mail, title, body, append, buss);
			}
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
	}

	public void toGroupFirst(String code, String groupname, String title,
			String body, String append, String buss) {

		ResourceRmi resourceRmi;

		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = new Clerk();
			clerk.setCompany(groupname);
			List list = resourceRmi.fetchClerk(code, clerk, null, null);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Clerk object = (Clerk) iterator.next();
				toMail(object.getEmail(), title, body, append, buss);
			}

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

	}

	public void toGroupSecond(String code, String groupname, String title,
			String body, String append, String buss) {
		ResourceRmi resourceRmi;

		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = new Clerk();
			clerk.setProvince(groupname);
			clerk.setOfficeNO(code);
			List list = resourceRmi.fetchClerk(code, clerk, null, null);

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Clerk object = (Clerk) iterator.next();
				toMail(object.getEmail(), title, body, append, buss);
			}
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

	}

}
