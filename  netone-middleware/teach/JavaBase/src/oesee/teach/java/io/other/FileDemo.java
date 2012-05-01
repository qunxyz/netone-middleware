package oesee.teach.java.io.other;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;

/**
 * 文件协议相关
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class FileDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		demo1();

	}

	public static void demo() throws Exception {
		// 确定寻址方式
		String filename = "c:/a.data";
		// 确定通讯协议
		File file = new File(filename);
		// 获得通讯流
		InputStream input = new FileInputStream(file);
		// 通讯事件
		while (input.read() != -1) {}
	}

	/**
	 * 研究文件的属性
	 * 
	 */
	public static void demo1() {
		File fi = new File("C:/WINDOWS/","hh.exe");
		System.out.println(fi.getAbsolutePath());
		System.out.println(fi.getParent());
		System.out.println(fi.canWrite());
		System.out.println(fi.exists());
		System.out.println(fi.isHidden());
	}

	/**
	 * 研究目录的属性
	 * 
	 */
	public static void demo2() {
		File fi = new File("C:/WINDOWS");
		System.out.println(fi.getAbsolutePath());
		System.out.println(fi.getParent());
		System.out.println(fi.canWrite());
		System.out.println(fi.exists());
		System.out.println(fi.isHidden());

		// 所有的文件列表
		File[] list1 = fi.listFiles();
		for (int i = 0; i < list1.length; i++) {
			System.out.print(list1[i].getName() + ",");
		}
		System.out.println();
	}

	/**
	 * 文件过滤
	 * 
	 */
	public static void demo3() {
		File fi = new File("C:/WINDOWS");
		// 文件过滤处理
		FilenameFilter filefilt = new FileFilter(".log");
		String[] list2 = fi.list(filefilt);
		for (int i = 0; i < list2.length; i++) {
			System.out.print(list2[i] + ",");
		}
		System.out.println();
	}

}
