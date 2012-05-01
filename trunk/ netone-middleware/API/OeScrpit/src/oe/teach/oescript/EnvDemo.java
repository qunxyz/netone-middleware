package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * 应用脚本内部对象 env
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class EnvDemo extends OeScript {
	public static void main(String[] args) {
		// 采集fjycit网站的数据
		// String htmlinfo = env.invokeUrl("http://www.fjycit.com");
		// 将网站的html的代码打印到控制台
		// System.out.println(htmlinfo);

		// 获得 环境 变量 curl的值
		//String value1 = env.value("curl");
		//将获得的值打印到控制台 
		//System.out.println(value1);

		// 从指定的位置URL 获得 环境 变量 curl
		String value2 = env.value("rmi://192.168.2.4:2888/envinfo", "curl");
		//将获得的值打印到控制台 
		System.out.println(value2);

	}
}