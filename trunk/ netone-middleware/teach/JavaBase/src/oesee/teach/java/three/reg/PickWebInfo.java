package oesee.teach.java.three.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������򣬴�web�ϳ�ȡ��Ϣ
 * 
 * @author ibm
 * 
 */
public class PickWebInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ���ĳ����Ŀ��Ϣ�Ĺ���
		String rule1 = "<!--������̬��ʾ��  1-->[^��]+<!--������̬��ʾ��  2-->";
		// ���������е�URL��ַ�Ĺ���
		String rule2 = "<\\s*a\\s+href\\s*=\\s*\".*</a>";
		// ����
		PickWebInfo.pickWebInfo("http://java.ccidnet.com/", "gb2312", rule1,
				rule2);

	}

	public static void pickWebInfo(String url, String encode, String rule1,
			String rule2) {

		String info = ReadNetInfo.netParser(url, encode);
		Pattern pat = Pattern.compile(rule1);
		Matcher mat = pat.matcher(info);
		if (mat.find()) {
			String infonext = mat.group();

			Pattern patx = Pattern.compile(rule2);
			Matcher matx = patx.matcher(infonext);
			while (matx.find()) {
				System.out.println(matx.group());
			}
		}
	}

}
