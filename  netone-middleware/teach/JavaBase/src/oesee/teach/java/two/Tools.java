package oesee.teach.java.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author chenjx
 * 
 */
public class Tools {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please entry info:");
		String line = br.readLine();
		while (!line.equals("over")) {
			System.out.println("ƒ„∏’≤≈ ‰»Î¡À:" + line);
			line = br.readLine();
		}
	}

}
