package oesee.teach.java.io.other;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RecoveryMyData {

	public static void main(String[] args) throws IOException {
		InputStream input = new FileInputStream("d:/seril.ser");
		byte[] bytearr = new byte[input.available()];
		input.read(bytearr);
		String rs = new String(bytearr);
		System.out.println(rs);
		
		
	}

}
