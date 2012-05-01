package oesee.teach.java.io.other;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Êý¾ÝÁ÷
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class DataSubDemo {

	public static void main(String args[]) throws IOException {
		demo1();
		demo2();
	}

	public static void demo1() throws IOException {
		FileOutputStream fos = new FileOutputStream("IOTestx.java");
		DataOutputStream dos = new DataOutputStream(fos);
		try {
			dos.writeBoolean(true);
			dos.writeByte((byte) 123);
			dos.writeChar('J');
			dos.writeDouble(3.141592654);
			dos.writeFloat(2.7182f);
			dos.writeInt(1234567890);
			dos.writeLong(998877665544332211L);
			dos.writeShort((short) 11223);
		} finally {
			dos.close();
		}
	}

	public static void demo2() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(
				"IOTestx.java"));
		try {
			System.out.println("\t " + dis.readBoolean());
			System.out.println("\t " + dis.readByte());
			System.out.println("\t " + dis.readChar());
			System.out.println("\t " + dis.readDouble());
			System.out.println("\t " + dis.readFloat());
			System.out.println("\t " + dis.readInt());
			System.out.println("\t " + dis.readLong());
			System.out.println("\t " + dis.readShort());
		} finally {
			dis.close();
		}
	}

}
