package oe.bi.view.bus.filt.util;

public class FetchTargetColumnIndex {

	public static int fetchTargetColumnIndex(String[] targets, String choiceId) {
		// 步骤一 获得指标所在的位置索引值
		for (int i = 0; i < targets.length; i++) {
			if (targets[i].equals(choiceId)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
