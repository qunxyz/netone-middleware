//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocell;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.dao.infocell.InfoCellDao;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.frame.web.util.WebStr;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoCellListAction extends Action {

	// 资讯元列表
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Security ser = new Security(request);

		RequestUtil.setParamMapToRequest(request);

		List celllist = new ArrayList(); // 信息元列表List
		RequestUtil.setParamMapToRequest(request);
		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");// Dao对象
		String searchSql = "";// 查询名称的值
		PageInfo pginfo = null; // 分页信息


		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("datasearchName") != null) {
			// 翻页

			pginfo = new PageInfo(request, "CELL");
			// 从session获取查询条件
			searchSql = (String) request.getSession().getAttribute(
					"datasearchName");

			celllist = infocellDao.searchCellList(searchSql, ser
					.getUserLoginName(), pginfo.getPageStartIndex(), pginfo
					.getPageItemCount());

		} else {// 查询
			String searchName = request.getParameter("searchName");
			searchName = WebStr.encode(request, searchName);
			String intime = request.getParameter("intime");

			String belongto = request.getParameter("group");
			searchSql = this.getSearchSql(searchName, belongto, intime);

			int dataNumber = infocellDao.searchCellNum(searchSql, ser
					.getUserLoginName());// 总记录数

			pginfo = new PageInfo(dataNumber, 7, request, "CELL");// 分页信息
			// 保存查询条件到Session中
			request.getSession().setAttribute("datasearchName", searchSql);
			// 根据查询条件,记录位置 查找列表
			celllist = infocellDao.searchCellList(searchSql, ser
					.getUserLoginName(), pginfo.getPageStartIndex(), pginfo
					.getPageItemCount());
		}
		// UserDao.(celllist);
		request.setAttribute("cmsInfocelllist", celllist);
		return mapping.getInputForward();
	}

	/**
	 * 获取查询条件
	 * 
	 * @param paramMap
	 * @return
	 */
	private String getSearchSql(String searchName, String belongto,
			String intime) {

		StringBuffer sb = new StringBuffer();
		if (searchName != null && !searchName.equals(""))
			sb.append(" and cellname like '%" + searchName + "%' ");
		if (belongto != null && !belongto.equals(""))
			sb.append(" and belongto='" + belongto + "' ");
		if ("on".equals(intime))
			sb.append(" and intime='" + CellInfo._IN_TIME + "'");
		else
			sb.append(" and ( intime!='" + CellInfo._IN_TIME
					+ "' or intime is null )");
		return sb.toString();
	}

}
