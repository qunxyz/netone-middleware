package oe.mid.netone.web;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.DbTools;

public class DeleteFileAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeleteFileAction() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String u_unid = null;
		String address = null;
		String judge = null;
		String id = request.getParameter("unid");
		String userid = request.getParameter("userid");
		String sqlstr = "SELECT u_unid,address FROM iss.t_file  WHERE unid='"
				+ id + "'";
		List list = DbTools.queryData(sqlstr);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			u_unid = (String) map.get("u_unid");
			address = (String) map.get("address");
		}
		//�ж�ɾ�������ǲ������
		if (StringUtils.isNotEmpty(userid) && StringUtils.isNotEmpty(u_unid)) {
			if (userid.equals(u_unid)) {
				String sqlDELETE = " DELETE FROM iss.t_file  WHERE unid = '"
						+ id + "'";
				int i = DbTools.execute(sqlDELETE);
				if (i == 1) {
					File file = new File(address);
					judge = deleteFile(file);
				}
			} else {
				judge = "have no right!";
			}
		} else {
			judge = "have no right!";
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(judge);
	}

	//ɾ�������ϵĸ���
	private String deleteFile(File file) {
		String str = null;
		if (file.exists()) { //�ж��ļ��Ƿ����
			if (file.isFile()) { //�ж��Ƿ����ļ�
				file.delete(); //delete()���� ��Ӧ��֪�� ��ɾ������˼;
			} else if (file.isDirectory()) { //�����������һ��Ŀ¼
				File files[] = file.listFiles(); //����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { //����Ŀ¼�����е��ļ�
					this.deleteFile(files[i]); //��ÿ���ļ� ������������е���
				}
			}
			file.delete();
			str = "true";
		} else {
			str = "File Not Exist��";
			System.out.println("File Not Exist��" + '\n');
		}
		return str;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
