/**
 * 
 */
package com.report.flashchart.bar.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.report.chart.entity.BarChartObj;
import com.report.flashchart.BaseChart;
import com.report.flashchart.ifc.BarChartService;

/**
 * 图表业务实现类
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:45:29 AM
 * @history
 */
public class BarChartServiceImpl extends BaseChart implements BarChartService {

	public BarChartServiceImpl() {
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
	private List returnTestData(Map map) {
		String s = "First";
		String s1 = "Second";
		String s2 = "Third";
		String s3 = "Category 1";
		String s4 = "Category 2";
		String s5 = "Category 3";
		String s6 = "Category 4";
		String s7 = "Category 5";
		List<BarChartObj> list = new ArrayList();
		BarChartObj obj = new BarChartObj();
		obj.setValue(1.0D);
		obj.setRowKey(s);
		obj.setColumnKey(s3);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s);
		obj.setColumnKey(s4);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(3D);
		obj.setRowKey(s);
		obj.setColumnKey(s5);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s);
		obj.setColumnKey(s6);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s);
		obj.setColumnKey(s7);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s1);
		obj.setColumnKey(s3);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(7D);
		obj.setRowKey(s1);
		obj.setColumnKey(s4);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(6D);
		obj.setRowKey(s1);
		obj.setColumnKey(s5);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(8D);
		obj.setRowKey(s1);
		obj.setColumnKey(s6);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s1);
		obj.setColumnKey(s7);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s2);
		obj.setColumnKey(s3);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(3D);
		obj.setRowKey(s2);
		obj.setColumnKey(s4);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(2D);
		obj.setRowKey(s2);
		obj.setColumnKey(s5);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(3D);
		obj.setRowKey(s2);
		obj.setColumnKey(s6);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(6D);
		obj.setRowKey(s2);
		obj.setColumnKey(s7);
		obj.setParams("{\"p2\":1,\"$comp$\":\"COMP_BAR_LINK_CHART\"}");
		list.add(obj);
		return list;
	}

	private List returnTestData2(Map map) {
		String s = "First";
		String s1 = "Second";
		String s2 = "Third";
		String s3 = "Category 1";
		String s4 = "Category 2";
		String s5 = "Category 3";
		String s6 = "Category 4";
		String s7 = "Category 5";
		List<BarChartObj> list = new ArrayList();
		BarChartObj obj = new BarChartObj();
		obj.setValue(21.0D);
		obj.setRowKey(s);
		obj.setColumnKey(s3);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(24D);
		obj.setRowKey(s);
		obj.setColumnKey(s4);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(23D);
		obj.setRowKey(s);
		obj.setColumnKey(s5);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(25D);
		obj.setRowKey(s);
		obj.setColumnKey(s6);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s);
		obj.setColumnKey(s7);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(25D);
		obj.setRowKey(s1);
		obj.setColumnKey(s3);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(27D);
		obj.setRowKey(s1);
		obj.setColumnKey(s4);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(26D);
		obj.setRowKey(s1);
		obj.setColumnKey(s5);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(8D);
		obj.setRowKey(s1);
		obj.setColumnKey(s6);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(24D);
		obj.setRowKey(s1);
		obj.setColumnKey(s7);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s2);
		obj.setColumnKey(s3);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(23D);
		obj.setRowKey(s2);
		obj.setColumnKey(s4);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(22D);
		obj.setRowKey(s2);
		obj.setColumnKey(s5);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(23D);
		obj.setRowKey(s2);
		obj.setColumnKey(s6);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(26D);
		obj.setRowKey(s2);
		obj.setColumnKey(s7);
		list.add(obj);
		return list;
	}

}
