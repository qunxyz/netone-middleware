/**
 * 
 */
package com.report.flashchart.line.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.report.chart.entity.LineChartObj;
import com.report.flashchart.BaseChart;
import com.report.flashchart.ifc.LineChartService;

/**
 * 图表业务实现类
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:45:29 AM
 * @history
 */
public class LineChartServiceImpl extends BaseChart implements LineChartService {

	public LineChartServiceImpl() {
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
			list = transformLineChartData(list);
			String s = toJSONStr(list);
			return s;
		} catch (Exception e) {
			System.out.println("图表出错了" + e);
			e.printStackTrace();
		}
		return null;
	}

	public String load2LData(String param) {
		JSONObject json = new JSONObject();
		json = json.fromObject(param);
		try {
			Map map = jsonToMap(json);

			List list = returnTestData2(map);
			list = transformLineChartData(list);
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
	private List returnTestData(Map map) {
		List<LineChartObj> list = new ArrayList();
		LineChartObj LineChartObj = new LineChartObj();
		LineChartObj.setValue(212D);
		LineChartObj.setRowKey("Classes");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		LineChartObj.setColumnKey("JDK 1.0");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(504D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.1");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1520D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.2");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1842D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.3");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(2991D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.4");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1212D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.0");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(534D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.1");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1720D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.2");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1942D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.3");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(2091D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.4");
		LineChartObj
				.setParams("{\"p2\":1,\"$comp$\":\"COMP_LINE_LINK_CHART\"}");
		list.add(LineChartObj);
		return list;
	}

	private List returnTestData2(Map map) {
		List<LineChartObj> list = new ArrayList();
		LineChartObj LineChartObj = new LineChartObj();
		LineChartObj.setValue(2212D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("PHP 1.0");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(2504D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("PHP 1.1");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(21520D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("PHP 1.2");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(21842D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("PHP 1.3");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(22991D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("PHP 1.4");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(21212D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("PHP 1.0");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(534D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("PHP 1.1");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(21720D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("PHP 1.2");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(21942D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("PHP 1.3");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(22091D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("PHP 1.4");
		list.add(LineChartObj);
		return list;
	}

}
