package oesee.teach.java.io.input;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析话务数据
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo6 {

	public static void main(String[] args) {
		todo();
	}

	/**
	 * 解析话务数据
	 * 
	 */
	public static void todo() {
		File file = new File("src/oesee/teach/java/io/input/Session.data");

		try {
			// 构造文件流
			InputStream input = new FileInputStream(file);
			// 构造缓存
			InputStream inputBut = new BufferedInputStream(input);

			StringBuffer but = new StringBuffer();
			// 统计0的个数
			int zerocount = 0;
			int read = 0;
			while ((read = inputBut.read()) != -1) {
				if ((char) read == '0') {// 如果连续遇到0那么开始计数
					zerocount++;
				} else {// 遇到1,将计数置0
					zerocount = 0;
				}

				if (zerocount == 4) {
					int readNext = inputBut.read();
					if ('0' == readNext) {
						throw new RuntimeException("数据传输错误");
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
