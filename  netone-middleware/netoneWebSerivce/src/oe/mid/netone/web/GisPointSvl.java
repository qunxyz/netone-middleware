package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

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
		  String userid=request.getParameter("userid");
		  double xoff=Double.parseDouble(xoffset);
		  double yoff=Double.parseDouble(yoffset);
		  double roff=Double.parseDouble(roffset);
		  List findPoint=new ArrayList();
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
			String naturalname=((UmsProtectedobject)formlist.get(0)).getNaturalname();
			String tablename=StringUtils.substringAfterLast(naturalname, ".");
			
			String sql="select lsh,column5,column3,column4 from dyform."+tablename+ " where userid like '"+userid+"[%'";
			if(StringUtils.isEmpty(userid)){
				sql="select lsh,column5,column3,column4 from dyform."+tablename ;
			}
			//String sql="select lsh,column5,column3,column4 from dyform."+tablename;

			list=DbTools.queryData(sql);
			
			System.out.println(xoffset+","+yoffset+","+roffset);
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String oxsr=(String)object.get("column4");
				String oysr=(String)object.get("column3");
				if(StringUtils.isEmpty(oxsr))oxsr="0.0";
				if(StringUtils.isEmpty(oysr))oysr="0.0";
				double ox=Double.parseDouble(oxsr);
				double oy=Double.parseDouble(oysr);
				double oz=GetDistance(xoff,yoff,ox,oy);
				System.out.println("-----"+oxsr+","+oysr+","+oz);
				if(oz<=roff){//说明在指定半径内
					findPoint.add(object);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(JSONArray.fromObject(findPoint).toString());
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
	
	
	private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d) {
		return d * Math.PI/ 180.0;
	}
	
	/*
	 * 将两地点的经纬度值换算成两点间距离
	 * @param lat1 位置1的经度
	 * @param lng1 位置1的纬度
	 * @param lat2 位置2的经度
	 * @param lng2 位置2的纬度
	 * @return s 两点间距离
	 */
	private static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
				Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10;
		return s;
	}
	
	public static void main(String[] args) {
		double rs=GetDistance(26.081308,119.2685816,26.0805727,119.26907953333334);
		System.out.println(rs);
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
