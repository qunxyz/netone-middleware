package oesee.teach.java.one;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sample4: ���������
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Array {
	public static void main(String[] args) {
		// -------����ԭʼ����--------
		// �Ѿ�֪�����Ԫ��
		int[] value0 = { 1, 2, 3 };
		// ��֪�����Ԫ��,��֪��Ԫ�ص�����
		int[] value01 = new int[3];
		// �Ȳ�֪�������Ԫ��,Ҳ��֪������ĸ���
		int num = 0;
		int[] value02 = new int[num];

		// -------��������-------

		String[] value1 = { new String("1"), new String("2"), new String("3") };
		String[] value11 = new String[3];
		int num1 = 0;
		String[] value12 = new String[num1];

		// ------�Զ�������-----------

		// �Ȳ�֪�������Ԫ��,Ҳ��֪������ĸ���
		List list = new ArrayList();
		list.add(1);
		list.remove(0);
		
		


	}
}
