/**
 * 
 */
package com.report.flashchart.ifc;


/**
 * ͼ��ҵ��
 * 
 * @author Don(Cai.You.Dun)
 * @date Jul 4, 2010 11:47:01 AM
 * @history
 */
public interface BarChartService {

	/**
	 * ͨ�ü�������Դ
	 * 
	 * @param param
	 *            ��Flex Flash������ȡ����
	 * @return
	 */
	public String loadData(String param);
	
	/**
	 * ͨ�ü��ض�����չ����Դ
	 * 
	 * @param param
	 *            ��Flex Flash������ȡ����
	 * @return
	 */
	public String load2LData(String param);

}
