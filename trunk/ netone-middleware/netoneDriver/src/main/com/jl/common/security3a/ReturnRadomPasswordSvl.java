package com.jl.common.security3a;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;

import org.apache.commons.lang.StringUtils;

import com.jl.common.message.Message;
import com.jl.common.workflow.DbTools;

/**
 * 提供密码重置，发送短信通知的功能
 * 
 */
public class ReturnRadomPasswordSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ReturnRadomPasswordSvl() {
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
		response.setContentType("gbk");

		String usercode = request.getParameter("usercode");
		//String mobile = request.getParameter("mobile");
		String errortip = "";
		try {
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = rs.loadClerk("0000", usercode);
			if (clerk.getPhoneNO() == null || clerk.getPhoneNO().equals("")) {
				WebTip.htmlInfo("该用户无注册手机号码!", true, response);
				return;
			}
//			if (!clerk.getPhoneNO().equals(mobile)) {
//				WebTip.htmlInfo("输入的手机号码与该用户的注册手机号码不一致!", true, response);
//				return;
//			}
			// 新密码
			String password = StringUtils.substring(UUID.randomUUID()
					.toString().replaceAll("-", ""), 0, 6);
            //System.out.println("PASSWORD="+password);
			clerk.setPassword(MD5Util.MD5_UTF16LE(password));
			rs.updateClerk("0000", clerk);
			Message.toMessageByUser(clerk.getDescription(), "新密码:" + password);

			// 同步旧系统的密码
//			try {
//				String sqlCheck = "select count(*) from t_user Where alias='"
//						+ usercode + "'";
//				String sqlupdate = "update t_user set password='"
//						+ MD5Util.MD5_UTF16LE(password) + "' Where alias='"
//						+ usercode + "'";
//				DbTools db = new DbTools();
//				if (db.countData(sqlCheck) == 1) {
//					System.out.print("--- 重置密码 更新旧系统....");
//					db.execute(sqlupdate);
//					System.out.println("--- done!");
//				} else {
//					System.err.println(usercode + "存在多个别名");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			// 同步php的帐号
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			boolean admin = cupmRmi.checkUserPermission("0000", usercode,
					"BUSSENV.BUSSENV.SECURITY.ROLE.CMSADMIN", "1");
			String adminKey = "0";
			if (admin) {
				adminKey = "1";
			}
			EnvService env = (EnvService) RmiEntry.iv("envinfo");
			String url = env.fetchEnvValue("WEBSER_PHPCMS");
			url = url + "/api.php?op=member_update&username=" + usercode
					+ "&password=" + MD5Util.MD5_UTF16LE(password)
					+ "&realname=" + "&isadmin=" + adminKey;

			String rsx = todo(url);
			System.out.println("------ 重置门户帐户" + rsx);

			WebTip.htmlInfo("新密码已经以短信方式发送至该账户的注册手机中，请您查收!", true, response);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String todo(String url) {
		try {
			// 通讯协议
			URL rul = new URL(url);
			// 获得数据流
			URLConnection urlc = rul.openConnection();
			InputStream input = urlc.getInputStream();
			// 进行数据交换
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
