package oe.cms.web.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.dao.infomodel.ModelDao;
import oe.frame.web.util.WebStr;
import oe.mid.io.CommonIoTools;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;


/**
 * Portal 样式服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class AutoStyleSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceBundle messages = ResourceBundle.getBundle("resource",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public AutoStyleSvl() {
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
		String modelid = "";
		String styleinfo = "";
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String name = request.getParameter("name");// 文件名
			if (name != null && !name.equals("")) {
				UmsProtectedobject obj = rsrmi.loadResourceByNatural(name);
				modelid = obj.getExtendattribute();
				ModelDao modeldao = (ModelDao) CmsEntry.fetchDao("modelDao");
				styleinfo = modeldao.view(modelid).getUserid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 页面参数
		styleinfo = StringUtils.substringBetween(styleinfo, "[", "]");
		if (styleinfo == null || styleinfo.equals("")) {
			styleinfo = "CSSFILE.CSSFILE.DEFAULT";
		}

		InputStream input = null;
		OutputStream out = null;
		try {

			UmsProtectedobject upo = rsrmi.loadResourceByNatural(styleinfo);

			String extName = StringUtils.substringAfterLast(
					upo.getObjecttype(), ".");
			extName = StringUtils.substringBefore(extName, "]");

			if (!StringUtils.isEmpty(extName)) {
				extName = "." + extName;
			} else {
				extName = "";
			}
			String path = messages.getString("BinarySavePath");
			String contextpath = messages.getString("BinarySaveContextPath");
			if (contextpath == null || contextpath.equals("")) {
				contextpath = getServletContext().getRealPath("");
			}
			// Binary的存储路径
			String rspath = contextpath + path;
			String filename = upo.getId() + extName;
			String dir = rspath + filename;
			File fileDir = new File(dir);
			if (fileDir.exists()) {
				input = new FileInputStream(fileDir);

				response.setContentType("text/html; charset=GBK");
				response.setContentType("application/x-msdownload");
				response
						.setHeader("Content-Disposition",
								"attachment; filename="
										+ WebStr.gbkToiso8859(filename));
				out = response.getOutputStream();

				CommonIoTools.inputDo(input, out);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
