package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * ɾ����Դʱ���жϲ����߶���Դ��Ȩ�� ɾ����Դʱ�漰ɾ��һ�� �� ɾ����� ����ɾ�������Դ��ʱ�����������û�в�����Ȩ�ޣ���ô����ʧ�ܣ���ɾ���κ���Դ
 * 
 * ������־�Ǽ� Mar 7, 2009 2:19:50 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */

public class DeleteSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DeleteSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();
		String userName = oluser.getLoginname();

		// ��ڲ���
		String pagename = request.getParameter("pagename");
		String task = request.getParameter("task");
		// ҳ�����
		String id = request.getParameter("id");
		String delid = request.getParameter("delid");

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// String file = "";
		// ���ɾ��
		String info = "";
		Security sec = new Security(request);
		if ("del".equals(task)) {
			if (request.getParameter("chkid") != null) {
				String str[] = request.getParameterValues("chkid");
				info = "û�ж�������Դ�Ĳ���Ȩ��:";
				boolean hasPermission = true;

				// ����Ƿ񶼾���ɾ����Ȩ��
				for (int i = 0; i < str.length; i++) {
					if (!"0".equals(str[i])) {
						UmsProtectedobject upo = rsrmi.loadResourceById(str[i]);
						// file = upo.getActionurl();
						if (!sec.check(upo.getNaturalname(), LogUtil._DEL)) {
							request.setAttribute("DeleteSuccess", "n");
							info += "[" + upo.getName() + "]";
							hasPermission = false;
						}
					}
				}
				info += ",ɾ������ȡ��";
				// ɾ������,��Ҫ�����жϵõ������е���Դ����ɾ��Ȩ�޵��������ɾ��
				if (hasPermission) {
					for (int i = 0; i < str.length; i++) {
						info = "ɾ���ɹ�";
						if (!"0".equals(str[i])) {
							UmsProtectedobject upo = rsrmi
									.loadResourceById(str[i]);
							// file = upo.getActionurl();
							if (!rsrmi.dropResource(str[i])) {
								request.setAttribute("DeleteSuccess", "n");
								info += "[ʧ��:" + upo.getName() + "]";
								break;
							}
							info += "," + upo.getName();
							sec.log(upo.getNaturalname(), LogUtil._DEL,
									LogUtil._ADD_SUCCESS);
						}
					}
					// ɾ���ļ�
					// if (StringUtils.isNotEmpty(file) &&
					// StringUtils.contains(file,
					// messages.getString("rsLogicPath"))) {
					// String pathRoot = getServletContext().getRealPath("");
					// String dir = pathRoot +
					// messages.getString("rsSaveWebPath");
					// file = StringUtils.substringAfter(file,
					// messages.getString("rsLogicPath"));
					// File delfile = new File(dir + file);
					// delfile.delete();
					// }
					request.setAttribute("DelSuccess", "y");
				}
			}
			UmsProtectedobject f = rsrmi.loadResourceById(id);
			request.setAttribute("upo", f);

			// ����ɾ��
		} else if ("delone".equals(task)) {
			UmsProtectedobject f = rsrmi.loadResourceById(id);
			request.setAttribute("upo", f);
			if (!"0".equals(delid)) {
				UmsProtectedobject upo = rsrmi.loadResourceById(delid);
				// file = upo.getActionurl();
				// ɾ������
				int userAction = sec.getUserAction(upo.getNaturalname());
				if (!sec.check(upo.getNaturalname(), LogUtil._DEL)) {
					info = "û��ɾ��Ȩ��";
				} else {
					if (!rsrmi.dropResource(delid)) {
						info = "ɾ��ʧ��";
					} else {
						sec.log(upo.getNaturalname(), LogUtil._DEL,
								LogUtil._DEL_SUCCESS);
					}
				}
			}
		}
		WebTip.htmlInfoOri("<script>alert('" + info
				+ "');window.close();opener.search();</script>", response);
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

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {

	}

}
