package oe.cav.web.data.dyform.utils;

import java.util.ArrayList;
import java.util.List;

public class DealwithButton {
	/**
	 * ������еİ�ť�ű����������ڿ�����ʾ��ť��Ϣ
	 * 
	 * @param formcode
	 * @return
	 */
	public static String[][] dealwithBut(String butinfo) {

		if (butinfo == null || butinfo.length() == 0) {
			return new String[0][2];
		}
		String[] butinfoArr = butinfo.split(",");
		List listvalue = new ArrayList();
		for (int i = 0; i < butinfoArr.length; i++) {
			String preButInfo = butinfoArr[i];
			if (preButInfo == null || preButInfo.length() == 0) {
				continue;
			}
			String[] preBufInfoArr = preButInfo.split(":");
			if (preBufInfoArr.length != 3) {
				throw new RuntimeException("��Ч��ť���ʽ!=3:" + butinfo);
			}
			listvalue.add(preBufInfoArr);
		}
		return (String[][]) listvalue.toArray(new String[0][3]);
	}

}
