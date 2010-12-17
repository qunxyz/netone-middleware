package oe.mid.web.rspage.common;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

public class UploadSvl extends HttpServlet {

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
		String dir = request.getRealPath("") + "\\fileroot\\";
		String dirid = request.getParameter("dirid");
		String dirthis = dir + request.getParameter("dirid");
		File dirRoot = new File(dirthis);
		if (!dirRoot.exists()) {
			dirRoot.mkdir();
		}
		// 上传文件
		
		if ("yes".equals(request.getParameter("add"))) {
			try {
				DiskFileUpload fu = new DiskFileUpload();
				List fileItems = fu.parseRequest(request);
				Iterator i = fileItems.iterator();
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					String fileName = fi.getName();
					if (fileName != null) {
						fi.write(new File(dirthis + "/"
								+ System.currentTimeMillis()));
						
					}
				}
				request.setAttribute("result", "y");
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String[] piclist = dirRoot.list();

		request.setAttribute("piclist", piclist);
		request.setAttribute("dirid", dirid);

		request.getRequestDispatcher("pictool/createBindary.jsp").forward(
				request, response);

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
