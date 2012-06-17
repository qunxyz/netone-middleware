package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.app.AppEntry;
import com.jl.common.workflow.DbTools;

/**
 * 获取临近的网点
 * @author robanco
 *
 */
public class GisPointSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GisPointSvl() {
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
		  String appname=request.getParameter("appname");
		  String xoffset=request.getParameter("xoffset");
		  String yoffset=request.getParameter("yoffset");
		  String roffset=request.getParameter("roffset");
		  double xoff=Double.parseDouble(xoffset);
		  double yoff=Double.parseDouble(yoffset);
		  double roff=Double.parseDouble(roffset);
		  double xoffTop=xoff+roff;
		  double xoffBut=xoff-roff;
		  double yoffTop=yoff+roff;
		  double yoffBut=yoff-roff;
		  String xoffCond="column3>"+xoffBut+" and column3<"+xoffTop+" and column4>"+yoffBut+" and column4<"+yoffTop;
		  List list=new ArrayList();
		  try {
			String formcode= AppEntry.iv().loadApp(appname).getDyformCode_();
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setExtendattribute(formcode);
			upo.setNaturalname("BUSSFORM.BUSSFORM.%");
			Map map = new HashMap();
			map.put("naturalname", "like");
			List formlist = rs.fetchResource(upo, map);
			if(formlist.size()!=1){
			throw new RuntimeException("存在表单异常定义");
			}
			String tablename=((UmsProtectedobject)formlist.get(0)).getDescription();
			
			String sql="select lsh,column5 from "+tablename+" where "+xoffCond;
			list=DbTools.queryData(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(JSONArray.fromObject(list).toString());
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
