package oe.mid.web.rspage.common;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class RsCopySvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public RsCopySvl() {
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
		String thisid = request.getParameter("thisid");
		String selectCopy = request.getParameter("selectcopy");


		Security sec = new Security(request);

		if (selectCopy != null) {
			String[] selectItem = selectCopy.split(",");
			ResourceRmi rsrmi = null;
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int level = 1000;// 可以通过参数控制拷贝的层数的,这个简化了不读取参数，所以默认用个较大的1000表示所有层
			UmsProtectedobject upo = rsrmi.loadResourceById(thisid);
			String parentNatural = upo.getNaturalname();
			copy(rsrmi, sec, selectItem, level, thisid,
					parentNatural);
		}
		WebTip.htmlInfo("拷贝创建成功", false, response);
	}

	public void copy(ResourceRmi rsrmi, Security sec, String[] naturalname,
			int level, String id, 
			String parentNatural) {
		try {

			if (level >= 0 && naturalname.length > 0) {
				for (int i = 0; i < naturalname.length; i++) {
					UmsProtectedobject upo = rsrmi
							.loadResourceByNatural(naturalname[i]);
					String xnat = StringUtils.substringAfterLast(upo
							.getNaturalname(), ".");

					UmsProtectedobject uponext = new UmsProtectedobject();
					BeanUtils.copyProperties(uponext, upo);
					uponext.setNaturalname(xnat);
					uponext.setParentdir(id);
					Serializable idnext = rsrmi.addResource(uponext);

					List list = rsrmi.subResource(upo.getId());
					String[] naturalnameNext = new String[list.size()];
					int loop = 0;
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						naturalnameNext[loop++] = object.getNaturalname();
					}
					// 登记 创建 日志
					sec.log(parentNatural + "." + xnat, LogUtil._ADD,
							LogUtil._ADD_SUCCESS);
					copy(rsrmi, sec, naturalnameNext, --level, idnext
							.toString(),  parentNatural);
				}
			}

		} catch (Exception e) {
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
		// Put your code here
	}

}
