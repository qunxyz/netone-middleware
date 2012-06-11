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
 * @version 1.0.0 2012-1-9 上午10:38:49
 * @history
 */
public class LineTest {
	public static JFreeChart todo() {
		// 1.创建实例对象
		LineIfc line = new LineImpl();

		// 2.构造数据源
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

		// 3.生成图表
		CategoryDataset categorydataset = line.createDataset(list);
		JFreeChart chart = line.createChart("Java Standard Class Library",
				"Release", "Class Count", categorydataset);
		// 3.5 可以对JFreeChart chart进行二次扩展
		return chart;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 4.图表输出方式(输出结果以页面或文件方式展现)
		ChartCommon.saveChartAsJPEG("LineChart.jpg", todo());
	}

}
