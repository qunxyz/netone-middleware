package oesee.teach.java.three;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * �����ַ��� ����Human��Ϣ
 * 
 * @author chen.jia.xun <br>
 *         email:chenjx@fjycit.com<br>
 *         mobile:15860836998
 * 
 */
public class StringOfHuman {

	final static String _HUMEN_LIST_INFO = "[name=mike][age=4q][name=jim][age=40]";

	public static void main(String[] args) {
		// �ݴ���Ա��Ϣ�ַ���
		String tmpInfo = _HUMEN_LIST_INFO;
		// ��ȡ������ ����Ϊ���ݷ����ַ�������ɽṹ��֪��name����Ϣһ���� age֮ǰ
		String firstName = StringUtils.substringBetween(tmpInfo, "[", "]");
		// ������̬���飬�������е���Ա��Ϣ
		List list = new ArrayList();
		boolean success = true;
		// ���δ������е���Ա��Ϣ
		while (firstName != null) {
			String[] nameArr = firstName.split("=");
			if (nameArr[0].equals("age")) {
				System.err.print("��Ч���ʽ:" + _HUMEN_LIST_INFO + "����ԭ��:"
						+ firstName + " û����֮��Ӧ�� Name��Ϣ  ");
				success = false;
				break;
			}
			// ȡ�õ�ǰ��õ�����֮����ַ���
			tmpInfo = StringUtils.substringAfter(tmpInfo, "]");
			// ȡ�õ�ǰ��õ�����֮����ַ����еĵ�һ��λ��[]���������Ϣ
			String firstAge = StringUtils.substringBetween(tmpInfo, "[", "]");
			// ����һ�� Human����洢 ��ǰ������������Ա��Ϣ
			Human human = new Human();
			// ȡ�������������ַ�����ʽ��֪����λ�� ����"="֮��
			String name = StringUtils.substringAfter(firstName, "=");
			human.setName(name);
			// ȡ�������������ַ�����ʽ��֪����λ�� ����"="֮��
			String[] ageArr = StringUtils.split(firstAge,"=");
			if (ageArr[0].equals("age")) {
				if (ageArr[1].matches("\\d+")) {
					// ת�� ���ͣ���Ϊ Human�е�����������
					Integer age = Integer.valueOf(ageArr[1]);
					human.setAge(age);
				}
			} else {
				System.err.print("��Ч���ʽ:" + _HUMEN_LIST_INFO + "����ԭ��:" + name
						+ " û����֮��Ӧ�� Age��Ϣ ");
				success = false;
				break;
			}

			// ����̬�����м��� ������������Human��Ϣ
			list.add(human);
			// ����ȡ�Ӵ���Ϊ������һ��Human��Ϣ��׼��
			tmpInfo = StringUtils.substringAfter(tmpInfo, "]");
			// ��ȡ����һ�� Human������ ����Ϊ���ݷ����ַ�������ɽṹ��֪��name����Ϣһ���� age֮ǰ
			firstName = StringUtils.substringBetween(tmpInfo, "[", "]");
		}
		if (success) {
			// ���������е� Human��Ϣ
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Human object = (Human) iterator.next();
				System.out.println(object.getName() + ":" + object.getAge());
			}
		}

	}
}
