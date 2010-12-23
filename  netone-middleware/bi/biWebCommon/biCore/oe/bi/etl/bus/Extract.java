package oe.bi.etl.bus;

import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.MiddleCode;


public interface Extract {

	String _DEFAULT_DIM = "sys_int_id";

	String _DEFAULT_TIME = "start_time";

	/**
	 * 根据维度创建维度中的条件信息（直接写入维度对象中）
	 * 
	 * @param runtimeDimColumn
	 */
	MiddleCode performExtract(ChoiceInfo choice) throws UnableLoadDataModel,
			NullDataSetException;

}
