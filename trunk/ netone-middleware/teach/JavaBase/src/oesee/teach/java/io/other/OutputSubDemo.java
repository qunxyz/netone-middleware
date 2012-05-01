package oesee.teach.java.io.other;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * OnputStream流的几个相关子类
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class OutputSubDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		demo1();
	}

	public static void demo1() {
		String outpuinfo = "import java.sql.Timestamp;\npublic class IoOut {\n"
				+ "public static void aboutTimestamp() {\n"
				+ "Timestamp timestamp = new Timestamp(System.currentTimeMillis());"
				+ "Timestamp timestampnew = timestamp.valueOf(\"2004-12-12 10:10:10.000\");\n}\n}";
		OutputStream output = null;
		try {
			output = new FileOutputStream("IoOut.java");
			byte[] info = outpuinfo.getBytes();

			output.write(info);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void demo1x() {
		String outpuinfo = "import java.sql.Timestamp;\npublic class IoOut {\n"
				+ "public static void aboutTimestamp() {\n"
				+ "Timestamp timestamp = new Timestamp(System.currentTimeMillis());"
				+ "Timestamp timestampnew = timestamp.valueOf(\"2004-12-12 10:10:10.000\");\n}\n}";
		OutputStream output = null;
		try {
			output = new FileOutputStream("IoOut.java");

			byte[] info = outpuinfo.getBytes();
			for (int i = 0; i < info.length; i++) {
				output.write(info[i]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void demo2() {

	}
}
