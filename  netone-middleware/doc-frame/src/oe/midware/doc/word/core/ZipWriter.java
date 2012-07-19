package oe.midware.doc.word.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import oe.midware.doc.common.ReadIoInfo;

/**
 * 
 * @author hjy
 * 
 */
public class ZipWriter {
	private ZipOutputStream out = null;

	private final int BUFFER_SIZE = 2048;

	private Hashtable names = new Hashtable();

	public ZipWriter(String fileName) {

		try {
			out = new ZipOutputStream(new FileOutputStream(fileName));
		} catch (Exception e) {
			throw new RuntimeException("create file:" + fileName + " fail", e);
		}
	}

	public ZipWriter(OutputStream os) {
		out = new ZipOutputStream(os);
	}

	public void addDirectory(String path) throws IOException {

		if (path.length() == 0) {
			return;
		}
		if (!path.endsWith("/")) {
			path += "/";
		}
		if (names.put(path, "1") == null) {
			out.putNextEntry(new ZipEntry(path));
		}

	}

	public void addFile(File file) throws IOException {
		addFile(file.getName(), file);
	}

	public void addFile(String fileName, File file) throws IOException {
		if (file.isDirectory()) {
			File[] fl = file.listFiles();
			if (!fileName.endsWith("/")) {
				fileName += "/";
			}
			addDirectory(fileName);
			for (int i = 0; i < fl.length; i++) {
				addFile(fileName + fl[i].getName(), fl[i]);
			}
		} else {
			FileInputStream in = new FileInputStream(file);
			addFile(fileName, in);
			in.close();
		}

	}

	public void addFile(String fileName, InputStream in) throws IOException {
		if (names.put(fileName, "1") == null) {
			out.putNextEntry(new ZipEntry(fileName));
			byte[] b = new byte[this.BUFFER_SIZE];
			int read = 0;
			while ((read = in.read(b, 0, BUFFER_SIZE)) > 0) {
				out.write(b, 0, read);
			}
		}
	}

	public void close() throws IOException {
		out.close();
		this.names.clear();

	}

	/**
	 * 对于web上的保存输出
	 * 
	 * @param outPutType
	 * @param docPrintList
	 * @param output
	 * @param iszip（true）
	 */
	public static void printFileZip(String outPutType, String docInfo,
			String templateExt, String templateId, OutputStream output) {

		InputStream inputExt = null;
		ZipWriter zip = null;
		try {
			zip = new ZipWriter(output);
			zip.addFile(templateId + outPutType, new ByteArrayInputStream(
					docInfo.getBytes()));
			// 扩展文件架
			File extFileInfo = new File(templateExt);
			if (extFileInfo.exists()) {
				zip.addFile(extFileInfo);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				zip.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
