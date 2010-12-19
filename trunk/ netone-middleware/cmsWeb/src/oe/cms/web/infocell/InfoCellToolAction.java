package oe.cms.web.infocell;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.dao.file.FileUtil;
import oe.cms.dao.infocell.InfoCellDao;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoCellToolAction extends Action {

	// 新增资讯元
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		Security ser = new Security(request);
		InfoCellForm form = (InfoCellForm) actionform;

		String newFlag = request.getParameter("newFlag");

		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");// Dao对象

		if ("changeCellGroup".equals(newFlag)) { // 下拉选择资讯元源事件

			List cellToolList = infocellDao.getCellToolList(
					form.getCellgroup(), ser.getUserLoginName());

			request.getSession().setAttribute("cellToolList", cellToolList);

		} else if ("changeFi".equals(newFlag)) {
			String fiId = form.getFiId();
			List fiColumnList = FileUtil.getFIColumnidList(fiId);
			request.getSession().setAttribute("fiColumnList", fiColumnList);
		} else {// 初始化资讯元
			// 资讯组列表
			

//			// 资讯元列表
//			List cellToolList = infocellDao.getCellToolList(groputId, ser
//					.getUserLoginName());
//			request.getSession().setAttribute("groupList", groupList);
//			request.getSession().setAttribute("cellToolList", cellToolList);

			// 文件数据源列表
			List fiList = FileUtil.getFiIdList();
			List fiColumnList = FileUtil.getFIColumnidList("");
			request.getSession().setAttribute("fiList", fiList);
			request.getSession().setAttribute("fiColumnList", fiColumnList);

			// 业务列表

//			//List formlist = BIUtil.getFormList("oec");
//			List wflist = BIUtil.getWfList("oec");
//			//request.getSession().setAttribute("formlist", formlist);
//			request.getSession().setAttribute("wflist", wflist);
//
//			List selectall = BIUtil.getList();
//			request.getSession().setAttribute("selectall", selectall);

		}

		return mapping.getInputForward();
	}

}
