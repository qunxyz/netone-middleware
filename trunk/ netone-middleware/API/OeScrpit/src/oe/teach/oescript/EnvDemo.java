package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� env
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class EnvDemo extends OeScript {
	public static void main(String[] args) {
		// �ɼ�fjycit��վ������
		// String htmlinfo = env.invokeUrl("http://www.fjycit.com");
		// ����վ��html�Ĵ����ӡ������̨
		// System.out.println(htmlinfo);

		// ��� ���� ���� curl��ֵ
		//String value1 = env.value("curl");
		//����õ�ֵ��ӡ������̨ 
		//System.out.println(value1);

		// ��ָ����λ��URL ��� ���� ���� curl
		String value2 = env.value("rmi://192.168.2.4:2888/envinfo", "curl");
		//����õ�ֵ��ӡ������̨ 
		System.out.println(value2);

	}
}