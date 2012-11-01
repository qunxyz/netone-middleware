package oe.security4a.web.selfmodify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.util.IdServer;
import oe.frame.web.WebCache;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
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
import oe.security4a.severlet.MD5Util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 人员信息的操作，包括 个人密码修改、导出 个人信息的查看（个人基本信息、人员角色、权限列表）
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
		
		String loginName =null;
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			Clerk clerk = null;

			if (oluser != null) {
				loginName = oluser.getLoginname();
				
				String code = oluser.getBelongto();
				String flag = "";
				flag = request.getParameter("flag");
				String task = reqmap.getParameter("task");

				if (null == task) {
					task = "";
				}
				if (flag != null && "me".equals(flag.trim())) {
					// 进入个人信息查看，这个页面只是做显示

					// 查询当前在线用户的信息
					clerk = rmi.loadClerk(code, loginName);
					try {
						List<UmsRole> userroles = rmi.getUserRoles(code, clerk
								.getDescription());
						request.setAttribute("userroles", userroles);
					} catch (Exception e) {
						e.printStackTrace();
						reqmap.setAlertMsg(e.getMessage());
						OperationLog.info(request, "个人信息修改" + clerk.getName()
								+ clerk.getNaturalname(), e.getMessage(), true);
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
					// 进入个人信息查看页面后，需要查看该用户的权限列表
					// 这里把查看个人的权限分开来，因为之前把权限类表罗列在个人信息页面上导致数据过多阻塞
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
					request.setAttribute("clerk", clerk);
					request.setAttribute("usergroups", usergroups);
					request.setAttribute("task", "second");
					WebCache.removeCache("USERX_"+loginName);
					return mapping.findForward("permission");
				} else if (flag != null && "pass".equals(flag.trim())) {
					// 进入修改密码的界面，需要获得用户的相关基本信息，默认显示在修改页面上。
					clerk = rmi.loadClerk(code, loginName);
					String truenamex = clerk.getName();
					String outemail = clerk.getEmail();// 外部邮箱
					request.setAttribute("clerk", clerk);
					request.setAttribute("truenamex", truenamex);
					request.setAttribute("outemail", outemail);
					request.setAttribute("flag", request.getParameter("flag"));
				} else if ("output".equals(task.trim())) {
					// 进入密码修改界面后，要以xecel导出相关的个人信息
					change(code, loginName, rmi, cupmRmi, response);
				} else if ("modify".equals(task.trim())) {
					
					String types=reqmap.getParameter("types");
					// 进入密码修改界面后，提交修改密码的信息
					clerk = (Clerk) RequestUtil.mappingReqParam(Clerk.class,
							request);
					clerk.setDeptment(reqmap.getParameter("department0"));
					String oldpass = reqmap.getParameter("oldpass");
					String newpass = reqmap.getParameter("newpass");
					String pass = reqmap.getParameter("pass");
				
					if(StringUtils.isEmpty(oldpass)&&StringUtils.isEmpty(newpass)&&StringUtils.isEmpty(pass)){
						//有时用户可以不用去修改密码或邮件
						Clerk newclerk = rmi.loadClerk(code, loginName);
						newclerk.setTypes(types);
						rmi.updateClerk(code, newclerk);
						reqmap.setAlertMsg("个人信息修改成功！");
						OperationLog.info(request, "个人信息修改",
								 "个人信息修改成功！", true);
					}else{

					String truenamex = reqmap.getParameter("truenamex");
					String outemail = reqmap.getParameter("outemail");
					if (StringUtils.isNotEmpty(oldpass)
							&& StringUtils.isNotEmpty(newpass)
							&& newpass.equals(pass)
							&& StringUtils.isNotEmpty(truenamex)
							&& StringUtils.isNotEmpty(outemail)) {
						try {
							clerk = this.passwordMode( oldpass, loginName);
							if (clerk.getDescription() != null) {
								clerk.setPassword(this.makePassword(
										newpass));// 修改密码
								clerk.setName(truenamex);// 修改真实名称
								clerk.setEmail(outemail);// 修改邮件
								clerk.setTypes(types);
								if (rmi.updateClerk(code, clerk)) {
									try {
																				
										cupmRmi.initCacheUser(clerk
												.getDescription());
										// 修改密码时,自动同步帐号
										rmi.SyncUser(
												SyncUserUtil._PARAM_OPE_MOD,
												code, clerk.getDescription());
										reqmap.setAlertMsg("个人信息修改成功！");
										OperationLog.info(request, "个人信息修改",
												truenamex + "个人信息修改成功！", true);
									} catch (Exception e) {
										e.printStackTrace();
										reqmap.setAlertMsg(e.getMessage());
										OperationLog.info(request, "个人信息修改", e
												.getMessage(), false);
									}
								} else {
									reqmap.setAlertMsg("个人信息修改失败！");
									OperationLog.info(request, "个人信息修改",
											truenamex + "个人信息修改失败！", false);
								}
							} else {
								reqmap.setAlertMsg("原密码错误！");
								OperationLog.info(request, "个人信息修改", truenamex
										+ "个人信息修改失败！", false);
							}
						} catch (Exception e) {
							reqmap.setAlertMsg("旧密码输入错误,个人信息修改失败！");
							OperationLog.info(request, "个人信息修改", truenamex
									+ "旧密码输入错误,个人信息修改失败！", false);
						}
					} else {
						reqmap.setAlertMsg("信息输入不完整,个人信息修改失败！");
						OperationLog.info(request, "个人信息修改", truenamex
								+ "信息输入不完整,个人信息修改失败！", false);
					}
					

					
					}
					Clerk newclerk = rmi.loadClerk(code, loginName);
					request.setAttribute("clerk", clerk);
					request.setAttribute("truenamex", newclerk.getName());
					request.setAttribute("outemail", newclerk.getEmail());
					request.setAttribute("flag", "pass");
				}
			} else {
				// 用户没有登录
				reqmap.setAlertMsg("您的登录已经过期，请重新登录！");
				OperationLog.info(request, "个人信息修改", "您的登录已经过期，请重新登录！", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		WebCache.removeCache("USERX_"+loginName);
		return mapping.findForward("index");
	}

	private Clerk passwordMode(String password, String username)
			throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		String encryptionMode = "default";
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			encryptionMode=cupm.fetchConfig("EncryptionMode");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("md5".equalsIgnoreCase(encryptionMode)) {
			String passwordx = MD5Util.MD5_UTF16LE(password);
			Clerk user = rsrmi.loadClerk("0000", username);
			if (!passwordx.equals(user.getPassword())) {
				user.setDescription(null);
			}
			user.setPassword(passwordx);
			return user;
		} else {
			return rsrmi.validationUserOpe("0000", username, password);
		}
	}

	private String makePassword(String password)
			throws Exception {
		String encryptionMode = "default";
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			encryptionMode=cupm.fetchConfig("EncryptionMode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("md5".equalsIgnoreCase(encryptionMode)) {
			return MD5Util.MD5_UTF16LE(password);
		} else {
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			String key = cupmRmi.fetchConfig("EncrypKey");
			return Encryption.encry(password, key, true);
		}

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
			// 用户基本信息
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
			// 用户角色信息
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
			// 用户权限信息
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
