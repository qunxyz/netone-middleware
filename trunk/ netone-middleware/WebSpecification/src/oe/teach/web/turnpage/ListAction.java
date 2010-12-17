package oe.teach.web.turnpage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.page.PageInfo;

import oe.teach.mid.buss.OecStuDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 基于标准的Servlet+JSP实现的业务数据管理应用<br>
 * 该应用是描述了一个完整企业环境下的业务应用模式,在该模式中存在以下几个重要的组成 1) 中间层DAO :
 * 代码包buss[oe.teach.web.turnpage.buss下] 2) 前台WEB控制:
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class ListAction extends Action {

	private static int _PRE_PAGE = 10;// 默认分页

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OecStuDao bussdao = (OecStuDao) OrmerEntry.fetchDAO("bussdao");
		WebForm formReal = (WebForm) form;

		String condition = "";
		if (formReal.getStuname() != null) {
			condition = " and stuname like '%" + formReal.getStuname() + "%'";
		}

		PageInfo pginfo = null;// 分页信息

		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("first") != null) {// 每次翻页交互

			pginfo = new PageInfo(request, "flist");

		} else { // 查询或者第一次页面交互
			int dataNumber = bussdao.totalNum(condition);// 总记录数
			pginfo = new PageInfo(dataNumber, _PRE_PAGE, request, "flist");// 分页信息
			pginfo.setNowpage(1);
			request.getSession().setAttribute("first", "yes");
		}
		// 根据分页的参数查询本页的相关数据
		List list = bussdao.query(condition, pginfo.getPageStartIndex(), pginfo
				.getPageStartIndex()
				+ pginfo.getPageItemCount() + 1);

		request.setAttribute("listinfo", list);

		return mapping.getInputForward();
	}

}
