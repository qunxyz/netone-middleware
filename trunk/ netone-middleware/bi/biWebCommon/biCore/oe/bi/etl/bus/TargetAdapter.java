package oe.bi.etl.bus;

import java.util.List;

import oe.bi.etl.obj.ChoiceInfo;


public interface TargetAdapter {

	String _TARGET_TYPE_ORDER = "order";

	/**
	 * 处理Choiceinfo中的指标, 指标的内容最终放在list中返回,同时方法返回的字符串中是保存着非
	 * sys_int_id和start_time的指标所形成的条件信息
	 * 
	 * @param cho
	 * @return
	 */
	String adapt(ChoiceInfo cho, List list);

}
