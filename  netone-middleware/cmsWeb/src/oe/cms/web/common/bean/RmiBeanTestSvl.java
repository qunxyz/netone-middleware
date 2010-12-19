package oe.cms.web.common.bean;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.ClassProperty;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;

/**
 * 测试Bean的服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class RmiBeanTestSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RmiBeanTestSvl() {
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
		String beanname = request.getParameter("beanname");
		String beanurl = request.getParameter("beanurl");
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
			SoaBean beaIn = beansv.inParamDescription(beanname);
			SoaBean beaOut = beansv.outParamDescription(beanname);
			if (beaIn != null) {
				testtip = "<h5>入口参数:(" + beaIn.getBelongapp() + beaIn.getName()
						+ ")</h5>";

				testtip += descriptionSoa(beaIn);
			}
			if (beaOut != null) {
				testtip += "<h5>出口参数:(" + beaOut.getBelongapp()
						+ beaOut.getName() + ")</h5>";
				testtip += descriptionSoa(beaOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
			testtip = e.getMessage();
		}
		response.setCharacterEncoding("gbk");
		response.getWriter().print(testtip);
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
