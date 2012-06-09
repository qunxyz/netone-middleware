/**
 * 
 */
package oe.netone.bi.flashchart;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.netone.bi.ConnDB;
import oe.netone.bi.xyData;
import oe.netone.bi.bricktake.BTAnalysisXml;
import oe.netone.bi.bricktake.ChartValue;
import oe.netone.bi.gd.GraphicXML;
import oe.netone.bi.gd.GraphicsDisplaySelect;
import oe.netone.bi.gd.ObjectGraph;
import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;


import com.report.chart.entity.BarChartObj;
import com.report.chart.entity.PieChartObj;
import com.report.flashchart.BaseChart;
import com.report.flashchart.ifc.PieChartService;

/**
 * 饼图  图表业务实现类
 *  xuwei（2012-2-13）
 *
 */
public class PieChart extends BaseChart implements PieChartService {

	public PieChart() {
	}

	/**
	 * flash加载的数据源
	 * 
	 * @param param
	 *            以JSON格式构造 例:{title:'标题'}
	 */
	public String loadData(String param) {
		JSONObject json = new JSONObject();
		json = json.fromObject(param);
		try {
			Map map = jsonToMap(json);
			List list = returnTestData(map);
			String s = toJSONStr(list);
			return s;
		} catch (Exception e) {
			System.out.println("图表出错了" + e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * flash加载的数据源
	 * 
	 * @param param
	 *            以JSON格式构造 例:{title:'标题'}
	 */
	public String load2LData(String param) {
		JSONObject json = new JSONObject();
		json = json.fromObject(param);
		try {
			Map map = jsonToMap(json);

			List list = returnTestData2(map);
			String s = toJSONStr(list);
			return s;
		} catch (Exception e) {
			System.out.println("图表出错了" + e);
			e.printStackTrace();
		}
		return null;
	}

	// -- Private Methods
	/**
	 * 返回测试数据集
	 * 
	 * @param map
	 *            参数
	 * @return
	 */
	public  List returnTestData(Map map) {
		List<PieChartObj> Pielist = new ArrayList();
		Object naturalname = map.get("params");
		 List<ChartValue> cvList=null;
		 String strname=null;
	     String valueString="";
	     String typeString="";
	     String string="";
		JSONObject json = new JSONObject();
		json = json.fromObject(naturalname);
		Map nameMap=null;
		try {
			nameMap= jsonToMap(json);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//配置的数据源
		 if(((String) nameMap.get("name2")).equals((String) nameMap.get("name1"))){
			 
		 }else{
		ResourceRmi resource= null;
		try {
			resource=(ResourceRmi) RmiEntry.iv("resource");
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
		UmsProtectedobject upof1=null;
		try {
			upof1= resource.loadResourceByNatural((String) nameMap.get("name2"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String obl1=upof1.getExtendattribute();
	     if(obl1!="" || obl1!=null){
	     cvList=BTAnalysisXml.readXML(obl1);
	     }	
	     
	     for(int i=0;i<cvList.size();i++){
	    	 string =cvList.get(i).getId();
	    	 string=StringUtils.substringBetween(string, "[", "]");
	    	 if(string.equals((String) nameMap.get("name1"))){
	    		  if(i!=cvList.size()-1){
		    		 	strname =cvList.get(i+1).getId();
		    		 	strname=StringUtils.substringBetween(strname, "[", "]");
		    		 	 valueString=cvList.get(i).getValue();  
		    		 	GraphicsDisplaySelect gds=new GraphicsDisplaySelect();
		    		 	ObjectGraph ob=gds.ConfigurationData(strname);
		    		 	if(ob.getTubiaotype().equals("xianxingtu")){
		    		 		if(cvList.get(i).getBTModel().equals("0")){
		    		 			typeString="COMP_LINE_2L_CHART";
		    		 		}else{
		    		 			typeString="COMP_LINE_LINK_CHART";
		    		 		}
		    		 			
		    				}
		    		 	if(ob.getTubiaotype().equals("zhutu")){
		    		 		if(cvList.get(i).getBTModel().equals("0")){
		    		 			typeString="COMP_BAR_2L_CHART";
		    		 		}else{
		    		 			typeString="COMP_BAR_LINK_CHART";
		    		 		} 
	    				}
		    		 	if(ob.getTubiaotype().equals("bingtu")){
		    		 		if(cvList.get(i).getBTModel().equals("0")){
		    		 			typeString="COMP_LINE_2L_CHART";
		    		 		}else{
		    		 			typeString="COMP_LINE_LINK_CHART";
		    		 		} 
	    				}
		    		 	 } 
		    		    }
	     }
		 }
		 
	    //图表的数据
		ObjectGraph OGXML = null;
		ResourceRmi resourceRmi = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
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
		UmsProtectedobject upof = null;
		try {
			upof = resourceRmi.loadResourceByNatural((String) nameMap.get("name1"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String obl = upof.getExtendattribute();
		if (obl != "" || obl != null) {
			GraphicXML GX = new GraphicXML();
			obl = obl.trim();
			OGXML = GX.readXML(obl);
		}
		String Formcode = OGXML.getFormcode();
		String limitcolumn = OGXML.getQiepianweidu();
		String limit = OGXML.getQiepianweiduzhi();
		String childrenstring = OGXML.getKuozhantiaojian();
		String xdata = OGXML.getZhankaiX();
		String xuanzhezhibiao = OGXML.getXuanzhezhibiao();
		System.out.println("ss" + xuanzhezhibiao);
		String[] zbList = xuanzhezhibiao.split(",");
		//    	String[] zbList=deleteLast(zbList1);

		String predictionbegan = OGXML.getPredictionbegan();
		String predictionend = OGXML.getPredictionend();
		String Predictiontype = OGXML.getPredictiontype();

		if (childrenstring == null) {
			childrenstring = "";
		}

		String tablename;
		TCsForm busForm = null;
		try {
			DyFormDesignService dys = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
			ResourceInfo rsInfo = new ResourceInfo();
			busForm = dysc.loadForm(Formcode);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tablename = busForm.getTablename();
		String str = null;
		int j = 1;
		StringBuffer fdBuffer = new StringBuffer();
		String sqlString = null;
		ArrayList bArrayList = new ArrayList();
		List list = new ArrayList();
		for (int i = 0; i < zbList.length; i++) {
			if (i != zbList.length - 1) {

				fdBuffer.append(zbList[i]);
				fdBuffer.append(",");

			} else {
				j = i + 2;
				fdBuffer.append(zbList[i]);
			}
		}
		if (limit.equals("")) {
			sqlString = "select " + xdata + "," + fdBuffer.toString()
					+ " from dyform." + tablename + "";
		} else {
			sqlString = "select  " + xdata + " ," + fdBuffer.toString()
					+ " from dyform." + tablename + " where " + limitcolumn
					+ "='" + limit + "'" + childrenstring + " order by TIMEX";
		}
		System.out.println("......" + sqlString);
		ConnDB connDB = new ConnDB();
		ResultSet rSet = connDB.doQuery(sqlString);
		try {
			while (rSet.next()) {
				if (j == 1) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					list.add(xyobjcect);
				}
				if (j == 2) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					list.add(xyobjcect);
				}
				if (j == 3) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					list.add(xyobjcect);
				}
				if (j == 4) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					list.add(xyobjcect);
				}
				if (j == 5) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					list.add(xyobjcect);
				}
				if (j == 6) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					list.add(xyobjcect);
				}
				if (j == 7) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					list.add(xyobjcect);
				}
				if (j == 8) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					xyobjcect.setY6data(rSet.getString(8));
					list.add(xyobjcect);
				}
				if (j == 9) {
					xyData xyobjcect = new xyData();
					xyobjcect.setXdata(rSet.getString(1));
					xyobjcect.setYdata(rSet.getString(2));
					xyobjcect.setY1data(rSet.getString(3));
					xyobjcect.setY2data(rSet.getString(4));
					xyobjcect.setY3data(rSet.getString(5));
					xyobjcect.setY4data(rSet.getString(6));
					xyobjcect.setY5data(rSet.getString(7));
					xyobjcect.setY6data(rSet.getString(8));
					xyobjcect.setY7data(rSet.getString(9));
					list.add(xyobjcect);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connDB.close();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			xyData object = (xyData) iterator.next();
			PieChartObj PieChartObj = new PieChartObj();
			PieChartObj.setKey(object.getXdata());
			if(valueString.equals(object.getXdata())){
			PieChartObj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
			}
			double Data = Double.valueOf(object.getYdata()).doubleValue();
			PieChartObj.setValue(Data);
			Pielist.add(PieChartObj);
		}

		predictionbegan.trim();
		predictionend.trim();
		Predictiontype.trim();
		List<PieChartObj> list1 = null;
		if (predictionbegan != null && predictionbegan.length() != 0
				&& predictionend != null && predictionend.length() != 0
				&& Predictiontype != null && Predictiontype.length() != 0) {
			list1 = PiePredictive.predictiveList(predictionbegan,
					predictionend, Predictiontype, xdata, zbList, limitcolumn,
					limit, tablename, j, Formcode);
			for (int i = 0; i < list1.size(); i++) {
				Pielist.add(list1.get(i));
			}
		}
		return Pielist;
	}

	private List returnTestData2(Map map) {
		Object naturalname = map.get("params");
		JSONObject json = new JSONObject();
		json = json.fromObject(naturalname);
		Map nameMap=null;
		try {
			nameMap= jsonToMap(json);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 List<ChartValue> cvList=null;
		 String strname=null;
	     String valueString="";
	     String typeString="";
	     String string="";
	    if(((String) nameMap.get("name2")).equals((String) nameMap.get("name1"))){
			 
		 }else{
		ResourceRmi resource= null;
		try {
			resource=(ResourceRmi) RmiEntry.iv("resource");
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
		UmsProtectedobject upof1=null;
		try {
			upof1= resource.loadResourceByNatural((String) nameMap.get("name2"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String obl1=upof1.getExtendattribute();
	     if(obl1!="" || obl1!=null){
	     cvList=BTAnalysisXml.readXML(obl1);
	     }	
	     
	     for(int i=0;i<cvList.size();i++){
	    	 string =cvList.get(i).getId();
	    	 string=StringUtils.substringBetween(string, "[", "]");
	    	 if(string.equals((String) nameMap.get("name1"))){
	    		  if(i!=cvList.size()-1){
		    		 	strname =cvList.get(i+1).getId();
		    		 	strname=StringUtils.substringBetween(strname, "[", "]");
		    		 	valueString=cvList.get(i).getValue();  
		    		 	 }else{
		    		 	 }
		    		    typeString=cvList.get(i).getType();	    	 }
	     }
		 }
	    String string2="{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\"}";
	    map.put("params", string2);
		List  list=returnTestData(map);
			 
		return list;
	}

}
