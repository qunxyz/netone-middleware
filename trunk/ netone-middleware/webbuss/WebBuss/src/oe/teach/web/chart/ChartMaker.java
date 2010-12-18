package oe.teach.web.chart;

import javax.servlet.http.HttpServletRequest;

/**
 * 图表应用接口
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface ChartMaker {
	// 构建图表
	String todo(HttpServletRequest request, String datasource, String title,
			String xtitle, String ytitle,String width,String height);

	// 图表缓存处理
	void setDelay(String particiapnt, long time);

}
