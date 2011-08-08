package oescript;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import oe.cav.bean.logic.bus.TCsBus;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DyScriptFunction;

public class TestDy {

	static DyScriptFunction dy = (DyScriptFunction) WfEntry.fetchBean("dy");

	public static void main(String[] args) throws RemoteException {
		// 应用动态表单
		usedata();
		// 应用动态表单带事务
		useDatax();
		// 修改表单
		modify();
		// 查询
		query();

	}

	/**
	 * 应用动态表单数据应用
	 */
	public static void usedata() {
		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// 创建一个动态表单实例
		String lsh = dy.newInstance(dyformnaturalname);
		// 赋值(赋值字段参考从动态表单的WEB创建表单的结果列表中的表单描述XML中的说明)
		// 其中uuid是paramName，attributeType指名该字段的类型
		dy.set(lsh, "column3", "mike");// 参考Dy定义描述 column3为名字字段，类型为字符型
		dy.set(lsh, "column4", 20);// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		// 读取表单的字段值
		String name = dy.get(lsh, "column3");
		int age = dy.getn(lsh, "column4");
		System.out.println("i'm " + name + " my age is " + age);

	}

	/**
	 * 带事务的动态表单数据应用
	 */
	public static void useDatax() {
		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// 创建一个动态表单实例
		String lsh = dy.newInstance(dyformnaturalname);
		Object objin = dy.getInobj(lsh);// 将远程对象装载到本地
		// 赋值(赋值字段参考从动态表单的WEB创建表单的结果列表中的表单描述XML中的说明)
		// 其中uuid是paramName，attributeType指名该字段的类型
		dy.setIn("column3", "mike", objin);// 参考Dy定义描述 column3为名字字段，类型为字符型
		dy.setIn("column4", 20, objin);// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		dy.submit(objin);// 需要手工提交来更新远程对象

		// 读取表单的字段值
		Object objout = dy.getOutobj(lsh);// 将远程对象装载到本地（注意动态表单中这里的objout和之前的inobj是相同的远程对象）
		String name = dy.getOut("column3", objout);
		int age = dy.getOutn("column4", objout);
		System.out.println("i'm " + name + " my age is " + age);
	}

	/**
	 * 查询出符合条件的记录
	 */
	public static void query() {

		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// 创建查询对象（也可以为空）
		TCsBus bus = new TCsBus();
		// 设置条件,参考Dy定义描述 column3为名字字段，类型为字符型
		bus.setColumn3("mike");
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			System.out.println("mike's age is" + dy.getOutn("column4", object));
		}
	}

	/**
	 * 将查询出来的对象的年龄设置为12岁
	 */
	public static void modify() {
		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = "BUSSFORM.BUSSFORM.DY_201220536427078";
		// 创建查询对象（也可以为空）
		TCsBus bus = new TCsBus();
		// 设置条件 设置条件,参考Dy定义描述 column3为名字字段，类型为字符型
		bus.setColumn3("mike");
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column4", 12, object);
			dy.submit(object);
		}

	}

}
