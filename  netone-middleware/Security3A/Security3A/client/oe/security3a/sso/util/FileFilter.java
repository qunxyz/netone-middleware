package oe.security3a.sso.util;


import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件过滤类
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class FileFilter implements FilenameFilter {

	String ext;

	public FileFilter(String ext) {
		this.ext = ext;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(this.ext);
	}

}
