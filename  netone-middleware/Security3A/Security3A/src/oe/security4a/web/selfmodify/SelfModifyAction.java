package oe.security4a.web.selfmodify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.util.IdServer;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
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
import oe.security3a.sso.util.Encryption;
import oe.security3a.sso.util.SyncUserUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��Ա��Ϣ�Ĳ��������� ���������޸ġ����� ������Ϣ�Ĳ鿴�����˻�����Ϣ����Ա��ɫ��Ȩ���б�
 * 
 * @author Administrator
 * 
 */
public class SelfModifyAction extends Action {

	static Log log = LogFactory.getLog(SelfModifyAction.class);

	public OnlineUserMgr getOnlineUserMgr() {
		return new DefaultOnlineUserMgr();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		OnlineUserMgr olmgr = getOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			Clerk clerk = null;

			if (oluser != null) {
				String loginName = oluser.getLoginname();
				String code = oluser.getBelongto();
				String flag = "";
				flag = request.getParameter("flag");
				String task = reqmap.getParameter("task");
				
				if(null==task){
					task="";
				}
				if (flag != null && "me".equals(flag.trim())) {
					// ���������Ϣ�鿴�����ҳ��ֻ������ʾ

					// ��ѯ��ǰ�����û�����Ϣ
					clerk = rmi.loadClerk(code, loginName);
					try {
						List<UmsRole> userroles = rmi.getUserRoles(code, clerk
								.getDescription());
						request.setAttribute("userroles", userroles);
					} catch (Exception e) {
						e.printStackTrace();
						reqmap.setAlertMsg(e.getMessage());
						OperationLog.error(request, "������Ϣ�޸�", e.getMessage());
					}
					request.setAttribute("clerk", clerk);
					UmsProtectedobject upo = rmi.loadResourceByNatural(cupmRmi
							.fetchConfig("ITEMTEAM"));
					List itemList = rmi.subResource(upo.getId());
					request.setAttribute("itemList", itemList);
					upo = rmi
							.loadResourceByNatural(cupmRmi.fetchConfig("DEPT"));
					List deptList = rmi.subResource(upo.getId());
					request.setAttribute("deptList", deptList);
					request.setAttribute("flag", "me");

				} else if ("permission".equals(task.trim())) {
					// ���������Ϣ�鿴ҳ�����Ҫ�鿴���û���Ȩ���б�
					// ����Ѳ鿴���˵�Ȩ�޷ֿ�������Ϊ֮ǰ��Ȩ����������ڸ�����Ϣҳ���ϵ������ݹ�������
					List<String> usergroups = new ArrayList<String>();
					UmsProtectedobject uproobj = new UmsProtectedobject();
					List protectobjlist = rmi.fetchResource(uproobj, null);
					clerk = rmi.loadClerk(code, loginName);
					UmsProtectedobject ups = rmi.loadResourceByNatural(cupmRmi
							.fetchConfig("OPE"));
					List levellist = rmi.subResource(ups.getId());
					if (protectobjlist != null && protectobjlist.size() > 0
							&& levellist != null && levellist.size() > 0) {
						for (Iterator iteratorer = protectobjlist.iterator(); iteratorer
								.hasNext();) {
							UmsProtectedobject proobj = (UmsProtectedobject) iteratorer
									.next();
							for (Iterator iterator = levellist.iterator(); iterator
									.hasNext();) {
								UmsProtectedobject level = (UmsProtectedobject) iterator
										.next();
								String extendattribute = level
										.getExtendattribute();
								if (cupmRmi.checkUserPermissionCore(code, clerk
										.getDescription(), proobj.getOu(),
										extendattribute)) {
									usergroups.add(proobj.getName() + " "
											+ level.getName());
								}
							}
						}
					}
					request.setAttribute("usergroups", usergroups);
					request.setAttribute("task", "second");
					return mapping.findForward("permission");
				} else if (flag != null && "pass".equals(flag.trim())) {
					// �����޸�����Ľ��棬��Ҫ����û�����ػ�����Ϣ��Ĭ����ʾ���޸�ҳ���ϡ�
					clerk = rmi.loadClerk(code, loginName);
					String truenamex = clerk.getName();
					String outemail = clerk.getEmail();// �ⲿ����
					request.setAttribute("truenamex", truenamex);
					request.setAttribute("outemail", outemail);
					request.setAttribute("flag", request.getParameter("flag"));
				} else if ("output".equals(task.trim())) {
					// ���������޸Ľ����Ҫ��xecel������صĸ�����Ϣ
					change(code, loginName, rmi, cupmRmi, response);
				} else if ("modify".equals(task.trim())) {
					// ���������޸Ľ�����ύ�޸��������Ϣ
					clerk = (Clerk) RequestUtil.mappingReqParam(Clerk.class,
							request);
					clerk.setDeptment(reqmap.getParameter("department0"));
					String oldpass = reqmap.getParameter("oldpass");
					String newpass = reqmap.getParameter("newpass");
					String pass = reqmap.getParameter("pass");
					String truenamex = reqmap.getParameter("truenamex");
					String outemail = reqmap.getParameter("outemail");
					if (StringUtils.isNotEmpty(oldpass)
							&& StringUtils.isNotEmpty(newpass)
							&& newpass.equals(pass)
							&& StringUtils.isNotEmpty(truenamex)
							&& StringUtils.isNotEmpty(outemail)) {
						try {
							clerk = rmi.validationUserOpe(code, loginName,
									oldpass);
							if (clerk.getDescription() != null) {
								String key = cupmRmi.fetchConfig("EncrypKey");
								
								clerk.setPassword(Encryption.encry(newpass,
										key, true));// �޸�����
								clerk.setName(truenamex);// �޸���ʵ����
								clerk.setEmail(outemail);// �޸��ʼ�
								if (rmi.updateClerk(code, clerk)) {
									try {
										cupmRmi.initCacheUser(clerk
												.getDescription());
										// �޸�����ʱ,�Զ�ͬ���ʺ�
										rmi.SyncUser(
												SyncUserUtil._PARAM_OPE_MOD,
												code, clerk.getDescription());
										reqmap.setAlertMsg("������Ϣ�޸ĳɹ���");
										OperationLog.info(request, "������Ϣ�޸�",
												"������Ϣ�޸ĳɹ���");
									} catch (Exception e) {
										e.printStackTrace();
										reqmap.setAlertMsg(e.getMessage());
										OperationLog.error(request, "������Ϣ�޸�", e
												.getMessage());
									}
								} else {
									reqmap.setAlertMsg("������Ϣ�޸�ʧ�ܣ�");
									OperationLog.error(request, "������Ϣ�޸�",
											"������Ϣ�޸�ʧ�ܣ�");
								}
							} else {
								reqmap.setAlertMsg("ԭ�������");
								OperationLog.error(request, "������Ϣ�޸�",
										"������Ϣ�޸�ʧ�ܣ�");
							}
						} catch (Exception e) {
							reqmap.setAlertMsg("�������������,������Ϣ�޸�ʧ�ܣ�");
							OperationLog.error(request, "������Ϣ�޸�",
									"�������������,������Ϣ�޸�ʧ�ܣ�");
						}
					} else {
						reqmap.setAlertMsg("��Ϣ���벻����,������Ϣ�޸�ʧ�ܣ�");
						OperationLog.error(request, "������Ϣ�޸�",
								"��Ϣ���벻����,������Ϣ�޸�ʧ�ܣ�");
					}
					Clerk newclerk = new Clerk();
					newclerk = rmi.loadClerk(code, loginName);
					request.setAttribute("truenamex", newclerk.getName());
					request.setAttribute("outemail", newclerk.getEmail());
					request.setAttribute("flag", "pass");
				}
			} else {
				// �û�û�е�¼
				reqmap.setAlertMsg("���ĵ�¼�Ѿ����ڣ������µ�¼��");
				OperationLog.error(request, "������Ϣ�޸�", "���ĵ�¼�Ѿ����ڣ������µ�¼��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("index");
	}

	public void change(String code, String loginName, ResourceRmi rmi,
			CupmRmi cupmRmi, HttpServletResponse response) {
		InputStream input = null;
		try {
			input = new FileInputStream(getClass()
					.getResource("/ClerkInfo.xls").getPath());
			byte[] byteinfo = new byte[102400];
			int read = input.read(byteinfo);
			String info = new String(byteinfo, 0, read);
			// �û�������Ϣ
			Clerk clerk = rmi.loadClerk(code, loginName);
			info = StringUtils.replace(info, "$namex", clerk.getDescription());
			info = StringUtils.replace(info, "$chinesename", clerk.getName());
			info = StringUtils.replace(info, "$namespell", clerk
					.getNaturalname());
			info = StringUtils.replace(info, "$duty", clerk.getCompany());
			info = StringUtils.replace(info, "$type", clerk.getProvince());
			info = StringUtils.replace(info, "$dept", clerk.getFaxNO());
			info = StringUtils.replace(info, "$mobile", clerk.getPhoneNO());
			info = StringUtils.replace(info, "$mail", clerk.getEmail());
			info = StringUtils.replace(info, "$description", clerk.getRemark());
			// �û���ɫ��Ϣ
			String roles = StringUtils.substringBetween(info, "@$role");
			String tmp = roles;
			List<UmsRole> userroles = rmi.getUserRoles(code, clerk
					.getDescription());
			String str = "";
			if (userroles != null && userroles.size() > 0) {
				int i = 0;
				for (UmsRole role : userroles) {
					if (i == 0) {
						str = role.getName();
						i++;
					} else {
						str = str + "," + role.getName();
					}
				}
			}
			tmp = StringUtils.replace(tmp, "$role", str);
			info = StringUtils.replace(info, roles, tmp);
			info = StringUtils.replace(info, "@$role", "");
			// �û�Ȩ����Ϣ
			String permissions = StringUtils.substringBetween(info,
					"@$permission");
			List<String> usergroups = new ArrayList<String>();
			UmsProtectedobject uproobj = new UmsProtectedobject();
			List protectobjlist = rmi.fetchResource(uproobj, null);
			UmsProtectedobject ups = rmi.loadResourceByNatural(cupmRmi
					.fetchConfig("OPE"));
			List levellist = rmi.subResource(ups.getId());
			if (protectobjlist != null && protectobjlist.size() > 0
					&& levellist != null && levellist.size() > 0) {
				for (Iterator iteratorer = protectobjlist.iterator(); iteratorer
						.hasNext();) {
					UmsProtectedobject proobj = (UmsProtectedobject) iteratorer
							.next();
					for (Iterator iterator = levellist.iterator(); iterator
							.hasNext();) {
						UmsProtectedobject level = (UmsProtectedobject) iterator
								.next();
						String extendattribute = level.getExtendattribute();
						if (cupmRmi.checkUserPermissionCore(code, clerk
								.getDescription(), proobj.getOu(),
								extendattribute)) {
							usergroups.add(proobj.getName() + " "
									+ level.getName());
						}
					}
				}
			}
			StringBuffer sb = new StringBuffer();
			for (String per : usergroups) {
				tmp = permissions;
				tmp = tmp.replace("$permission", per);
				sb.append(tmp);
			}
			info = StringUtils.replace(info, permissions, sb.toString());
			info = StringUtils.replace(info, "@$permission", "");
			response.setContentType("text/html; charset=GBK");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ IdServer.uuid() + ".xls");
			OutputStream out = response.getOutputStream();
			out.write(info.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
