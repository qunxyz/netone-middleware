package com.report.chart.ifc;

import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import com.report.chart.entity.LineChartObj;

/**
 * ��ͼ�ӿ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 ����02:36:35
 * @history
 */
public interface LineIfc {

	/**
	 * ��������Դ
	 * 
	 * @param list
	 * @return
	 */
	CategoryDataset createDataset(List<LineChartObj> list);

	/**
	 * ��ȡͼ�����
	 * 
	 * @param title
	 *            ����
	 * @param categoryAxisLabel
	 *            ����label
	 * @param valueAxisLabel
	 *            ֵlabel
	 * @param categorydataset
	 *            ����Դ
	 * @return
	 */
	JFreeChart createChart(String title, String categoryAxisLabel,
			String valueAxisLabel, CategoryDataset categorydataset);

	/**
	 * չ��
	 */
	void render();

}
