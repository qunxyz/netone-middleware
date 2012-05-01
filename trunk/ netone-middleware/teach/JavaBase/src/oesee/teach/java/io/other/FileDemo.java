package oesee.teach.java.io.other;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;

/**
 * �ļ�Э�����
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
		// ȷ��Ѱַ��ʽ
		String filename = "c:/a.data";
		// ȷ��ͨѶЭ��
		File file = new File(filename);
		// ���ͨѶ��
		InputStream input = new FileInputStream(file);
		// ͨѶ�¼�
		while (input.read() != -1) {}
	}

	/**
	 * �о��ļ�������
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
	 * �о�Ŀ¼������
	 * 
	 */
	public static void demo2() {
		File fi = new File("C:/WINDOWS");
		System.out.println(fi.getAbsolutePath());
		System.out.println(fi.getParent());
		System.out.println(fi.canWrite());
		System.out.println(fi.exists());
		System.out.println(fi.isHidden());

		// ���е��ļ��б�
		File[] list1 = fi.listFiles();
		for (int i = 0; i < list1.length; i++) {
			System.out.print(list1[i].getName() + ",");
		}
		System.out.println();
	}

	/**
	 * �ļ�����
	 * 
	 */
	public static void demo3() {
		File fi = new File("C:/WINDOWS");
		// �ļ����˴���
		FilenameFilter filefilt = new FileFilter(".log");
		String[] list2 = fi.list(filefilt);
		for (int i = 0; i < list2.length; i++) {
			System.out.print(list2[i] + ",");
		}
		System.out.println();
	}

}
