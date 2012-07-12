package com.lucaslee.report;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * 报表系统抛出的异常。
 * 
 * @author Lucas lee
 * @version 1.0
 */
public class ReportException extends NestableRuntimeException {

	private static final long serialVersionUID = 1L;

	public ReportException() {
		super();
	}

	public ReportException(String msg) {
		super(msg);
	}

	public ReportException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ReportException(Throwable cause) {
		super(cause);
	}
}
