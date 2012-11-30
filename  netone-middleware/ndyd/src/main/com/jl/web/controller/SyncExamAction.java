package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jl.common.app.SpringBeanUtilExam;

/**
 * 同步考试部门及人员ACTION
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-11-30 上午09:31:47
 * @history
 */
public class SyncExamAction extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SpringBeanUtilExam.getInstance().syncDeptToExam();
		SpringBeanUtilExam.getInstance().syncUserToExam();
		response.getWriter().append("sync exam complete!");

		return null;
	}
}
