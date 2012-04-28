package com.lucaslee.report;

import java.io.IOException;
import java.io.OutputStream;

import com.lucaslee.report.model.Report;

/**
 * �����ӡ��������printer��Ҫʵ�ֵĽӿڡ�
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
	 * ��ӡ����
	 * 
	 * @param t
	 *            �������ݡ�
	 * @param result
	 *            �ѱ����������������������
	 * @throws ReportException
	 */
	public abstract void print(Report r, OutputStream result)
			throws ReportException, IOException;
}