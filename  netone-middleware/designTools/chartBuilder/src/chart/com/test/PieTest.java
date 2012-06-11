/**
 * 
 */
package com.test;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

import com.report.chart.common.ChartCommon;
import com.report.chart.entity.PieChartObj;
import com.report.chart.ifc.PieIfc;
import com.report.chart.pie.impl.PieImpl;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 ����10:37:30
 * @history
 */
public class PieTest {
	public static JFreeChart todo() {
		// 1.����ʵ������
		PieIfc pie = new PieImpl();

		// 2.��������Դ
		List<PieChartObj> list = new ArrayList();
		PieChartObj PieChartObj = new PieChartObj();
		PieChartObj.setKey("One");
		PieChartObj.setValue(43.200000000000003D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Two");
		PieChartObj.setValue(10D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Three");
		PieChartObj.setValue(27.5D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Four");
		PieChartObj.setValue(17.5D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Five");
		PieChartObj.setValue(11D);
		list.add(PieChartObj);

		PieChartObj = new PieChartObj();
		PieChartObj.setKey("Six");
		PieChartObj.setValue(19.399999999999999D);
		list.add(PieChartObj);

		// 3.����ͼ��
		PieDataset pieDataset = pie.createDataset(list);
		JFreeChart chart = pie.createChart("Pie Chart Demo ", pieDataset);
		// 3.5 ���Զ�JFreeChart chart���ж�����չ
		return chart;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 4.ͼ�������ʽ(��������ҳ����ļ���ʽչ��)
		ChartCommon.saveChartAsJPEG("PieChart.jpg", todo());
	}

}
