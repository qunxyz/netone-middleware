package oe.midware.doc.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReadIoInfo {

	private static Log _log = LogFactory.getLog(ReadIoInfo.class);

	public static String getFileContenStr(String fileName) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			String temp;
			StringBuffer strBuff = new StringBuffer();
			while ((temp = in.readLine()) != null) {
				strBuff.append(temp + "\n");
			}
			if (_log.isDebugEnabled()) {
				_log.debug(strBuff);
			}
			return strBuff.toString();
		} catch (FileNotFoundException e) {
			_log.error("�ļ�:" + fileName + " ������");
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			_log.error("�ļ���д��������");
			e.printStackTrace();
			return "";
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ļ�д�������
	 * 
	 * @param outPutType
	 * @param dataStr
	 */
	public static void printFileWord( OutputStream output,
			String dataStr) {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new StringReader(dataStr));
			out = new PrintWriter(output);
			String s;
			while ((s = in.readLine()) != null) {
				out.println(s);
			}

		} catch (IOException e) {
			_log.error(dataStr+"��д�ļ�����");
			e.printStackTrace();
		} finally {
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
