package oe.security4a.web.human;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.util.WebStr;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import oe.security3a.sso.util.SyncUserUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HumanAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		Clerk clerk = (Clerk) RequestUtil.mappingReqParam(Clerk.class, reqmap);

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String loginName = oluser.getLoginname();
		String code = oluser.getBelongto();

		String actionurl = request.getParameter("actionurl");
		actionurl = actionurl == null ? "" : actionurl;

		// �����

		// �������ҳ��ǰ�����ҳ�����ݵĻ�á�
		if ("add".equals(reqmap.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
				request.setAttribute("flag", "add");
				UmsProtectedobject upo = rsrmi.loadResourceByNatural(cupm
						.fetchConfig("ITEMTEAM"));
				List itemList = rsrmi.subResource(upo.getId());
				request.setAttribute("itemList", itemList);
				upo = rsrmi.loadResourceByNatural(cupm.fetchConfig("DEPT"));
				List deptList = rsrmi.subResource(upo.getId());
				request.setAttribute("deptList", deptList);
				saveToken(request);

				// ���Ĭ�ϵĽ�ɫ����ӵ�ҳ����

				if ("dept".equals(request.getParameter("task2"))) {
					Clerk cl = new Clerk();
					String deptid = reqmap.getParameter("id");
					cl.setDeptment(deptid);
					UmsProtectedobject upodept = rsrmi.loadResourceById(deptid);

					cl.setFaxNO(upodept.getName());
					cl.setOfficeNO(code);

					// if ("35".equals(actionurl)) {//ʡ��˾
					// String initphone = cupm.fetchConfig("initphone");
					// String initmail = cupm.fetchConfig("initmail");
					//
					// cl.setPhoneNO(initphone);
					// cl.setEmail(initmail);
					//						
					// String defaultRoleID = "63";//�̶���ʡ��˾��ɫ
					// Long id = Long.valueOf(defaultRoleID);
					// UmsRole role = rsrmi.loadRole(id);
					// List roleList = new ArrayList();
					// roleList.add(role);
					// request.setAttribute("rolelist", roleList);
					//						
					// }else if(actionurl.length()==4){//Ӫ������
					// String defaultRoleID ="61";
					// Long id = Long.valueOf(defaultRoleID);
					// UmsRole role = rsrmi.loadRole(id);
					// List roleList = new ArrayList();
					// roleList.add(role);
					// request.setAttribute("rolelist", roleList);
					// }else if(actionurl.length()==8){
					// String defaultRoleID ="62";
					// Long id = Long.valueOf(defaultRoleID);
					// UmsRole role = rsrmi.loadRole(id);
					// List roleList = new ArrayList();
					// roleList.add(role);
					// request.setAttribute("rolelist", roleList);
					// }

					List roleList = new ArrayList();
					request.setAttribute("rolelist", roleList);

					request.setAttribute("actionurl", upodept.getActionurl());
					request.setAttribute("clerk", cl);
				}

				request.setAttribute("extendattribute", "DEPT");
				if (!"adminx".equals(loginName)) {
					try {
						Clerk user = rsrmi.loadClerk(code, loginName);
						String extend = user.getExtendattribute();
						request.setAttribute("extendattribute", extend);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ִ����Ӳ���
		if ("save".equals(reqmap.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");

				if (StringUtils.isNotEmpty(reqmap.getParameter("newhiddenid"))) {
					clerk.setDeptment(reqmap.getParameter("newhiddenid"));
					UmsProtectedobject upo = rsrmi.loadResourceById(reqmap
							.getParameter("newhiddenid"));
					clerk.setExtendattribute(upo.getNaturalname());
					String loginname = request.getParameter("naturalname")
							.trim();
					clerk.setDescription(loginname);

					// ���Ⱥ��
					String teams = reqmap.getParameter("teams");
					clerk.setProvince(teams);
					if (rsrmi.addClerk(code, clerk)) {

						// ��ӽ�ɫ
						String roles = reqmap.getParameter("roles");
						if (roles != null && !roles.equals("")) {
							String[] role = roles.split("@");
							List<UmsRole> list = new ArrayList<UmsRole>();
							if (role != null && role.length > 0) {
								for (int i = 0; i < role.length; i++) {
									UmsRole urole = new UmsRole();
									urole.setId(new Long(role[i]));
									list.add(urole);
								}
							}
							rsrmi.roleRelationupdate(code, loginname, list);
						}
						CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
						cupmRmi.initCacheUser(loginname);

						// ���д����û�ʱ,�Զ�ͬ���ʺ�
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ADD, code,
								loginname);

						reqmap.setAlertMsg("�½��û��ɹ���");
						request.setAttribute("result", "y");
						OperationLog.info(request, "�½��û�", "�½��û�" + loginname
								+ clerk.getName() + "�ɹ���", true);
					} else {
						reqmap.setAlertMsg("�½��û�ʧ��!�����û������ʺ��Ƿ���ȷ���Ѵ���!");
						request.setAttribute("result", "n");
						OperationLog.info(request, "�½��û�", "�½��û�ʧ��!"
								+ loginname + ",�����û������ʺ��Ƿ���ȷ���Ѵ���!", false);
					}
				} else {
					reqmap.setAlertMsg("���Ų���ȷ,�޷��½��û���");
					request.setAttribute("result", "n");
					OperationLog.info(request, "�½��û�", "���Ų���ȷ,�޷��½��û���", false);
				}
				resetToken(request);

			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("result", "n");
				OperationLog.info(request, "�½��û�", e.getMessage(),false);
			}
		}

		return mapping.getInputForward();
	}
}
