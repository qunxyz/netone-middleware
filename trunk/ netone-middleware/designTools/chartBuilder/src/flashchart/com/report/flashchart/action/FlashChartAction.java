/**
 * 
 */
package com.report.flashchart.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.report.flashchart.entity.ChartDataObj;
import com.report.flashchart.ifc.ChartService;

/**
 * 核心flash图表ACTION 不可更新
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-29 下午02:55:12
 * @history
 */
public class FlashChartAction extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ChartService service = (ChartService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"flashChartService");
		String param = request.getParameter("param");
		param = new String(param.getBytes("iso-8859-1"), "UTF-8");
 
		JSONObject json = new JSONObject();
		json = json.fromObject(param);

		
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
			upof = resourceRmi.loadResourceByNatural(json.getString("name1"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String obl=upof.getExtendattribute();
	     if(obl!="" || obl!=null){
		
	     }	
	     

			upof = resourceRmi.loadResourceByNatural(json.getString("name1"));
		String string2="{\"name1\":\""+json.getString("name1")+"\",\"name2\":\""+json.getString("name2")+"\"}";
		json.element("params", string2);
		ChartDataObj data = jsonToChartDataObj(json);
		if (upof.getName()!="") {
			data.setTitle(upof.getName());
		}
		// 2
		String $comp = "";
		if (json.containsKey(service.$COMP$)) {
			$comp = json.getString(service.$COMP$);
		}
		String datajson = service.entry(json, $comp, data);

		// 3
		service.output(request, datajson, data.getTitle());

		// 4
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/flashchart/entry.jsp");
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected ChartDataObj jsonToChartDataObj(JSONObject json) {
		ChartDataObj data = new ChartDataObj();
		try {
			for (Iterator iterator = json.keys(); iterator.hasNext();) {
				String key = (String) iterator.next();
				BeanUtils.copyProperty(data, key, json.get(key));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return data;
	}
}
