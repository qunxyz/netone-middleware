package oe.bi.view.bus.filt;

import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.view.obj.ViewModel;


public interface TargetFilt {
	/**
	 * ����ָ����Ϣ������ͼ����
	 * �㷨�ı��� ����targetFilt������ѡ���ָ����Ϣ����viewmodel�е�ָ��ֵ���й���
	 * 
	 * @param viewmodel
	 * @param targetFilt
	 * @return
	 */
	ViewModel filtvalue(ViewModel viewmodel, TargetFiltObj targetFilt);

}
