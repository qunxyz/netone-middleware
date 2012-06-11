/**
 * 
 */
package com.test;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import com.report.chart.common.ChartCommon;
import com.report.chart.entity.LineChartObj;
import com.report.chart.ifc.LineIfc;
import com.report.chart.line.impl.LineImpl;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 ����10:38:49
 * @history
 */
public class LineTest {
	public static JFreeChart todo() {
		// 1.����ʵ������
		LineIfc line = new LineImpl();

		// 2.��������Դ
		List<LineChartObj> list = new ArrayList();

		LineChartObj LineChartObj = new LineChartObj();
		LineChartObj.setValue(212D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.0");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(504D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.1");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1520D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.2");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1842D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.3");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(2991D);
		LineChartObj.setRowKey("Classes");
		LineChartObj.setColumnKey("JDK 1.4");
		list.add(LineChartObj);

		LineChartObj.setValue(232D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.0");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(534D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.1");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1720D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.2");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(1942D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.3");
		list.add(LineChartObj);

		LineChartObj = new LineChartObj();
		LineChartObj.setValue(2091D);
		LineChartObj.setRowKey("Classes2");
		LineChartObj.setColumnKey("JDK 1.4");
		list.add(LineChartObj);

		// 3.����ͼ��
		CategoryDataset categorydataset = line.createDataset(list);
		JFreeChart chart = line.createChart("Java Standard Class Library",
				"Release", "Class Count", categorydataset);
		// 3.5 ���Զ�JFreeChart chart���ж�����չ
		return chart;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 4.ͼ�������ʽ(��������ҳ����ļ���ʽչ��)
		ChartCommon.saveChartAsJPEG("LineChart.jpg", todo());
	}

}
