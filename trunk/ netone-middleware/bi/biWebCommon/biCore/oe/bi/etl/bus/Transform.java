package oe.bi.etl.bus;

import java.util.List;

import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.MiddleCode;


public interface Transform {
	/**
	 * ת������Ҫ�ǹ�������̬SQL���
	 * 
	 */
	AimCode performTransform(MiddleCode midd);

}
