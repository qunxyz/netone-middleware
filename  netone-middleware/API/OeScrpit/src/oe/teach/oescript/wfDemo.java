package oe.teach.oescript;

import org.apache.commons.lang.StringUtils;

import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� wf
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class wfDemo extends OeScript {
	static String runtimeid;
	public static void main(String[] args) {



	}
	
	public static void todo1(){
		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		 runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");

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
	
	public static void todo2(){
		
		// ��������ʵ��BUSSWF.BUSSWF.OESCRIPTDEMO ��Ӧ�� �м��ƽ̨�ϵ� OeScript����)
		String userinfo=wf.get(runtimeid, "customer");
		String usercode=StringUtils.substringBetween(userinfo, "[", "]");
		String username=StringUtils.substringBefore(userinfo, "[");
		
	}
}
