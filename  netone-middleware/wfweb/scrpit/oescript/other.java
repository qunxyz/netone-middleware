package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DyScriptFunction;
import oe.frame.bus.workflow.rule.WfScriptFunction;

public class other {
	static WfScriptFunction wf = (WfScriptFunction) WfEntry.fetchBean("wf");
	static DyScriptFunction dy = (DyScriptFunction) WfEntry.fetchBean("dy");

	public static void main(String[] args) {
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		String lsh = dy.newInstance(dyformnaturalname);
		String runtimeid = "8a408fd41cdc4202011cdc9e84d90027";

		dy.set(lsh, "column3", "mike");
		dy.set(lsh, "column4", 20);

		wf.set(runtimeid, "dyform", lsh);

		System.out.println(wf.get(runtimeid, "dyform"));

	}
}
