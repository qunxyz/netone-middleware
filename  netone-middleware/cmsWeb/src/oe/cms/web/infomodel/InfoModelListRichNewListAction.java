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

	// ��Ѷģ���б�
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("type");

		List modellist = new ArrayList(); // ��ϢԪ�б�List
		RichLevel richLevel = (RichLevel) CmsEntry.fetchDao("richLevel");
		// ���ݲ�ѯ����,��¼λ�� �����б�
		if ("modifyNew".equals(name)) {
			modellist = richLevel.queryByNewCreate();
		} else {
			modellist = richLevel.queryByNewModify();
		}
		request.setAttribute("cmsinfomodellist", modellist);

		return mapping.findForward("richQ");
	}

}
