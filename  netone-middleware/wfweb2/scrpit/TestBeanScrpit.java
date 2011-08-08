import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.MsgScriptFunction;
import oe.frame.bus.workflow.rule.ScriptFunction;
import oe.frame.bus.workflow.rule.WfScriptFunction;

public class TestBeanScrpit {

	static MsgScriptFunction msc = (MsgScriptFunction) WfEntry.fetchBean("msg");
	static ScriptFunction bean = (ScriptFunction) WfEntry.fetchBean("bean");
	static WfScriptFunction wf = (WfScriptFunction) WfEntry.fetchBean("wf");
	static ScriptFunction dy = (ScriptFunction) WfEntry.fetchBean("dy");
	
	public static void main(String[] args) {
		
		// 普通应用
		todo1();
		// 带事务的应用
		todo2();
		// 与WF整合
		todo3();
	}

	private static void todo1() {
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		bean.set(beanid, "age", 1000);
		bean.set(beanid, "name", "mike");

		bean.run(beanid);
		System.out.println(bean.get(beanid, "types"));
		System.out.println("done");

	}

	private static void todo2() {
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);
		bean.setIn("sex", true, obj);
		bean.submit(obj);
		bean.run(beanid);
		System.out.println(bean.get(beanid, "types"));
		System.out.println("done");
	}

	private static void todo3() {
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);
		bean.setIn("sex", true, obj);

		wf.setSession(beanid, obj);

		Object objnext = wf.getSession(beanid);
		bean.submit(objnext);
		bean.run(beanid);
		System.out.println(bean.get(beanid, "types"));
		System.out.println("done");

	}
}
