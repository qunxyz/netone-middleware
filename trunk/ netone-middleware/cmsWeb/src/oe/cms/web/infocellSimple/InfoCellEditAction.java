//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocellSimple;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.web.infocell.InfoCellForm;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoCellEditAction extends Action {

	// �༭��ѶԪ
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		Security ser = new Security(request);
		request.setAttribute("nologin", "2");
		InfoCellForm form = (InfoCellForm) actionform;

		String editFlag = request.getParameter("editFlag");// �༭��־λ

		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");

		String quick = request.getParameter("quick");


		if ("update".equals(editFlag)) {// ������ѶԪ
			boolean todo = false;
			try {
				TCmsInfocell cmsinfocell = new TCmsInfocell();
				cmsinfocell.setCellid(form.getCellid());
				cmsinfocell.setCellname(form.getCellname());
				cmsinfocell.setBelongto(form.getBelongto());
				if ("on".equals(form.getIntime())){
					cmsinfocell.setIntime(CellInfo._IN_TIME);
				}
				

				// ���밲ȫ
				cmsinfocell.setParticipant(ser.getUserLoginName());

				todo = infocellDao.update(cmsinfocell, form.getBody());
				if (!todo) {
					try {
						response
								.getWriter()
								.print(
										"<script>alert('�Բ���,��û�в���Ȩ��');history.go(-1);</script>");
						return null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// ˢ�²��ֽ������޸�J++�ű�
			if ("ok".equals(quick)) {

				try {
					response.getWriter().print(
							"<script>self.opener.refreshdiv('"
									+ form.getCellid()
									+ "');window.close();</script>");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				return null;
			} else {

				return mapping.findForward("celleditSimple");
			}

		} else { // ��ʾ

			// ����id������ѶԪ�ĳ�ʼֵ
			String id = request.getParameter("id");
			TCmsInfocell cmsinfocell = infocellDao.view(id);

			form.setCellid(String.valueOf(cmsinfocell.getCellid()));
			form.setCellname(cmsinfocell.getCellname());
			form.setBelongto(cmsinfocell.getBelongto());
			form.setIntime(cmsinfocell.getIntime());
			if (CellInfo._IN_TIME.equals(form.getIntime())){
				form.setIntime("on");
			}

			form.setBody(cmsinfocell.getBody());
			if ("ok".equals(quick)) {
				return mapping.findForward("celleditSimpleQuick");
			} else {
				return mapping.findForward("celleditSimple");
			}

		}

	}
}
