package oe.cms.web.common;

import java.io.IOException;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.web.common.bean.ScriptUtils;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 根据模板创建 模板的应用表单（在Portal的项中使用）
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class TemplateExeInItemSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public TemplateExeInItemSvl() {
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
		// 该项 所隶属的页ID
		String belongcellid = request.getParameter("id");
		// 该项的 应用脚本类型（函数名）
		String naturalnamefunc = request.getParameter("naturalnamefunc");

		String naturalname = request.getParameter("naturalname");
		String cname = request.getParameter("cname");
		String cachecycle = request.getParameter("cachecycle");
		String extvalue = naturalname + "," + cname + "," + cachecycle;

		ResourceRmi resourceRmi;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi
					.loadResourceByNatural(naturalnamefunc);

			String jppScriptid = upo.getExtendattribute();

			response.setCharacterEncoding("GBK");
			response.getWriter().print(
					ScriptUtils.addScriptView(jppScriptid, naturalnamefunc,
							"/cmsWeb/servlet/AjaxPageItemSvl", belongcellid,
							extvalue));
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
	 *             if an error occure
	 */
	public void init() throws ServletException {

	}

}
