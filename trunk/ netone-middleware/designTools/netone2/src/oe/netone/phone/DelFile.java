package oe.netone.phone;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DelFile extends HttpServlet {

	/**
	 *  删除 上传文件和图片 
	 *  xuwei（2012-2-27）
	 *  
	 */
	public DelFile() {
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
	  // 定义文件的上传路径 
    public String uploadPath; 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String naturalname=request.getParameter("naturalname");
		
		List<PhoneData> pd=null;
		ResourceRmi resourceRmi=null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UmsProtectedobject upj=resourceRmi.loadResourceByNatural(naturalname);
		String strxml=upj.getExtendattribute();
		String type=upj.getObjecttype();
		if(type.equals("PF")){
		 pd=XmlAnalysis.readXML(strxml);
		}
		if(type.equals("Map")){
		pd=XmlAnalysis.readXML(strxml);
		}
		if(pd!=null){
			for (Iterator iterator = pd.iterator(); iterator.hasNext();) {
				PhoneData name = (PhoneData) iterator.next();
				 uploadPath=System.getProperty("user.dir");
        	     String [] strings=uploadPath.split("bin");
        	     uploadPath=strings[0]+"webapps/SpeedyForm_v1.3/PhoneConfig/";
        	     File  file=new  File(uploadPath+name.getName());
        	     file.delete();//删除文件
			}
		}
		String id1=upj.getId();
		Boolean fal=resourceRmi.dropResource(id1);
		response.getWriter().print(fal);
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

		doGet(request,response);
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
