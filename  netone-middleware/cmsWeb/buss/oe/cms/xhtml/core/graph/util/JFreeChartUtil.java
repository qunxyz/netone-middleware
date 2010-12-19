package oe.cms.xhtml.core.graph.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * 提供在web页面上使用jfreechart的工具。
 * 
 * @author hls
 * @serialData 2006-7-21
 */
public class JFreeChartUtil {

	static Log log = LogFactory.getLog(JFreeChartUtil.class);

	/**
	 * 将jfreechart的图片缓存到session,并与ChartProviderSvl结合实现对session中图片的管理，
	 * 将该方法的返回值添加到html中<img>标签的src属性即可。
	 * 
	 * @param chart
	 * @param chartinfo
	 * @param request
	 * @return 返回需要在页面上标识的图表id
	 */
	public static byte[] cacheChartToSession(JFreeChart chart,
			ChartParameter chartinfo) {

	
		BufferedImage bufimg = chart.createBufferedImage(chartinfo.getWidth(),
				chartinfo.getHeigth());
		try {
			return ChartUtilities.encodeAsPNG(bufimg);

		} catch (IOException e) {
			log.error("图像编码成PNG格式出错！", e);
		}
		return null;

	}
}
