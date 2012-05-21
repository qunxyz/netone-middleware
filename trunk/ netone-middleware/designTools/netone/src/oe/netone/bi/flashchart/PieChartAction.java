/**
 * 
 */
package oe.netone.bi.flashchart;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.netone.bi.gd.GraphicXML;
import oe.netone.bi.gd.ObjectGraph;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mysql.jdbc.Field;
import com.report.flashchart.entity.ChartCompVar;
import com.report.flashchart.entity.ChartDataObj;
import com.report.flashchart.ifc.ChartService;

/**  实现饼图 的Servlet
 *  
 *  
 *  
 */
public class PieChartAction extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String naturalname=request.getParameter("naturalname");
		
		//通过naturalname 来获取资源里面的数据
		ObjectGraph OGXML = null;
		ResourceRmi resourceRmi = null;
		try {
			resourceRmi=(ResourceRmi) RmiEntry.iv("resource");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UmsProtectedobject upof=null;
		try {
			upof = resourceRmi.loadResourceByNatural(naturalname);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String obl=upof.getExtendattribute();
	     if(obl!="" || obl!=null){
		GraphicXML GX=new GraphicXML();
		obl=obl.trim();
		OGXML=GX.readXML(obl);
	     }	
	        DyFormDesignService dys = null;
			try {
				dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	        TCsColumn busForm = new TCsColumn();
			busForm.setFormcode(OGXML.getFormcode());
			//long indexname=dys.queryObjectsNumber(busForm);
		   	List listmame =dys.queryObjects(busForm);
			String xString="";
			String yString="";
			for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
			    TCsColumn columnname=(TCsColumn)iterator1.next();
				if(columnname.getColumncode().equals(OGXML.getZhankaiX())){
					xString=columnname.getColumname();
				}
				if(columnname.getColumncode().equals(OGXML.getQiepianweidu())){
					yString=columnname.getColumname();
				}
			}
			
			
		// 1.实例实现类
		ChartService service = (ChartService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"flashChartService");

		// 2.实现图表数据对象
		ChartDataObj data = new ChartDataObj();
		//OGXML.getZhankaiX()
		data.setXAxisLabel(xString);//
//		data.setYAxisLabel(yString);//
 
    	data.setParams("{\"name1\":\""+naturalname+"\",\"name2\":\""+naturalname+"\"}");
		String endpoint = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		if (request.getScheme().equalsIgnoreCase("https")) {// https
			endpoint += "messagebroker/amfsecure";
		} else {// http
			endpoint += "messagebroker/amf";
		}
		data.setEndpoint(endpoint);
		data.setTitle(upof.getName());//

		
		if(OGXML.getTubiaotype().equals("xianxingtu")){
		// 2.指定输出报表格式 具体变量查看com.report.flashchart.entity.ChartCompVar.java
		String json = service.entry(ChartCompVar.COMP_LINE_CHART, data);
		// 3.输出参数
		service.output(request, json, data.getTitle());
		}
		if(OGXML.getTubiaotype().equals("zhutu")){
			// 2.指定输出报表格式 具体变量查看com.report.flashchart.entity.ChartCompVar.java
			String json = service.entry(ChartCompVar.COMP_BAR_CHART, data);
			// 3.输出参数
			service.output(request, json, data.getTitle());
		}
		if(OGXML.getTubiaotype().equals("bingtu")){
			 //2.指定输出报表格式 具体变量查看com.report.flashchart.entity.ChartCompVar.java
			String json = service.entry(ChartCompVar.COMP_PIE_CHART, data);
			// 3.输出参数
			service.output(request, json, data.getTitle());
		}
		
		// 4.指定输出页面 页面地址不能更改必须是/flashchart/entry.jsp 如果使用MVC框架，根据相关框架重写
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/flashchart/entry.jsp");
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

}
