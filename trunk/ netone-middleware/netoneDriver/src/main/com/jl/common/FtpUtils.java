package com.jl.common;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FileTransferClient;

public class FtpUtils {
	private static Logger LOG = Logger.getLogger(FtpUtils.class);

	private final String FTP_FILE = "ftp.properties";

	private FileTransferClient ftp = new FileTransferClient();

	private String host = null;
	private String userName = null;
	private String password = null;

	private static FtpUtils ftpUtils = null;

	private FtpUtils() {
		try {
			Properties properties = ClassLoaderUtil.newInstance()
					.getProperties(FTP_FILE);
			host = properties.getProperty("ftp.host");
			userName = properties.getProperty("ftp.userName");
			password = properties.getProperty("ftp.password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FtpUtils getInstance() {
		if (ftpUtils == null) {
			ftpUtils = new FtpUtils();
		}

		return ftpUtils;
	}

	public void open() throws Exception {
		ftp.setRemoteHost(host);
		ftp.setUserName(userName);
		ftp.setPassword(password);

		FtpListener ul = new FtpListener(ftp);
		ftp.setEventListener(ul);
		// the transfer notify interval must be greater than buffer size
		ftp.getAdvancedSettings().setTransferBufferSize(1000);
		ftp.getAdvancedSettings().setTransferNotifyInterval(5000);
		// set enconding ,to support chinese file name
		ftp.getAdvancedSettings().setControlEncoding("GBK");
		ftp.connect();
	}

	public void upload(String filePath, String fileName, InputStream in)
			throws Exception {
		ftp.setContentType(FTPTransferType.BINARY);

		String[] filePathArray =filePath.split("/");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < filePathArray.length; i++) {
			FTPFile[] ftpFile = ftp.directoryList();
			boolean isExist = false;
			for (int j = 0; j < ftpFile.length; j++) {
				if (filePathArray[i].equals(ftpFile[j].getName())) {
					isExist = true;
					break;
				}
			}

			if (!isExist) {
				ftp.createDirectory(filePathArray[i]);
			}

			buf.append(java.io.File.separator + filePathArray[i]);
			ftp.changeDirectory(buf.toString());
		}

		String remoteFile = buf.toString() + File.separator + fileName;
		// 构造文件流上传
		OutputStream os = ftp.uploadStream(remoteFile);
		try {
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} finally {
			in.close();
			os.close();
			in = null;
			os = null;
		}
	}

	public void close() throws Exception {
		ftp.disconnect();
	}

}
