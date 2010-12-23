//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.etl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.common.StringUtil;
import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.view.obj.ViewModel;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/** 
 * MyEclipse Struts
 * Creation date: 07-19-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class TgFilterAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		String[] tgname = null ;
		String[] tgid = null ;
		String tgids = request.getParameter("tgids");
		System.out.println("tgids===="+tgids);
		if(tgids != null && !"".equals(tgids)){
			tgid = tgids.split(",");
			String tgnames = request.getParameter("tgnames");
			String enctgnames = StringUtil.iso8859ToUTF8(tgnames);
			tgname = enctgnames.split(",");
		}
		else{
			Object obj = request.getSession().getAttribute("viewModelOri");
			if(obj != null){
				ViewModel vm = (ViewModel)obj;
				tgname = vm.getTargetname();
				tgid = vm.getTargetid() ;
			}
		}
		
		List tgnamelist=new ArrayList();
		List tgidlist=new ArrayList();
		for (int i = 0; i < tgid.length; i++) {
			if(tgid[i].indexOf("$nofilter$")<0){		
				tgnamelist.add(tgname[i]);
				tgidlist.add(tgid[i]);
			}
		}		
		request.setAttribute("tgnamelist" , tgnamelist);
		request.setAttribute("tgidlist", tgidlist);
		
		TargetFiltObj tfo = new TargetFiltObj();
		request.setAttribute("_CHOICE_TOP",tfo._CHOICE_TOP);
		request.setAttribute("_CHOICE_TOP_VALUE",tfo._CHOICE_TOP_VALUE);
		request.setAttribute("_CHOICE_ORDER",tfo._CHOICE_ORDER);
		request.setAttribute("_CHOICE_ALARM",tfo._CHOICE_ALARM);
		
		
		return mapping.findForward("selectjsp");
	}

}

