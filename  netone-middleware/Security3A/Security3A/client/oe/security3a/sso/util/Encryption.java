package oe.security3a.sso.util;

/**
 * 可逆加解密算法
 * @author Linhuabing
 * @version 1.0
 */

import java.util.*;

public class Encryption {

	private Encryption() {
	}

	// 加密解密密码字符串。Src为加密前的明文；Key为密钥；Encrypt为加、解密控制符，true为加密，反之解密
	public static String encry(String Src, String Key, boolean Encrypt) {
		int KeyLen;
		int KeyPos;
		int offset;
		int SrcPos;
		int SrcAsc;
		int TmpSrcAsc;
		int Range;
		String dest = "";

		KeyLen = Key.length();
		if (KeyLen == 0)
			Key = "Tom Lee";
		KeyPos = -1;
		SrcPos = 0;
		SrcAsc = 0;
		Range = 256;
		if (Encrypt == true) // Encryption
		{
			Random r1 = new Random();
			offset = r1.nextInt(Range);
			if (offset >= 16) // 保证转化为2位十六进制数
				dest = Integer.toHexString(offset).toUpperCase();
			else
				dest = "0" + Integer.toHexString(offset).toUpperCase();
			for (SrcPos = 0; SrcPos < Src.length(); SrcPos++) {
				SrcAsc = ((int) (Src.charAt(SrcPos)) + offset) % 255; // % =
				// mod
				if (KeyPos < KeyLen - 1)
					KeyPos = KeyPos + 1;
				else
					KeyPos = 0;
				SrcAsc = SrcAsc ^ (int) (Key.charAt(KeyPos)); // ^ = XOR
				if (SrcAsc >= 16)
					dest = dest + Integer.toHexString(SrcAsc).toUpperCase();
				else
					dest = dest + "0"
							+ Integer.toHexString(SrcAsc).toUpperCase();
				offset = SrcAsc;
			}
		} else // Decryption
		{
			// 空值加密的为2位
			if (Src.length() == 2) {
				return "";
			}
			offset = HexToInt(Src.substring(0, 2)); // Hexadecimal to decimal
			SrcPos = 2;
			do {
				SrcAsc = HexToInt(Src.substring(SrcPos, SrcPos + 2));
				if (KeyPos < KeyLen - 1)
					KeyPos = KeyPos + 1;
				else
					KeyPos = 0;
				TmpSrcAsc = SrcAsc ^ (int) (Key.charAt(KeyPos)); // Ascii
				if (TmpSrcAsc <= offset)
					TmpSrcAsc = 255 + TmpSrcAsc - offset;
				else
					TmpSrcAsc = TmpSrcAsc - offset;
				dest = dest + (char) (TmpSrcAsc);
				offset = SrcAsc;
				SrcPos = SrcPos + 2;
			} while (SrcPos < Src.length() - 1);
		}
		return dest;
	}

	// Hexadecimal's string to decimal int
	private static int HexToInt(String HexStr) {
		int Result = 0;
		char[] SingleHex = HexStr.toCharArray();
		for (int i = 0; i < SingleHex.length; i++) {
			int temp1 = (int) SingleHex[i]; // Ascii
			if (temp1 >= 97) // a...f
				Result = Result * 16 + temp1 - 87; // Ascii to decimal
			else // A...F
			if (temp1 >= 65)
				Result = Result * 16 + temp1 - 55;
			else
				// 0...10
				Result = Result * 16 + temp1 - 48;
		}
		return Result;
	}

}
