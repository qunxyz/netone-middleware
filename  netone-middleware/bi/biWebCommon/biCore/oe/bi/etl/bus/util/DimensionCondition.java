package oe.bi.etl.bus.util;

import java.util.List;

import oe.bi.dataModel.obj.DataModel;


public interface DimensionCondition {
	String makeCondition(List dimensionElement, DataModel dataModel,
			String targetCondtion);
}
