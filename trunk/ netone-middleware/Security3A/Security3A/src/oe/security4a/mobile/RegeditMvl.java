package oe.security4a.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class RegeditMvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RegeditMvl() {
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
			throws ServletException {

		String clientId = request.getParameter("name");
		String dispname = request.getParameter("dispname");
		dispname = WebStr.uTF8Toiso8859(dispname);
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");

		ResourceRmi rmi = null;
		UmsProtectedobject upo = null;
		try {
			rmi = (ResourceRmi) RmiEntry.iv("resource");
			upo = rmi.loadResourceByNatural("DEPT.DEPT");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String orgNaturalname = upo.getNaturalname();
		String tip = "";
		if (upo == null) {
			tip = "lose dir " + orgNaturalname;
		}
		Clerk clerkFind = null;
		try {
			clerkFind = rmi.loadClerk("0000", clientId);
			if (clerkFind != null) {
				tip = "user " + clientId + "has been created";
			}

			Clerk clerk = new Clerk();
			clerk.setName(WebStr.encode(request, dispname));
			clerk.setNaturalname(clientId);
			clerk.setDeptment(upo.getId());
			clerk.setDescription(clientId);
			clerk.setEmail(email);
			clerk.setPhoneNO(mobile);
			boolean rs = rmi.addClerk("0000", clerk);
			if (rs) {
				tip = "y";
			}
			response.getWriter().print(tip);
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private UmsProtectedobject getUmsProtectedobjectByBussid(String bussDirId)
			throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setActionurl(bussDirId);
		List list = rmi.queryObjectProtectedObj(upo, null, 0, 10, "");
		if (list == null || list.size() == 0) {
			throw new Exception("丢失目录,要使用的目录属性为" + bussDirId);
		}
		if (list.size() != 1) {
			throw new Exception("目录定义异常,找到多个可使用的目录，个数为:" + list.size());
		}

		return (UmsProtectedobject) list.get(0);
	}
}
