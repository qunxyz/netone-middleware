package oe.bi.common.chart;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * �ṩ��webҳ����ʹ��jfreechart�Ĺ��ߡ�
 * 
 * @author hls
 * 
 */
public class JFreeChartUtil {

	static Log log = LogFactory.getLog(JFreeChartUtil.class);

	public static final String ChartProviderServlet_Context = "/servlet/ChartProviderSvl";

	/**
	 * ��jfreechart��ͼƬ���浽session,����ChartProviderSvl���ʵ�ֶ�session��ͼƬ�Ĺ���
	 * ���÷����ķ���ֵ��ӵ�html��<img>��ǩ��src���Լ��ɡ�
	 * 
	 * @param chart
	 * @param chartinfo
	 * @param request
	 * @param response
	 * @return
	 */
	public static ResponseChartInfo cacheChartToSession(JFreeChart chart,
			ChartParameter chartinfo, HttpServletRequest request,
			HttpServletResponse response, int picid) {

		String imgurl = "";
		BufferedImage bufimg = chart.createBufferedImage(chartinfo.getWidth(),
				chartinfo.getHeigth());

		try {
			byte[] imgbyte = ChartUtilities.encodeAsPNG(bufimg);
			CachedChart cchart = new CachedChart();
			cchart.setImg(imgbyte);

			String id = picid + ".png";

			String imgid = "jfreechart" + id;
			request.getSession().removeAttribute(imgid);
			request.getSession().setAttribute(imgid, cchart);
			imgurl = request.getContextPath() + ChartProviderServlet_Context
					+ ";jsessionid=" + request.getSession().getId() + "?imgid="
					+ id;


			ResponseChartInfo respinfo = new ResponseChartInfo();
			respinfo.setImgurlid("imgurl" + id);
			respinfo.setImgwidthid("imgwidth" + id);
			respinfo.setImgheightid("imgheight" + id);
			request.setAttribute(respinfo.getImgurlid(), imgurl);
			request.setAttribute(respinfo.getImgwidthid(), ""
					+ chartinfo.getWidth());
			request.setAttribute(respinfo.getImgheightid(), ""
					+ chartinfo.getHeigth());
			return respinfo;
		} catch (IOException e) {
			log.error("ͼ������PNG��ʽ����", e);
		}

		return null;

	}

	public static String createCachedImgId() {
		Random r = new Random();
		r.nextInt(10);
		return "";
	}

}
