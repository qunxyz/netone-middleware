package com.report.chart.ifc;

import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import com.report.chart.entity.BarChartObj;

/**
 * ��ͼ�ӿ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 ����02:36:18
 * @history
 */
public interface BarIfc {

	/**
	 * ��������Դ
	 * 
	 * @param list
	 * @return
	 */
	CategoryDataset createDataset(List<BarChartObj> list);

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
