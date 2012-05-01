package oesee.teach.java.io.output;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * ¹ÜµÀÁ÷
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo6 {
	static PipedInputStream pis = new PipedInputStream();
	static PipedOutputStream pos = null;
	static {
		try {
			pos = new PipedOutputStream(pis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		todo1((byte)1);
		todo2();
	}

	public static void todo1(byte byte1) throws IOException {
		pos.write(byte1);
	}

	public static void todo2() throws IOException {
		byte rs = (byte) pis.read();
		System.out.println(rs);
	}
}
