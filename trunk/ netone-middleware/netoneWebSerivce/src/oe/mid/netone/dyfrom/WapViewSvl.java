package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.dyform.DyFormData;
import com.jl.common.workflow.DbTools;

public class WapViewSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WapViewSvl() {
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

		String info="<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\" \"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">"+
		"<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		
		String formcode=request.getParameter("formcode");
		String fatherlsh=request.getParameter("fatherlsh");
		String condition=request.getParameter("column6");
		String basePathX = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
		String path=request.getContextPath();
		StringBuffer but=new StringBuffer();
		try{
		DyForm []form=DyEntry.iv().loadForm(formcode).getSubform_();

		if(form!=null&&form.length>0){
			for (int i=0;i<form.length;i++) {
				System.out.println("xxxxxx"+form[i].getFormname());
			}
			DyFormData dyd=new DyFormData();
			dyd.setFormcode(formcode);
			dyd.setFatherlsh(fatherlsh);
			String conditionX=" and column6='"+condition+"'";
			if(StringUtils.isEmpty(condition)){
				conditionX="";
			}
			List list=DyEntry.iv().queryData(dyd, 0, 100,conditionX );
		
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData object = (DyFormData) iterator.next();
				but.append("<a href='"+basePathX+path+"/servlet/WapViewSvl?formcode="+form[0].getFormcode()+"&fatherlsh="+object.getLsh()+"'>"+object.getColumn3()+"</a><br>");
			}
		}else{
			DyFormData dyd=new DyFormData();
			dyd.setFormcode(formcode);
			dyd.setFatherlsh(fatherlsh);
			List list=DyEntry.iv().queryData(dyd, 0, 100, "");
		
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData object = (DyFormData) iterator.next();
				but.append("<a href='"+basePathX+path+"/servlet/WapViewSingleSvl?formcode="+formcode+"&lsh="+object.getLsh()+"'>"+object.getColumn3()+"</a><br>");
			}
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out
		.println(info);
		out.println("  <HEAD></HEAD>");
		out.println("  <BODY  bgcolor='#FF9933'>");
		out.print(but.toString()+"<br><a href='javascript:history.go(-1)'>[·µ»Ø]</a>");

		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
