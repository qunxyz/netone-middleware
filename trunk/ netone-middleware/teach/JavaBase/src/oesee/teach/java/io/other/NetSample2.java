package oesee.teach.java.io.other;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang.StringUtils;

public class NetSample2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 通讯地址
			String netaddress = "http://soso.vvpo.cn/song9/UUauth/vvpo/华语/欧阳靖-ABC/4.wma";
			// 通讯协议
			URL rul = new URL(netaddress);
			// 获得数据流
			URLConnection urlc = rul.openConnection();
			InputStream input = urlc.getInputStream();
			BufferedInputStream butinput = new BufferedInputStream(input);
			// 进行数据交换
			String filename = StringUtils.substringAfterLast(netaddress, "/");
			OutputStream out = new FileOutputStream("e:/" + filename);
			BufferedOutputStream butoutput = new BufferedOutputStream(out);

			int read = 0;
			while ((read = butinput.read()) != -1) {
				butoutput.write(read);
			}
			input.close();
			out.close();
		} catch (Exception e) {

		}

	}
}
