package oe.teach.web.chart;

import javax.servlet.http.HttpServletRequest;

/**
 * ͼ��Ӧ�ýӿ�
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface ChartMaker {
	// ����ͼ��
	String todo(HttpServletRequest request, String datasource, String title,
			String xtitle, String ytitle,String width,String height);

	// ͼ���洦��
	void setDelay(String particiapnt, long time);

}
