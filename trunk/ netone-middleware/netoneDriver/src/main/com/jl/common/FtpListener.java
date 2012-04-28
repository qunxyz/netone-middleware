package com.jl.common;

import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.EventAdapter;
import com.enterprisedt.net.ftp.FileTransferClient;

public class FtpListener extends EventAdapter {
	private Logger log = Logger.getLogger(FtpListener.class);

	/**
	 * Keep the last reported byte count
	 */
	private long bytesTransferred = 0;

	/**
	 * FTPClient reference
	 */
	private FileTransferClient ftp;

	/**
	 * Constructor
	 * 
	 * @param ftp
	 */
	public FtpListener(FileTransferClient ftp) {
		this.ftp = ftp;
	}

	public void bytesTransferred(String connId, String remoteFilename,
			long bytes) {
		log.info("transfered size:" + bytes);
		bytesTransferred = bytes;
	}

	/**
	 * Will contain the total bytes transferred once the transfer is complete
	 */
	public long getBytesTransferred() {
		return bytesTransferred;
	}

}
