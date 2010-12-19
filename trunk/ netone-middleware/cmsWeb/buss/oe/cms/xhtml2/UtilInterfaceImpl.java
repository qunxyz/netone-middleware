package oe.cms.xhtml2;


public final class UtilInterfaceImpl implements UtilInterface {

	public String arrVar(String[] arr, int index) {
		if (arr == null || index < 0) {
			return "0";
		}

		int reallean = arr.length;
		if (index < reallean) {
			return arr[index];
		} else {
			System.err.print("xhtml脚本错误:数组的下标益处");
			return "0";
		}
	}

	public String arrVar(String[][] arr, int indx, int indy) {
		if (arr == null || indy < 0 || indx < 0) {
			return "0";
		}

		int reallean = arr.length;
		if (indx < reallean) {
			if (arr[indx].length > indy) {
				return arr[indx][indy];
			}
		} else {
			System.err.print("xhtml脚本错误:数组的下标益处");
		}
		return "0";
	}

	public long parLong(String arg0) {
		try {
			return Long.parseLong(arg0);
		} catch (Exception e) {
			System.err.println("无效数字：" + arg0);
			return 0;
		}
	}

	public double parDouble(String arg0) {
		try {
			return Double.parseDouble(arg0);
		} catch (Exception e) {
			System.err.println("无效数字：" + arg0);
			return 0;
		}
	}

	public float parFloat(String arg0) {
		try {
			return Float.parseFloat(arg0);
		} catch (Exception e) {
			System.err.println("无效数字：" + arg0);
			return 0;
		}
	}

	public int parInt(String arg0) {
		try {
			return Integer.parseInt(arg0);
		} catch (Exception e) {
			System.err.println("无效数字：" + arg0);
			return 0;
		}
	}



}
