package oe.frame.bus.workflow.rule;

import oe.frame.bus.workflow.WfEntry;

/**
 * ��������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuleEngine {

	/**
	 * �ж�ҵ���߼�
	 * 
	 * @param elogicExpress
	 *            String ����ʽ�� ?*?(?+?)
	 * @param values
	 *            List :ֵ����ΪelogicExpress�еģ�����������ֵ���Ⱥ�ѡ˳���������Ӧ�ģ���λ��һ��
	 * @return boolean
	 */
	public boolean rule(String elogicExpress, String runtimeid, String workcode);

	/**
	 * ִ�нű�����
	 * 
	 * @param elogicExpress
	 * @param runtimeid,����������̻�����,runtimeid��Ϊ��,ͬʱ���ʹ��rv��صķ���������0
	 * @return
	 */
	public String todo(String elogicExpress, String runtimeid, String workcode);

}
