package oe.bi.view.bus.report;

import javax.servlet.http.HttpServletRequest;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.obj.ViewModel;


public interface GraphView {
	/**
	 * 图表视图
	 * 
	 * @param viewModel
	 *            视图数据对象
	 * @param graph
	 *            图表类型
	 * @param cur 
	 *            分页参数
	 * 
	 * @param request
	 * @return
	 * 
	 * 注意：在实现该方法中，由于图表只能显示二维信息，此时需要检查viewModel的维度数组dimensionid的长度是否为1，如果不为1
	 * 那么需要抛出 MoreThenOneDimensionViewModel异常 告诫无法显示1维以上的图表信息
	 */
	Object viewGraph(ViewModel viewModel,int cur, String graphType,
			HttpServletRequest request) throws MoreThenOneDimensionViewModel;
}
