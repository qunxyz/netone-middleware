package oe.bi.view.bus.report;

import javax.servlet.http.HttpServletRequest;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.obj.ViewModel;


public interface GraphView {
	/**
	 * ͼ����ͼ
	 * 
	 * @param viewModel
	 *            ��ͼ���ݶ���
	 * @param graph
	 *            ͼ������
	 * @param cur 
	 *            ��ҳ����
	 * 
	 * @param request
	 * @return
	 * 
	 * ע�⣺��ʵ�ָ÷����У�����ͼ��ֻ����ʾ��ά��Ϣ����ʱ��Ҫ���viewModel��ά������dimensionid�ĳ����Ƿ�Ϊ1�������Ϊ1
	 * ��ô��Ҫ�׳� MoreThenOneDimensionViewModel�쳣 ����޷���ʾ1ά���ϵ�ͼ����Ϣ
	 */
	Object viewGraph(ViewModel viewModel,int cur, String graphType,
			HttpServletRequest request) throws MoreThenOneDimensionViewModel;
}
