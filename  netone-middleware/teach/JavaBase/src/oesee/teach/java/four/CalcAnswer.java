package oesee.teach.java.four;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public class CalcAnswer {
	public static int CalcAnswer(String a, String b) throws NumberRangeException {
		int int1, int2;
		int answer = -1;
		int1 = Integer.parseInt(a);
		int2 = Integer.parseInt(b);
		if ((int1 < 10) || (int1 > 20) || (int2 < 10) || (int2 > 20)) {
			String info = "数字超出指定范围";

			throw new NumberRangeException(info);
		}
		return int1 + int2;
	}

	public static int demo6(String a, String b) throws Exception {
		int int1, int2;
		int answer = -1;
		int1 = Integer.parseInt(a);
		int2 = Integer.parseInt(b);
		if ((int1 < 10) || (int1 > 20) || (int2 < 10) || (int2 > 20)) {
			String info = "数字超出指定范围";
			throw new Exception(info);
		}
		return int1 + int2;
	}

	public static void main(String[] args) {
		try {
			CalcAnswer("5", "10");
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
