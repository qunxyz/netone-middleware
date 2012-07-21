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
public class ReportX9Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");

		// ��������Ϣ
		request.setAttribute("list_FClient", ReportBaseData.getFClientInfo());
		request.setAttribute("list_GClient", ReportBaseData.getGClientInfo());
		request.setAttribute("list_ChangPingDaLei", ReportBaseData.getChangPingDaLeiInfo());
		request.setAttribute("list_ChengSe", ReportBaseData.getChengSeXingXiInfo());
		request.setAttribute("list_BaoShi", ReportBaseData.getBaoShiMingChengInfo());
		request.setAttribute("list_PingMing", ReportBaseData.getChangPingMingChengInfo());
		request.setAttribute("list_FenXiaoGuiZu", ReportBaseData.getFenXiaoGuiZuInfo());
		request.setAttribute("list_ZiDingDaLei", ReportBaseData.getZiDingDaLeiInfo());
		request.setAttribute("list_JingYingPingPai", ReportBaseData.getJingYingPingPaiInfo());
		request.setAttribute("list_ShouHuoYuan", ReportBaseData.getShouHuoYuanInfo());
		request.setAttribute("list_DianMianJingLi", ReportBaseData.getDianMianJingLiInfo());
		request.setAttribute("list_ShouYinBanZu", ReportBaseData.getShouYinBanZuInfo());
		request.setAttribute("list_XiaoShouShuXing", ReportBaseData.getXiaoShouShuXingInfo());
		
		String forward = "/xreport/xreport9.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��ӡ �鱦 �������۴�ӡ
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String format = request.getParameter("format");

		String repselect4 = request.getParameter("repselect4");
		String repstrcompare1_START = request.getParameter("repstrcompare1_START");
		String repstrcompare1_END = request.getParameter("repstrcompare1_END");
		String repstrcompare2_START = request.getParameter("repstrcompare2_START");
		String repstrcompare2_END = request.getParameter("repstrcompare2_END");
		String reptimes1_START = request.getParameter("reptimes1_START");
		String reptimes1_END = request.getParameter("reptimes1_END");

		String repselect1 = request.getParameter("repselect1");
		String repselect2 = request.getParameter("repselect2");
		String repselect3 = request.getParameter("repselect3");
		String repstr3 = request.getParameter("repstr3");
		String repstr4 = request.getParameter("repstr4");
		String repselect5 = request.getParameter("repselect5");
		String repselect6 = request.getParameter("repselect6");
		String repselect7 = request.getParameter("repselect7");
		String repselect8 = request.getParameter("repselect8");
		String repselect9 = request.getParameter("repselect9");
		String repselect10 = request.getParameter("repselect10");
		String repselect11 = request.getParameter("repselect11");
		String repselect12 = request.getParameter("repselect12");
		String repselect13 = request.getParameter("repselect13");
		String repselect14 = request.getParameter("repselect14");
		String repstr6 = request.getParameter("repstr6");
		String repstr7 = request.getParameter("repstr7");
		StringBuffer sb = new StringBuffer();

		/** ��������ͳ�Ʊ�(�۳�����) */
		sb.append("SELECT ");
		/* Ʒ�� */
		sb.append("IFNULL(t1.column4,'') pm, ");
		/* ϵ������ */
		sb.append("IFNULL(xl.column3,'') xlname, ");
		/* ����ɫ */
		sb.append("IFNULL(cs.column3,'') cs, ");
		/* ����� */
		sb.append("IFNULL(rkmx.column11,'') kh, ");
		/* ԭ��� */
		sb.append("IFNULL(rkmx.column13,'') ybh, ");
		/* ���ִ� */
		sb.append("IFNULL(rkmx.column24,'') sc, ");
		/* �ۻ�Ա */
		sb.append("IFNULL(xsr.column4,'') shy, ");
		/* ������ */
		sb.append("IFNULL(xtdl.column3,'') cpdl, ");
		/* �Զ����� */
		sb.append("IFNULL(zdy.column3,'') zdy, ");
		/* ������ */
		sb.append("IFNULL(fxs.column3,'') fxsname, ");
		/* ������ */
		sb.append("IFNULL(gz.column4,'') gz, ");
		/* ����ͳ�� */
		sb.append("IFNULL(t.column3,'') xsno, ");

		sb.append("IFNULL(COUNT(t1.column3),0) sl, ");
		sb.append("IFNULL(SUM(rkmx.column16),0) zz,IFNULL(SUM(rkmx.column17),0) jz, ");
		sb.append("IFNULL(rkmx.column37,'/') zs,IFNULL(rkmx.column66,'/') fs, ");
		sb.append("IFNULL(SUM(t1.column11),0) sj,IFNULL(SUM(t1.column12),0) zkr, ");
		sb.append("IFNULL(SUM(t1.column15),0) ssj ");
		sb.append("FROM dyform.DY_371337952339241 t ");
		sb.append("LEFT JOIN dyform.DY_371337952339238 t1 ON t.LSH = t1.FATHERLSH ");
		sb.append("LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4 ");
		sb.append("LEFT JOIN dyform.DY_271334208897441 rkmx ON rkmx.column4 = t1.column3 ");
		sb.append("LEFT JOIN dyform.DY_271334208897439 rkd ON rkmx.FATHERLSH=rkd.LSH ");
		sb.append("LEFT JOIN dyform.DY_381336140843574 xl ON xl.column9 = rkmx.column51 ");
		sb.append("LEFT JOIN dyform.DY_61336130537507 cs ON rkmx.column52 = cs.column9 ");
		sb.append("LEFT JOIN dyform.DY_521339922112143 xtdl ON t1.column19 = xtdl.column8 ");
		sb.append("LEFT JOIN dyform.DY_381336140843575 zdy ON zdy.column8 = rkmx.column48 ");
		sb.append("LEFT JOIN dyform.DY_61336130537510 gz ON gz.column7 = t1.column18 ");
		sb.append("LEFT JOIN dyform.DY_381336140843571 xsr ON xsr.column3 = t.column10 ");
		sb.append("WHERE t.STATUSINFO = '01' AND t1.STATUSINFO = '01'  ");
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		    sb.append(" AND t.column3= '" + repstrcompare1_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
		    sb.append(" AND t.column3= '" + repstrcompare1_END + "' "); 
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		    sb.append(" AND t.column3= '" + repstrcompare2_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
		    sb.append(" AND t.column3= '" + repstrcompare2_END + "' "); 
		if(StringUtils.isNotEmpty(reptimes1_START))
		    sb.append("AND t1.column4 >= '" + reptimes1_START + "' ");
		if(StringUtils.isNotEmpty(reptimes1_END))
		    sb.append(" AND t1.column4 <= '" + reptimes1_END + "' ");
		if(StringUtils.isNotEmpty(repselect1))
			sb.append(" AND t.column8= '" + repselect1 + "' ");
		
		sb.append("AND rkd.column8 = '" + repselect2 + "' ");
		sb.append("AND rkmx.column50 = '" + repselect3 + "' ");
		sb.append("AND t.column12 LIKE '%" + repstr3 + "%' ");
		sb.append("AND t.column13 LIKE '%" + repstr4 + "%' ");
		sb.append("AND t1.column17 = '" + repselect5 + "' ");
		sb.append("AND rkmx.column48 = '" + repselect6 + "' ");
		sb.append("AND t1.column19 = '" + repselect7 + "' ");
		sb.append("AND rkmx.column52 = '" + repselect8 + "' ");
		sb.append("AND rkmx.column81 = '" + repselect9 + "' ");
		sb.append("AND t1.column4 LIKE '%" + repselect10 + "%' ");
		sb.append("AND t.column10 = '" + repselect11 + "' ");
		sb.append("AND t.column11 = '" + repselect12 + "' ");
		sb.append("AND t.column9 = '" + repselect13 + "' ");
		sb.append("AND t1.column18 = '%" + repselect14 + "%' ");
		sb.append("AND t.column19 LIKE '%" + repstr6 + "%' ");
		sb.append("AND t.column16 LIKE '%" + repstr7 + "%' ");

		/* Ʒ�� */
		if("Ʒ��".equals(repselect1))
		sb.append("GROUP BY t1.column4 ");
		/* ϵ������ */
		if("ϵ������".equals(repselect1))
		sb.append("GROUP BY rkmx.column51 ");
		/* ����ɫ */
		if("����ɫ".equals(repselect1))
		sb.append("GROUP BY rkmx.column52 ");
		/* ����� */
		if("�����".equals(repselect1))
		sb.append("GROUP BY rkmx.column11 ");
		/* ԭ��� */
		if("ԭ���".equals(repselect1))
		sb.append("GROUP BY rkmx.column13 ");
		/* ���ִ� */
		if("���ִ�".equals(repselect1))
		sb.append("GROUP BY rkmx.column24 ");
		/* �ۻ�Ա */
		if("�ۻ�Ա".equals(repselect1))
		sb.append("GROUP BY t.column10 ");
		/* ������ */
		if("������".equals(repselect1))
		sb.append("GROUP BY t1.column19 ");
		/* �Զ����� */
		if("�Զ�����".equals(repselect1))
		sb.append("GROUP BY rkmx.column48 ");
		/* ������ */
		if("������".equals(repselect1))
		sb.append("GROUP BY t1.column8 ");
		/* ������ */
		if("������".equals(repselect1))
		sb.append("GROUP BY t1.column18 ");
		/* ����ͳ�� */
		if("����ͳ��".equals(repselect1))
		sb.append("GROUP BY t.column3 ");
		
		
		
		
		
		
		// ���ԭʼ���ݱ���
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("СƷ��".equals(repselect1)) {
			headerSet1.add(new TableCell("Ʒ��"));
		} else if ("ϵ������".equals(repselect1)) {
			headerSet1.add(new TableCell("ϵ������"));
		} else if ("�����".equals(repselect1)) {
			headerSet1.add(new TableCell("�����"));
		} else if ("ԭ���".equals(repselect1)) {
			headerSet1.add(new TableCell("ԭ���"));
		} else if ("���ִ�".equals(repselect1)) {
			headerSet1.add(new TableCell("���ִ�"));
		} else if ("������".equals(repselect1)) {
			headerSet1.add(new TableCell("������"));
		} else if ("������".equals(repselect1)) {
			headerSet1.add(new TableCell("������"));
		} else if ("�Զ�����".equals(repselect1)) {
			headerSet1.add(new TableCell("�Զ�����"));
		} else if ("������".equals(repselect1)) {
			headerSet1.add(new TableCell("������"));}
		  else if ("����ɫ".equals(repselect1)) {
					headerSet1.add(new TableCell("����ɫ"));
			}
		  else if ("�ۻ�Ա".equals(repselect1)) {
					headerSet1.add(new TableCell("�ۻ�Ա"));
			}	
		  else if ("�������".equals(repselect1)) {
					headerSet1.add(new TableCell("�������"));
			}
		  else if ("ʯ��ͳ��".equals(repselect1)) {
					headerSet1.add(new TableCell("ʯ��ͳ��"));
			}
		  else if ("����ͳ��".equals(repselect1)) {
					headerSet1.add(new TableCell("����ͳ��"));
		}
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("��ʯ(ct/p)"));
		headerSet1.add(new TableCell("��ʯ(ct/p)"));
		headerSet1.add(new TableCell("�ۼ�"));
		headerSet1.add(new TableCell("�����ɱ�"));

		ReportExt reportExt = new ReportExt();

		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = object.get("pm").toString();
			String xlname = object.get("xlname").toString();
			String kh = object.get("kh").toString();
			String ybh = object.get("ybh").toString();
			String sc = object.get("sc").toString();
			String fxsname = object.get("fxsname").toString();
			String zdydlname = object.get("zdydlname").toString();
			String gzname = object.get("gzname").toString();
			String sl = object.get("sl").toString();
			String zz = object.get("zz").toString();
			String jz = object.get("jz").toString();
			String xtdlname = object.get("xtdlname").toString();
			String zs = object.get("zs").toString();
			String sj = object.get("sj").toString();
			String fs = object.get("fs").toString();
			String jhcb = object.get("jhcb").toString();
			String cs = object.get("cs").toString();
			String shy = object.get("shy").toString();
			String xsno = object.get("xsno").toString();
			String zkr = object.get("zkr").toString();
			String ssg = object.get("ssg").toString();
			
			TableRow tr = new TableRow();
			if ("СƷ��".equals(repselect1)) {
				tr.addCell(new TableCell(pm));
			} else if ("ϵ������".equals(repselect1)) {
				tr.addCell(new TableCell(xlname));
			} else if ("ԭ���".equals(repselect1)) {
				tr.addCell(new TableCell(ybh));
			}else if ("�����".equals(repselect1)) {
				tr.addCell(new TableCell(kh));
			} else if ("���ִ�".equals(repselect1)) {
				tr.addCell(new TableCell(sc));
			} else if ("������".equals(repselect1)) {
				tr.addCell(new TableCell(xtdlname));
			} else if ("�Զ�����".equals(repselect1)) {
				tr.addCell(new TableCell(zdydlname));
			}else if ("������".equals(repselect1)) {
				tr.addCell(new TableCell(gzname));
			} else if ("������".equals(repselect1)) {
				tr.addCell(new TableCell(fxsname));
			}else if ("����ɫ".equals(repselect1)) {
				tr.addCell(new TableCell(cs));
			}else if ("�ۻ�Ա".equals(repselect1)) {
				tr.addCell(new TableCell(shy));
			}else if ("����ͳ��".equals(repselect1)) {
				tr.addCell(new TableCell(xsno));
			}
		    tr.addCell(new TableCell(pm));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sl),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz), 
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fs));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zkr),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(ssg),
					Rectangle.ALIGN_RIGHT));
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "��������ͳ�Ʊ�", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "��������ͳ�Ʊ�" + currentTimeMillis, report,
				response);
	}
}