package oe.teach.oescript;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

/**
 * 应用脚本内部对象 DY  <br> 该模式通过在流程驱动的表单中应用，通过工作流的相关变量对接表单数据
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class DyFomDemo1 extends OeScript {

	public static void main(String[] args) throws RemoteException {

		String formNaturalname = "BUSSFORM.BUSSFORM.PMS.DY_571239285556990";
		// 应用动态表单
		usedata(formNaturalname);
		// 应用动态表单带事务
		// useDatax(formNaturalname);
		// 修改表单
		// modify(formNaturalname);
		// 查询
		// query(formNaturalname);
		// 删除数据
		// delete(formNaturalname);
		
		
		System.out.println("flag!-------------------------------------");
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String a1=dateformat.format(new java.util.Date());
		dy.set("$(lsh)"+":"+"$(formcode)","column18" , a1);
		dy.set("$(lsh)"+":"+"$(formcode)","column21" , "$(participant)");
	}

	/**
	 * 应用动态表单数据应用
	 */
	public static void usedata(String formcode) {
		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = formcode;
		// 创建一个动态表单实例
		String lsh = dy.newInstance(dyformnaturalname);
		// 赋值(赋值字段参考从动态表单的WEB创建表单的结果列表中的表单描述XML中的说明)
		// 其中uuid是paramName，attributeType指名该字段的类型
		dy.set(lsh, "column3", "mike");// 参考Dy定义描述 column3为名字字段，类型为字符型
		dy.set(lsh, "column4", 20);// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		dy.set(lsh, "belongx", "你好[dept.dept]");
		

		// 读取表单的字段值
		String name = dy.get(lsh, "column3");
		int age = dy.getn(lsh, "column4");
		// 将结果打印到控制台
		System.out.println("i'm " + name + " my age is " + age);

	}

	/**
	 * 带事务的动态表单数据应用
	 */
	public static void useDatax(String formcode) {
		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = formcode;
		// 创建一个动态表单实例
		String lsh = dy.newInstance(dyformnaturalname);
		Object objin = dy.getInobj(lsh);// 将远程对象装载到本地
		// 赋值(赋值字段参考从动态表单的WEB创建表单的结果列表中的表单描述XML中的说明)
		// 其中uuid是paramName，attributeType指名该字段的类型
		dy.setIn("column3", "jim", objin);// 参考Dy定义描述 column3为名字字段，类型为字符型
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
	public static void query(String formcode) {

		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = formcode;
		// 创建查询对象（也可以为空）
		TCsBus bus = new TCsBus();
		// 设置条件,参考Dy定义描述 column3为名字字段，类型为字符型
		bus.setColumn3("mike");
		// 将查询的结果保存list对像当中
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		// 通过循环遍历将结果打印在控制台
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			System.out
					.println("mike's age is " + dy.getOutn("column4", object));
		}
	}

	/**
	 * 将查询出来的对象的年龄设置为12岁
	 */
	public static void modify(String formcode) {
		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = formcode;
		// 创建查询对象（也可以为空）
		TCsBus bus = new TCsBus();
		// 设置条件 设置条件,参考Dy定义描述 column3为名字字段，类型为字符型
		bus.setColumn3("mike");
		List list = dy.queryData(dyformnaturalname, bus, 0, 20,
				" and column4>10");// 参考Dy定义描述 column4为名字年龄字段，类型为数字
		// 通过循环遍历将满足条件数据记录进行修改
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			dy.setIn("column4", 50, object);
			dy.submit(object);
		}
	}

	/**
	 * 删除表记录
	 * 
	 * @param formcode
	 *            表单的naturalname
	 */
	public static void delete(String formcode) {

		// 在资源中注册的命名(从动态表单的WEB创建表单的结果列表中获得该命名)
		String dyformnaturalname = formcode;
		String lsh = null;
		// 创建查询对象（也可以为空）
		int operateCountSuccess = 0;// 定义变量保存删除数据该操作的成功数
		int operatecountFail = 0;// 定义变量保存删除数据该操作的失败数
		TCsBus bus = new TCsBus();
		// 统计指定表单的记录总数
		int datacout = dy.queryDataNum(formcode, bus, " and column4>40");
		// 获得满足条件的数据记录保存在list对象
		List list = dy.queryData(dyformnaturalname, bus, 0, datacout,
				" and column4>40");
		// 遍历结果集
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			String Deletecondition = object.getLsh() + ":"
					+ object.getFormcode();// 获得记录的lsh与forcode
			Boolean success = dy.deleteData(Deletecondition);// 删除记录
			if (success) {
				operateCountSuccess++;
			} else {
				operatecountFail++;
			}
		}
		// 在控制台打印操作的结果
		System.out.println("成功删除数据共" + operateCountSuccess + "条  ; 失败"
				+ operatecountFail + "条");

	}
}
