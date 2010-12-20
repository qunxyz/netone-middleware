package oe.cav.web.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Õ®”√I/O≥Ã–Ú
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class CommonIoTools {

	public static void inputDo(InputStream input, OutputStream out) {

		try {
			BufferedInputStream inputBuf = new BufferedInputStream(input);
			int size = inputBuf.available();
			
			for (int i = 0; i < size; i++) {
				out.write(inputBuf.read());
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
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		InputStream input = new FileInputStream("IoOut.java");
		OutputStream ouput = System.out;
		inputDo(input, ouput);
	}

}
