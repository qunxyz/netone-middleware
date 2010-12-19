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
public class InfoModelListRichQueryAction extends Action {

	// 资讯模型列表
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		name = WebStr.encode(request, name);
		TCmsInfomodel tim = new TCmsInfomodel();
		response.setHeader("Content-type", "text/html; charset=GBK");

		List modellist = new ArrayList(); // 信息元列表List

		PageInfo pginfo = null;// 分页信息

		RichLevel richLevel = (RichLevel) CmsEntry.fetchDao("richLevel");

		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("name") != null) {
			// 翻页

			pginfo = new PageInfo(request, "MODEL");

			// // 从session获取查询条件
			name = (String) request.getSession().getAttribute("name");

		} else {// 查询

			int dataNumber = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(tim, null,
							"and modelname like '%" + name + "%'");

			pginfo = new PageInfo(dataNumber, request, "MODEL");// 分页信息
			// // 保存查询条件到Session 中
			request.getSession().setAttribute("name", name);
		}
		// 根据查询条件,记录位置 查找列表
		modellist = richLevel.queryByName(name);
		if (modellist == null || modellist.size() == 0) {
			return mapping.findForward("richQ");
		}
		List listFinal = richLevel.queryLimit(modellist, pginfo
				.getPageStartIndex(), pginfo.getPageItemCount());
		request.setAttribute("cmsinfomodellist", listFinal);

		return mapping.findForward("richQ");
	}

}
