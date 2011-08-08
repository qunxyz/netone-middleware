package oescript.parent;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DbScriptFunction;
import oe.frame.bus.workflow.rule.DyScriptFunction;
import oe.frame.bus.workflow.rule.EnvScriptFunction;
import oe.frame.bus.workflow.rule.MsgScriptFunction;
import oe.frame.bus.workflow.rule.ScriptFunction;
import oe.frame.bus.workflow.rule.WfScriptFunction;

/**
 * 改类用于继承，该类的子类可以中可以直接调试相关的OeScript脚本。<br>
 * 注意：这种模式中可能没法初始化 工作流环境信息 runtimeid和workcode的值,但是 可以手工来实现 相关的业务对象 dy,wf
 * 都存在init方式可以完成该任务
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
