package oe.bi.etl.bus;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.etl.obj.MergerObj;
import oe.bi.etl.obj.MiddleCode;


public interface Optimize {
	/**
	 * �Ż��������ù鲢���滻��С���ȱ�
	 * 
	 * @param middlecode
	 * @return
	 */
	void performOptimize(MiddleCode middlecode, DataModel dataModel,
			OptimizeTable mergerobj);

}
