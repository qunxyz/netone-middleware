package oesee.teach.java.three.reg;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获得网络地址比如www.sina.com 的 html信息
 * 
 * @author ibm
 * 
 */
public class ReadNetInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String newUrl = java.net.URLEncoder.encode("你好", "UTF-8");
			String info = netParser(
					"http://www.excite.co.jp/world/chinese/?before=" + newUrl
							+ "&wb_lp=CHJA&big5=no", "UTF-8");
			System.out.println(info);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String netParser(String url, String endcode) {
		StringBuffer but = new StringBuffer();
		try {
			URL rul = new URL(url);
			URLConnection urlc = rul.openConnection();

			InputStream input = urlc.getInputStream();

			int read = 0;
			while ((read = input.read()) != -1) {
				but.append((char) read);
			}
			return new String(but.toString().getBytes("iso-8859-1"), "UTF-8");
			// String rule1 = "<table cellspacing='0' border='1'
			// cellpadding='3'[^桝]+</tr></table>";
			// String rule2 = "\\$_[^桝]*</font></a>_\\$</td><td>[0-9]</td>";
			//
			// Pattern pat = Pattern.compile(rule1);
			// Matcher mat = pat.matcher(info);
			//
			// if (mat.find()) {
			//
			// String infonext = mat.group();
			// List list=new ArrayList();
			// String infox=StringUtils.substringBetween(infonext,"$_","_$");
			// while(infox!=null && infox.length()>0){
			// list.add(infox);
			// infonext=StringUtils.substringAfter(infonext,"_$");
			// infox=StringUtils.substringBetween(infonext,"$_","_$");
			//					
			// }
			// String []listinfo=(String[])list.toArray(new String[0]);
			// }

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
