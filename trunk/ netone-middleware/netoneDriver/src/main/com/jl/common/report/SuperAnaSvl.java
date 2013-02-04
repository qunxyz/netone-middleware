package com.jl.common.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.DbTools;
import com.jl.entity.PointDx;

public class SuperAnaSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SuperAnaSvl() {
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
		String disp=request.getParameter("disp");// disp=gis地图展示 ,disp=rep报表展示

		if("rep".equals(disp)){
			String mode=request.getParameter("mode");//mode=1 是include,mode=2 unclude,mode=0 all;
			String form1=request.getParameter("form1");
			String form2=request.getParameter("form2");
			
			String table1=StringUtils.substringAfterLast(form1, ".");
			String table2=StringUtils.substringAfterLast(form2, ".");
			
			String table1_con=request.getParameter("con1");
			
			int cond = Integer.parseInt(request.getParameter("cond"));
			if(StringUtils.isEmpty(table1_con)){
				table1_con="1=1";
			}
			String table1_col=request.getParameter("col1");
			if(StringUtils.isEmpty(table1_col)){
				table1_col="*";
			}		
			String table2_con=request.getParameter("con2");
			if(StringUtils.isEmpty(table2_con)){
				table2_con="1=1";
			}
			String table2_col=request.getParameter("col2");
			if(StringUtils.isEmpty(table2_col)){
				table2_col="*";
			}
			

			
			String sql1="";
			String sql2="";
			List list1=null;
			List list2=null;
			String dbsource=request.getParameter("sour");
			if("oracle".equals(dbsource)){
				try {
					ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
					UmsProtectedobject upo=(UmsProtectedobject)rs.loadResourceByNatural(form2);
					//String referenceString=upo.getReference();
					String referenceString="CDL_RAW_1X_10808_HWV6,jdbc:oracle:thin:@127.0.0.1:1521:ORCL,oracle.jdbc.driver.OracleDriver,FJCRNOP,orcl";
					//tablename@table1,url@localhost,driver@com.orcele,username@roosd,pasword@sdfds
					//test,jdbc:oracle:thin:@127.0.0.1:1521:ORCL,oracle.jdbc.driver.OracleDriver,FJCRNOP,orcl
					String[] par = StringUtils.split(referenceString,",");
					String tablename = par[0];
					String url = par[1];
					String driver = par[2];
					String name = par[3];
					String password = par[4];
					Connection con = DbTools.getOuterCon(driver, url, name, password);
					sql1="select "+table1_col+" from dyform."+table1+" where " +table1_con;
					sql2="select f0245,f0384,f0244,f0383,f0390,f0251,f0389,f0250,f0257,f0396,f0395,f0256,f0402,f0263,f0262,f0401,f0408,f0269,f0268,f0407,f0415,f0414,f0413,f0274,last_lon poffx,last_lat poffy from CDL_RAW_1X_10808_HWV6 where last_lon is not null and last_lat is not null";
					list1=DbTools.queryData(sql1);
					list2=DbTools.queryData(sql2,con);			
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				sql1=" select "+table1_col+" from dyform."+table1+" where " +table1_con;
				sql2=" select "+table2_col+" from dyform."+table2+" where " +table2_con;
				list1=DbTools.queryData(sql1);
				list2=DbTools.queryData(sql2);
			}
			

			request.getSession().setAttribute("list1", list1);
			request.getSession().setAttribute("list2", list2);
			StringBuffer but1=new StringBuffer();
			but1.append("<table border=\"1\">" +
					"		<tr>" +
					"			<td>基站名称</td><td>覆盖率百分比</td><td>扇区内节点数</td><td>总节点数</td><td>节点选择</td><td>操作</td>" +
					"		</tr>");
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				StringBuffer but2=new StringBuffer("");
				Map object = (Map) iterator.next();
				Double gpx1=Double.valueOf(object.get("gpx").toString());
				Double gpx2=Double.valueOf(object.get("gpy").toString());
				PointDx sq = new PointDx();
				PointDx sq1 = new PointDx();
				double pz = Double.valueOf(object.get("gppz").toString())*1.5;
				double jl = 0.5;
				double zk = Double.valueOf(object.get("gpzk").toString());
				sq.setX(gpx1);
				sq.setY(gpx2);
				sq.setName((String)object.get("name"));
				sq1.setX(sq.getX());
				sq1.setY(sq.getY() - jl);
				PointDx sq2 = getPoint(sq,sq1,pz*Math.PI/180);
				PointDx sq3 = getPoint(sq,sq2,zk*Math.PI/180);
				int tol = 0;
				int in = 0;
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					PointDx sqx = new PointDx();
					sqx.setX(Double.valueOf(object2.get("POFFX").toString()));
					sqx.setY(Double.valueOf(object2.get("POFFY").toString()));
					if((object.get("CID").toString()).equals(object2.get("F0274").toString())||(object.get("CID").toString()).equals(object2.get("F0413").toString())||(object.get("CID").toString()).equals(object2.get("F0407").toString())||(object.get("CID").toString()).equals(object2.get("F0268").toString())||(object.get("CID").toString()).equals(object2.get("F0401").toString())||(object.get("CID").toString()).equals(object2.get("F0262").toString())||(object.get("CID").toString()).equals(object2.get("F0256").toString())||(object.get("CID").toString()).equals(object2.get("F0395").toString())||(object.get("CID").toString()).equals(object2.get("F0250").toString())||(object.get("CID").toString()).equals(object2.get("F0389").toString())||(object.get("CID").toString()).equals(object2.get("F0383").toString())||(object.get("CID").toString()).equals(object2.get("F0244").toString())||(object.get("SID").toString()).equals(object2.get("F0245").toString())||(object.get("SID").toString()).equals(object2.get("F0384").toString())||(object.get("SID").toString()).equals(object2.get("F0390").toString())||(object.get("SID").toString()).equals(object2.get("F0251").toString())||(object.get("SID").toString()).equals(object2.get("F0257").toString())||(object.get("SID").toString()).equals(object2.get("F0396").toString())||(object.get("SID").toString()).equals(object2.get("F0402").toString())||(object.get("SID").toString()).equals(object2.get("F0263").toString())||(object.get("SID").toString()).equals(object2.get("F0408").toString())||(object.get("SID").toString()).equals(object2.get("F0269").toString())||(object.get("SID").toString()).equals(object2.get("F0415").toString())||(object.get("SID").toString()).equals(object2.get("F0414").toString())){
						if(isInclude(sqx, sq2, sq, sq3, jl))
							in++;
						tol++;
					}

//					if("1".equals(mode)){
//						if(inTriangle(poffx, poffy, gpx1, gpx2, gpx3, gpy1, gpy2, gpy3))
//							flag++;
//					} else if ("2".equals(mode)){
//						if(!inTriangle(poffx, poffy, gpx1, gpx2, gpx3, gpy1, gpy2, gpy3))
//							flag++;
//					} else {
//						flag++;
//					}
				}
				if(tol > 0 && in*100/tol<cond){
					but1.append("<tr>" +
							"		<td>"+ sq.getName() +"</td>" +
							"		<td>"+ in*100/tol +"%</td>" +
							"		<td>"+ in +"</td>" +
							"		<td>"+ tol +"</td>" +
							"		<td>" +
						    "<select name='mode' id='mode'>"+
			    	"<option value='0'>全部节点</option>"+
			    	"<option value='1'>扇区内节点</option>"+
			    	"<option value='2'>扇区外节点</option>"+
			    "</select>" +
							"		</td>" +
							"		<td><button onclick=\"javascript:window.open('http://127.0.0.1:8001/ndyd/servlet/SuperAnaSvl?disp=gis&sid="+ object.get("SID").toString() +"&cid="+ object.get("CID").toString() +"&mode='+$(this).parent().parent().find('#mode').val());\">查看</button></td>" +
							"	</tr>");
				}
			}
			but1.append("</table>");
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out
					.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>分析结果</TITLE>" +
					"<script type='text/javascript' src='http://127.0.0.1:8001/ndyd/script/jquery-1.3.2.min.js'></script>" +
					"</HEAD>");
			out.println("  <BODY>");
			out.print(but1.toString());
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		}
		
		if("gis".equals(disp)){
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out
					.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>" +
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
					"<style type=\"text/css\">" +
					"body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}" +
					"#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}" +
					"#r-result{height:100%;width:20%;float:left;}" +
					"</style>" +
					"<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=1.4\"></script>" +
					"<title>节点分布</title>" +
					"</head>" +
					"<body>" +
					"<div id=\"allmap\"></div>" +
					"</body>" +
					"</html>" +
					"<script type=\"text/javascript\">" +
					"var map = new BMap.Map(\"allmap\");" +
					"var polygon = new BMap.Polygon(["
					);
			boolean flag = true;
			StringBuffer but1=new StringBuffer();
			StringBuffer but2=new StringBuffer();
			String cid = request.getParameter("cid");
			String sid = request.getParameter("sid");
			String mode = request.getParameter("mode");
			List list1 = (List)request.getSession().getAttribute("list1");
			List list2 = (List)request.getSession().getAttribute("list2");
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				if(!(cid.equals(object.get("CID").toString())&&sid.equals(object.get("SID").toString())))
					continue;
				Double gpx1=(((java.math.BigDecimal)object.get("gpx")).doubleValue());
				Double gpx2=(((java.math.BigDecimal)object.get("gpy")).doubleValue());
				PointDx sq = new PointDx();
				PointDx sq1 = new PointDx();
				double pz = ((java.math.BigDecimal)object.get("gppz")).doubleValue()*1.5;
				double jl = 0.5;
				double zk = ((java.math.BigDecimal)object.get("gpzk")).doubleValue();
				sq.setX(gpx1.doubleValue());
				sq.setY(gpx2.doubleValue());
				sq.setName((String)object.get("name"));
				sq1.setX(sq.getX());
				sq1.setY(sq.getY() - jl);
				out.println("new BMap.Point(" + sq.getX() + "," + sq.getY() + "),");
				but1.append("," + sq.getX() + "," + sq.getY());
				PointDx sq2 = getPoint(sq,sq1,pz*Math.PI/180);
				out.println("new BMap.Point(" + sq2.getX() + "," + sq2.getY() + "),");
				but1.append("," + sq2.getX() + "," + sq2.getY());
				PointDx sq3 = getPoint(sq,sq2,zk*Math.PI/180);
				for(int i = 1;i<zk;i++){
					out.println("new BMap.Point(" + getPoint(sq,sq2,i*Math.PI/180).getX() + "," + getPoint(sq,sq2,i*Math.PI/180).getY() + "),");
					but1.append("," + getPoint(sq,sq2,i*Math.PI/180).getX() + "," + getPoint(sq,sq2,i*Math.PI/180).getY());
				}
				out.println("new BMap.Point(" + sq3.getX() + "," + sq3.getY() + ")");
				but1.append("," + sq3.getX() + "," + sq3.getY());
				but1.append("\n");
				out.println("], {strokeColor:\"blue\", strokeWeight:6, strokeOpacity:0.5});");
				out.println("map.addOverlay(polygon);");
				//基站描图 
				but2.append(sq.getX()+","+sq.getY()+","+sq.getName()+"\n");
				int k = 0;
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					if(!((object.get("CID").toString()).equals(object2.get("F0274").toString())||(object.get("CID").toString()).equals(object2.get("F0413").toString())||(object.get("CID").toString()).equals(object2.get("F0407").toString())||(object.get("CID").toString()).equals(object2.get("F0268").toString())||(object.get("CID").toString()).equals(object2.get("F0401").toString())||(object.get("CID").toString()).equals(object2.get("F0262").toString())||(object.get("CID").toString()).equals(object2.get("F0256").toString())||(object.get("CID").toString()).equals(object2.get("F0395").toString())||(object.get("CID").toString()).equals(object2.get("F0250").toString())||(object.get("CID").toString()).equals(object2.get("F0389").toString())||(object.get("CID").toString()).equals(object2.get("F0383").toString())||(object.get("CID").toString()).equals(object2.get("F0244").toString())||(object.get("SID").toString()).equals(object2.get("F0245").toString())||(object.get("SID").toString()).equals(object2.get("F0384").toString())||(object.get("SID").toString()).equals(object2.get("F0390").toString())||(object.get("SID").toString()).equals(object2.get("F0251").toString())||(object.get("SID").toString()).equals(object2.get("F0257").toString())||(object.get("SID").toString()).equals(object2.get("F0396").toString())||(object.get("SID").toString()).equals(object2.get("F0402").toString())||(object.get("SID").toString()).equals(object2.get("F0263").toString())||(object.get("SID").toString()).equals(object2.get("F0408").toString())||(object.get("SID").toString()).equals(object2.get("F0269").toString())||(object.get("SID").toString()).equals(object2.get("F0415").toString())||(object.get("SID").toString()).equals(object2.get("F0414").toString())))
						continue;
					if(flag){
						out.println("var point = new BMap.Point("+ object2.get("POFFX").toString() +", "+ object2.get("POFFY").toString() +");");
						out.println("map.centerAndZoom(point, 15);");
						out.println("function addMarker(point){");
						out.println("var marker = new BMap.Marker(point);");
						out.println("map.addOverlay(marker);");
						out.println("}");
						flag = false;
					}
					PointDx sqx = new PointDx();
					sqx.setX(Double.valueOf(object2.get("POFFX").toString()));
					sqx.setY(Double.valueOf(object2.get("POFFY").toString()));
					if("1".equals(mode)){
						if(isInclude(sqx, sq2, sq, sq3, jl)){
							out.println("var point = new BMap.Point("+ sqx.getX() +", "+ sqx.getY() +");");
							out.println("addMarker(point);");
						}
					} else if ("2".equals(mode)){
						if(!isInclude(sqx, sq2, sq, sq3, jl)){
							out.println("var point = new BMap.Point("+ sqx.getX() +", "+ sqx.getY() +");");
							out.println("addMarker(point);");
						}
					} else {
						out.println("var point = new BMap.Point("+ sqx.getX() +", "+ sqx.getY() +");");
						out.println("addMarker(point);");
					}
				}
			}
			out.println("</script>");
			out.flush();
			out.close();
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

		doGet(request, response);
	}
	
	public PointDx getPoint(PointDx p1,PointDx p2,double pz) {
		PointDx p3 = new PointDx();
		p3.setX((p2.getX()-p1.getX())*Math.cos(-pz) + (p2.getY()-p1.getY())*Math.sin(-pz) + p1.getX());
		p3.setY(-(p2.getX()-p1.getX())*Math.sin(-pz) + (p2.getY()-p1.getY())*Math.cos(-pz) + p1.getY());
	    return p3;
	}
	public double getAngle(PointDx a,PointDx b,PointDx c){
		double ma_x = a.getX() - b.getX();  
		double ma_y = a.getY() - b.getY();  
		double mb_x = c.getX() - b.getX();  
		double mb_y = c.getY() - b.getY();  
		double v1 = (ma_x * mb_x) + (ma_y * mb_y);  
		double ma_val = Math.sqrt(ma_x*ma_x + ma_y*ma_y);  
		double mb_val = Math.sqrt(mb_x*mb_x + mb_y*mb_y);  
		double cosM = v1 / (ma_val*mb_val);  
		Double angleAMB = Math.acos(cosM) * 180 / Math.PI;  
		return angleAMB.doubleValue();
	}
	public boolean isInclude(PointDx n,PointDx a,PointDx b,PointDx c,double jl){ 
		double s = Math.sqrt((n.getX()-b.getX())*(n.getX()-b.getX()) + (n.getY()-b.getY())*(n.getY()-b.getY()));
		if(getAngle(n, b, c) < getAngle(a, b, c) && getAngle(n, b, a) < getAngle(a, b, c))
			return true;
		return false;}
}
