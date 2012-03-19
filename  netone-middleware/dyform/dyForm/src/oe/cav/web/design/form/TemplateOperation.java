package oe.cav.web.design.form;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;


import oe.cav.web.util.CommonIoTools;
import oe.frame.web.util.WebTip;

public class TemplateOperation {

	public static final String createUrl = "/data/showdata/createview.do";

	public static final String queryUrl = "/data/showdata/queryview.do";
	


	/**
	 * 读取模版头信息
	 * 
	 * @return
	 */
	public static String readtemplatehead(String templateHead) {
		InputStream input = null;
		try {
			input = new FileInputStream(TemplateOperation.class.getResource("")
					.getPath().replaceAll("%20", " ")
					+ "/" + templateHead);
			byte[] byteinfo = new byte[input.available()];
			input.read(byteinfo);
			return new String(byteinfo);
		} catch (FileNotFoundException e) {
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

	/**
	 * 获得URL中html信息
	 * 
	 * @param url
	 * @return
	 */
	public static String buildUrl(String url) {
		URL rul;
		InputStream input = null;
		try {
			rul = new URL(url);
			URLConnection urlc = rul.openConnection();
			input = urlc.getInputStream();
			byte[] byteinfo = new byte[input.available()];
			int size = 0;
			input.read(byteinfo);
			return new String(byteinfo);
		} catch (Exception e) {
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
