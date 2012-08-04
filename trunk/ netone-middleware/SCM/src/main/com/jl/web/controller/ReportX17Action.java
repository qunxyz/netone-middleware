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
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * 首饰销退统计表
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-10 上午10:58:41
 * @history
 */
public class ReportX17Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");
		// 分销商信息
		request.setAttribute("list_FClient", ReportBaseData.getFClientInfo());
		request.setAttribute("list_GClient", ReportBaseData.getGClientInfo());
		request.setAttribute("list_XiaoShouShuXing", ReportBaseData.getXiaoShouShuXingInfo());
		request.setAttribute("list_JingYingPingPai", ReportBaseData.getJingYingPingPaiInfo());
		request.setAttribute("list_ZiDingDaLei", ReportBaseData.getZiDingDaLeiInfo());
		request.setAttribute("list_ChangPingDaLei", ReportBaseData.getChangPingDaLeiInfo());
		request.setAttribute("list_FuKuanFangShi", ReportBaseData.getFuKuanFangShiInfo());
		request.setAttribute("list_ChengSe", ReportBaseData.getChengSeXingXiInfo());
		request.setAttribute("list_ShouHuoYuan", ReportBaseData.getShouHuoYuanInfo());
		request.setAttribute("list_DianMianJingLi", ReportBaseData.getDianMianJingLiInfo());
		request.setAttribute("list_ShouYinBanZu", ReportBaseData.getShouYinBanZuInfo());
		request.setAttribute("list_FenXiaoGuiZu", ReportBaseData.getFenXiaoGuiZuInfo());
		String forward = "/xreport/xreport17.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 打印 珠宝 首饰销售打印
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Client3A user=SecurityEntry.iv().onlineUser(request);
		boolean rs=SecurityEntry.iv().permission(user.getClientId(), "BUSSENV.BUSSENV.SECURITY.ROLE.ZBROLE.CBCK");
		System.out.println(rs);
		String format = request.getParameter("format");
		//条形码
		String repstrcompare1_START = request.getParameter("repstrcompare1_START");
		String repstrcompare1_END = request.getParameter("repstrcompare1_END");
		//销售单号
		String repstrcompare8_START = request.getParameter("repstrcompare8_START");
		String repstrcompare8_END = request.getParameter("repstrcompare8_END");
		//销售日期
		String reptimes1_START = request.getParameter("reptimes1_START");
		String reptimes1_END = request.getParameter("reptimes1_END");
		//分销商
		String repselect1 = request.getParameter("repselect1");
		//供应商
		String repselect2 = request.getParameter("repselect2");
		//销售属性
		String repselect4 = request.getParameter("repselect4");
		//经营品牌
		String repselect3 = request.getParameter("repselect3");
		//客户名称
		String repstrdim3 = request.getParameter("repstrdim3");
		//客户卡号
		String repstrdim13 = request.getParameter("repstrdim13");
		//自定义大类
		String repselect5 = request.getParameter("repselect5");
		//产品大类
		String repselect6 = request.getParameter("repselect6");
		//付款方式
//		String repselect12 = request.getParameter("repselect12");
		//成色
		String repselect7 = request.getParameter("repselect7");
		//主石名称
