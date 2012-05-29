package oe.mid.netone.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import com.jl.common.TimeUtil;
import com.jl.common.workflow.DbTools;
import com.jl.dao.CommonDAO;

public class UploadFileAction extends HttpServlet {

	/**
	 * xuwei(2012-5-4) 上传图片的服务；
	 */
	// 限制文件的上传大小
	private int maxPostSize = 100 * 1024 * 1024; // 最大100M

	public UploadFileAction() {
		super();
	}

	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
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
		doPost(request, response);
	}

	private static String formatKbSize(float size) {
		long kb = 1024;
		return String.format("%.2f", size / kb); // 保留两位小数
	}

	private int saveFile(String id, String userCode, String userName,
			String address, String size, String type, String filename)
			throws Exception {
		int i = 0;
		address = address.replaceAll("\\\\", "\\\\\\\\");
		String sqlStr = "INSERT INTO iss.t_file (unid, d_unid, u_unid, f_size, f_type, `address`, filename,"
				+ "updatetime,note)VALUES('"
				+ UUID.randomUUID().toString().replaceAll("-", "")
				+ "', '"
				+ id
				+ "', '"
				+ userCode
				+ "', '"
				+ size
				+ "', '"
				+ type
				+ "', '"
				+ address
				+ "', '"
				+ filename
				+ "', '"
				+ TimeUtil.formatDateTime(new Date())
				+ "', '用户"
				+ userName
				+ "创建!');";
		System.out.println(sqlStr);
		i = DbTools.execute(sqlStr);
		return i;
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
		request.setCharacterEncoding("utf-8");

		boolean flag = false;
		String error = null;// 插入数据的错误信息
		File _file = null;
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目
		int index = 0;
		path = path + "files";
		String id = null;
		String userid = request.getParameter("username");
		String filetype = "1";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(maxPostSize);
		List fileItems = null;
		try {
			fileItems = upload.parseRequest(request);
		} catch (FileUploadException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Iterator iter = fileItems.iterator();
		while (iter.hasNext()) {
			FileItem fileItem = (FileItem) iter.next();
			if (!fileItem.isFormField()) {
				String fileName = fileItem.getName();
				path += "\\" + TimeUtil.formatDate(new Date(), "yyyy-MM-dd");
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				String format = StringUtils.substringAfterLast(fileName, ".");// 后辍名
				fileName = StringUtils.substringBeforeLast(fileName, ".");
				String[] arrstr = fileName.split("&");
				id = arrstr[2];
				userid = arrstr[0];
				fileName = arrstr[1] + "&" + arrstr[2] + "&" + arrstr[3];
				path += "\\" + fileName + "." + format;// 保存的文件绝对路径
				com.jl.entity.File files = null;
				try {
					index = saveFile(id, userid, userid, path, ""
							+ formatKbSize(fileItem.getSize()), filetype,
							fileName);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (fileItem != null) {
					_file = new File(path);
					try {
						fileItem.write(_file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileItem.getOutputStream().flush();
					fileItem.getOutputStream().close();
				}
			}
		}
		if (index == 1) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
		// return json.toString();
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
