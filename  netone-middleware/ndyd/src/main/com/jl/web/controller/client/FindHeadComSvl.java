package com.jl.web.controller.client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 创建经销商时，自动寻找其上级营销部门
 * 
 * @author Administrator
 * 
 */
public class FindHeadComSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FindHeadComSvl() {
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
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		
		String naturalname = request.getParameter("naturalname");
		String names[] = StringUtils.split(naturalname, ".");

		String topHeadCom = null;
		if (names.length < 4) {
			return;
		}

		try {
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			UmsProtectedobject clientdept = rs
			.loadResourceByNatural(naturalname);
			if(!"jxs".equals(clientdept.getObjecttype())){
				//选择的部门不是经销商公司
				response.getWriter().print("-1");
				return;
			}
			

			// 营销公司就是最顶级
			topHeadCom = names[0] + "." + names[1] + "." + names[2];
			UmsProtectedobject topHeadComobj = rs
					.loadResourceByNatural(topHeadCom);
			String headHeadComName = topHeadComobj.getName();
			// 寻找营销部门
			UmsProtectedobject headComObj = null;
			String headComName = null;
			String headCom = StringUtils.substringBeforeLast(naturalname, ".");
			for (int i = 0; i < names.length - 4; i++) { // 循环次数
				headComObj = rs
						.loadResourceByNatural(headCom);
				// 找到营销部门类型的部门
				if ("yxbm".equals(headComObj.getObjecttype())) {
					headComName = headComObj.getName();
					break; // 从后往前找到最近的一个即可完成
				}
				// 继续从后往前找可能营销部门
				headCom = StringUtils.substringBeforeLast(headCom, ".");
			}

			Clerk clerk = new Clerk();
			clerk.setExtendattribute(headCom);

			List list = rs.fetchClerk("0000", clerk, null, null);
			String headComMen = "";
			String headComMenNatualname = "";
			String headComCode = "";
			if (list.size() > 0) {
				Clerk nowClerk = ((Clerk) list.get(0));
				headComMen = nowClerk.getName();
				headComMenNatualname = nowClerk.getNaturalname();
				headComCode = headComObj.getActionurl();
			}

			String outinfo = headCom + "#" + headComName + "#" + topHeadCom
					+ "#" + headHeadComName + "#" + headComMenNatualname + "#"
					+ headComMen + "#" + headComCode+"%9%9X"+clientdept.getExtendattribute();

			response.setContentType("text/html;charset=gbk");
			response.getWriter().print(outinfo);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
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

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
