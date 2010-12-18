package oe.teach.web.chart.bar;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.teach.mid.bussmap.CommSelecTools;
import oe.teach.web.chart.ChartMaker;
import oe.teach.web.chart.PrintWebChart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 饼图实现
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class BarMakerImpl implements ChartMaker {
	public String todo(HttpServletRequest request, String datasource,
			String title, String xtitle, String ytitle, String width, String height) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		List data = (new CommSelecTools()).fetchdata(datasource, BarObj.class);
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			BarObj object = (BarObj) iterator.next();
			dataset.addValue(object.getValue(), object.getLevel1(), object
					.getLevel2());
		}

		JFreeChart chart = ChartFactory.createBarChart(title, xtitle, ytitle,
				dataset, PlotOrientation.VERTICAL, true, false, false);
		Font font = new Font("SimSun", 10, 13);

		chart.getLegend().setItemFont(font);

		TextTitle txtTitle = chart.getTitle();
		txtTitle.setFont(font);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		plot.setBackgroundPaint(Color.white);

		CategoryAxis domainAxis = plot.getDomainAxis(); // 获得横轴——目录轴

		ValueAxis rangeAxis = plot.getRangeAxis(); // 获得纵轴——数值轴

		domainAxis.setLabelFont(font);

		domainAxis.setTickLabelFont(font);

		rangeAxis.setLabelFont(font);

		if (width == null || height == null) {
			return PrintWebChart.toprint(chart, 500, 260, datasource, request);
		}else{
			return PrintWebChart.toprint(chart, Integer.parseInt(width), Integer.parseInt(height), datasource, request);
		}
	}

	public void setDelay(String particiapnt, long time) {
		// TODO Auto-generated method stub

	}

}
