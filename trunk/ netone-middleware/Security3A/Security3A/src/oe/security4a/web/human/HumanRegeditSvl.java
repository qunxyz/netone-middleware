package oe.security4a.web.human;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.util.SyncUserUtil;

import com.octo.captcha.service.CaptchaServiceException;

public class HumanRegeditSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * �жϺϳɵ�key�Ƿ���jndi�ļ��д���
	 * 
	 * @param key
	 * @return //
	 */
	// private boolean existKey(String key) {
	//
	// Enumeration<String> enumer = messages.getKeys();
	// Boolean result = false;
	// while (enumer.hasMoreElements() && !result) {
	// if (key.equals(enumer.nextElement())) {
	// result = true;
	// }
	// }
	// return result;
	// }
	public void doGet(HttpServletRequest req, HttpServletResponse rep) {

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");

			String checknum = req.getParameter("checknum");

			String email = req.getParameter("email");
			String phone = req.getParameter("phone");
			String name = req.getParameter("name");
			String name_zh = req.getParameter("name_zh");
			String remark = req.getParameter("remark");

			// ���������൱����λ����
			String getcode = req.getParameter("code");

			String deptName = req.getParameter("deptName");
			String deptId = req.getParameter("deptId");

			// ------�����֤���Ƿ���ȷ--------------
			Boolean isResponseCorrect = Boolean.FALSE;
			// remenber that we need an id to validate!
			String captchaId = req.getSession().getId();
			// retrieve the response

			// Call the Service method
			try {
				isResponseCorrect = CaptchaServiceSingleton.getInstance()
						.validateResponseForID(captchaId, checknum);
			} catch (CaptchaServiceException e) {
				// should not happen, may be thrown if the id is not valid
			}
			if (!isResponseCorrect.booleanValue()) {
				req.setAttribute("regErrorMsg", "��֤���������");
				req.setAttribute("regSuccess", "n");
				req.setAttribute("name", name);
				req.setAttribute("name_zh", name_zh);
				req.setAttribute("email", email);
				req.setAttribute("phone", phone);
				req.setAttribute("remark", remark);
				req.setAttribute("deptName", deptName);
				req.setAttribute("deptId", deptId);
			}
			// ----------------------------------

			// ��oecϵͳ��ע��һ���û�

			else if ("".equals(name.trim())) {
				req.setAttribute("regErrorMsg", "�ʺŲ���Ϊ��");
				req.setAttribute("regSuccess", "n");
				req.setAttribute("name", name);
				req.setAttribute("name_zh", name_zh);
				req.setAttribute("email", email);
				req.setAttribute("phone", phone);
				req.setAttribute("remark", remark);
				req.setAttribute("deptName", deptName);
				req.setAttribute("deptId", deptId);
			} else {
				// String code = "";// Ĭ��������
				String dept = "";// Ĭ�ϲ���
				String role = null;// Ĭ�Ͻ�ɫ

				String codes = "";//
				try {
					dept = cupm.fetchConfig("GUEST_DEPT");
					role = cupm.fetchConfig("GUEST_DEFAULT_ROLE");
					codes = cupm.fetchConfig("_ACCOUNT");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotEmpty(deptId)) {
					UmsProtectedobject upo = rsrmi.loadResourceById(deptId);
					dept = upo.getNaturalname();
				}

				if (StringUtils.contains(codes, getcode)) {
					// ϵͳ�д����û������������

					Clerk clerk = new Clerk();
					clerk.setActive(false);
					clerk.setFaxNO(dept);// ����
					clerk.setEmail(email);
					clerk.setPhoneNO(phone);
					clerk.setName(name_zh);
					clerk.setNaturalname(name);
					clerk.setRemark(remark);
					clerk.setDescription(name);// usercode

					// ����
					clerk.setDeptment(deptId);// �û���systemid
					clerk.setExtendattribute(dept);// ���ŵ�naturename

					boolean result = rsrmi.addClerk(getcode, clerk);
					List roleList = new ArrayList();
					if (role != null && !role.equals("")) {
						try {
							Long id = Long.valueOf(role);
							UmsRole umsrole = rsrmi.loadRole(id);
							roleList.add(umsrole);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (result) {
						rsrmi.roleRelationupdate(getcode, name, roleList);
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ADD, getcode,
								name);
						// req.setAttribute("regSuccessMsg",
						// "�û�����"+name+"\n���룺admin@1234\n��λ���룺"+getcode);
						req.setAttribute("regSuccess", "y");
						req.setAttribute("name", name);
						req.setAttribute("name_zh", name_zh);
						req.setAttribute("email", email);
						req.setAttribute("phone", phone);
						req.setAttribute("remark", remark);
						req.setAttribute("deptName", deptName);
						req.setAttribute("deptId", deptId);
					} else {
						req.setAttribute("regErrorMsg", "�û��Ѿ�����");
						req.setAttribute("regSuccess", "n");
						req.setAttribute("name", name);
						req.setAttribute("name_zh", name_zh);
						req.setAttribute("email", email);
						req.setAttribute("phone", phone);
						req.setAttribute("remark", remark);
						req.setAttribute("deptName", deptName);
						req.setAttribute("deptId", deptId);
					}
				} else {
					// ϵͳ�в������û�ѡ��Ĺ�������
					req.setAttribute("regErrorMsg", "��ѡ��Ĺ������򲻴���,ע��ʧ��");
					req.setAttribute("regSuccess", "n");
					req.setAttribute("name", name);
					req.setAttribute("name_zh", name_zh);
					req.setAttribute("email", email);
					req.setAttribute("phone", phone);
					req.setAttribute("remark", remark);
					req.setAttribute("deptName", deptName);
					req.setAttribute("deptId", deptId);

				}
			}

			req.getRequestDispatcher("HumanRegeditSelf.jsp").forward(req, rep);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse rep) {
		doGet(req, rep);
	}

}
