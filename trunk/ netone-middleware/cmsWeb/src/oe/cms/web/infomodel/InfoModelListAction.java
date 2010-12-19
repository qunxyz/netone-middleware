//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.BuildCmsJpp;
import oe.cms.BuildStaticPage;
import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.blog.LayoutX;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;
import oe.cms.runtime.XHtmlCachepool;
import oe.cms.runtime.XHtmlHistoryCachePool;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.frame.web.util.WebTip;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelListAction extends Action {

	// 资讯模型列表
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String init = (String) request.getParameter("init");
		response.setHeader("Content-type", "text/html; charset=GBK");
		Security ser = new Security(request);

		if ("all".equals(init)) {

			XHtmlCachepool.removeInfoCacheAll();
			BuildCmsJpp.todo(request);

			try {
				response.getWriter().print(
						"<script>alert('缓存初始化成功！');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("hist".equals(init)) {

			XHtmlHistoryCachePool.serialHistory();
			try {
				response
						.getWriter()
						.print(
								"<script>alert('本日系统信息已经保存！');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("static".equals(init)) {

			BuildStaticPage.initWeb();
			try {
				response
						.getWriter()
						.print(
								"<script>alert('所有静态页面已经保存！');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("order".equals(init)) {

			ModelDao modeldao = (ModelDao) CmsEntry.fetchDao("modelDao");
			modeldao.serialRich(RichLevel._LEVEL_A);
			modeldao.initview(RichLevel._LEVEL_A);
			modeldao.serialRich(RichLevel._LEVEL_B);
			modeldao.initview(RichLevel._LEVEL_B);
			modeldao.serialRich(RichLevel._LEVEL_C);
			modeldao.initview(RichLevel._LEVEL_C);
			try {
				response.getWriter().print(
						"<script>alert('完成排名！');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("model".equals(init)) {

			LayoutX layoutX = (LayoutX) CmsEntry.fetchDao("layoutX");
			layoutX.initTemplate();
			try {
				response.getWriter().print(
						"<script>alert('完成初始化！');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		RequestParamMap paramMap = new RequestParamMap(request);
		RequestUtil.setParamMapToRequest(request, paramMap);

		List modellist = new ArrayList(); // 信息元列表List
		String flag = paramMap.getParameter("flag"); // 标志位
		PageInfo pginfo = null;// 分页信息
		String searchName = "";// 查询名称的值
		if ("delete".equals(flag)) { // 删除信息元
			ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
			String id = request.getParameter("id"); // 信息元ID
			try {

				boolean done = modelDao.delete(id, ser.getUserLoginName());
				if (done) {
					request.setAttribute("done", "成功");
				} else {
					request.setAttribute("done", "失败，您没有该操作权限");
				}
			} catch (Exception e) {

			}
			// // 从session获取查询条件
			// searchName = (String) request.getSession().getAttribute(
			// "modelsearchName");
			//
			// int dataNumber = searchModelNum(searchName);// 总记录数
			//
			// pginfo = new PageInfo(dataNumber, request, "MODEL");// 分页信息
			// // 根据查询条件,记录位置 查找列表
			// modellist = searchModelList(searchName,
			// pginfo.getPageStartIndex(),
			// pginfo.getPageItemCount());
			WebTip.htmlInfo("成功", true, response);
			return null;
		} else {
			request.setAttribute("done", "");
			if (PageInfo.isPageEvent(request)
					&& request.getSession().getAttribute("modelsearchName") != null) {
				// 翻页

				pginfo = new PageInfo(request, "MODEL");

				// 从session获取查询条件
				searchName = (String) request.getSession().getAttribute(
						"modelsearchName");
				// 根据查询条件,记录位置 查找列表
				modellist = searchModelList(searchName, pginfo
						.getPageStartIndex(), pginfo.getPageItemCount());

			} else {// 查询
				// 查询名称的值
				searchName = paramMap.getParameter("searchName") == null ? ""
						: paramMap.getParameter("searchName");

				int dataNumber = searchModelNum(searchName);// 总记录数

				pginfo = new PageInfo(dataNumber, request, "MODEL");// 分页信息
				// 保存查询条件到Session 中
				request.getSession()
						.setAttribute("modelsearchName", searchName);
				// 根据查询条件,记录位置 查找列表
				modellist = searchModelList(searchName, pginfo
						.getPageStartIndex(), pginfo.getPageItemCount());
			}
		}

		// UserDao.userViewRichx(modellist);
		request.setAttribute("cmsinfomodellist", modellist);

		return mapping.getInputForward();
	}

	/**
	 * 根据查询的信息元名称查询 取得 信息元列表
	 * 
	 * @param name
	 *            //查询的信息元名称
	 * @return
	 */
	private List searchModelList(String name, int from, int to) {
		List list = new ArrayList();
		if (name == null || name.trim().equals("")) {
			list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					new TCmsInfomodel(), null, from, to);
		} else {
			list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					new TCmsInfomodel(), null, from, to,
					" and modelname like '%" + name + "%' ");
		}
		return list;

	}

	/**
	 * 根据查询的信息元名称查询 取得 信息元列表的总数
	 * 
	 * @param name
	 *            //查询的信息元名称
	 * @return
	 */
	private int searchModelNum(String name) {
		int i = 0;
		if (name == null || name.trim().equals("")) {
			i = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(new TCmsInfomodel(), null);
		} else {
			i = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(new TCmsInfomodel(), null,
							" and modelname like '%" + name + "%' ");
		}
		return i;

	}

}
