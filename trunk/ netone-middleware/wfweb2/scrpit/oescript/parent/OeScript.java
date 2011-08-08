package oescript.parent;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DbScriptFunction;
import oe.frame.bus.workflow.rule.DyScriptFunction;
import oe.frame.bus.workflow.rule.EnvScriptFunction;
import oe.frame.bus.workflow.rule.MsgScriptFunction;
import oe.frame.bus.workflow.rule.ScriptFunction;
import oe.frame.bus.workflow.rule.WfScriptFunction;

/**
 * �������ڼ̳У��������������п���ֱ�ӵ�����ص�OeScript�ű���<br>
 * ע�⣺����ģʽ�п���û����ʼ�� ������������Ϣ runtimeid��workcode��ֵ,���� �����ֹ���ʵ�� ��ص�ҵ����� dy,wf
 * ������init��ʽ������ɸ�����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class OeScript {

	public static ScriptFunction bean = (ScriptFunction) WfEntry.fetchBean("bean");
	public static DbScriptFunction db = (DbScriptFunction) WfEntry.fetchBean("db");
	public static DyScriptFunction dy = (DyScriptFunction) WfEntry.fetchBean("dy");
	public static EnvScriptFunction env = (EnvScriptFunction) WfEntry.fetchBean("env");
	public static MsgScriptFunction msg = (MsgScriptFunction) WfEntry.fetchBean("msg");
	public static WfScriptFunction wf = (WfScriptFunction) WfEntry.fetchBean("wf");

}
