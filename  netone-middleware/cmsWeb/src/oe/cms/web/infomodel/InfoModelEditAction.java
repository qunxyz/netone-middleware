//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.blog.LayoutX;
import oe.cms.dao.infomodel.ModelDao;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelEditAction extends Action {

	// �༭��Ѷģ��
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		InfoModelForm form = (InfoModelForm) actionform;

		String tomodel = request.getParameter("tomodel");
		if ("ok".equals(tomodel)) {
			String modelid = request.getParameter("id");
			LayoutX layoutX = (LayoutX) CmsEntry.fetchDao("layoutX");
			TCmsInfomodel inf = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(modelid));
			layoutX.copyToTemplate(inf);
			try {
				response.setContentType("text/html; charset=GBK");
				response.getWriter().print(
						"<script>alert('�����ɹ�');window.close();</script>");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		Security ser = new Security(request);

		String editFlag = request.getParameter("editFlag");// �༭��־λ

		String seeFlag = request.getParameter("seeFlag"); // �Ƿ���"�鿴"��־λ
		request.setAttribute("seeFlag", seeFlag);

		ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
		if ("update".equals(editFlag)) { // ����
			try {
				TCmsInfomodel cmsinfomodel = new TCmsInfomodel();
				try {
					BeanUtils.copyProperties(cmsinfomodel, form);
					cmsinfomodel.setModelname(WebStr.encode(request, cmsinfomodel
							.getModelname()));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cmsinfomodel.setAccessmode("1");
				cmsinfomodel.setUserid(WebStr.encode(request,form.getUserid()));
				cmsinfomodel.setParticipant(ser.getUserLoginName());

				boolean todo = modelDao.update(cmsinfomodel);
				if (todo) {
					WebTip
							.htmlInfoOri(
									"<script>alert('�޸ĳɹ�');opener.location.reload();window.close()</script>",
									response);
				} else {
					WebTip.htmlInfo("ʧ��", true, response);
				}
				return null;
			} catch (Exception e) {
				request.setAttribute("updateFlag", "false");
				e.printStackTrace();
			}

		} else { // ��ʼ��

			// ��ʼ��ģ�ʹ�ȡģʽ
			// String[][] accessmode=InfomodelExtend._ACCESS;//ģ�ʹ�ȡģʽ
			// List accessmodeList=new ArrayList();
			// for(int i=0;i<accessmode.length;i++){
			// LabelValueBean lv=new
			// LabelValueBean(accessmode[i][1],accessmode[i][0]);
			// accessmodeList.add(lv);
			// }
			// request.setAttribute("accessmodeList",accessmodeList);

			String id = request.getParameter("id");

			TCmsInfomodel cmsinfomodel = modelDao.view(id);
			try {
				BeanUtils.copyProperties(form, cmsinfomodel);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("done", "");

		}

		return mapping.getInputForward();
	}
}
