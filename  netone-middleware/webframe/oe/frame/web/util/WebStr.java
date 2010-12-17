package oe.frame.web.util;



import javax.servlet.http.HttpServletRequest;

/**
 * Web�ַ���������ת������������
 * 
 * @author chen.jia.xun(Robanco) support by Oesee.org
 * 
 */
public class WebStr {
	/**
	 * Ĭ�ϴ���ʵ�� iso-8859-1 �� GBK ��ת������
	 * 
	 * @param req
	 * @param str
	 * @return
	 */
	public static String encode(HttpServletRequest req, String str) {
		boolean needEndcode = checkNeedEncode(req);
		if (needEndcode) {
			return iso8859ToGBK(str);
		}
		return str;

	}

	// public static String webTipInfo(String tipinfo) {
	// if (tipinfo == null || tipinfo == "") {
	// return null;
	// }
	// return "<script type='text/javascript'>" + "alert('" + tipinfo
	// + "');</script>";
	// }

	private static boolean checkNeedEncode(HttpServletRequest req) {
		if (req.getMethod().equalsIgnoreCase("POST")) {
			String encoding = req.getCharacterEncoding();
			if (encoding != null) {
				if (encoding.equalsIgnoreCase("GB2312")
						|| encoding.equalsIgnoreCase("GBK")) {
					return false;
				}
			}
		}
		return true;
	}

	public static String iso8859ToGBK(String str) {
		if (str != null) {
			try {
				return new String(str.getBytes("iso-8859-1"), "GBK");
			} catch (Exception e) {
				System.err.print("����ת������:" + e.getMessage());
			}
		}
		return str;
	}

	public static String gbkToiso8859(String str) {
		if (str != null) {
			try {
				return new String(str.getBytes("GBK"), "iso-8859-1");
			} catch (Exception e) {
				System.err.print("����ת������:" + e.getMessage());
			}
		}
		return str;
	}

	public static String iso8859ToGB2312(String str) {
		if (str != null) {
			try {
				return new String(str.getBytes("iso-8859-1"), "GB2312");
			} catch (Exception e) {
				System.err.print("����ת������:" + e.getMessage());
			}
		}
		return str;
	}

	public static String iso8859ToUTF8(String str) {
		if (str != null) {
			try {
				return new String(str.getBytes("iso-8859-1"), "UTF-8");
			} catch (Exception e) {
				System.err.print("����ת������:" + e.getMessage());
			}
		}
		return str;
	}

	public static String uTF8Toiso8859(String str) {
		if (str != null) {
			try {
				return new String(str.getBytes("UTF-8"), "iso-8859-1");
			} catch (Exception e) {
				System.err.print("����ת������:" + e.getMessage());
			}
		}
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
