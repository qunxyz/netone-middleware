package com.report.chart.line.impl;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.report.chart.entity.LineChartObj;
import com.report.chart.ifc.LineIfc;

/**
 * 线图实现类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 上午11:11:09
 * @history
 */
public class LineImpl implements LineIfc {

	public CategoryDataset createDataset(List<LineChartObj> list) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for (LineChartObj lineChartObj : list) {
			defaultcategorydataset.addValue(lineChartObj.getValue(),
					lineChartObj.getRowKey(), lineChartObj.getColumnKey());
		}
		return defaultcategorydataset;
	}

	public JFreeChart createChart(String title, String categoryAxisLabel,
			String valueAxisLabel, CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createLineChart(title,
				categoryAxisLabel, valueAxisLabel, categorydataset,
				PlotOrientation.VERTICAL, false, true, false);
		jfreechart.addSubtitle(new TextTitle("Number of Classes By Release"));
		// TextTitle texttitle = new TextTitle(
		// "Source: Java In A Nutshell (4th Edition) by David Flanagan
		// (O'Reilly)");
		// texttitle.setFont(new Font("SansSerif", 0, 10));
		// texttitle.setPosition(RectangleEdge.BOTTOM);
		// texttitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		// jfreechart.addSubtitle(texttitle);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setFillPaint(Color.white);
		return jfreechart;
	}

	public void render() {
		// TODO Auto-generated method stub

	}

}
