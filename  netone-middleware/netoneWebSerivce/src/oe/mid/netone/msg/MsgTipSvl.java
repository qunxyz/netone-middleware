package oe.mid.netone.msg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;

public class MsgTipSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MsgTipSvl() {
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
		
		String appname=request.getParameter("appname");
		String lsh=request.getParameter("lsh");
		String type=request.getParameter("type");
		try{
			String formcode=AppEntry.iv().loadApp(appname).getDyformCode_();
			DyFormData dydata=DyEntry.iv().loadData(formcode, lsh);
			if("01".equals(type)){//ÆÀÂÛ
				String column7=dydata.getColumn7();
				if(StringUtils.isEmpty(column7)){
					column7="0";
				}
				int rpnum=Integer.parseInt(column7);
				rpnum++;
				dydata.setColumn7(String.valueOf(rpnum));
			}else if("02".equals(type)){
				String column8=dydata.getColumn8();
				if(StringUtils.isEmpty(column8)){
					column8="0";
				}
				int rtnum=Integer.parseInt(column8);
				rtnum++;
				dydata.setColumn8(String.valueOf(rtnum));
			}
			dydata.setColumn9(new Date(System.currentTimeMillis()).toString());
			DyEntry.iv().modifyData(formcode, dydata);
		}catch(Exception e){
			e.printStackTrace();
		}

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
		this.doGet(request, response);
	}

}
