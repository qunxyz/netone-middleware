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
			// ͨѶ��ַ
			String netaddress = "http://www.oesee.com";
			// ͨѶЭ��
			URL rul = new URL(netaddress);
			// ���������
			URLConnection urlc = rul.openConnection();
			InputStream input = urlc.getInputStream();
			// �������ݽ���
			StringBuffer but = new StringBuffer();
			int read = 0;
			while ((read = input.read()) != -1) {
				but.append((char) read);
			}
		} catch (Exception e) {

		}

	}
}
