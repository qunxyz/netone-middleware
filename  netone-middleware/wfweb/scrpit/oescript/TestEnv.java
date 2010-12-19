package oescript;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.EnvScriptFunction;

public class TestEnv {

	static EnvScriptFunction env = (EnvScriptFunction) WfEntry.fetchBean("env");

	public static void main(String[] args) {
		// �ɼ�Google��վ������
		String htmlinfo = env.invokeUrl("http://www.google.com");
		System.out.println(htmlinfo);

		// ��� ���� ���� curl
		String value1 = env.value("curl");
		System.out.println(value1);

		// ��ָ����λ��URL ��� ���� ���� curl
		String value2 = env.value("rmi://10.192.15.84:2888/envinfo", "curl");
		System.out.println(value2);
		
		

	}

}
