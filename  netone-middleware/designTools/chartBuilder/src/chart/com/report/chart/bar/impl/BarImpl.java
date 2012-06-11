package com.report.chart.bar.impl;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.report.chart.entity.BarChartObj;
import com.report.chart.ifc.BarIfc;

/**
 * 柱状图实现类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 上午11:11:09
 * @history
 */
public class BarImpl implements BarIfc {

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
		JFreeChart jfreechart = ChartFactory.createBarChart(title,
				categoryAxisLabel, valueAxisLabel, categorydataset,
				PlotOrientation.VERTICAL, false, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setUpperMargin(0.14999999999999999D);
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		categoryitemrenderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		return jfreechart;
	}

	public void render() {

	}

}
