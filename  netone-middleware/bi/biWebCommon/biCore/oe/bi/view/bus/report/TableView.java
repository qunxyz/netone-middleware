package oe.bi.view.bus.report;

import javax.servlet.http.HttpServletRequest;

import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


/**
 * ����web��ʾ��ͼ��Ϣ������html����ͼƬ����Ϣ��ֱ�ӿ�����IE����ʾ��
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface TableView {
	// ÿҳ��
	int pagesize = 10;

	/**
	 * ����web���
	 * 
	 * @param viewModel :
	 *            ����ģ��
	 * @param cur
	 *            ����ǰҳ
	 * @param request :
	 *            web��Ϣ
	 * @return ����webҳ��չʾ�Ķ���
	 */
	Object viewTable(ViewModel viewModel, int cur, HttpServletRequest request);


}
