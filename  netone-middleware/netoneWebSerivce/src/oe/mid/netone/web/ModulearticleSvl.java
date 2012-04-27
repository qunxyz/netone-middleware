package oe.mid.netone.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import com.jl.common.workflow.DbTools;

public class ModulearticleSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ModulearticleSvl() {
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
		String sqlStr=null;
		
		String model=request.getParameter("model");
		if(model==null){
			model="0";		
		}
	 
		//一级频道栏目
		if(model.equals("0")){
		sqlStr="select catid,catname,child from cms_phpcms.v9_category where parentid=0 and siteid=1 and ismenu=1 and catid not in(15,28,29)";
		}
		//栏目树
		if(model.equals("1")){
			String	parentid=request.getParameter("parentid");
			sqlStr="select catid,catname,parentid,child from cms_phpcms.v9_category where parentid="+parentid+"";
		}
		//频道树
		if(model.equals("2")){
			String	catid=request.getParameter("catid");
			String  id=request.getParameter("id");
			sqlStr="select title,url from cms_phpcms.v9_news where catid="+catid+" and id="+id+"";
		}
 		List list = DbTools.queryData(sqlStr);
		
		String  json = JSONArray.fromObject(list).toString(); 
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
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
