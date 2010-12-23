package oe.bi.view.bus.report;

import javax.servlet.http.HttpServletRequest;

import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


/**
 * 创建web显示视图信息（生成html或者图片的信息，直接可用于IE上显示）
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface TableView {
	// 每页数
	int pagesize = 10;

	/**
	 * 创建web表格
	 * 
	 * @param viewModel :
	 *            数据模型
	 * @param cur
	 *            ：当前页
	 * @param request :
	 *            web信息
	 * @return 用于web页面展示的对象
	 */
	Object viewTable(ViewModel viewModel, int cur, HttpServletRequest request);


}