//		String repstrdim4 = request.getParameter("repstrdim4");
		//品名
		String repstrdim5 = request.getParameter("repstrdim5");
		//售货员
		String repselect8 = request.getParameter("repselect8");
		//店面经理
		String repselect9 = request.getParameter("repselect9");
		//收银班组
		String repselect10 = request.getParameter("repselect10");
		//售货柜组
		String repselect11 = request.getParameter("repselect11");
		//款号
		String repstrdim6 = request.getParameter("repstrdim6");
		//货号
		String repstrdim7 = request.getParameter("repstrdim7");
		StringBuffer sb = new StringBuffer();

		/** 首饰销售明细表 */		
		sb.append("SELECT '' timex,'' belongx, ");
		sb.append("IFNULL(t.column3,'') 'ssdh'," +
				"IFNULL(t.column4,'-') 'ssdate', ");
		sb.append("IFNULL(t.column8,'') 'fxsno'," +
				"IFNULL(fxs.column3,'') 'fxsname', ");
		sb.append("IFNULL(t1.column3,'') 'txm'," +
				"IFNULL(t1.column4,'') 'pm', ");
		sb.append("IFNULL(t1.column20,'') 'kh'," +
				"IFNULL(t1.column21,'') 'zsh', ");
		sb.append("IFNULL(fxrh.column13,'') 'sc'," +
				"IFNULL(fxrh.column11,0) 'zz'," +
				"IFNULL(fxrh.column12,0) 'jz', ");
		sb.append("IFNULL(ssrkmx.column24,'') 'zhushigg', ");
		sb.append("IFNULL(t1.column23,'') 'hh', ");
		sb.append("IFNULL(fxrh.column14,'/') 'zhushi'," +
				"IFNULL(fxrh.column19,'/') 'fushi', ");
		sb.append("IFNULL(fxrh.column16,'') 'yanse'," +
				"IFNULL(fxrh.column17,'') 'jd'," +
				"IFNULL(fxrh.column23,'') 'cg', ");
		sb.append("IFNULL(t1.column11,0) 'sj'," +
				"IFNULL(t1.column12,0) 'zkr'," +
				"IFNULL(t1.column15,0) 'ssj', ");
		sb.append("IFNULL(t1.column16,'') 'xsxbz'," +
				"IFNULL(fxrh.column20,'') 'cpbz', ");
		sb.append("IFNULL(ssrk.column8,'') 'gys',");
		sb.append("IFNULL(ssrkmx.column50,'') 'pinpai'," +
				"IFNULL(t.column12,'') 'khname', ");
		sb.append("IFNULL(t.column13,'') 'hycard'," +
				"IFNULL(t1.column17,'') 'xssx'," +
				"IFNULL(ssrkmx.column48,'') 'zdydl', ");
		sb.append("IFNULL(t1.column19,'') 'cpdl'," +
				"IFNULL(ssrkmx.column52,'') 'cs'," +
				"IFNULL(ssrkmx.column31,'') 'cb'," +
				"IFNULL(ssrkmx.column7,'') 'zsname', ");
		sb.append("IFNULL(t.column10,'') 'shy'," +
				"IFNULL(t.column11,'') 'dmjl'," +
				"IFNULL(t.column9,'') 'sybz', ");
		sb.append("IFNULL(fxrhd.column9,'') 'xsgz'," +
				"IFNULL(t.column16,'') 'bz' ");
		//首饰销售单
		sb.append("FROM dyform.DY_371337952339241 t ");
		//销售明细
		sb.append("LEFT JOIN dyform.DY_371337952339238 t1 ON t.LSH = t1.FATHERLSH ");
		sb.append("LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4 ");
		sb.append("LEFT JOIN dyform.DY_661338441749388 fxrh ON t1.column3 = fxrh.column3 ");
		sb.append("LEFT JOIN dyform.DY_661338441749389 fxrhd ON fxrhd.LSH=fxrh.FATHERLSH ");
		sb.append("LEFT JOIN dyform.DY_271334208897441 ssrkmx ON  ssrkmx.column4 = t1.column3 ");
		sb.append("LEFT JOIN dyform.DY_271334208897439 ssrk ON ssrk.LSH = ssrkmx.FATHERLSH ");
		sb.append("WHERE t.STATUSINFO='01' AND t1.STATUSINFO='01' AND fxrh.STATUSINFO = '03'");
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		    sb.append(" AND t1.column3 >= '" + repstrcompare1_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare1_END))
		    sb.append(" AND t1.column3 <= '" + repstrcompare1_END + "' ");
		if(StringUtils.isNotEmpty(repstrcompare8_START))
		    sb.append(" AND t.column3 >= '" + repstrcompare8_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare8_END))
		    sb.append(" AND t.column3 <= '" + repstrcompare8_END + "' ");
		if(StringUtils.isNotEmpty(reptimes1_START))
		    sb.append(" AND t.column4 >= '" + reptimes1_START + "' ");
		if(StringUtils.isNotEmpty(reptimes1_END))
		    sb.append(" AND t.column4 <= '" + reptimes1_END + "' ");
		if(StringUtils.isNotEmpty(repselect1))
		    sb.append(" AND t.column8 = '" + repselect1 + "' ");
		if(StringUtils.isNotEmpty(repselect2))
		    sb.append(" AND ssrk.column8 = '" + repselect2 + "' ");
		if(StringUtils.isNotEmpty(repselect4))
		    sb.append(" AND t1.column17 = '" + repselect4 + "' ");
		if(StringUtils.isNotEmpty(repselect3))
		    sb.append(" AND ssrkmx.column50 = '" + repselect3 + "' ");
		if(StringUtils.isNotEmpty(repstrdim3))
		    sb.append(" AND t.column12 like '%" + repstrdim3 + "%' ");
		if(StringUtils.isNotEmpty(repstrdim13))
		    sb.append(" AND t.column13 = '" + repstrdim13 + "' ");
		if(StringUtils.isNotEmpty(repselect5))
		    sb.append(" AND ssrkmx.column48 = '" + repselect5 + "' ");
		if(StringUtils.isNotEmpty(repselect6))
		    sb.append(" AND t1.column19 = '" + repselect6 + "' ");
		if(StringUtils.isNotEmpty(repselect7))
		    sb.append(" AND ssrkmx.column52 = '" + repselect7 + "' ");
		if(StringUtils.isNotEmpty(repstrdim5))
		    sb.append(" AND t1.column4 like '%" + repstrdim5 + "%' ");
		if(StringUtils.isNotEmpty(repselect8))
		    sb.append(" AND t.column10 = '" + repselect8 + "' ");
		if(StringUtils.isNotEmpty(repselect9))
		    sb.append(" AND t.column11 = '" + repselect9 + "' ");
		if(StringUtils.isNotEmpty(repselect10))
		    sb.append(" AND t.column9 = '" + repselect10 + "' ");
		if(StringUtils.isNotEmpty(repselect11))
		    sb.append(" AND fxrhd.column9 = '" + repselect11 + "' ");
		if(StringUtils.isNotEmpty(repstrdim6))
		    sb.append(" AND t1.column20 like '%" + repstrdim6 + "%' ");
		if(StringUtils.isNotEmpty(repstrdim7))
		    sb.append(" AND t1.column23 like '%" + repstrdim7 + "%' ");

		
		
		
		
		
		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		headerSet1.add(new TableCell("货号"));
		headerSet1.add(new TableCell("条形码"));
		headerSet1.add(new TableCell("品名"));
		headerSet1.add(new TableCell("款号"));
		headerSet1.add(new TableCell("证书号"));
		headerSet1.add(new TableCell("手寸"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("颜色"));
		headerSet1.add(new TableCell("净度"));
		headerSet1.add(new TableCell("车工"));
		headerSet1.add(new TableCell("售价"));
		headerSet1.add(new TableCell("折扣率"));
		headerSet1.add(new TableCell("实售价"));
		headerSet1.add(new TableCell("销售小备注"));
		headerSet1.add(new TableCell("产品备注"));
		if(rs)
			headerSet1.add(new TableCell("成本"));
		ReportExt reportExt = new ReportExt();
		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = (String)object.get("pm");
			String hh = (String)object.get("hh");
			String txm = (String)object.get("txm");
			String kh = (String)object.get("kh");
			String zsh = (String)object.get("zsh");
			String sc = (String)object.get("sc");
			String yanse = (String)object.get("yanse");
			String jz = object.get("jz")==null?"0":object.get("jz").toString();	
			String jd = (String)object.get("jd");
			String cg = (String)object.get("cg");
			String sj = object.get("sj")==null?"0":object.get("sj").toString();	
			String zkr = (String)object.get("zkr");
			String ssj = object.get("ssj")==null?"0":object.get("ssj").toString();
			String xsxbz = (String)object.get("xsxbz");
			String bz = (String)object.get("bz");
			String cb = object.get("cb")==null?"0":object.get("cb").toString();
			TableRow tr = new TableRow();
			
			tr.addCell(new TableCell(hh));
			tr.addCell(new TableCell(txm));
			tr.addCell(new TableCell(pm));
			tr.addCell(new TableCell(kh));
			tr.addCell(new TableCell(zsh));
			tr.addCell(new TableCell(sc));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(yanse));
			tr.addCell(new TableCell(jd));
			tr.addCell(new TableCell(cg));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zkr));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(ssj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(xsxbz));
			tr.addCell(new TableCell(bz));
			if(rs)
				tr.addCell(new TableCell("" + MathHelper.moneyFormat(cb),
						Rectangle.ALIGN_RIGHT));
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "首饰销售明细表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "首饰销售明细表" + currentTimeMillis, report,
				response);
	}
}
