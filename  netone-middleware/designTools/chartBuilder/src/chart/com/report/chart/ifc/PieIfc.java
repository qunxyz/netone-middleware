package com.report.chart.ifc;

import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

import com.report.chart.entity.PieChartObj;

/**
 * ��ͼ�ӿ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 ����02:36:49
 * @history
 */
public interface PieIfc {

	/**
	 * ��������Դ
	 * 
	 * @param list
	 * @return
	 */
	PieDataset createDataset(List<PieChartObj> list);

	/**
	 * ��ȡͼ�����
	 * 
	 * @param title
	 *            ����
	 * @param piedataset
	 *            ����Դ
	 * @return
	 */
	JFreeChart createChart(String title, PieDataset piedataset);

	/**
	 * չ��
	 */
	void render();

}
