package oesee.teach.java.io.other;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * ¹ÜµÀÁ÷
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class PipeDemo {

	public static void main(String args[]) throws IOException {
		byte aByteData1 = 123;
		byte aByteData2 = 111;
		PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = new PipedOutputStream(pis);
		try {
			pos.write(aByteData1);
			pos.write(aByteData2);
			System.out.println((byte) pis.read());
			System.out.println((byte) pis.read());
		} finally {
			pis.close();
			pos.close();
		}
	}
}
