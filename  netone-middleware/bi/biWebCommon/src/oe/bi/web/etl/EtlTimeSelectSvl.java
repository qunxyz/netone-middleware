package oe.bi.web.etl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.common.StringUtil;


public class EtlTimeSelectSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public EtlTimeSelectSvl() {
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
		
		response.setContentType("text/xml; charset=GBK");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		
		//type(时间类型) 1=年 , 2=月 , ...
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String prestr = null;
		int seltype = 0;
		int interval = 0;
		int size = 0;
		String endTime = null;
		String timetmpstr = null;
		String time = null;
		try {
			prestr = StringUtil.iso8859ToUTF8(request.getParameter("prestr"));
			seltype = Integer.parseInt(request.getParameter("seltype"));
			interval = Integer.parseInt(request.getParameter("interval"));
			if(request.getParameter("size") != null){
				size = Integer.parseInt(request.getParameter("size"));
			}
			endTime = request.getParameter("endTime");
			
			timetmpstr = StringUtil.getThValue(prestr, 2);
			time = timetmpstr.substring(0,timetmpstr.indexOf("$"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int pretype = 0;
		Calendar  cal = Calendar.getInstance();
		pretype = string2Calendar(cal, time);
		Calendar endcal = null;
		if(endTime != null){
			//初始化size的值；
			size = 10000 ;
			endcal = Calendar.getInstance();
			string2Calendar(endcal, endTime);
		}
		
		int caltype = 0 ;
		switch(seltype){
		case 1:
			caltype = Calendar.YEAR;
			break;
		case 2:
			caltype = Calendar.MONTH;
			break;
		case 3:
			caltype = Calendar.DATE;
			break;
		case 4:
			caltype = Calendar.HOUR;
			break;
		case 5:
			caltype = Calendar.MINUTE;
			break;
		case 6:
			caltype = Calendar.SECOND;
		}
		
		String formatstr = "";
		switch(pretype){
		case 1:
			formatstr = "yyyy";
			break;
		case 2:
			formatstr = "yyyy-MM";
			break;
		case 3:
			formatstr = "yyyy-MM-dd";
			break;
		case 4:
			formatstr = "yyyy-MM-dd HH";
			break;
		case 5:
			formatstr = "yyyy-MM-dd HH:mm";
			break;
		case 6:
			formatstr = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(formatstr);
		ArrayList timelist = new ArrayList();
		String str = df.format(new Date(cal.getTimeInMillis()));
		timelist.add(str);
		for(int i=0 ; i<size-1 ; i++){
			cal.add(caltype,interval);
			//当结束时间点不为空时
			if(endcal != null){
				if(cal.getTimeInMillis() > endcal.getTimeInMillis()){
					//超过结束点时退出循环。
					break;
				}
			}
			String tmpstr = df.format(new Date(cal.getTimeInMillis()));
			timelist.add(tmpstr);
		}
		
		
		
		/*****  旧版构造返回值
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
//		sb1.append(timetmpstr);
		String level = StringUtil.getThValue(prestr, 3);
//		sb2.append(level);
		for(int i=0 ;i<timelist.size() ; i++){
			String tmpsbstr = timetmpstr.replaceAll(time+"\\$", timelist.get(i).toString()+"\\$");
			sb1.append(tmpsbstr);
			sb2.append(level);
		}
		String height = StringUtil.getThValue(prestr, 1);
		String redimvalue = "["+height+"]"+"["+sb1.toString()+"]"+"["+sb2.toString()+"]";
		out.print(redimvalue+"@");
		
		StringBuffer sb3 = new StringBuffer();
		for(int i=0 ;i <timelist.size() ;i++){
			if(i!=0){
				sb3.append(",");
			}
			sb3.append(timelist.get(i).toString());
		}
		out.print(sb3.toString());
		
		 *******/
		
		StringBuffer sb1 = new StringBuffer();
		for(int i=0 ;i<timelist.size() ; i++){
			if(i != 0){
				sb1.append("#");
			}
			
			String tmpsbstr = timetmpstr.replaceAll(time+"\\$", timelist.get(i).toString()+"\\$");
			String[] strs = parsePreStr(tmpsbstr);
			sb1.append("treeModelId=timetree,");
			sb1.append("nodeid="+strs[0]+",");
			sb1.append("nodename="+strs[1]+",");
			sb1.append("levelname="+strs[2]);
		}
		out.print(sb1.toString());
		
		
		out.flush();
		out.close();
		
	}
	
	//返回id，name，level
	public String[] parsePreStr(String prestr){
		//解析“2006-12-04 00$5&0时,”串
		String[] strs = new String[3];
		int i = prestr.indexOf("$");
		int j = prestr.indexOf("&");
		int k = prestr.indexOf(",");
		strs[0] = prestr.substring(0,i);
		strs[2] = prestr.substring(i+1,j);
		strs[1] = prestr.substring(j+1,k);
		return strs;
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
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	private int string2Calendar(Calendar cal , String time ){
		int pretype = 0 ;
		cal.setTimeInMillis(0);
		if(time.length()>=4){
			String year = time.substring(0,4);
			cal.set(Calendar.YEAR,Integer.parseInt(year));
			pretype = 1 ;
		}
		if(time.length()>=7){
			String month = time.substring(5,7);
			cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
			pretype = 2 ; 
		}
		if(time.length()>=10){
			String day = time.substring(8 ,10);
			cal.set(Calendar.DATE,Integer.parseInt(day));
			pretype = 3;
		}
		if(time.length()>=13){
			String hour = time.substring(11,13);
			cal.set(Calendar.HOUR,Integer.parseInt(hour));
			pretype = 4;
		}
		if(time.length()>=16){
			String minute = time.substring(14,16);
			cal.set(Calendar.MINUTE,Integer.parseInt(minute));
			pretype = 5;
		}
		if(time.length()>=19){
			String second = time.substring(17,19);
			cal.set(Calendar.SECOND,Integer.parseInt(second));
			pretype = 6;
		}
		return pretype;
	}
	
}
