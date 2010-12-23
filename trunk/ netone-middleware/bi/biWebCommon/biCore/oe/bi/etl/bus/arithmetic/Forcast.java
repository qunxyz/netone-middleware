package oe.bi.etl.bus.arithmetic;

import oe.bi.view.obj.ViewModel;

/**
 * 预测分析
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface Forcast {

	/**
	 * 预测分析
	 * 
	 * @param viewObj
	 * @param limitUp
	 * @throws InvalidationLimitUpException
	 */
	void forcast(ViewModel viewObj, double limitUp)
			throws InvalidationLimitUpException;

	/**
	 * 预测分析
	 * 
	 * @param viewObj
	 * @throws InvalidationLimitUpException
	 * 
	 * 补充说明：该方法中默认的上限值是viewObj.getXyValue中x坐标的最后的一个值加上10倍的步长
	 */
	void forcast(ViewModel viewObj) throws InvalidationLimitUpException;

}
