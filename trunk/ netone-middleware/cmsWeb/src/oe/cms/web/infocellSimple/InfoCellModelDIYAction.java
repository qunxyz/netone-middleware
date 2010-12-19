//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocellSimple;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsJppmidware;
import oe.cms.dao.infocell.JppMiddwareDIY;
import oe.frame.web.util.WebTip;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * JPPģ��DIY
 */
public class InfoCellModelDIYAction extends Action {

	// �༭��ѶԪ
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {

		InfoCellModelDIYForm formx = (InfoCellModelDIYForm) actionform;
		String opeType = request.getParameter("opeType");
		JppMiddwareDIY jppMiddwareDIY = (JppMiddwareDIY) CmsEntry
				.fetchDao("jppMiddwareDIY");
		
		Security ser = new Security(request);

		if ("dele".equals(opeType)) {
			jppMiddwareDIY.delete(formx.getJppmid());
			WebTip.htmlInfo("ɾ���ɹ�", true, response);
			return null;
		}
		boolean ismodify = false;
		if ("view".equals(opeType)) {// ��ʾ����(����/�޸�)
			if (formx.getJppmid() != null && !"".equals(formx.getJppmid())) {
				ismodify = true;
				TCmsJppmidware jmwxs = jppMiddwareDIY.load(formx.getJppmid());
				try {
					BeanUtils.copyProperties(formx, jmwxs);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// formx.setAccesstype(jmwxs.getAccesstype());
				// formx.setJppmidname(jmwxs.getJppmidname());
				// formx.setJppmidscript(jmwxs.getJppmidscript());
				// formx.setJppmidscriptapi(jmwxs.getJppmidscriptapi());
			} else {
				formx.setJppmidname("δ�����ű�ģ��");
				formx.setAccesstype(CellInfo._TYPE_PUBLIC);
				formx.setJppmidscript("<!--�ű�ģ������-->");
				formx
						.setJppmidscriptapi("<!--�ű�ģ��API˵��:.... [����1:] [����2:]....-->\n\r$:func{<!--��������-->&+}");
			}
		} else {// �־û�����
			String pathname = request.getParameter("pathname");

			TCmsJppmidware jmw = new TCmsJppmidware();
			jmw.setAccesstype(formx.getAccesstype());
			jmw.setJppmidname(formx.getJppmidname());
			jmw.setJppmidscript(formx.getJppmidscript());
			jmw.setJppmidscriptapi(formx.getJppmidscriptapi());
			jmw.setJppmid(formx.getJppmid());
			jmw.setParticipant(ser.getUserLoginName());
			jmw.setNaturalname(formx.getNaturalname());
			response.setContentType("text/html; charset=GBK");

			// ͳһ����� func ����ʵ����
			String naturalnameReal = pathname + "."
					+ formx.getNaturalname().toUpperCase();
			String apiScript = formx.getJppmidscriptapi();
			String apiScriptFunc = StringUtils.substringBetween(apiScript,
					"$:", "{");
			apiScript = StringUtils.replaceOnce(apiScript, "$:" + apiScriptFunc
					+ "{", "$:" + naturalnameReal + "{");
			jmw.setJppmidscriptapi(apiScript);
			if (formx.getJppmid() == null || "".equals(formx.getJppmid())) {// ����
				jppMiddwareDIY.create(jmw, pathname);
			} else {// �޸�
				jppMiddwareDIY.modify(jmw);

			}
			request.setAttribute("done", "ok");
			request.setAttribute("name", "�Զ���:" + jmw.getJppmidname());
			request.setAttribute("id", jmw.getJppmid());
			

		}
		request.setAttribute("ismodify",ismodify);
		return mapping.findForward("view");

	}
}
