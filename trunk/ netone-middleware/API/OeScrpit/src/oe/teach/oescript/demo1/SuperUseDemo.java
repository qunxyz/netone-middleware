package oe.teach.oescript.demo1;

import oe.midware.workflow.engine.rule2.func.STools;

/**
 * �м�����ڲ�API������ script��ȥ����������Scrpit�Ĺ���
 * @author robanco
 *
 */
public class SuperUseDemo {
	
	public static void main(String[] args) throws Exception{
		STools st=new STools();
		st.dy_.deleteData("formcode", "lsh");
		st.cupm_.checkUserPermission("0000", "adminx", "fsdf.sdf.dsfds.", "3");
	}

}
