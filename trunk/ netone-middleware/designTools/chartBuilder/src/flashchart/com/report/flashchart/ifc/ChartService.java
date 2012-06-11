/**
 * 
 */
package com.report.flashchart.ifc;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.report.flashchart.entity.ChartDataObj;

/**
 * ͼ��ҵ��
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:47:01 AM
 * @history
 */
public interface ChartService {

	/**
	 * �������
	 */
	public static final String $COMP$ = "$comp$";

	/**
	 * ���
	 * 
	 * @param chartCompVar
	 *            ����
	 * @param data
	 *            ��������
	 */
	public String entry(String chartCompVar, ChartDataObj data);

	/**
	 * ���
	 * 
	 * @param paramjson
	 *            ���α���
	 * @param chartCompVar
	 *            ����
	 * @param data
	 *            ��������
	 * @return
	 */
	public String entry(JSONObject paramjson, String chartCompVar,
			ChartDataObj data);

	/**
	 * ���
	 * 
	 * @param request
	 * @param output
	 * @param title
	 */
	public void output(HttpServletRequest request, String output, String title);

	/**
	 * ��ȡ���
	 * 
	 * @return
	 */
	@Deprecated
	public String loadComp();

}
