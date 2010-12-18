package oe.teach.web.chart.line;

import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.teach.mid.bussmap.CommSelecTools;
import oe.teach.web.chart.ChartMaker;
import oe.teach.web.chart.PrintWebChart;


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
 * ��ͼʵ���� ��������������zcolumn�ֶν��й�������
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class LineMakerImpl3 implements ChartMaker {
	public String todo(HttpServletRequest request, String datasource,
			String title, String xtitle, String ytitle, String width,
			String height) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		// ds[0]������Դ  ds[1]��zcolumn�ֶ���
		String ds[]=datasource.split("#");
		// �������
		List data = (new CommSelecTools()).fetchdata(ds[0], LineObj.class);
		
		//�������ݸ���zcolumn�ֶζ�����Դ���з��ദ��
		Map map=new HashMap();// all data set
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			LineObj object = (LineObj) iterator.next();
			String level3=object.getLevel3();
			if(!map.containsKey(level3)){
				List list=new ArrayList();
				list.add(object);
				map.put(level3, list);
			}else{
				List list=(List)map.get(level3);
				list.add(object);
			}
		}
		

		TimeSeries pop[] = new TimeSeries[map.size()];
		String format = "yyyy-MM-dd";
		
		int i=0;
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			pop[i] = new TimeSeries(object, Day.class);
			List datax=(List)map.get(object);
			for (Iterator iteratorx = datax.iterator(); iteratorx.hasNext();) {
				LineObj objectx = (LineObj) iteratorx.next();
				String level1 = objectx.getLevel1();
				format = objectx.getLevel2();
				pop[i].add(new Day(Date.valueOf(level1)), objectx.getValue());
			}
			dataset.addSeries(pop[i]);
			i++;
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, xtitle,
				ytitle, dataset, true, true, false);

		// ����������ʾ��ʽ
		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat(format));

		Font font = new Font("SimSun", 10, 13);

		chart.getLegend().setItemFont(font);

		TextTitle txtTitle = chart.getTitle();
		txtTitle.setFont(font);

		plot.setBackgroundPaint(Color.white);

		// X��
		ValueAxis domainAxis = plot.getDomainAxis();
		// ����X���������
		domainAxis.setLabelFont(font);
		// domainAxis.setAutoRange(false);
		// domainAxis.setRange(0, range);

		// ����X������
		domainAxis.setTickLabelFont(font);
		// ����������ɫ
		domainAxis.setTickLabelPaint(Color.BLUE);
		// �����ϵ�labelб��ʾ
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		// ������߾�,ͬ������֮��ľ���,����˵Row 1֮���
		// domainAxis.setCategoryMargin(0.2f);
		// �������£��󣩱߾�,��������ߵľ���
		domainAxis.setLowerMargin(0.1);
		// �������£��ң��߾�,���������ұߵľ���
		domainAxis.setUpperMargin(0.1);

		// Y ��
		ValueAxis rangeAxis = plot.getRangeAxis();
		// ����Y���������
		rangeAxis.setLabelFont(font);
		// ����Y������
		rangeAxis.setTickLabelFont(font);
		// ������ɫ
		rangeAxis.setLabelPaint(Color.RED);

		if (width == null || height == null) {
			return PrintWebChart.toprint(chart, 780, 250, ds[0], request);
		}else{
			return PrintWebChart.toprint(chart, Integer.parseInt(width), Integer.parseInt(height), ds[0], request);
		}

	}

	public void setDelay(String particiapnt, long time) {
		// TODO Auto-generated method stub

	}

}
