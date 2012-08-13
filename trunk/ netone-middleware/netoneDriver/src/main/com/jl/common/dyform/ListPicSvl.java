package com.jl.common.dyform;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

import com.jl.common.workflow.DbTools;

/**
 * 展示图片
 * @author robanco
 *
 */
public class ListPicSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListPicSvl() {
		super();
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
		
		String id=request.getParameter("id");
		String sql="select unid,filename from iss.t_file where d_unid='"+id+"'";
		List list=DbTools.queryData(sql);
		EnvService env = null;
		String value =null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			value = env.fetchEnvValue("WEBSER_APPFRAMEX");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		StringBuffer but=new StringBuffer("<html><body>");
		
		but.append("<a href='"+value+"/file.do?method=onMainView&d_unid="+id+"' target='_blank'>[图片管理]</a><br>");
		
		String gradp="<hr><table border='1' style='table-layout:fixed;word-break:break-all'><tr><td width='250' height='220'>$k1</td><td width='250' height='220'>$k2</td><td width='250' height='220'>$k3</td><td width='250' height='220'>$k4</td></tr>"+
		"<tr bgcolor='#999990'><td width='250' height='20'>$x1</td><td width='250' height='20'>$x2</td><td width='250' height='20'>$x3</td><td width='250' height='20'>$x4</td></tr>"+
		"<tr><td width='250' height='220'>$k5</td><td width='250' height='220'>$k6</td><td width='250' height='220'>$k7</td><td width='250' height='220'>$k8</td></tr>"+
		"<tr bgcolor='#999990'><td width='250' height='20'>$x5</td><td width='250' height='20'>$x6</td><td width='250' height='20'>$x7</td><td width='250' height='20'>$x8</td></tr></table>";
		int i=1;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map type = (Map) iterator.next();
			String unid=(String)type.get("unid");
			String filename=(String)type.get("filename");
			String url=value+"/file.do?method=onDownLoadFile&isOnLine=0&unid="+unid;
			gradp=StringUtils.replace(gradp, "$k"+i, "<a href='"+value+"/SinglePicSvl?unid="+unid+"' target='_blank'><img src='"+url+"' onload='javascript:this.resized=true;this.style.width=250;this.style.height=200;'/></a>");
			gradp=StringUtils.replace(gradp, "$x"+i,filename);
			i++;
		}
		but.append(gradp);
		but.append("</body></html>");
		response.setContentType("text/html;charset=GBK");
		response.getWriter().print(but.toString());

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

}
