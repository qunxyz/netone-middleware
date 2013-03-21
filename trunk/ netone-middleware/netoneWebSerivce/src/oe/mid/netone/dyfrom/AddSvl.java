package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormData;
import com.jl.common.workflow.DbTools;

public class AddSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddSvl() {
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
		request.setCharacterEncoding("utf-8");
		String formcode = null;
		String appname = request.getParameter("appname");
		String parentId = request.getParameter("parentid");
		String userid = request.getParameter("userid");

		DyFormData dydata = new DyFormData();
		if (StringUtils.isEmpty(parentId)) {
			parentId = "1";
		}
		dydata.setFatherlsh(parentId);
		dydata.setParticipant(userid);

		if(parentId.equals("1")){
			//添加主表单
			try {
				formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
				List list = DyEntry.iv().fetchColumnList(formcode);
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					TCsColumn object = (TCsColumn) iterator.next();
					String columnId=object.getColumnid().toLowerCase();
					if(columnId.startsWith("column")){
						String value = request.getParameter(columnId);
						if (StringUtils.isNotEmpty(value)) {
	
							BeanUtils.setProperty(dydata, columnId, value);
	
						}
					}
				}
				String belongx=request.getParameter("belongx");
				if(StringUtils.isNotEmpty(belongx)){
					dydata.setBelongx(belongx);
				}
				String lsh = DyEntry.iv().addData(formcode, dydata);
				lsh=modifyLsh(request,formcode,lsh);
				modifyTime(request,formcode,lsh);
				response.getWriter().print(lsh);
				
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}else{
			//添加子表单
			try {
				formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
				DyForm dyx[]=DyEntry.iv().loadForm(formcode).getSubform_();
				if(dyx!=null&&dyx.length>0){
					formcode=dyx[0].getFormcode();
				}
				List list = DyEntry.iv().fetchColumnList(formcode);
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					TCsColumn object = (TCsColumn) iterator.next();
					String columnId=object.getColumnid().toLowerCase();
					if(columnId.startsWith("column")){
						String value = request.getParameter(columnId);
						if (StringUtils.isNotEmpty(value)) {
							BeanUtils.setProperty(dydata, columnId, value);
						}
					}
				}
				String belongx=request.getParameter("belongx");
				if(StringUtils.isNotEmpty(belongx)){
					dydata.setBelongx(belongx);
				}
				
				String lsh = DyEntry.iv().addData(formcode, dydata);
				lsh=modifyLsh(request,formcode,lsh);
				modifyTime(request,formcode,lsh);
				response.getWriter().print(lsh);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	
	private String modifyLsh(HttpServletRequest request,String formcode,String str)throws Exception{
		String lsh=request.getParameter("lsh");
		if(StringUtils.isNotEmpty(lsh)&&lsh.length()==32){
			String tablename=DyEntry.iv().loadForm(formcode).getTablename();
			String sql="update dyform."+tablename+" set lsh='"+lsh+"',hit=9 where lsh='"+str+"'";
			int rs=DbTools.execute(sql);
			return lsh;
		}else{
			return str;
		}
		
	}
	/**
	 * 有时系统需要客户端传递时间过来 
	 * @param request
	 * @param formcode
	 * @param lsh
	 * @throws Exception
	 */
	private void modifyTime(HttpServletRequest request,String formcode,String lsh)throws Exception{
		String timex=request.getParameter("timex");
		if(StringUtils.isNotEmpty(timex)){
			String tablename=DyEntry.iv().loadForm(formcode).getTablename();
			String sql="update dyform."+tablename+" set timex='"+timex+"',created='"+timex+"' where lsh='"+lsh+"'";
			int rs=DbTools.execute(sql);

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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
