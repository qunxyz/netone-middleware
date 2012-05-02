package com.jl.common.portal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.util.Encryption;
import oe.security3a.sso.util.SyncUserUtil;

public final class SyncPhpCmsUserSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SyncPhpCmsUserSvl() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String ope = request.getParameter(SyncUserUtil._PARAM_OPE);
		// String code = request.getParameter(SyncUserUtil._PAEAM_CODE);
		String usercode = request.getParameter(SyncUserUtil._PARAM_USERCODE);

		if (usercode == null) {
			WebTip.htmlInfo("��Чͬ������", true, response);
			return;
		}

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			EnvService env = (EnvService) RmiEntry.iv("envinfo");
			String url = env.fetchEnvValue("WEBSER_PHPCMS");

			if (SyncUserUtil._PARAM_OPE_DEL.equals(ope)) {
				url = url + "/api.php?op=member_delete&username=" + usercode;
				response.sendRedirect(url);
				return;
			}

			// �������м��ƽ̨���û���Ϣ
			Clerk clerkInfo = rsrmi.loadClerk("0000", usercode);

			String name = clerkInfo.getName();
			name = java.net.URLEncoder.encode(name, "utf-8");
			String password = clerkInfo.getPassword();
			// String key = cupmRmi.fetchConfig("EncrypKey");
			// password = Encryption.encry(password, key, false);// ��ԭ����
			boolean admin = cupmRmi.checkUserPermission("0000", usercode,
					"BUSSENV.BUSSENV.SECURITY.ROLE.CMSADMIN", "1");
			String adminKey = "0";
			if (admin) {
				adminKey = "1";
			}

			if (SyncUserUtil._PARAM_OPE_ADD.equals(ope)) {
				url = url + "/api.php?op=member_add&username=" + usercode
						+ "&password=" + password + "&realname=" + name
						+ "&isadmin=" + adminKey;

			} else if (SyncUserUtil._PARAM_OPE_MOD.equals(ope)) {
//				try {
//					// / ͬ����ϵͳ������
//					String sqlCheck = "select count(*) from t_user Where alias='"
//							+ usercode + "'";
//					String sqlupdate = "update t_user set password='"
//							+ password + "' Where alias='" + usercode + "'";
//					DbTools db = new DbTools();
//					if (db.countData(sqlCheck) == 1) {
//						db.execute(sqlupdate);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

				url = url + "/api.php?op=member_update&username=" + usercode
						+ "&password=" + password + "&realname=" + name
						+ "&isadmin=" + adminKey;
			}
			String rsx = todo(url);
			System.out.println("------ �����Ż��ʻ�" + rsx);

			// WebTip.htmlInfo("ͬ���ɹ�", true, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String todo(String url) {
		try {
			//System.out.println("sync user to php:"+url);
			// ͨѶЭ��
			URL rul = new URL(url);
			// ���������
			URLConnection urlc = rul.openConnection();
			InputStream input = urlc.getInputStream();
			// �������ݽ���
			StringBuffer but = new StringBuffer();
			int read = 0;
			while ((read = input.read()) != -1) {
				but.append((char) read);
			}
			return but.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
