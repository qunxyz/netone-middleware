//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocell;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.dao.infocell.InfoCellDao;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 11-01-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class InfoCellDeleteAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		Security ser = new Security(request);
		String id = request.getParameter("id"); // 信息元ID
		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");

		boolean done = infocellDao.delete(id, ser.getUserLoginName());
		response.setContentType("text/html; charset=GBK");
		if (done) {
			try {
				response
						.getWriter()
						.print(
								"<script>alert('成功');window.close();window.opener.location.reload();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {

				response
						.getWriter()
						.print(
								"<script>alert('失败，可能是系统操作或者您没有该操作权限');window.close();window.opener.location.reload();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

}
