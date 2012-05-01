package oesee.teach.java.three.reg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ϰ��ο���
 * 
 * �������һ���ַ��� String abc=�� [name=mike][age=20] [name=jim][age=22]
 * [name=tomi][age=23] �� �����������ַ���,������Human����,���ұ���������е�human����
 * 
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class HomeWork {

	public static void main(String[] args) throws IOException {
		// ��������
		String abc = "[name=mike][age=20] [name=jim][age=22] [name=tomi][age=23]";
		// ��Բ�������,�������������ʽ
		String _REG = "\\[name=\\w+\\]\\s*\\[age=[0-9]{1,2}\\]";
		Pattern pat = Pattern.compile(_REG);
		Matcher mat = pat.matcher(abc);

		// ʶ�� ���� [name=mike] �����ַ�����������ʽ, [name=mike]��ƥ�䵽 =mike
		String _REG_NAME = "=\\w+";
		Pattern pat_name = Pattern.compile(_REG_NAME);
		// ʶ�� ���� [age=20] �����ַ�����������ʽ,[age=20]��ƥ�䵽 =20
		String _REG_AGE = "=[0-9]{1,2}";
		Pattern pat_age = Pattern.compile(_REG_AGE);

		// ������̬���鱣��Human����
		List list = new ArrayList();
		while (mat.find()) {
			// �ҵ���һ��������Human��Ϣ
			String findElement = mat.group();

			// ��Human��Ϣ�л��������Ϣ
			Matcher mat_name = pat_name.matcher(findElement);
			Human human = new Human();
			if (mat_name.find()) {
				human.setName(mat_name.group().substring(1));
			}
			// ��Human��Ϣ�л��������Ϣ
			Matcher mat_age = pat_age.matcher(findElement);
			if (mat_age.find()) {
				human.setAge(Integer.parseInt(mat_age.group().substring(1)));
			}
			// ���ҵ���Human������붯̬����
			list.add(human);
		}

		// ����������е���Ա
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Human human = (Human) itr.next();
			System.out.println(human.getName() + "," + human.getAge());
		}

	}
}
