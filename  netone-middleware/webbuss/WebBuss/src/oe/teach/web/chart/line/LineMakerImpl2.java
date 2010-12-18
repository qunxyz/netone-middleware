package oe.teach.web.chart.line;

import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.teach.mid.bussmap.CommSelecTools;
import oe.teach.web.chart.ChartMaker;
import oe.teach.web.chart.PrintWebChart;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * 线图实现类
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class LineMakerImpl2 implements ChartMaker {
	public String todo(HttpServletRequest request, String datasource,
			String title, String xtitle, String ytitle, String width,
			String height) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		String titlearr[] = title.split("0000");
		String dsarr[] = datasource.split("0000");
		title = title.replace("0000", "&");
		int length = titlearr.length;
		if (dsarr.length < titlearr.length) {
			length = dsarr.length;
		}

		TimeSeries pop[] = new TimeSeries[length];
		String format = "yyyy-MM-dd";
		for (int i = 0; i < pop.length; i++) {
			pop[i] = new TimeSeries(titlearr[i], Day.class);
			List data = (new CommSelecTools()).fetchdata(dsarr[i],
					LineObj.class);

			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				LineObj object = (LineObj) iterator.next();
				String level1 = object.getLevel1();
				format = object.getLevel2();
				pop[i].add(new Day(Date.valueOf(level1)), object.getValue());

			}
			dataset.addSeries(pop[i]);
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, xtitle,
				ytitle, dataset, true, true, false);

		// 设置日期显示格式
		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat(format));

		Font font = new Font("SimSun", 10, 13);

		chart.getLegend().setItemFont(font);

		TextTitle txtTitle = chart.getTitle();
		txtTitle.setFont(font);

		plot.setBackgroundPaint(Color.white);

		// X轴
		ValueAxis domainAxis = plot.getDomainAxis();
		// 设置X轴标题字体
		domainAxis.setLabelFont(font);
		// domainAxis.setAutoRange(false);
		// domainAxis.setRange(0, range);

		// 设置X轴字体
		domainAxis.setTickLabelFont(font);
		// 设置字体颜色
		domainAxis.setTickLabelPaint(Color.BLUE);
		// 横轴上的label斜显示
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		// 分类轴边距,同种类型之间的距离,比如说Row 1之间的
		// domainAxis.setCategoryMargin(0.2f);
		// 分类轴下（左）边距,就是离左边的距离
		domainAxis.setLowerMargin(0.1);
		// 分类轴下（右）边距,就是离最右边的距离
		domainAxis.setUpperMargin(0.1);

		// Y 轴
		ValueAxis rangeAxis = plot.getRangeAxis();
		// 设置Y轴标题字体
		rangeAxis.setLabelFont(font);
		// 设置Y轴字体
		rangeAxis.setTickLabelFont(font);
		// 字体颜色
		rangeAxis.setLabelPaint(Color.RED);

		if (width == null || height == null) {
			return PrintWebChart.toprint(chart, 780, 250, datasource, request);
		}else{
			return PrintWebChart.toprint(chart, Integer.parseInt(width), Integer.parseInt(height), datasource, request);
		}
	}

	public void setDelay(String particiapnt, long time) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String[] xxx = "fsdf0ffds".split("0000");
		for (int i = 0; i < xxx.length; i++) {
			System.out.println(xxx[i]);
		}
	}

}
