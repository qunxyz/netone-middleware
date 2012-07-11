package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 航港打印报表
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-11 上午10:38:52
 * @history
 */
public class XReportHGAction extends AbstractAction {

	// 促销
	public void report1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HGScriptReportBase r= new HGScriptReportBase();
		String lsh = request.getParameter("lsh");
		if (StringUtils.isNotEmpty(lsh)){
			r.listtable2(lsh, response);
		}
	}

	// 新品
	public void report2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HGScriptReportBase r= new HGScriptReportBase();
		String lsh = request.getParameter("lsh");
		if (StringUtils.isNotEmpty(lsh)){
			r.listtable1(lsh, response);
		}
	}

	// 新用户
	public void report3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HGScriptReportBase r= new HGScriptReportBase();
		String lsh = request.getParameter("lsh");
		if (StringUtils.isNotEmpty(lsh)){
			r.listtable3(lsh, response);
		}
	}

}
