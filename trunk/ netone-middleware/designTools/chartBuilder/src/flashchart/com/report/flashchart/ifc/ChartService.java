/**
 * 
 */
package com.report.flashchart.ifc;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.report.flashchart.entity.ChartDataObj;

/**
 * 图表业务
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:47:01 AM
 * @history
 */
public interface ChartService {

	/**
	 * 组件变量
	 */
	public static final String $COMP$ = "$comp$";

	/**
	 * 入口
	 * 
	 * @param chartCompVar
	 *            变量
	 * @param data
	 *            参数对象
	 */
	public String entry(String chartCompVar, ChartDataObj data);

	/**
	 * 入口
	 * 
	 * @param paramjson
	 *            传参变量
	 * @param chartCompVar
	 *            变量
	 * @param data
	 *            参数对象
	 * @return
	 */
	public String entry(JSONObject paramjson, String chartCompVar,
			ChartDataObj data);

	/**
	 * 输出
	 * 
	 * @param request
	 * @param output
	 * @param title
	 */
	public void output(HttpServletRequest request, String output, String title);

	/**
	 * 获取组件
	 * 
	 * @return
	 */
	@Deprecated
	public String loadComp();

}
