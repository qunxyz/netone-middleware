package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.ScriptFunction;

public class TestBean {
	// ��ʼ����
	static ScriptFunction bean = (ScriptFunction) WfEntry.fetchBean("bean");

	public static void main(String[] args) {
		// ��ͨBeanӦ��
		use_demo1();
		// �������Ӧ��
		use_demo2();

	}

	/**
	 * ��ͨBeanӦ��
	 */
	public static void use_demo1() {
		// ����Դģʽ�ж�λ��Bean������󣬴���ʵ��
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		// ��ֵ-��ͨģʽ(���ܽϵ�)
		bean.set(beanid, "age", 1000);
		bean.set(beanid, "name", "mike");

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
		String beanid = bean.newInstance("BUSSBEAN.BUSSBEAN.SAMPLE1");
		// ��ֵ-������ģʽ-���ܽϸ�
		Object obj = bean.getInobj(beanid);
		bean.setIn("age", 1000, obj);
		bean.setIn("name", "mike", obj);

		bean.submit(obj);
		// ִ��
		bean.run(beanid);

		// ��÷��ؽ��
		System.out.println(bean.get(beanid, "types"));
	}

}
