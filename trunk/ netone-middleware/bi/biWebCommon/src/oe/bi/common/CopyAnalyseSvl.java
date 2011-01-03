package oe.bi.common;

import java.io.IOException;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.wizard.WizardDao;
import oe.frame.orm.util.IdServer;
import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class CopyAnalyseSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CopyAnalyseSvl() {
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
		response.setContentType("text/html;charset=GBK");
		try {
			WizardDao wd = (WizardDao) RmiEntry.iv("bihandle");
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String naturalname = WebStr.iso8859ToGBK(request
					.getParameter("naturalname"));// 分析主题名称
			String naturalname2 = request.getParameter("naturalname2");// 表单名称
			String pagepath = request.getParameter("pagepath");
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(naturalname);
			String lsh = upo.getExtendattribute();
			ChoiceInfo choiceinfo = wd.fromXml(lsh);
			
			String dataModelid =WebStr.encode(request, naturalname2) ;
			choiceinfo.setDataModelid(dataModelid);
			choiceinfo.setTggroup(dataModelid);
			String lshNew = IdServer.uuid();
			choiceinfo.setLsh(lshNew);
			wd.create(choiceinfo, pagepath);
			WebTip.htmlInfo("复制成功[andcode:" + lshNew + "]", true, true,
					response);

		} catch (NotBoundException e) {
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
