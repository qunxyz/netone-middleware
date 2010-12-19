package oe.cms.xhtml2;

/**
 * 工具箱
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface UtilInterface {
	/**
	 * 获得数组信息(自动处理空数组,下标益处的问题)
	 * 
	 * @param arr
	 *            数组
	 * @param index
	 *            下标
	 * @return 正常情况下返回数组中的值,当数组为空时返回,当下标益处的时候返回-1
	 */
	String arrVar(String[] arr, int index);

	/**
	 * 获得数组信息(自动处理空数组,下标益处的问题)
	 * 
	 * @param arr
	 *            数组
	 * @param x
	 *            下标x
	 * @param y
	 *            下标y
	 * @return 正常情况下返回数组中的值,当数组为空时返回,当下标益处的时候返回-1
	 */
	String arrVar(String[][] arr, int x, int y);

	/**
	 * 解析long数字
	 * 
	 * @param value
	 * @return
	 */
	long parLong(String value);

	/**
	 * 解析double数字
	 * 
	 * @param value
	 * @return
	 */
	double parDouble(String value);

	/**
	 * 解析float数字
	 * 
	 * @param value
	 * @return
	 */
	float parFloat(String value);

	/**
	 * 解析int数字
	 * 
	 * @param value
	 * @return
	 */
	int parInt(String value);



}
