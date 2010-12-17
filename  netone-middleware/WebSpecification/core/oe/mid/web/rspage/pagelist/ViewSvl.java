package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 查看资源，增加权限判断
 *
 * Mar 8, 2009  8:02:05 PM<br>
 *
 * modify wu.shang.zhan<br>
 */
public class ViewSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public ViewSvl() {
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
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// 入口参数
		String pagename = request.getParameter("pagename");

		// 页面参数
		String chkid = request.getParameter("chkid");// 选择id

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}

		UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
		boolean b = true;
		//权限控制，查看需要 3 的权限， 这里做判断是为了增加安全性，防止从地址栏访问
		//考虑几种情况
		//目录
		//需要 3 的权限 ,否则无法查看
		
		
		//文件
		//用户对 文件所在的目录 必须 具有 3 的权限 ,否则无法查看
		
		
		//2009-3-8  去掉上面的做法，改为把文件和文件夹一致对待，鉴权的时候不区分文件和文件夹
		Security sec = new Security(request);
		if(sec.check(upo.getNaturalname(), LogUtil._READ)){
			request.setAttribute("upo", upo);
		}else{
			b=false;
		}
		
			
		// 获得 定制的资源页的地址，默认从配置resourceweb.properties中获得,如果默认中没有那么
		// 转向从资源页中获得
		pagename=pagename + ".view";
		String pageurl = null;
		try {
			pageurl = web.getString(pagename);
		} catch (Exception e) {
		}
		if (pageurl == null) {// 从资源页中获得
			UmsProtectedobject upoPage = null;
			try {
				upoPage = rsrmi.loadResourceByNatural(pagename);
			} catch (Exception e) {

			}
			if (upoPage != null) {
				pageurl = "/" + upoPage.getId() + ".jsp";
			} else {
				response.setCharacterEncoding("GBK");
				WebTip.htmlInfo("pagename：" + pagename + " 无效,非配置信息且非页面信息",
						true, response);
				return;
			}
		}
		if(!b){
			request.setAttribute("viewSuccess", "noPermission");
		}
		request.getRequestDispatcher(pageurl)
				.forward(request, response);
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
