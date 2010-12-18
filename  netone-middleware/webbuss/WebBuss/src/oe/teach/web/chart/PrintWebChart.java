package oe.teach.web.chart;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.common.chart.CachedChart;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * 通用打印类，实现在web上展现 图表
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class PrintWebChart {


	public static String toprint(JFreeChart jfreechart, int width, int height,
			String picid, HttpServletRequest request) {

		String imgurl = "";
		BufferedImage bufimg = jfreechart.createBufferedImage(width, height);

		try {
			byte[] imgbyte = ChartUtilities.encodeAsPNG(bufimg);
			CachedChart cchart = new CachedChart();
			cchart.setImg(imgbyte);
			String id = picid + ".png";

			String imgid = "jfreechart" + id;
			request.getSession().removeAttribute(imgid);
			request.getSession().setAttribute(imgid, cchart);
			imgurl = request.getContextPath()
					+ "/servlet/ChartProviderSvl;jsessionid="
					+ request.getSession().getId() + "?imgid=" + id;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return imgurl;
	}
}
