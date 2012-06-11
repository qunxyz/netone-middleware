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
 * @version 1.0.0 2011-11-22 下午04:47:30
 * @history
 */
public class ChartCommon {

	/**
	 * 保存图表以JPEG格式保存磁盘
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
	 * 保存图表以PNG格式保存磁盘
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
	 * 输出图表以JPEG格式到页面
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
	 * 输出图表以PNG格式到页面
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
