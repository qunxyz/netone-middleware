package oe.security4a.web.department2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DepartmentRightAction extends Action {

	private static int _PRE_PAGE = 10;// 默认分页，显示10条数据

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			String id = request.getParameter("id");
			UmsProtectedobject upo = rmi.loadResourceById(id);
			request.setAttribute("upo", upo);

			UmsRole ur = new UmsRole();
			ur.setBelongingness(upo.getNaturalname());

			PageInfo pageinfo = null;// 分页信息
			if (PageInfo.isPageEvent(request)) {
				pageinfo = new PageInfo(request, "rolelist");
			} else {
				long dataNumber = rmi.queryObjectsNumberRole(ur, null);// 总记录数
				pageinfo = new PageInfo((int) dataNumber, _PRE_PAGE, request,
						"rolelist");// 分页信息
			}
			// 根据分页的参数查询本页的相关数据
			List list = rmi.queryObjectsRole(ur, null, pageinfo
					.getPageStartIndex(), pageinfo.getPageEndIndex() + 1-pageinfo
					.getPageStartIndex());
			List<UmsRole> newlist = new ArrayList<UmsRole>();
			if (list != null) {
				for (Iterator iteror = list.iterator(); iteror.hasNext();) {
					UmsRole tmpur = (UmsRole) iteror.next();
//					UmsProtectedobject up = rmi.loadResourceByNatural(tmpur
//							.getBelongingness());
					tmpur.setBelongingness(upo.getName());
					newlist.add(tmpur);
				}
			}
			request.setAttribute("listinfo", newlist);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("departmentright");
	}
}