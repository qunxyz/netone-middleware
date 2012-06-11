/**
 * 
 */
package com.report.chart.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 ����04:47:30
 * @history
 */
public class ChartCommon {

	/**
	 * ����ͼ����JPEG��ʽ�������
	 * 
	 * @param filename
	 * @param chart
	 */
	public static void saveChartAsJPEG(String filename, JFreeChart chart) {
		String servicePath = "C:/chart/" + filename;
		File fp = new File(servicePath);
		if (fp.exists())
			fp.mkdirs();

		try {
			ChartUtilities.saveChartAsJPEG(fp, chart, 800, 600);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͼ����PNG��ʽ�������
	 * 
	 * @param filename
	 * @param chart
	 */
	public static void saveChartAsPNG(String filename, JFreeChart chart) {
		String servicePath = ChartCommon.class.getClassLoader()
				.getResource(".").getPath()
				+ File.separator + filename;
		File fp = new File(servicePath);
		if (fp.exists())
			fp.mkdir();

		try {
			ChartUtilities.saveChartAsPNG(fp, chart, 800, 600);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ͼ����JPEG��ʽ��ҳ��
	 * 
	 * @param filename
	 * @param chart
	 */
	public static void writeChartAsJPEG(OutputStream out, JFreeChart chart,
			int width, int height) {
		try {
			ChartUtilities.writeChartAsJPEG(out, chart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ͼ����PNG��ʽ��ҳ��
	 * 
	 * @param filename
	 * @param chart
	 */
	public static void writeChartAsPNG(OutputStream out, JFreeChart chart,
			int width, int height) {
		try {
			ChartUtilities.writeChartAsPNG(out, chart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
