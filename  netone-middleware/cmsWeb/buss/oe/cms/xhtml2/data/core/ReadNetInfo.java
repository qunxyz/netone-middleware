package oe.cms.xhtml2.data.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 获得网络地址比如www.sina.com 的 html信息
 * 
 * @author ibm
 * 
 */
public final class ReadNetInfo {

	public static String netParser(String url, String endcode) {
		InputStream input = null;
		try {
			URL rul = new URL(url);
			URLConnection urlc = rul.openConnection();

			input = urlc.getInputStream();

			StringBuffer but = new StringBuffer();
			int read = 0;
			while ((read = input.read()) != -1) {
				but.append((char) read);
			}
			String info = new String(but.toString().getBytes("iso-8859-1"),
					endcode);
			return info;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

}
