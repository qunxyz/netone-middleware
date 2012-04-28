package com.jl.common.report;

import javax.servlet.http.HttpServletResponse;

import com.jl.common.report.obj.QueryCondition;

import com.jl.common.report.obj.XReportFace;

public interface XReportIfc {
	// ��������ֶ�����
	String _TYPE[][] = { { "01", "ʱ��" }, { "02", "����" }, { "03", "����ʱ��" },
			{ "04", "�ַ�" }, { "05", "����" }, { "06", "ʱ�䷶Χ" }, { "07", "���ڷ�Χ" },
			{ "08", "����ʱ�䷶Χ" }, { "09", "���ַ�Χ" }, { "10", "ʱ��Ƚ�" },
			{ "11", "��ֵ�Ƚ�" }, { "12", "��ѡ��Ա" }, { "13", "��ѡ��Ա" },
			{ "14", "��ѡĿ¼" }, { "15", "��ѡĿ¼" }, { "16", "��ѡ��Դ" },
			{ "17", "��ѡ��Դ" }, { "18", "����" }, { "19", "���ڱȽ�" },
			{ "20", "����ʱ��Ƚ�" },{"21","�ַ�����Χ"},{"22","ģ����ѯ"} };

	/**
	 * ���챨���ѯ����
	 * 
	 * @param naturalname
	 * @return
	 */
	XReportFace buildFace(String naturalname);

	/**
	 * ���챨��
	 * 
	 * @param naturalname
	 *            ��ѯ��ƶ����name
	 * @param type
	 *            ���յ������������ html\xls\cvs\pdf
	 * @return
	 */
	Object buildReport(String naturalname, String type,QueryCondition qd,
			HttpServletResponse rs);

}
