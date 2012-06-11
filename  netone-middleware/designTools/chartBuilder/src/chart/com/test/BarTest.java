/**
 * 
 */
package com.test;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import com.report.chart.bar.impl.BarImpl;
import com.report.chart.common.ChartCommon;
import com.report.chart.entity.BarChartObj;
import com.report.chart.ifc.BarIfc;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 上午10:38:26
 * @history
 */
public class BarTest {
	public static JFreeChart todo() {
		// 1.创建实例对象
		BarIfc bar = new BarImpl();

		// 2.构造数据源
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
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s);
		obj.setColumnKey(s4);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(3D);
		obj.setRowKey(s);
		obj.setColumnKey(s5);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s);
		obj.setColumnKey(s6);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s);
		obj.setColumnKey(s7);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(5D);
		obj.setRowKey(s1);
		obj.setColumnKey(s3);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(7D);
		obj.setRowKey(s1);
		obj.setColumnKey(s4);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(6D);
		obj.setRowKey(s1);
		obj.setColumnKey(s5);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(8D);
		obj.setRowKey(s1);
		obj.setColumnKey(s6);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s1);
		obj.setColumnKey(s7);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(4D);
		obj.setRowKey(s2);
		obj.setColumnKey(s3);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(3D);
		obj.setRowKey(s2);
		obj.setColumnKey(s4);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(2D);
		obj.setRowKey(s2);
		obj.setColumnKey(s5);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(3D);
		obj.setRowKey(s2);
		obj.setColumnKey(s6);
		list.add(obj);

		obj = new BarChartObj();
		obj.setValue(6D);
		obj.setRowKey(s2);
		obj.setColumnKey(s7);
		list.add(obj);

		// 3.生成图表
		CategoryDataset categorydataset = bar.createDataset(list);
		JFreeChart chart = bar.createChart("Bar Chart Demo", "Category",
				"Value", categorydataset);
		// 3.5 可以对JFreeChart chart进行二次扩展
		return chart;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 4.图表输出方式(输出结果以页面或文件方式展现)
		ChartCommon.saveChartAsJPEG("BarChart.jpg", todo());
	}

}
