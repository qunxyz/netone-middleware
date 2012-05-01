package oesee.teach.java.io.output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 用程序来写程序
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo1 {
	public static void todo() {
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

	public static void todox() {
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
}
