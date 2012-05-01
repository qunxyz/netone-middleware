package oe.teach.oescript;

import java.util.List;
import java.util.Map;

import oe.mid.soa.bean.SoaBean;
import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� bean
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class BeanDemo extends OeScript {
	public static void main(String[] args) {
		// // ��ͨBeanӦ��
		//use_demo1();
		// // �������Ӧ��
		 use_demo2();

	}

	/**
	 * ��ͨBeanӦ��
	 */
	public static void use_demo1() {
		// ����Դģʽ�ж�λ��Bean������󣬴���ʵ��
		String beanid = bean
				.newInstance("BUSSBEAN.BUSSBEAN.BEAN_INIS.BEANSAMPLE1");
		// ��ֵ-��ͨģʽ(���ܽϵ�)
		bean.set(beanid, "age", 2);
		bean.set(beanid, "name", "mike");
		bean.set(beanid, "sex", false);
		// ִ��
		bean.run(beanid);

		// ��÷��ؽ��
		System.out.println(bean.get(beanid, "types"));
	}

	/**
	 * �������BeanӦ��
	 */
	public static void use_demo2() {
		// ����Դģʽ�ж�λ��Bean������󣬴���ʵ��
		String beanid = bean
				.newInstance("BUSSBEAN.BUSSBEAN.BEAN_INIS.BEANSAMPLE1");
		// ��ֵ-������ģʽ-���ܽϸ�
		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);
		bean.setIn("sex", false, obj);
		bean.submit(obj);
		// ִ��
		bean.run(beanid);
		
		// ��÷��ؽ��
		System.out.println(bean.get(beanid, "types"));
	}

	public static void user_demo3() {
		/*-
		 * ��ѯ����Ѳ�������bean��������
		 */
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.BEANSAMPLE1");

		// // ��ֵ-��ͨģʽ(���ܽϵ�)
		// bean.set(beanid, "age", 1000);
		// bean.set(beanid, "name", "mike");
		//
		// // ִ��
		// bean.run(beanid);
		//
		// // ��÷��ؽ��
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
		 * ��ѯ����Ѳ�������bean��������
		 */
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.BEANSAMPLE1");

		// // ��ֵ-��ͨģʽ(���ܽϵ�)
		// bean.set(beanid, "age", 1000);
		// bean.set(beanid, "name", "mike");
		//
		// // ִ��
		// bean.run(beanid);
		//
		// // ��÷��ؽ��
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
