package com.jl.web.controller.shopping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfConsoleImpl;
import com.jl.entity.User;
import com.jl.service.BaseService;

public class BussTipAction extends DispatchAction 
{
	public void onSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		String workcode = request.getParameter("workcode");
		String info = request.getParameter("content");
		User user = BaseService.getOnlineUser(request);
		WebStr.encode(request, info); // зЊТы
		
		TWfConsoleIfc tc = new TWfConsoleImpl();
		tc.saveAuditNote(workcode, user.getUserCode(),info);
		
		System.out.println("workcode:"+workcode);
		System.out.println("xxx:"+info);
	}
}
