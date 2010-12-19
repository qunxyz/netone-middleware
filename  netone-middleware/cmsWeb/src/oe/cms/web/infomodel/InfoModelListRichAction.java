package oe.cms.web.infomodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.CmsEntry;

import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelListRichAction extends Action {

	// 资讯模型列表
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String levelId = request.getParameter("levelId");
		if (levelId == null || levelId.equals("")) {
			levelId = RichLevel._LEVEL_C;
		}

		response.setHeader("Content-type", "text/html; charset=GBK");
		//
		// RequestParamMap paramMap = new RequestParamMap(request);
		// RequestUtil.setParamMapToRequest(request, paramMap);

		List modellist = new ArrayList(); // 信息元列表List

		PageInfo pginfo = null;// 分页信息
		String searchName = "";// 查询名称的值

		String nopageeach = request.getParameter("nopageeach");

		request.setAttribute("done", "");
		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("modelsearchName") != null) {
			// 翻页

			pginfo = new PageInfo(request, "MODEL");

			// // 从session获取查询条件
			// searchName = (String) request.getSession().getAttribute(
			// "modelsearchName");
			// 根据查询条件,记录位置 查找列表
			modellist = searchModelList(levelId, pginfo.getPageStartIndex(),
					pginfo.getPageStartIndex() + pginfo.getPageItemCount());

		} else {// 查询

			int dataNumber = searchModelNum(levelId);// 总记录数

			pginfo = new PageInfo(dataNumber, request, "MODEL");// 分页信息
			// // 保存查询条件到Session 中
			// request.getSession().setAttribute("modelsearchName", searchName);
			// 根据查询条件,记录位置 查找列表
			if (nopageeach != null) {
				modellist = searchModelList(levelId, 0, Integer
						.parseInt(nopageeach));
			} else {
				modellist = searchModelList(levelId,
						pginfo.getPageStartIndex(), pginfo.getPageItemCount());
			}
		}

		request.setAttribute("cmsinfomodellist", modellist);

		if (RichLevel._LEVEL_A.equals(levelId)) {
			return mapping.findForward("richA");
		} else if (RichLevel._LEVEL_B.equals(levelId)) {
			return mapping.findForward("richB");
		}
		return mapping.findForward("richC");
	}

	/**
	 * 根据查询的信息元名称查询 取得 信息元列表
	 * 
	 * @param name
	 *            //查询的信息元名称
	 * @return
	 */
	private List searchModelList(String levelid, int from, int to) {

		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		return dao.richview(levelid, from, to);
	}

	/**
	 * 根据查询的信息元名称查询 取得 信息元列表的总数
	 * 
	 * @param name
	 *            //查询的信息元名称
	 * @return
	 */
	private int searchModelNum(String levelid) {
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		return dao.totalRichview(levelid);
	}

}
