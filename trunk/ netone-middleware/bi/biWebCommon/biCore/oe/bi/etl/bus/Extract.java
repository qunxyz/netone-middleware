package oe.bi.etl.bus;

import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.MiddleCode;


public interface Extract {

	String _DEFAULT_DIM = "sys_int_id";

	String _DEFAULT_TIME = "start_time";

	/**
	 * ����ά�ȴ���ά���е�������Ϣ��ֱ��д��ά�ȶ����У�
	 * 
	 * @param runtimeDimColumn
	 */
	MiddleCode performExtract(ChoiceInfo choice) throws UnableLoadDataModel,
			NullDataSetException;

}
