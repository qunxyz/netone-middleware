package oe.bi.view.bus.filt.util;

public class FetchTargetColumnIndex {

	public static int fetchTargetColumnIndex(String[] targets, String choiceId) {
		// ����һ ���ָ�����ڵ�λ������ֵ
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
