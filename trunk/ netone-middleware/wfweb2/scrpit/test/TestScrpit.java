package test;


import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.EnvScriptFunction;
import oe.frame.bus.workflow.rule.MsgScriptFunction;
import oe.frame.bus.workflow.rule.ScriptFunction;
import oe.frame.bus.workflow.rule.WfScriptFunction;


public class TestScrpit {

	public static void main(String[] args) {

		MsgScriptFunction msc = (MsgScriptFunction) WfEntry.fetchBean("msg");
		ScriptFunction bean = (ScriptFunction) WfEntry.fetchBean("bean");
		WfScriptFunction wf = (WfScriptFunction) WfEntry.fetchBean("wf");
		ScriptFunction dy = (ScriptFunction) WfEntry.fetchBean("dy");
		EnvScriptFunction env = (EnvScriptFunction) WfEntry.fetchBean("env");
		// 消息测试
		msc.toMen("adminx", "adminx", "aaa", "sex",
				"PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("done");
		// bean测试
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		bean.set(beanid, "age", 1000);
		bean.set(beanid, "name", "mike");

		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);
		bean.setIn("sex", true, obj);
		bean.submit(obj);

		bean.run(beanid);
		System.out.println(bean.get(beanid, "types"));
		System.out.println("done---bean");

		// wf测试 测试
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.UYTR");

		/* WF ESB 入口参数 */
		wf.set(runtimeid, "rev1", null);
		wf.set(runtimeid, "rev2", 100);
		/* WF ESB 出口参数 */
		int rev1 = wf.getn(runtimeid, "rev1");

		wf.run(runtimeid);
		int level2 = wf.getn(runtimeid, "rev2");
		System.out.println(level2);
		System.out.println("done---wf");

		// dy 测试
		String lsh = dy.newInstance("BUSSFORM.BUSSFORM.DY_691210062299360");
		dy.set(lsh, "column3", 1000);
		double value = dy.getd(lsh, "column3");
		System.out.println(value);
		System.out.println("done---dy");
		
		
//		System.out.println(env.value("rmi://10.192.15.84:2888/envinfo","loadDataSer"));
//		
//		System.out.println(env.invokeUrl("http://www.sina.com"));

	}
}
