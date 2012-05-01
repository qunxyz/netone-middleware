package oesee.teach.java.one;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Title: 练习题一
 * </p>
 * 
 * <p>
 * Description:
 * 一张单据上有一个５位数的号码1xx11，其千位数和百位数已涂抹不清；另一方面，我们知道这个数能被57或67除尽。设计一个算法，找出该单据的可能号码。
 * </p>
 * 
 * 12711,15611,18411,12711,15611,18411,
 * 
 * @author: Chen.Jia.Xun(Robanco)
 * 
 * @version 1.0
 */

public class No1 {

	// 满足条件的号码数组
	List usefulData = new ArrayList();

	public No1() {
	}

	/**
	 * 执行
	 * 
	 * @param myriad：
	 *            万位数字
	 * @param hundred：百位数字
	 * @param ten：十位数字
	 * @param Entries
	 *            ：个位数字
	 */
	public void toDo(int myriad, int ten, int Entries) {
		// 参数检查
		if (myriad < 1 || ten < 0 || Entries < 0) {
			System.err.println("无效参数请先检查参数！");
			return;
		}
		// 构造号码值的上限
		int valueTop = myriad * 10000 + 9000 + 900 + ten * 10 + Entries;
		// 构造号码值的下限
		int valueButton = myriad * 10000 + ten * 10 + Entries;
		/*
		 * 寻找满足条件的数组算法1
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
		// * 寻找满足条件的数组算法2
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
		 * 寻找满足条件的数组算法3
		 */

		for (int i = valueButton; i < valueTop; i = i + 100) {
			if (i % 57 == 0 || i % 67 == 0) {
				usefulData.add(i);
			}
		}
	}

	/**
	 * 格式化输出值
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
		return "总数:" + usefulData.size() + "\n\r" + valueList.toString();
	}

	public static void main(String[] args) {
		No1 no11 = new No1();
		// 普通测试
		no11.toDo(1, 1, 1);
		System.out.println(no11);
		// // 普通测试
		// no11.toDo(9, 9, 9, 9);
		// System.out.println(no11);
		// // 普通测试
		// no11.toDo(9, 0, 0, 0);
		// System.out.println(no11);
		// // 容错测试
		// no11.toDo(0, -1, 1, 1);
		// System.out.println(no11);
		// // 容错测试
		// no11.toDo(-1, -1, -1, -1);
		// System.out.println(no11);
	}

}