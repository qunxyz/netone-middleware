package com.report.chart.ifc;

import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

import com.report.chart.entity.PieChartObj;

/**
 * 饼图接口
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 下午02:36:49
 * @history
 */
public interface PieIfc {

	/**
	 * 构造数据源
	 * 
	 * @param list
	 * @return
	 */
	PieDataset createDataset(List<PieChartObj> list);

	/**
	 * 获取图表对象
	 * 
	 * @param title
	 *            标题
	 * @param piedataset
	 *            数据源
	 * @return
	 */
	JFreeChart createChart(String title, PieDataset piedataset);

	/**
	 * 展现
	 */
	void render();

}
