package oesee.teach.java.one;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sample6:Ӧ�õ���ѭ��
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Loop2 {

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);

		// ��ͨѭ������
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		// ����ѭ������
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			System.out.println(itr.next());
		}
	}

}
