package oe.mid.netone.moblie;

import java.io.IOException;

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
	 * xuwei  手机注册用户信息
	 */
	public RegeditMvl() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
           
            response.setContentType("text/html;charset=utf-8");
            String clientId = request.getParameter("name");
    		String dispname = request.getParameter("dispname");
    		dispname = WebStr.uTF8Toiso8859(dispname);
    		String email = request.getParameter("email");
    		String mobile = request.getParameter("mobile");
    		String only=request.getParameter("only");
    		
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
    				tip = "false";
    			}

    			Clerk clerk = new Clerk();
    			clerk.setName(WebStr.encode(request, dispname));
    			clerk.setNaturalname(clientId);
    			clerk.setDeptment(upo.getId());
    			clerk.setDescription(clientId);
    			clerk.setEmail(email);
    	        clerk.setRemark(only);
    			clerk.setPhoneNO(mobile);
    			boolean rs = rmi.addClerk("0000", clerk);
    			if (rs) {
    				tip = "true";
    			}
    			response.getWriter().print(tip);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
