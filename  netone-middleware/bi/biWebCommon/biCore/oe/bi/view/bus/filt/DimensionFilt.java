package oe.bi.view.bus.filt;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


public interface DimensionFilt {

	/**
	 * 根据维度参数过滤视图数据
	 * 
	 * @param viewModel:
	 *            数据模型
	 * @param cur：当前页
	 * @param request:
	 *            web信息
	 * @return 用于web页面展示的对象
	 * 
	 * 算法的本质是对viewModel.getDimensionvalue()中的维度值，根据
	 * graphModel.getOhterDimension()中的维度值，进行过滤剔除所有graphModel.getOhterDimension()
	 * 中所没有的纪录。最后从新构造一个viewModel，只有一个维度是raphModel.getXOffsetDimension()中的维度
	 * 
	 */
	ViewModel filtvalue(ViewModel viewModel, GraphModel graphModel)throws MoreThenOneDimensionViewModel;

	/**
	 * 获得所有维度的值
	 * 
	 * @param viewModel
	 * @return [i][0] dimensionId [i][1]dimensionName
	 *         [i][2]dimensionValueLink(split by symbol",")
	 */
	String[][] allDimensionValueList(ViewModel viewModel);


}
