package oesee.teach.java.io.other;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class NetSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 通讯地址
			String netaddress = "http://www.oesee.com";
			// 通讯协议
			URL rul = new URL(netaddress);
			// 获得数据流
			URLConnection urlc = rul.openConnection();
			InputStream input = urlc.getInputStream();
			// 进行数据交换
			StringBuffer but = new StringBuffer();
			int read = 0;
			while ((read = input.read()) != -1) {
				but.append((char) read);
			}
		} catch (Exception e) {

		}

	}
}
