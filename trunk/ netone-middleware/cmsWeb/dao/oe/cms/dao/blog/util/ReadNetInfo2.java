package oe.cms.dao.blog.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 从网络上下载文件信息
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class ReadNetInfo2 {

	public static String netParser(String remoteuri, String localuri) {
		try {
			URL rul = new URL(remoteuri);
			URLConnection urlc = rul.openConnection();

			InputStream input = urlc.getInputStream();
			byte[] byteinfo = new byte[1024];

			OutputStream out = new FileOutputStream(localuri);
			int read = 0;
			while ((read = input.read(byteinfo)) != -1) {
				out.write(byteinfo, 0, read);
				out.flush();
			}
			out.close();
			input.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		return null;

	}

}
