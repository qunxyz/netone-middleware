package oesee.teach.java.io.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * �����������,���������ṩ�����ֽ������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demox {
	/**
	 * �����������,����1
	 * 
	 */
	public static void todo() {
		InputStream input = null;
		try {
			input = new FileInputStream("IOTest.java");
			int size = input.available();
			System.out.println("�ļ���С:" + size);
			byte[] bf = new byte[102400];
			int rs = 0;
			while ((rs = input.read(bf)) != -1) {
				for (int i = 0; i < rs; i++) {
					System.out.print((char) bf[i]);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����2,ʹ��Reader
	 * 
	 */
	public static void todo2() {
		Reader reader;
		try {
			reader = new FileReader("IOTest.java");
			int read = 0;
			while ((read = reader.read()) != -1) {
				System.out.print((char) read);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
