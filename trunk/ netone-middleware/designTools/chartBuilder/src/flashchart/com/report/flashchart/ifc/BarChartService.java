/**
 * 
 */
package com.report.flashchart.ifc;


/**
 * 图表业务
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:47:01 AM
 * @history
 */
public interface BarChartService {

	/**
	 * 通用加载数据源
	 * 
	 * @param param
	 *            与Flex Flash交互获取参数
	 * @return
	 */
	public String loadData(String param);
	
	/**
	 * 通用加载二级扩展数据源
	 * 
	 * @param param
	 *            与Flex Flash交互获取参数
	 * @return
	 */
	public String load2LData(String param);

}
