package oesee.teach.java.io.input;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo5 {
	/**
	 * ÑÝÊ¾pushbackInputStream
	 * 
	 */
	public static void todo() {
		String info = "if(a==4) a=0;\n";
		byte[] buf = info.getBytes();
		InputStream in = new ByteArrayInputStream(buf);
		PushbackInputStream f = new PushbackInputStream(in);
		int c;
		try {
			while ((c = f.read()) != -1) {
				switch (c) {
				case '=':
					if ((c = f.read()) == '=') {
						System.out.print(".eq.");
					} else {
						System.out.print("<-");
						f.unread(c);
					}
					break;

				default:
					System.out.print((char) c);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
