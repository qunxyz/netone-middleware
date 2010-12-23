package oe.bi.etl.bus.arithmetic;

import oe.bi.view.obj.ViewModel;

/**
 * Ԥ�����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface Forcast {

	/**
	 * Ԥ�����
	 * 
	 * @param viewObj
	 * @param limitUp
	 * @throws InvalidationLimitUpException
	 */
	void forcast(ViewModel viewObj, double limitUp)
			throws InvalidationLimitUpException;

	/**
	 * Ԥ�����
	 * 
	 * @param viewObj
	 * @throws InvalidationLimitUpException
	 * 
	 * ����˵�����÷�����Ĭ�ϵ�����ֵ��viewObj.getXyValue��x���������һ��ֵ����10���Ĳ���
	 */
	void forcast(ViewModel viewObj) throws InvalidationLimitUpException;

}
