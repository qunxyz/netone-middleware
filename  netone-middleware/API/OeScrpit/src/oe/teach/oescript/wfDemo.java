package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� wf
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class wfDemo extends OeScript {
	public static void main(String[] args) {

		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");

		// �����ݱ�����ֵ(ע�⣺�������������������ʱ�½��ı���)
		wf.set(runtimeid, "rev1", "OK");
		wf.set(runtimeid, "rev2", 100);
		


		// ��������
		wf.run(runtimeid);

		// ��ȡ�������
		int rev1 = wf.getn(runtimeid, "rev2");
		// ���������ֵ��ӡ������̨
		System.out.println(rev1);
		System.out.println("done---wf");

	}
}
