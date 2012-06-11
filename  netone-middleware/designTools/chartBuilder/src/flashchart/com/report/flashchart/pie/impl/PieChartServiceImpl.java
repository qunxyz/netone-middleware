/**
 * 
 */
package com.report.flashchart.pie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.report.chart.entity.PieChartObj;
import com.report.flashchart.BaseChart;
import com.report.flashchart.ifc.PieChartService;

/**
 * ͼ��ҵ��ʵ����
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:45:29 AM
 * @history
 */
public class PieChartServiceImpl extends BaseChart implements PieChartService {

	public PieChartServiceImpl() {
	}

	/**
	 * flash���ص�����Դ
	 * 
	 * @param param
	 *            ��JSON��ʽ���� ��:{title:'����'}
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
			System.out.println("ͼ�������" + e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * flash���ص�����Դ
	 * 
	 * @param param
	 *            ��JSON��ʽ���� ��:{title:'����'}
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
			System.out.println("ͼ�������" + e);
			e.printStackTrace();
		}
		return null;
	}
	
	// -- Private Methods
	/**
	 * ���ز������ݼ�
	 * 
	 * @param map
	 *            ����
	 * @return
	 */
	private List returnTestData(Map map) {
		List<PieChartObj> list = new ArrayList();
		PieChartObj PieChartObj = new PieChartObj();
		PieChartObj.setKey("One");
		PieChartObj.setValue(43.200000000000003D);
		PieChartObj.setParams("{\"p2\":1,\"$comp$\":\"COMP_PIE_2L_CHART\"}");
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Two");
		PieChartObj.setValue(10D);
		PieChartObj.setParams("{\"p2\":1,\"$comp$\":\"COMP_PIE_2L_CHART\"}");
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Three");
		PieChartObj.setValue(27.5D);
		PieChartObj.setParams("{\"p2\":1,\"$comp$\":\"COMP_PIE_2L_CHART\"}");
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Four");
		PieChartObj.setValue(17.5D);
		PieChartObj.setParams("{\"p2\":1,\"$comp$\":\"COMP_PIE_2L_CHART\"}");
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Five");
		PieChartObj.setValue(11D);
		PieChartObj.setParams("{\"p2\":1,\"$comp$\":\"COMP_PIE_2L_CHART\"}");
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Six");
		PieChartObj.setValue(19.399999999999999D);
		PieChartObj.setParams("{\"p2\":1,\"$comp$\":\"COMP_PIE_2L_CHART\"}");
		list.add(PieChartObj);
		return list;
	}

	private List returnTestData2(Map map) {
		List<PieChartObj> list = new ArrayList();
		PieChartObj PieChartObj = new PieChartObj();
		PieChartObj.setKey("One2");
		PieChartObj.setValue(43.200000000000003D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Two2");
		PieChartObj.setValue(10D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Three2");
		PieChartObj.setValue(27.5D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Four2");
		PieChartObj.setValue(17.5D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Five2");
		PieChartObj.setValue(11D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Six2");
		PieChartObj.setValue(19.399999999999999D);
		list.add(PieChartObj);
		return list;
	}

}
