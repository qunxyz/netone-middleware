package oe.security3a.sso.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * �ļ�Э�����
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class FilterFile {

	/**
	 * �ļ�����
	 * 
	 */
	public static List filterfile(String root, String extname) {
		File fi = new File(root);
		// �ļ����˴���
		FilenameFilter filefilt = new FileFilter(extname);
		String[] list2 = fi.list(filefilt);
		return Arrays.asList(list2);

	}

}
