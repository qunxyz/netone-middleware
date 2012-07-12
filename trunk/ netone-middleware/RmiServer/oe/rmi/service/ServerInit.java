package oe.rmi.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;

/**
 * RMI初始化程序,可以自动将目录下的包加入classpath
 * 
 * @author chen.jia.xun
 * 
 */
public class ServerInit {

	public static void main(String[] args) {

		File filePath = new File("app\\");
		File[] appPath = filePath.listFiles();
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < appPath.length; i++) {
			String appPreLib = appPath[i].getPath() + "\\lib\\";
			String appPreConf = appPath[i].getPath() + "\\conf\\";

			File fi = new File(appPreLib);
			// 文件过滤处理 增加 Jar
			FilenameFilter filefilt = new FileFilter(".jar");
			String[] list2 = fi.list(filefilt);
			if (list2 != null) {
				for (int k = 0; k < list2.length; k++) {
					but.append(appPreLib + list2[k] + ";");
				}
			}

			File appconf = new File(appPreConf);
			File[] appconflist = appconf.listFiles();
			if (appconflist != null) {
				for (int l = 0; l < appconflist.length; l++) {
					but.append(appPreConf + appconflist[l].getName() + ";");
				}
			}
		}
		String finalStr = "java -classpath \"" + but
				+ "\" oe.rmi.service.RmiStart ";
		OutputStream out = null;
		try {
			out = new FileOutputStream("env.bat");
			out.write(finalStr.getBytes());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
