package oe.bi.etl.bus;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.etl.obj.MergerObj;
import oe.bi.etl.obj.MiddleCode;


public interface Optimize {
	/**
	 * 优化分析（用归并表替换最小粒度表）
	 * 
	 * @param middlecode
	 * @return
	 */
	void performOptimize(MiddleCode middlecode, DataModel dataModel,
			OptimizeTable mergerobj);

}
