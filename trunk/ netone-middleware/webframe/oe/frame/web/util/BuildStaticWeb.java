package oe.frame.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * WEB动态页面优化<br>
 * 实现将动态页面转化为静态页面的工具类
 * 
 * @author chen.jia.xun(Robanco) support by Oesee.org
 * 
 */
public class BuildStaticWeb {
	/**
	 * 创建静态页面
	 * 
	 * @param url
	 * @param path
	 */
	public static void build(String url, String filename) {
		URL rul;
		InputStream input = null;
		OutputStream output = null;

		try {
			rul = new URL(url);
			
			URLConnection urlc = rul.openConnection();
			input = urlc.getInputStream();
			System.out.println("build static url:"+url);
			System.out.println("build static file:"+filename);
			output = new FileOutputStream(filename);
			byte[] byteinfo = new byte[8912];
			int size = 0;
			while ((size = input.read(byteinfo)) != -1) {
				output.write(byteinfo, 0, size);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void build(String url, String filepath, String filename) {
		URL rul;
		InputStream input = null;
		OutputStream output = null;
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.mkdir();
			}
			rul = new URL(url);
			URLConnection urlc = rul.openConnection();
			input = urlc.getInputStream();
			File realFile = new File(filepath + filename);
			output = new FileOutputStream(realFile);
			byte[] byteinfo = new byte[8912];
			int size = 0;
			while ((size = input.read(byteinfo)) != -1) {
				output.write(byteinfo, 0, size);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.flush();
				input.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
