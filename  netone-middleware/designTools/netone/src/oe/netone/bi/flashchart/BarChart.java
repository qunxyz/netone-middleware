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

import oe.cav.bean.logic.column.TCsColumn;
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

import net.sf.json.JSONObject;
import com.report.chart.entity.BarChartObj;
import com.report.flashchart.BaseChart;
import com.report.flashchart.ifc.BarChartService;

/**
 * 柱图  图表业务实现类
 *   xuwei（2012-2-13）
 *
 */
public class BarChart extends BaseChart implements BarChartService {

	public BarChart() {
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
			list = transformBarChartData(list);
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
			list = transformBarChartData(list);
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
	public List returnTestData(Map map) {
		List<BarChartObj> Barlist = new ArrayList();
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
		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
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

		TCsColumn busForm1 = new TCsColumn();
		busForm1.setFormcode(OGXML.getFormcode());
		//long indexname=dys.queryObjectsNumber(busForm);
		List listmame1 = null;
		try {
			listmame1 = dys.queryObjects(busForm1);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//获取到row的键值  
		String yString = "";
		String y1String = "";
		String y2String = "";
		String y3String = "";
		String y4String = "";
		String y5String = "";
		String y6String = "";
		String y7String = "";

		for (Iterator iterator1 = listmame1.iterator(); iterator1.hasNext();) {
			TCsColumn columnname = (TCsColumn) iterator1.next();

			for (int i = 0; i < zbList.length; i++) {
				if (columnname.getColumncode().equals(zbList[i])) {
					if (i == 0) {
						yString = columnname.getColumname();
					}
					if (i == 1) {
						y1String = columnname.getColumname();
					}
					if (i == 2) {
						y2String = columnname.getColumname();
					}
					if (i == 3) {
						y3String = columnname.getColumname();
					}
					if (i == 4) {
						y4String = columnname.getColumname();
					}
					if (i == 5) {
						y5String = columnname.getColumname();
					}
					if (i == 6) {
						y6String = columnname.getColumname();
					}
					if (i == 7) {
						y7String = columnname.getColumname();
					}
				}
			}
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
			if (j == 2) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barobj.setColumnKey(object.xdata);
				Barlist.add(Barobj);
			}
			if (j == 3) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
 				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
 				 }
				Barlist.add(Barobj);
				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
 				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
 				}
				Barobj1.setColumnKey(object.xdata);
				Barlist.add(Barobj1);
			}
			if (j == 4) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj);

				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
				Barobj1.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj1);

				BarChartObj Barobj2 = new BarChartObj();
				double Data2 = Double.valueOf(object.getY2data()).doubleValue();
				Barobj2.setValue(Data2);
				Barobj2.setRowKey(y2String);
				Barobj2.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj2.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj2);
			}
			if (j == 5) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj);

				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
				Barobj1.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj1);

				BarChartObj Barobj2 = new BarChartObj();
				double Data2 = Double.valueOf(object.getY2data()).doubleValue();
				Barobj2.setValue(Data2);
				Barobj2.setRowKey(y2String);
				Barobj2.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj2.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj2);

				BarChartObj Barobj3 = new BarChartObj();
				double Data3 = Double.valueOf(object.getY3data()).doubleValue();
				Barobj3.setValue(Data3);
				Barobj3.setRowKey(y3String);
				Barobj3.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj3.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj3);
			}
			if (j == 6) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj);

				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
				Barobj1.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj1);

				BarChartObj Barobj2 = new BarChartObj();
				double Data2 = Double.valueOf(object.getY2data()).doubleValue();
				Barobj2.setValue(Data2);
				Barobj2.setRowKey(y2String);
				Barobj2.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj2.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj2);

				BarChartObj Barobj3 = new BarChartObj();
				double Data3 = Double.valueOf(object.getY3data()).doubleValue();
				Barobj3.setValue(Data3);
				Barobj3.setRowKey(y3String);
				Barobj3.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj3.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj3);

				BarChartObj Barobj4 = new BarChartObj();
				double Data4 = Double.valueOf(object.getY4data()).doubleValue();
				Barobj4.setValue(Data4);
				Barobj4.setRowKey(y4String);
				Barobj4.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj4.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj4);
			}
			if (j == 7) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj);

				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
				Barobj1.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj1);

				BarChartObj Barobj2 = new BarChartObj();
				double Data2 = Double.valueOf(object.getY2data()).doubleValue();
				Barobj2.setValue(Data2);
				Barobj2.setRowKey(y2String);
				Barobj2.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj2.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj2);

				BarChartObj Barobj3 = new BarChartObj();
				double Data3 = Double.valueOf(object.getY3data()).doubleValue();
				Barobj3.setValue(Data3);
				Barobj3.setRowKey(y3String);
				Barobj3.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj3.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj3);

				BarChartObj Barobj4 = new BarChartObj();
				double Data4 = Double.valueOf(object.getY4data()).doubleValue();
				Barobj4.setValue(Data4);
				Barobj4.setRowKey(y4String);
				Barobj4.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj4.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj4);

				BarChartObj Barobj5 = new BarChartObj();
				double Data5 = Double.valueOf(object.getY5data()).doubleValue();
				Barobj5.setValue(Data5);
				Barobj5.setRowKey(y5String);
				Barobj5.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj5.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj5);
			}
			if (j == 8) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj);

				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
				Barobj1.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj1);

				BarChartObj Barobj2 = new BarChartObj();
				double Data2 = Double.valueOf(object.getY2data()).doubleValue();
				Barobj2.setValue(Data2);
				Barobj2.setRowKey(y2String);
				Barobj2.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj2.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj2);

				BarChartObj Barobj3 = new BarChartObj();
				double Data3 = Double.valueOf(object.getY3data()).doubleValue();
				Barobj3.setValue(Data3);
				Barobj3.setRowKey(y3String);
				Barobj3.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj3.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj3);

				BarChartObj Barobj4 = new BarChartObj();
				double Data4 = Double.valueOf(object.getY4data()).doubleValue();
				Barobj4.setValue(Data4);
				Barobj4.setRowKey(y4String);
				Barobj4.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj4.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj4);

				BarChartObj Barobj5 = new BarChartObj();
				double Data5 = Double.valueOf(object.getY5data()).doubleValue();
				Barobj5.setValue(Data5);
				Barobj5.setRowKey(y5String);
				Barobj5.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj5.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj5);

				BarChartObj Barobj6 = new BarChartObj();
				double Data6 = Double.valueOf(object.getY6data()).doubleValue();
				Barobj6.setValue(Data6);
				Barobj6.setRowKey(y6String);
				Barobj6.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj6.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj6);
			}
			if (j == 9) {
				BarChartObj Barobj = new BarChartObj();
				double Data = Double.valueOf(object.getYdata()).doubleValue();
				Barobj.setValue(Data);
				Barobj.setRowKey(yString);
				Barobj.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj);

				BarChartObj Barobj1 = new BarChartObj();
				double Data1 = Double.valueOf(object.getY1data()).doubleValue();
				Barobj1.setValue(Data1);
				Barobj1.setRowKey(y1String);
				Barobj1.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj1.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj1);

				BarChartObj Barobj2 = new BarChartObj();
				double Data2 = Double.valueOf(object.getY2data()).doubleValue();
				Barobj2.setValue(Data2);
				Barobj2.setRowKey(y2String);
				Barobj2.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj2.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj2);

				BarChartObj Barobj3 = new BarChartObj();
				double Data3 = Double.valueOf(object.getY3data()).doubleValue();
				Barobj3.setValue(Data3);
				Barobj3.setRowKey(y3String);
				Barobj3.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj3.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj3);

				BarChartObj Barobj4 = new BarChartObj();
				double Data4 = Double.valueOf(object.getY4data()).doubleValue();
				Barobj4.setValue(Data4);
				Barobj4.setRowKey(y4String);
				Barobj4.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj4.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj4);

				BarChartObj Barobj5 = new BarChartObj();
				double Data5 = Double.valueOf(object.getY5data()).doubleValue();
				Barobj5.setValue(Data5);
				Barobj5.setRowKey(y5String);
				Barobj5.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj5.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj5);

				BarChartObj Barobj6 = new BarChartObj();
				double Data6 = Double.valueOf(object.getY6data()).doubleValue();
				Barobj6.setValue(Data6);
				Barobj6.setRowKey(y6String);
				Barobj6.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj6.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj6);

				BarChartObj Barobj7 = new BarChartObj();
				double Data7 = Double.valueOf(object.getY7data()).doubleValue();
				Barobj7.setValue(Data7);
				Barobj7.setRowKey(y7String);
				Barobj7.setColumnKey(object.xdata);
				if(valueString.equals(object.xdata)){
					Barobj7.setParams("{\"name1\":\""+strname+"\",\"name2\":\""+(String) nameMap.get("name2")+"\",\"value\":\""+valueString+"\",\"$comp$\":\""+typeString+"\"}");
					}
				Barlist.add(Barobj7);
			}

		}
		predictionbegan.trim();
		predictionend.trim();
		Predictiontype.trim();
		List<BarChartObj> list1 = null;
		if (predictionbegan != null && predictionbegan.length() != 0
				&& predictionend != null && predictionend.length() != 0
				&& Predictiontype != null && Predictiontype.length() != 0) {
			list1 = BarPredictive.predictiveList(predictionbegan,
					predictionend, Predictiontype, xdata, zbList, limitcolumn,
					limit, tablename, j, Formcode);
			for (int i = 0; i < list1.size(); i++) {
				Barlist.add(list1.get(i));
			}
		}
		return Barlist;
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
