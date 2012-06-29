package com.wfp.util;

import java.io.File;
import java.util.Comparator;

/** * TODO 根据文件的最后修改时间进行排序 * */
class FileLastModifSort implements Comparator<File> {
	public int compare(File arg0, File arg1) {
		if (arg0.lastModified() > arg1.lastModified()) {
			return 1;
		} else if (arg0.lastModified() == arg1.lastModified()) {
			return 0;
		} else {
			return -1;
		}
	}
} 
