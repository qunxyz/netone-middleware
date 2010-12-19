package oe.cms.xhtml.core.graph.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * �ṩ��webҳ����ʹ��jfreechart�Ĺ��ߡ�
 * 
 * @author hls
 * @serialData 2006-7-21
 */
public class JFreeChartUtil {

	static Log log = LogFactory.getLog(JFreeChartUtil.class);

	/**
	 * ��jfreechart��ͼƬ���浽session,����ChartProviderSvl���ʵ�ֶ�session��ͼƬ�Ĺ���
	 * ���÷����ķ���ֵ��ӵ�html��<img>��ǩ��src���Լ��ɡ�
	 * 
	 * @param chart
	 * @param chartinfo
	 * @param request
	 * @return ������Ҫ��ҳ���ϱ�ʶ��ͼ��id
	 */
	public static byte[] cacheChartToSession(JFreeChart chart,
			ChartParameter chartinfo) {

	
		BufferedImage bufimg = chart.createBufferedImage(chartinfo.getWidth(),
				chartinfo.getHeigth());
		try {
			return ChartUtilities.encodeAsPNG(bufimg);

		} catch (IOException e) {
			log.error("ͼ������PNG��ʽ����", e);
		}
		return null;

	}
}
