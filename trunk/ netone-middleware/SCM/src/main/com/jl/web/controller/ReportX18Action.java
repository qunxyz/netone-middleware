package com.jl.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jl.common.MathHelper;
import com.jl.common.TimeUtil;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.common.workflow.DbTools;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * ��������ͳ�Ʊ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-10 ����10:58:41
 * @history
 */
public class ReportX18Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");
		// ��������Ϣ
		String forward = "/xreport/xreport18.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��ӡ �鱦 �������۴�ӡ
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean flag = false;
		String format = "excel";
		//��ˮ��
		String lsh = request.getParameter("lsh");
		StringBuffer sb = new StringBuffer();

		/** ������������ */		
		sb.append("SELECT * from dyform.DY_371337952339241 where lsh= '");
		sb.append(lsh);
		sb.append("'");

		
		// ���ԭʼ���ݱ��
		Table t = new Table();
		ReportExt reportExt = new ReportExt();
		List<TableCell> headerSet1 = new ArrayList();
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		headerSet1.add(new TableCell(""));
		List list = DbTools.queryData(sb.toString());
		TableRow tr = new TableRow();
		tr.addCell(new TableCell("����"));
		tr.addCell(new TableCell("��������"));
		tr.addCell(new TableCell("�Ƶ���"));
		tr.addCell(new TableCell("�Ƶ�ʱ��"));
		tr.addCell(new TableCell("��ӡ״̬"));
		tr.addCell(new TableCell("������"));
		tr.addCell(new TableCell("��������"));
		tr.addCell(new TableCell("�ۻ�Ա"));
		tr.addCell(new TableCell("���澭��"));
		tr.addCell(new TableCell("�ͻ�"));
		tr.addCell(new TableCell("��Ա����"));
		tr.addCell(new TableCell("�ܻ���"));
		tr.addCell(new TableCell("�ɶҷ�"));
		tr.addCell(new TableCell("��ע"));
		tr.addCell(new TableCell("������������"));
		tr.addCell(new TableCell("��������"));
		tr.addCell(new TableCell("��Ʒ����"));
		tr.addCell(new TableCell("���ϻ�������"));
		tr.addCell(new TableCell("�����Ż�����"));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		t.addRow(tr);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			TableRow tr_z = new TableRow();
			String column3  = (String)object.get("column3");
			String column4  = (String)object.get("column4");
			String column5  = (String)object.get("column5");
			String column6  = (String)object.get("column6");
			String column7  = (String)object.get("column7");
			String column8  = (String)object.get("column8");
			String column9  = (String)object.get("column9");
			String column10  = (String)object.get("column10");
			String column11  = (String)object.get("column11");
			String column12  = (String)object.get("column12");
			String column13  = (String)object.get("column13");
			String column14  = object.get("column14")==null?"0":object.get("column14").toString();	
			String column15  = object.get("column15")==null?"0":object.get("column15").toString();	
			String column16  = (String)object.get("column16");
			String column21  = (String)object.get("column21");
			String column22  = (String)object.get("column22");
			String column23  = (String)object.get("column23");
			String column24  = (String)object.get("column24");
			String column25  = (String)object.get("column25");
			tr_z.addCell(new TableCell(column3));
			tr_z.addCell(new TableCell(column4));
			tr_z.addCell(new TableCell(column5));
			tr_z.addCell(new TableCell(column6));
			tr_z.addCell(new TableCell(column7));
			tr_z.addCell(new TableCell(column8));
			tr_z.addCell(new TableCell(column9));
			tr_z.addCell(new TableCell(column10));
			tr_z.addCell(new TableCell(column11));
			tr_z.addCell(new TableCell(column12));
			tr_z.addCell(new TableCell(column13));
			tr_z.addCell(new TableCell(column14));
			tr_z.addCell(new TableCell(column15));
			tr_z.addCell(new TableCell(column16));
			tr_z.addCell(new TableCell(column21));
			tr_z.addCell(new TableCell(column22));
			tr_z.addCell(new TableCell(column23));
			tr_z.addCell(new TableCell(column24));
			tr_z.addCell(new TableCell(column25));
			tr_z.addCell(new TableCell(""));
			tr_z.addCell(new TableCell(""));
			tr_z.addCell(new TableCell(""));
			tr_z.addCell(new TableCell(""));
			tr_z.addCell(new TableCell(""));
			t.addRow(tr_z);
		}
		
		StringBuffer sb_1 = new StringBuffer();
		sb_1.append("SELECT * from dyform.DY_371337952339240 where fatherlsh= '");
		sb_1.append(lsh);
		sb_1.append("'");
		List list_1 = DbTools.queryData(sb_1.toString());
		TableRow tr_1 = new TableRow();
		tr_1.addCell(new TableCell("�����"));
		tr_1.addCell(new TableCell("��Ʒ����"));
		tr_1.addCell(new TableCell("���ѷ�ʽ"));
		tr_1.addCell(new TableCell("ԭ�ۼ�"));
		tr_1.addCell(new TableCell("ʵ�ۼ�"));
		tr_1.addCell(new TableCell("�۾���"));
		tr_1.addCell(new TableCell("���˼�"));
		tr_1.addCell(new TableCell("����С��ע"));
		tr_1.addCell(new TableCell("����"));
		tr_1.addCell(new TableCell("��Ʒ����"));
		tr_1.addCell(new TableCell("���"));
		tr_1.addCell(new TableCell("֤���"));
		tr_1.addCell(new TableCell("����(g)"));
		tr_1.addCell(new TableCell("����(g)"));
		tr_1.addCell(new TableCell("�ִ�"));
		tr_1.addCell(new TableCell("��ʯ��(cp/t)"));
		tr_1.addCell(new TableCell("��״"));
		tr_1.addCell(new TableCell("��ɫ"));
		tr_1.addCell(new TableCell("����"));
		tr_1.addCell(new TableCell("����"));
		tr_1.addCell(new TableCell("��ʯ��(ct/p)"));
		tr_1.addCell(new TableCell("��Ʒ��ע"));
		tr_1.addCell(new TableCell("���ѽ��"));
		tr_1.addCell(new TableCell("����"));
		t.addRow(tr_1);
		for (Iterator iterator = list_1.iterator(); iterator.hasNext();) {
			flag = true;
			Map object = (Map) iterator.next();
			TableRow tr_z = new TableRow();
			String column3  = (String)object.get("column3");
			String column4  = (String)object.get("column4");
			String column5  = (String)object.get("column5");
			String column6  = object.get("column6")==null?"0":object.get("column6").toString();	
			String column7  = object.get("column7")==null?"0":object.get("column7").toString();	
			String column8  = object.get("column8")==null?"0":object.get("column8").toString();	
			String column9  = object.get("column9")==null?"0":object.get("column9").toString();	
			String column10  = (String)object.get("column10");
			String column11  = (String)object.get("column11");
			String column12  = (String)object.get("column12");
			String column13  = (String)object.get("column13");
			String column14  = object.get("column14")==null?"0":object.get("column14").toString();	
			String column15  = object.get("column15")==null?"0":object.get("column15").toString();	
			String column16  = object.get("column16")==null?"0":object.get("column16").toString();
			String column17  = (String)object.get("column17");	
			String column18  = object.get("column18")==null?"0":object.get("column18").toString();
			String column19  = (String)object.get("column19");	
			String column20  = (String)object.get("column20");	
			String column21  = (String)object.get("column21");	
			String column22  = (String)object.get("column22");	
			String column23  = object.get("column23")==null?"0":object.get("column23").toString();
			String column24  = (String)object.get("column24");	
			String column25  = object.get("column25")==null?"0":object.get("column25").toString();
			String column26  = (String)object.get("column26");	
			tr_z.addCell(new TableCell(column3));
			tr_z.addCell(new TableCell(column4));
			tr_z.addCell(new TableCell(column5));
			tr_z.addCell(new TableCell(column6));
			tr_z.addCell(new TableCell(column7));
			tr_z.addCell(new TableCell(column8));
			tr_z.addCell(new TableCell(column9));
			tr_z.addCell(new TableCell(column10));
			tr_z.addCell(new TableCell(column11));
			tr_z.addCell(new TableCell(column12));
			tr_z.addCell(new TableCell(column13));
			tr_z.addCell(new TableCell(column14));
			tr_z.addCell(new TableCell(column15));
			tr_z.addCell(new TableCell(column16));
			tr_z.addCell(new TableCell(column17));
			tr_z.addCell(new TableCell(column18));
			tr_z.addCell(new TableCell(column19));
			tr_z.addCell(new TableCell(column20));
			tr_z.addCell(new TableCell(column21));
			tr_z.addCell(new TableCell(column22));
			tr_z.addCell(new TableCell(column23));
			tr_z.addCell(new TableCell(column24));
			tr_z.addCell(new TableCell(column25));
			tr_z.addCell(new TableCell(column26));
			t.addRow(tr_z);
		}
		
		
		
		
		StringBuffer sb_2 = new StringBuffer();
		sb_2.append("SELECT * from dyform.DY_371337952339239 where fatherlsh= '");
		sb_2.append(lsh);
		sb_2.append("'");
		List list_2 = DbTools.queryData(sb_2.toString());
		TableRow tr_2 = new TableRow();
		tr_2.addCell(new TableCell("��������"));
		tr_2.addCell(new TableCell("���ϱ��"));
		tr_2.addCell(new TableCell("���ϳ�ɫ"));
		tr_2.addCell(new TableCell("��ʯ����"));
		tr_2.addCell(new TableCell("���ϴ���"));
		tr_2.addCell(new TableCell("����"));
		tr_2.addCell(new TableCell("������"));
		tr_2.addCell(new TableCell("ʵ���ɫ"));
		tr_2.addCell(new TableCell("���"));
		tr_2.addCell(new TableCell("���ս��"));
		tr_2.addCell(new TableCell("ʯ����(ct)"));
		tr_2.addCell(new TableCell("ʯ����(p)"));
		tr_2.addCell(new TableCell("ʯ�Ͻ��"));
		tr_2.addCell(new TableCell("ʵ�ۼ�"));
		tr_2.addCell(new TableCell("�ֿ۽��"));
		tr_2.addCell(new TableCell("���ѷ�ʽ"));
		tr_2.addCell(new TableCell("���ѵ���"));
		tr_2.addCell(new TableCell("���ѽ��"));
		tr_2.addCell(new TableCell("С��ע"));
		tr_2.addCell(new TableCell("��������"));
		tr_2.addCell(new TableCell("��������"));
		tr_2.addCell(new TableCell("����"));
		tr_2.addCell(new TableCell("����"));
		tr_2.addCell(new TableCell(""));
		t.addRow(tr_2);
		for (Iterator iterator = list_2.iterator(); iterator.hasNext();) {
			flag = true;
			Map object = (Map) iterator.next();
			TableRow tr_z = new TableRow();
			String column3  = (String)object.get("column3");
			String column4  = (String)object.get("column4");
			String column5  = (String)object.get("column5");
			String column6  = (String)object.get("column6");	
			String column8  = (String)object.get("column8");	
			String column9  = object.get("column9")==null?"0":object.get("column9").toString();	
			String column10  = object.get("column10")==null?"0":object.get("column10").toString();	
			String column11  = (String)object.get("column11");
			String column12  = (String)object.get("column12");
			String column13  = object.get("column13")==null?"0":object.get("column13").toString();	
			String column14  = object.get("column14")==null?"0":object.get("column14").toString();	
			String column15  = object.get("column15")==null?"0":object.get("column15").toString();	
			String column16  = object.get("column16")==null?"0":object.get("column16").toString();
			String column17  = object.get("column17")==null?"0":object.get("column17").toString();	
			String column18  = object.get("column18")==null?"0":object.get("column18").toString();
			String column19  = (String)object.get("column19");	
			String column20  = object.get("column20")==null?"0":object.get("column20").toString();
			String column21  = object.get("column21")==null?"0":object.get("column21").toString();	
			String column22  = (String)object.get("column22");	
			String column23  = (String)object.get("column23");
			String column24  = (String)object.get("column24");	
			String column25  = (String)object.get("column25");
			String column26  = object.get("column26")==null?"0":object.get("column26").toString();
			tr_z.addCell(new TableCell(column3));
			tr_z.addCell(new TableCell(column4));
			tr_z.addCell(new TableCell(column5));
			tr_z.addCell(new TableCell(column6));
			tr_z.addCell(new TableCell(column8));
			tr_z.addCell(new TableCell(column9));
			tr_z.addCell(new TableCell(column10));
			tr_z.addCell(new TableCell(column11));
			tr_z.addCell(new TableCell(column12));
			tr_z.addCell(new TableCell(column13));
			tr_z.addCell(new TableCell(column14));
			tr_z.addCell(new TableCell(column15));
			tr_z.addCell(new TableCell(column16));
			tr_z.addCell(new TableCell(column17));
			tr_z.addCell(new TableCell(column18));
			tr_z.addCell(new TableCell(column19));
			tr_z.addCell(new TableCell(column20));
			tr_z.addCell(new TableCell(column21));
			tr_z.addCell(new TableCell(column22));
			tr_z.addCell(new TableCell(column23));
			tr_z.addCell(new TableCell(column24));
			tr_z.addCell(new TableCell(column25));
			tr_z.addCell(new TableCell(column26));
			tr_z.addCell(new TableCell(""));
			t.addRow(tr_z);
		}
		if(flag){
		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "���˻��ձ���", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "���˻��ձ���" + currentTimeMillis, report,
				response);}
		else { return;}
	}
}
