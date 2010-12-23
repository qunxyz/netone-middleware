package oe.bi.view.bus.filt;

import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.view.obj.ViewModel;


public interface TargetFilt {
	/**
	 * 根据指标信息过滤视图数据
	 * 算法的本质 根据targetFilt对象中选择的指标信息，对viewmodel中的指标值进行过滤
	 * 
	 * @param viewmodel
	 * @param targetFilt
	 * @return
	 */
	ViewModel filtvalue(ViewModel viewmodel, TargetFiltObj targetFilt);

}
