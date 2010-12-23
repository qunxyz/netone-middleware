package oe.bi.etl.bus;

import java.util.List;

import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.MiddleCode;


public interface Transform {
	/**
	 * 转换，主要是构件出动态SQL语句
	 * 
	 */
	AimCode performTransform(MiddleCode midd);

}
