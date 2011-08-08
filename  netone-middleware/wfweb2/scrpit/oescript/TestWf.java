package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.WfScriptFunction;

public class TestWf {
	static WfScriptFunction wf = (WfScriptFunction) WfEntry.fetchBean("wf");

	public static void main(String[] args) {

		// 创建流程实例
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.UYTR");

		// 赋值相关数据
		wf.set(runtimeid, "rev1", "OK");
		wf.set(runtimeid, "rev2", 100);

		// 启动流程
		wf.run(runtimeid);

		// 读取相关数据
		int rev1 = wf.getn(runtimeid, "rev2");
		System.out.println(rev1);
		System.out.println("done---wf");

	}

}
