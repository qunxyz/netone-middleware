package com.report.chart.pie.impl;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.report.chart.entity.PieChartObj;
import com.report.chart.ifc.PieIfc;

/**
 * 饼图实现类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 上午11:11:09
 * @history
 */
public class PieImpl implements PieIfc {

	public PieDataset createDataset(List<PieChartObj> list) {
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		for (PieChartObj pieChartObj : list) {
			defaultpiedataset.setValue(pieChartObj.getKey(), pieChartObj
					.getValue());
		}
		return defaultpiedataset;
	}

	public JFreeChart createChart(String title, PieDataset piedataset) {
		JFreeChart jfreechart = ChartFactory.createPieChart(title, piedataset,
				true, true, false);
		PiePlot pieplot = (PiePlot) jfreechart.getPlot();
		// pieplot.setSectionPaint(0, new Color(160, 160, 255));
		// pieplot.setSectionPaint(1, new Color(128, 128, 223));
		// pieplot.setSectionPaint(2, new Color(96, 96, 191));
		// pieplot.setSectionPaint(3, new Color(64, 64, 159));
		// pieplot.setSectionPaint(4, new Color(32, 32, 127));
		// pieplot.setSectionPaint(5, new Color(0, 0, 111));
		pieplot.setNoDataMessage("No data available");
		// pieplot.setExplodePercent(1, 0.5D);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} ({2} percent)"));
		pieplot.setLabelBackgroundPaint(new Color(220, 220, 220));
		pieplot
				.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator(
						"Tooltip for legend item {0}"));
		return jfreechart;
	}

	public void render() {
		// TODO Auto-generated method stub

	}

}
