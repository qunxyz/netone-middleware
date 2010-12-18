package oe.teach.web.chart.pie;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.teach.mid.bussmap.CommSelecTools;
import oe.teach.web.chart.ChartMaker;
import oe.teach.web.chart.PrintWebChart;
import oe.teach.web.chart.bar.BarObj;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 饼图实现
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class PieMakerImpl implements ChartMaker {

	public String todo(HttpServletRequest request, String datasource,
			String title, String xtitle, String ytitle, String width,
			String height) {
		// 构造Pie数据
		DefaultPieDataset dataset = new DefaultPieDataset();
		List data = (new CommSelecTools()).fetchdata(datasource, PieObj.class);
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			PieObj object = (PieObj) iterator.next();
			dataset.setValue(object.getLevel1(), object.getValue());
		}

		Font font = new Font("SimSun", 10, 13);

		// 构造Pie展现对象
		JFreeChart jfreechart = ChartFactory.createPieChart(title, dataset,
				false, false, false);

		TextTitle txtTitle = jfreechart.getTitle();
		txtTitle.setFont(font);
		// jfreechart.getLegend().setItemFont(font);

		jfreechart.setBackgroundPaint(Color.white);
		jfreechart.setBorderVisible(false);

		PiePlot plot3 = (PiePlot) jfreechart.getPlot();
		plot3.setForegroundAlpha(0.6f);
		plot3.setCircular(true);
		plot3.setBackgroundPaint(Color.white);

		plot3.setLabelFont(font);

		// 设置pie展现对象中，显示的标题内容
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
				"{0}{1}={2}", new DecimalFormat("0.00"), new DecimalFormat(
						"0.00%"));

		plot3.setLabelGenerator(generator);

		if (width == null || height == null) {
			return PrintWebChart.toprint(jfreechart, 300, 250, datasource,
					request);
		} else {
			return PrintWebChart.toprint(jfreechart, Integer.parseInt(width),
					Integer.parseInt(height), datasource, request);
		}
	}

	public void setDelay(String particiapnt, long time) {
		// TODO Auto-generated method stub

	}

}
