package oe.bi.etl.bus;

import java.util.List;

import oe.bi.etl.obj.ChoiceInfo;


public interface TargetAdapter {

	String _TARGET_TYPE_ORDER = "order";

	/**
	 * ����Choiceinfo�е�ָ��, ָ����������շ���list�з���,ͬʱ�������ص��ַ������Ǳ����ŷ�
	 * sys_int_id��start_time��ָ�����γɵ�������Ϣ
	 * 
	 * @param cho
	 * @return
	 */
	String adapt(ChoiceInfo cho, List list);

}
