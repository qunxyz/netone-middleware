package oesee.teach.java.io.input;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ������������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo6 {

	public static void main(String[] args) {
		todo();
	}

	/**
	 * ������������
	 * 
	 */
	public static void todo() {
		File file = new File("src/oesee/teach/java/io/input/Session.data");

		try {
			// �����ļ���
			InputStream input = new FileInputStream(file);
			// ���컺��
			InputStream inputBut = new BufferedInputStream(input);

			StringBuffer but = new StringBuffer();
			// ͳ��0�ĸ���
			int zerocount = 0;
			int read = 0;
			while ((read = inputBut.read()) != -1) {
				if ((char) read == '0') {// �����������0��ô��ʼ����
					zerocount++;
				} else {// ����1,��������0
					zerocount = 0;
				}

				if (zerocount == 4) {
					int readNext = inputBut.read();
					if ('0' == readNext) {
						throw new RuntimeException("���ݴ������");
					}
				}
				but.append((char) read);
			}
			System.out.println(but);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
