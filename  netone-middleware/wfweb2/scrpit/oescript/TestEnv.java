package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.EnvScriptFunction;

public class TestEnv {

	static EnvScriptFunction env = (EnvScriptFunction) WfEntry.fetchBean("env");

	public static void main(String[] args) {
		// 采集Google网站的数据
		String htmlinfo = env.invokeUrl("http://www.google.com");
		System.out.println(htmlinfo);

		// 获得 环境 变量 curl
		String value1 = env.value("curl");
		System.out.println(value1);

		// 从指定的位置URL 获得 环境 变量 curl
		String value2 = env.value("rmi://10.192.15.84:2888/envinfo", "curl");
		System.out.println(value2);
		
		

	}

}
