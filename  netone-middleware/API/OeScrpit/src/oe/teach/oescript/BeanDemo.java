package oe.teach.oescript;

import java.util.List;
import java.util.Map;

import oe.mid.soa.bean.SoaBean;
import oescript.parent.OeScript;

/**
 * 应用脚本内部对象 bean
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class BeanDemo extends OeScript {
	public static void main(String[] args) {
		// // 普通Bean应用
		//use_demo1();
		// // 带事务的应用
		 use_demo2();

	}

	/**
	 * 普通Bean应用
	 */
	public static void use_demo1() {
		// 从资源模式中定位到Bean服务对象，创建实例
		String beanid = bean
				.newInstance("BUSSBEAN.BUSSBEAN.BEAN_INIS.BEANSAMPLE1");
		// 赋值-普通模式(性能较低)
		bean.set(beanid, "age", 2);
		bean.set(beanid, "name", "mike");
		bean.set(beanid, "sex", false);
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
		String beanid = bean
				.newInstance("BUSSBEAN.BUSSBEAN.BEAN_INIS.BEANSAMPLE1");
		// 赋值-带事务模式-性能较高
		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);
		bean.setIn("sex", false, obj);
		bean.submit(obj);
		// 执行
		bean.run(beanid);
		
		// 获得返回结果
		System.out.println(bean.get(beanid, "types"));
	}

	public static void user_demo3() {
		/*-
		 * 查询启动巡检任务的bean服务并启动
		 */
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.BEANSAMPLE1");

		// // 赋值-普通模式(性能较低)
		// bean.set(beanid, "age", 1000);
		// bean.set(beanid, "name", "mike");
		//
		// // 执行
		// bean.run(beanid);
		//
		// // 获得返回结果
		// System.out.println(bean.get(beanid, "types"));
		//		
		Object obj = bean.getInobj(beanid);
		bean.setIn("taskName", "xxxxxx", obj);
		bean.setIn("runtimeId", "1", obj);
		bean.setIn("workItemId", "1", obj);
		bean.setIn("paramStr", "Resource:rs[123];Xtype:rs2[abc]", obj);
		bean.setIn("model", "one", obj);
		// bean.submit(obj);
		Object objx = bean.run(beanid);

		System.out.println(objx);
	}

	public static void user_demo4() {
		/*-
		 * 查询启动巡检任务的bean服务并启动
		 */
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.BEANSAMPLE1");

		// // 赋值-普通模式(性能较低)
		// bean.set(beanid, "age", 1000);
		// bean.set(beanid, "name", "mike");
		//
		// // 执行
		// bean.run(beanid);
		//
		// // 获得返回结果
		// System.out.println(bean.get(beanid, "types"));
		//		
		Object obj = bean.getInobj(beanid);
		bean.setIn("taskName", "xxxxxx", obj);
		bean.setIn("runtimeId", "1", obj);
		bean.setIn("workItemId", "1", obj);
		bean.setIn("paramStr", "Resource:rs[123];Xtype:rs2[abc]", obj);
		bean.setIn("model", "one", obj);
		// bean.submit(obj);
		Object objx = bean.run(beanid);

		SoaBean soabean = (SoaBean) bean.getOutobj(beanid);
		Map map = soabean.getValues();
		List list = (List) map.get("listname");

		System.out.println(objx);
	}
}
