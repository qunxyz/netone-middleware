package oe.bi.datasource;

import java.util.List;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.etl.obj.ChoiceInfo;


/**
 * ��������
 * 
 * @author ibm
 * 
 */
public interface ConditionAdapter {
	public String makeDimensionCondition(List dimensionElement,
			DataModel dataModel, ChoiceInfo cho);
}
