package oe.bi.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.datasource.SumUtilIfc;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.frame.web.util.WebStr;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class LoadDataSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadDataSvl() {
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
		String naturalname = request.getParameter("naturalname");
		String mode = request.getParameter("mode");
		String value = request.getParameter("value");

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(naturalname);
			DyFormDesignService dfd = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			DyObj objx = dfd.fromDy(upo.getExtendattribute());
			TCsForm tcform = objx.getFrom();
			String formcode = tcform.getFormcode();
			String sql = tcform.getSqlinfo();

			String syntable = tcform.getDescription();
			List columnlist = objx.getColumn();
			StringBuffer cnames = new StringBuffer();
			for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
				TCsColumn column = (TCsColumn) iter.next();
				if (column.isUseable()) {
					String othername = column.getColumnid();
					cnames.append(othername + ",");
				}
			}

			String[] sqls = StringUtils.split(sql, "$");
			Enumeration enu = request.getParameterNames();
			int i = sqls.length - 1;
			for (; enu.hasMoreElements();) {
				String name = (String) enu.nextElement();
				if (name != null && name.startsWith("p_")) {
					String paramvalue = WebStr.iso8859ToGBK(request
							.getParameter(name));
					if (paramvalue != null && paramvalue.equals("")) {
						paramvalue = null;
					} else {
						String paramname = "{" + name.substring(2) + "}";// 就是取p_后的字段名
						for (int j = i; j >= 0; j--) {
							i--;
							if (sqls[j].contains(paramname)) {
								sql = StringUtils.replace(sql, paramname,
										paramvalue);
								break;
							}
						}
					}
				}
			}
			sql = StringUtils.replace(sql, "$", "");

			SumUtilIfc sumutil = null;
			try {
				sumutil = (SumUtilIfc) RmiEntry.iv("biData");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sys = "";
			// 海量添加
			if ("all".equals(mode)) {
				sumutil.deleteTable(syntable);

				sys = sumutil.searchToAdd(sql, cnames.toString(), syntable,
						formcode);

				response.getWriter().print("success all");
				// 根据时间（通过识别时间 来自动添加新数据）
			} else if ("append".equals(mode)) {
				
				sys = sumutil.searchToAdd(sql, cnames.toString(), syntable,
						formcode);

				response.getWriter().print("success append");
			} else if ("attime".equals(mode)) {

				sys = sumutil.searchToAddByTime(sql, cnames.toString(),
						syntable, formcode, value);
				response.getWriter().print("success attime");
				// 自定义后N条
			} else if ("lastn".equals(mode)) {
				sys = sumutil.searchToAddByCount(sql, cnames.toString(),
						syntable, formcode, value);
				response.getWriter().print("success lastn");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
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
