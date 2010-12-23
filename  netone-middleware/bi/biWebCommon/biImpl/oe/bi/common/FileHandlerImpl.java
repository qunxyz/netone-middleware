package oe.bi.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import oe.bi.common.FileHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author wang-ting-jie
 *
 */
public class FileHandlerImpl implements FileHandler {
	private static Log _log = LogFactory.getLog(FileHandlerImpl.class);

	/**
	 * ���ļ� ֱ�Ӵ�ӡ����
	 * 
	 * @param url
	 * @param output
	 */
	public void readFile(String dataStr, OutputStream output) {
		// TODO Auto-generated method stub
		_log.debug(dataStr);
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
			_log.error(dataStr + "��д�ļ�����");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ļ�
	 * 
	 * @param url
	 * @return
	 */
	public String readFileStr(String url) {
		BufferedReader in = null;
		_log.debug(url);
		try {
			in = new BufferedReader(new FileReader(url));
			String temp;
			StringBuffer strBuff = new StringBuffer();
			while ((temp = in.readLine()) != null) {
				strBuff.append(temp + "\n");
			}
			return strBuff.toString();
		} catch (FileNotFoundException e) {
			_log.error("�ļ�:" + url + " ������");
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
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ļ�
	 * 
	 * @param url
	 * @return
	 */
	public InputStream readFile(String url) {
		_log.debug(url);
		InputStream input = null;
		try {
			input = new FileInputStream(url);
			return input;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} 
	}

	/**
	 * writeFile д�뱾���ļ�
	 * 
	 * @param url
	 *            �ļ���ַ
	 * @param filestr
	 *            ����
	 */
	public void writeFile(String url, String filestr) {
		_log.debug(url);
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(url));

			bw.write(filestr);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RuntimeException(e.getMessage());
		} finally {
			try {
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
	
	public void dropFile(String fileUrl) {
		_log.debug(fileUrl);
		File file = new File(fileUrl);
		if (file.exists())
			file.delete(); // ɾ���ļ�
	}


	/**
	 * writeFile д�뱾���ļ�
	 * 
	 * @param url
	 *            �ļ���ַ
	 * @param input
	 *            ������
	 */
	public void writeFile(String url, InputStream input) {
		_log.debug(url);
		// TODO Auto-generated method stub
		OutputStream output = null;
		try {
			output = new FileOutputStream(url);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = input.read(b)) > 0) {
				output.write(b, 0, i);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RuntimeException(e.getMessage());
		} finally {
			try {
				output.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		FileHandlerImpl fhImpl = new FileHandlerImpl();
		fhImpl.writeFile("c:/test.txt", "����������");
		String str = fhImpl.readFileStr("c:/test.txt");
	

	}

}
