package oe.cms.web.common.bean;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.mid.soa.bean.BeanMonitor;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.ClassProperty;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 展现Bean的描述属性服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class RmiBeanViewSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RmiBeanViewSvl() {
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

		String id = request.getParameter("id");
		String beanname = null;
		String beanurl = null;
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject pnj = (UmsProtectedobject) resourceRmi
					.loadResourceById(id);
			String extet = pnj.getExtendattribute();
			if (extet == null) {
				return;
			}
			String[] extarr = extet.split("#");
			if (extarr.length != 2) {
				return;
			}
			beanname = extarr[1];
			beanurl = extarr[0];
		} catch (NotBoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		String testtip = "";

		BeanService beansv = null;
		try {
			beansv = (BeanService) RmiEntry.ivCore(beanurl);
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			testtip = e1.getMessage() + "未绑定 ";
			WebTip.htmlInfo(testtip, true, response);
			return;
		} catch (ConnectException e2) {
			testtip = e2.getMessage() + "无法连接 ";
			WebTip.htmlInfo(testtip, true, response);
			return;
		}
		try {
			String beanmonitorurl = StringUtils.substringBeforeLast(beanurl,
					"/")
					+ "/beanmonitor";
			BeanMonitor bmoir = (BeanMonitor) RmiEntry.ivCore(beanmonitorurl);
			String release = request.getParameter("release");
			if (release != null && !release.equals("")) {
				bmoir.initSession(new String[] { release });
			}
			List list = bmoir.beanSessionMonitor(beanname);
			request.setAttribute("list", list);
			request.setAttribute("id", id);
		} catch (Exception e) {
			e.printStackTrace();
			testtip = e.getMessage();
		}
		request.getRequestDispatcher("/bean/beanmonitor.jsp").forward(request,
				response);
	}

	private String descriptionSoa(SoaBean bea) {
		Map map = bea.getClassproperty();
		StringBuffer tip = new StringBuffer();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			ClassProperty pre = (ClassProperty) map.get(key);
			tip.append("[" + pre.getColumn() + "," + pre.getName() + ","
					+ pre.getType() + "]<br>");
		}
		return tip.toString();
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
