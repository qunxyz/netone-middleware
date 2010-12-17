package oe.mid.web.rspage.pagelist;

import java.io.File;
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

import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;

/**
 * 文件上传，二进制流
 * 
 * 
 * Mar 9, 2009 7:40:28 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */
public class UploadSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceBundle messages = ResourceBundle.getBundle("resource",
			Locale.CHINESE);

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public UploadSvl() {
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
		Security sec = new Security(request);

		// 入口参数
		String pagename = request.getParameter("pagename");
		String task = request.getParameter("task");
		// 页面参数
		String dirid = request.getParameter("dirid");// 路径id
		String appid = request.getParameter("appid");// 应用程序id
		String filename = WebStr.iso8859ToGBK(request.getParameter("filename"));// filename
		String naturalname = WebStr.iso8859ToGBK(request
				.getParameter("naturalname"));// naturalname
		String extendattribute = request.getParameter("extendattribute");// 其他
		String active = request.getParameter("active");// 其他
		String objecttype = WebStr.iso8859ToGBK(request
				.getParameter("objecttype"));// 其他

		boolean b = false;
		UmsProtectedobject parentUpo = null;
		if (StringUtils.isNotEmpty(dirid)) {
			parentUpo = rsrmi.loadResourceById(dirid);
			b = sec.check(parentUpo.getNaturalname(), LogUtil._ADD);
			
		}

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// 进入上传页面
		if ("addfile".equals(task)) {

			if (b) {
				request.setAttribute("dirid", dirid);
				request.setAttribute("appid", appid);
			} else {
				request.setAttribute("result", "n");
				request.setAttribute("errorinfo", "无权创建");
			}

			// 执行上传操作
		} else if ("uploadfile".equals(task)) {
			request.setAttribute("dirid", dirid);
			request.setAttribute("appid", appid);
			// 添加上传记录
			if (b) {
				String format = StringUtils.substringAfterLast(filename, ".");
				filename = StringUtils.substringBeforeLast(filename, ".");
				naturalname = StringUtils.substringBeforeLast(naturalname, ".");
				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setNaturalname(naturalname);
				upo.setName(filename);
				upo.setExtendattribute(extendattribute);
				upo.setParentdir(request.getParameter("dirid"));
				upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
				upo.setDescription(format);
				upo.setObjecttype(objecttype);
				if (StringUtils.isEmpty(active)) {
					active = "0";
				}
				upo.setActive(active);
				if (!request.getParameter("appid").equals("")) {
					upo.setAppid(new Long(request.getParameter("appid")));
				}
				// upo.setId(null);
				Serializable fileid = rsrmi.addResource(upo);
				if (fileid == null) {
					request.setAttribute("result", "n");
					request.setAttribute("errorinfo", "名称已经存在");
				} else {
					String fileidSaveName = "";
					if (StringUtils.isEmpty(format)) {
						fileidSaveName = fileid.toString();
					} else {
						fileidSaveName = fileid + "." + format;
					}
					String path = messages.getString("BinarySavePath");
					String contextpath = messages
							.getString("BinarySaveContextPath");
					if (contextpath == null || contextpath.equals("")) {
						contextpath = getServletContext().getRealPath("");
					}
					// Binary的存储路径
					String rspath = contextpath + path;
					File file = new File(rspath);
					if (!file.exists()) {
						file.mkdirs();
					}

					String dir = rspath + fileidSaveName;

					// 上传文件
					DiskFileUpload fu = new DiskFileUpload();

					try {
						List fileItems = fu.parseRequest(request);
						Iterator i = fileItems.iterator();
						while (i.hasNext()) {
							FileItem fi = (FileItem) i.next();
							String fileName = fi.getName();
							if (fileName != null) {
								fi.write(new File(dir));
							}
						}
						request.setAttribute("result", "y");
					} catch (FileUploadException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					sec.log(parentUpo.getNaturalname() + "."
							+ upo.getNaturalname(), LogUtil._ADD, LogUtil._ADD_SUCCESS);
				}
			} else {
				request.setAttribute("result", "n");
				request.setAttribute("errorinfo", "无权创建");
			}

		}

		// 获得 定制的资源页的地址，默认从配置resourceweb.properties中获得,如果默认中没有那么
		// 转向从资源页中获得
		pagename = pagename + ".newbinary";
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

		request.getRequestDispatcher(pageurl).forward(request, response);
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
