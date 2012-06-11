package com.report.chart.ifc;

import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import com.report.chart.entity.LineChartObj;

/**
 * 线图接口
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 下午02:36:35
 * @history
 */
public interface LineIfc {

	/**
	 * 构造数据源
	 * 
	 * @param list
	 * @return
	 */
	CategoryDataset createDataset(List<LineChartObj> list);

	/**
	 * 获取图表对象
	 * 
	 * @param title
	 *            标题
	 * @param categoryAxisLabel
	 *            纵轴label
	 * @param valueAxisLabel
	 *            值label
	 * @param categorydataset
	 *            数据源
	 * @return
	 */
	JFreeChart createChart(String title, String categoryAxisLabel,
			String valueAxisLabel, CategoryDataset categorydataset);

	/**
	 * 展现
	 */
	void render();

}
