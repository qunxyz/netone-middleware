package com.lucaslee.report;

import java.io.IOException;
import java.io.OutputStream;

import com.lucaslee.report.model.Report;

/**
 * 报表打印机。所有printer都要实现的接口。
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:Lucas-lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 */
public interface Printer {

	/**
	 * 打印报表。
	 * 
	 * @param t
	 *            报表数据。
	 * @param result
	 *            把报表内容输出到这个输出流中
	 * @throws ReportException
	 */
	public abstract void print(Report r, OutputStream result)
			throws ReportException, IOException;
}