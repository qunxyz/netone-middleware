package oesee.teach.java.three;

import org.apache.commons.lang.StringUtils;

/**
 * �ַ��ָ�
 * 
 * @author chen.jia.xun <br>
 *         email:chenjx@fjycit.com<br>
 *         mobile:15860836998
 * 
 */
public class AboutSplit {

	public static void main(String[] args) {
		// ��һ�ַ�ʽ����Ҫת�⴦������������ʽƬ�η��� ��
		String str = "abc[efg[cdf";
		String[] strArr = str.split("\\["); // ע��ת��
		for (int i = 0; i < strArr.length; i++) {
			System.out.println(strArr[i]);
		}

		// �ڶ��ַ�ʽ (ʹ��StringUtils���߰�)
		String str1 = "abc[efg[cdf";
		String[] strArr1 = StringUtils.split(str1, "[");
		for (int i = 0; i < strArr.length; i++) {
			System.out.println(strArr[i]);// ���
		}
	}

}
