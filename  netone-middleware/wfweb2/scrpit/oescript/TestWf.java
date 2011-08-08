package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.WfScriptFunction;

public class TestWf {
	static WfScriptFunction wf = (WfScriptFunction) WfEntry.fetchBean("wf");

	public static void main(String[] args) {

		// ��������ʵ��
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.UYTR");

		// ��ֵ�������
		wf.set(runtimeid, "rev1", "OK");
		wf.set(runtimeid, "rev2", 100);

		// ��������
		wf.run(runtimeid);

		// ��ȡ�������
		int rev1 = wf.getn(runtimeid, "rev2");
		System.out.println(rev1);
		System.out.println("done---wf");

	}

}
