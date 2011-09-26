package oe.security4a.web.human;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUserUtil;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.frame.orm.util.IdServer;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;

public class HumanDelAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();
		if (reqmap.getParameter("chkid") != null) {
			String str[] = reqmap.getParameterValues("chkid");
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				if ("passreset".equals(reqmap.getParameter("task"))) {
					for (int i = 0; i < str.length; i++) {
						Clerk clerk = rsrmi.loadClerk(code, str[i]);
						// �ʻ����ã�ͨ��һ����������ȥ�������룬���ڿ�ͨ�������������ָ�
						if ("yes".equals(reqmap.getParameter("passConfus"))) {
							clerk.setPassword("9$9$");
						} else {

							
							clerk.setPassword(cupmRmi
									.fetchConfig("initpassword"));
						}

						String info = "";
						rsrmi.resetPassword(code, clerk);
						// ��������ʱ,�Զ�ͬ���ʺ�
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_MOD, code,
								str[i]);
						info = "�����ɹ���";
						reqmap.setAlertMsg(info);
						OperationLog.info(request, "��������", clerk.getName()
								+ info,true);
					}
				} else {
					for (int i = 0; i < str.length; i++) {
						if (oluser.getLoginname().equals(str[i])) {
							throw new RuntimeException("������ɾ���Լ�!");
						}
					}
					for (int i = 0; i < str.length; i++) {
						Clerk clerk = rsrmi.loadClerk(code, str[i]);
						User2Role userrole = new User2Role();
						userrole.setUserid(clerk.getDescription());
						List rolelist = rsrmi.fetchUser2role(code, userrole,
								null, null);
						if (rolelist != null && rolelist.size() > 0) {
							rsrmi.dropUserRoles(code, rolelist);
						}
						if (rsrmi.dropClerk(code, str[i])) {
							cupmRmi.initCacheUser(clerk.getDescription());

							// ɾ���û�ʱ,�Զ�ͬ���ʺ�
							rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_DEL, code,
									str[i]);

							reqmap.setAlertMsg("�û�ɾ���ɹ���");
							OperationLog.info(request, "ɾ���û�", clerk.getName()
									+ "�û�ɾ���ɹ���",true);
						} else {
							reqmap.setAlertMsg("�û�ɾ��ʧ�ܣ�");
							OperationLog.info(request, "ɾ���û�", clerk.getName()
									+ "�û�ɾ��ʧ�ܣ�",true);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				OperationLog.info(request, "ɾ���û�", e.getMessage(),true);
			}
		}
		if ("dept".equals(request.getParameter("task2"))) {
			return mapping.findForward("dept");
		}
		return mapping.findForward("humanindex");
	}
}
