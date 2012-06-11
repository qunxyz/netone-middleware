package com.report.chart.bar.impl;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.report.chart.entity.BarChartObj;
import com.report.chart.ifc.BarIfc;

/**
 * 柱状图3D实现类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 上午11:11:09
 * @history
 */
public class Bar3DImpl implements BarIfc {

	public CategoryDataset createDataset(List<BarChartObj> list) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for (BarChartObj barChartObj : list) {
			defaultcategorydataset.addValue(barChartObj.getValue(), barChartObj
					.getRowKey(), barChartObj.getColumnKey());
		}
		return defaultcategorydataset;
	}

	public JFreeChart createChart(String title, String categoryAxisLabel,
			String valueAxisLabel, CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D(title,
				categoryAxisLabel, valueAxisLabel, categorydataset,
				PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.39269908169872414D));
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelsVisible(true);
		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;
		barrenderer.setMaximumBarWidth(0.050000000000000003D);
		return jfreechart;
	}

	public void render() {
		// TODO Auto-generated method stub

	}

}
