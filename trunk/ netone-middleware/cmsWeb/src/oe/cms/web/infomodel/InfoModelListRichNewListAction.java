package oe.cms.web.infomodel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;

import oe.frame.web.page.PageInfo;
import oe.frame.web.util.WebStr;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.CmsEntry;

import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelListRichNewListAction extends Action {

	// 资讯模型列表
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("type");

		List modellist = new ArrayList(); // 信息元列表List
		RichLevel richLevel = (RichLevel) CmsEntry.fetchDao("richLevel");
		// 根据查询条件,记录位置 查找列表
		if ("modifyNew".equals(name)) {
			modellist = richLevel.queryByNewCreate();
		} else {
			modellist = richLevel.queryByNewModify();
		}
		request.setAttribute("cmsinfomodellist", modellist);

		return mapping.findForward("richQ");
	}

}
