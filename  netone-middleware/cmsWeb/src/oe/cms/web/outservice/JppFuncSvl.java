package oe.cms.web.outservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.web.common.bean.ScriptUtils;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 根据naturalname 来获得 脚本函数名 ，该服务目前被流程SOA配置中选择业务元时使用
 * 
 * @author chen.jia.xun
 * 
 */
public class JppFuncSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public JppFuncSvl() {
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
		response.setCharacterEncoding("GBK");
		response.setContentType("html/text");
		String name = request.getParameter("name");
		name=StringUtils.replace(name, "?", "");
		ResourceRmi resourceRmi;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceByNatural(name);

			String jppScriptid = upo.getExtendattribute();

			
			String script = ScriptUtils.scriptExpress(jppScriptid);
			response.getWriter().write(script);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write("<!--脚本读取错误-->");
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
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
