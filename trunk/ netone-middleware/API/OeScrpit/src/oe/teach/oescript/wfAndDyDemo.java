package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * �ۺϲ��ԣ�Ӧ�� dy �� wf���� һ�����һ��ҵ���߼���ʵ��
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class wfAndDyDemo extends OeScript {
	public static void main(String[] args) {
		/* �ù��ܹ������ڵ���б����� dyΪ���������ڹ����������½���չ�Ե�ֵvaluemode��ֵΪdyform,��valueΪ�� */

		// ����Naturalname(����)
		String formNaturalname = "BUSSFORM.BUSSFORM.DY_81237253530248";
		// ������ʵ��
		String lsh = dy.newInstance(formNaturalname);
		// Ϊָ������ֵ
		dy.set(lsh, "column3", "dai");
		dy.set(lsh, "column4", 20);
		// ����һ������ʵ��ID
		String runtimeid = wf.newInstance("BUSSWF.BUSSWF.OESCRIPTDEMO");// BUSSWF.BUSSWF.OESCRIPTDEMO�����̾�̬ID(��������)
		// Ϊdy������ֵ
		wf.set(runtimeid, "dy", lsh);
		// ������dy��ֵ��ӡ������̨
		System.out.println(wf.get(runtimeid, "dy"));
		
		
		int planmode=wf.getn(runtimeid,"planmode");
		if(planmode==1) { /*ȡ�����*/
		   		String formid=wf.get(runtimeid, "formai");
		                  float number=0-dy.getn(formid, "column4");
		                  dy.set(formid,"column4",number);
		}
		
	}
	
	
	
}
