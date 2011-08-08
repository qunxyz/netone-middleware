package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.ScriptFunction;

public class TestBean {
	// 初始环境
	static ScriptFunction bean = (ScriptFunction) WfEntry.fetchBean("bean");

	public static void main(String[] args) {
		// 普通Bean应用
		use_demo1();
		// 带事务的应用
		use_demo2();

	}

	/**
	 * 普通Bean应用
	 */
	public static void use_demo1() {
		// 从资源模式中定位到Bean服务对象，创建实例
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		// 赋值-普通模式(性能较低)
		bean.set(beanid, "age", 1000);
		bean.set(beanid, "name", "mike");

		// 执行
		bean.run(beanid);

		// 获得返回结果
		System.out.println(bean.get(beanid, "types"));
	}

	/**
	 * 带事务的Bean应用
	 */
	public static void use_demo2() {
		// 从资源模式中定位到Bean服务对象，创建实例
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		// 赋值-带事务模式-性能较高
		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);

		bean.submit(obj);
		// 执行
		bean.run(beanid);

		// 获得返回结果
		System.out.println(bean.get(beanid, "types"));
	}

}
