package oe.frame.bus.workflow.rule;

import oe.mid.soa.bean.SoaBean;

public interface BeanScriptFunction extends ScriptFunction {

	/**
	 * ����Ӧ��(Ĭ�ϵ��첽ģʽ)
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @return �������������ID
	 */
	String run(String id);

	/**
	 * ����Ӧ��(Ĭ�ϵ��첽ģʽ)
	 * 
	 * @param id
	 *            ҵ�����ID
	 * @return �������������ID
	 */
	SoaBean runSyncById(String id);

	/**
	 * ����Ӧ��(Ĭ�ϵ��첽ģʽ)
	 * 
	 * @param obj
	 *            ҵ�����ID
	 * @return �������������ID
	 */
	SoaBean runSyncByObj(Object obj);

}
