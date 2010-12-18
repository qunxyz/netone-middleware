package oe.security3a.sso.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 文件协议相关
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class FilterFile {

	/**
	 * 文件过滤
	 * 
	 */
	public static List filterfile(String root, String extname) {
		File fi = new File(root);
		// 文件过滤处理
		FilenameFilter filefilt = new FileFilter(extname);
		String[] list2 = fi.list(filefilt);
		return Arrays.asList(list2);

	}

}
