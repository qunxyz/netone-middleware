package oesee.teach.java.one;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Title: ��ϰ��һ
 * </p>
 * 
 * <p>
 * Description:
 * һ�ŵ�������һ����λ���ĺ���1xx11����ǧλ���Ͱ�λ����ͿĨ���壻��һ���棬����֪��������ܱ�57��67���������һ���㷨���ҳ��õ��ݵĿ��ܺ��롣
 * </p>
 * 
 * 12711,15611,18411,12711,15611,18411,
 * 
 * @author: Chen.Jia.Xun(Robanco)
 * 
 * @version 1.0
 */

public class No1 {

	// ���������ĺ�������
	List usefulData = new ArrayList();

	public No1() {
	}

	/**
	 * ִ��
	 * 
	 * @param myriad��
	 *            ��λ����
	 * @param hundred����λ����
	 * @param ten��ʮλ����
	 * @param Entries
	 *            ����λ����
	 */
	public void toDo(int myriad, int ten, int Entries) {
		// �������
		if (myriad < 1 || ten < 0 || Entries < 0) {
			System.err.println("��Ч�������ȼ�������");
			return;
		}
		// �������ֵ������
		int valueTop = myriad * 10000 + 9000 + 900 + ten * 10 + Entries;
		// �������ֵ������
		int valueButton = myriad * 10000 + ten * 10 + Entries;
		/*
		 * Ѱ�����������������㷨1
		 */
/* for(int i=valueButton;i<valueTop;i++){
	if((i%57==0||i%67==0)&&
			String.valueOf(i).substring(3).equals("11")){
		usefulData.add(i);
	}
}
		// for (int i = valueButton; i < valueTop; i++) {
		// if ((i % 57 == 0 || i % 67 == 0) &&
		// String.valueOf(i).substring(3).equals("11")) {
		// usefulData.add(i);
		// }
		// }
		// }
		// /*

*/
		for (int i =valueButton;i <valueTop;i++){
			if(i%57==0||i%67==0){
				if((i-11)%100==0){
					usefulData.add(i);
				}
			}
		}
		// * Ѱ�����������������㷨2
		// */
//		for (int i = valueButton; i < valueTop; i++) {
//			if (i % 57 == 0 || i % 67 == 0) {
//				if ((i - 11) % 100 == 0) {
//					usefulData.add(i);
//				}
//
//			}
//		}
		/*
		 * Ѱ�����������������㷨3
		 */

		for (int i = valueButton; i < valueTop; i = i + 100) {
			if (i % 57 == 0 || i % 67 == 0) {
				usefulData.add(i);
			}
		}
	}

	/**
	 * ��ʽ�����ֵ
	 * 
	 * @return
	 */
	public String toString() {
		// StringBuffer valueList = new StringBuffer();
		// for (Iterator itr = usefulData.iterator(); itr.hasNext();) {
		// valueList.append(itr.next());
		// valueList.append(",");
		// }

		String valueList = "";
		for (Iterator itr = usefulData.iterator(); itr.hasNext();) {
			valueList += itr.next() + ",";
		}
		return "����:" + usefulData.size() + "\n\r" + valueList.toString();
	}

	public static void main(String[] args) {
		No1 no11 = new No1();
		// ��ͨ����
		no11.toDo(1, 1, 1);
		System.out.println(no11);
		// // ��ͨ����
		// no11.toDo(9, 9, 9, 9);
		// System.out.println(no11);
		// // ��ͨ����
		// no11.toDo(9, 0, 0, 0);
		// System.out.println(no11);
		// // �ݴ����
		// no11.toDo(0, -1, 1, 1);
		// System.out.println(no11);
		// // �ݴ����
		// no11.toDo(-1, -1, -1, -1);
		// System.out.println(no11);
	}

}