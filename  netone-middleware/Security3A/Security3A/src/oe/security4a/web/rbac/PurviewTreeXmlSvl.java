package oe.security4a.web.rbac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;


public class PurviewTreeXmlSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public PurviewTreeXmlSvl() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			String userid = request.getParameter("userid");
			List<String> usergroups = new ArrayList<String>();
			UmsProtectedobject ups = rmi.loadResourceByNatural(cupmRmi.fetchConfig("OPE"));
			List levellist = rmi.subResource(ups.getId());
			if (StringUtils.isNotEmpty(userid)) {
				UmsProtectedobject uproobj = new UmsProtectedobject();
				List protectobjlist = rmi.fetchResource(uproobj, null);
				if (protectobjlist != null && protectobjlist.size() > 0 && levellist != null && levellist.size() > 0) {
					for (Iterator iteratorer = protectobjlist.iterator(); iteratorer.hasNext();) {
						UmsProtectedobject proobj = (UmsProtectedobject) iteratorer.next();
						for (Iterator iterator = levellist.iterator(); iterator.hasNext();) {
							UmsProtectedobject level = (UmsProtectedobject) iterator.next();
							String extendattribute = level.getExtendattribute();
							if (cupmRmi.checkUserPermissionCore(code, userid, proobj.getOu(), extendattribute)) {
								usergroups.add(proobj.getName() + " " + level.getName());
							}
						}
					}
				}
			}
			request.setAttribute("usergroups", usergroups);
			request.getRequestDispatcher("../human/humanpermission.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
