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
 * JPP模板DIY
 */
public class InfoCellModelDIYAction extends Action {

	// 编辑资讯元
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {

		InfoCellModelDIYForm formx = (InfoCellModelDIYForm) actionform;
		String opeType = request.getParameter("opeType");
		JppMiddwareDIY jppMiddwareDIY = (JppMiddwareDIY) CmsEntry
				.fetchDao("jppMiddwareDIY");
		
		Security ser = new Security(request);

		if ("dele".equals(opeType)) {
			jppMiddwareDIY.delete(formx.getJppmid());
			WebTip.htmlInfo("删除成功", true, response);
			return null;
		}
		boolean ismodify = false;
		if ("view".equals(opeType)) {// 显示操作(增加/修改)
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
				formx.setJppmidname("未命名脚本模板");
				formx.setAccesstype(CellInfo._TYPE_PUBLIC);
				formx.setJppmidscript("<!--脚本模板正文-->");
				formx
						.setJppmidscriptapi("<!--脚本模板API说明:.... [参数1:] [参数2:]....-->\n\r$:func{<!--参数描述-->&+}");
			}
		} else {// 持久化操作
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

			// 统一处理好 func 的真实名字
			String naturalnameReal = pathname + "."
					+ formx.getNaturalname().toUpperCase();
			String apiScript = formx.getJppmidscriptapi();
			String apiScriptFunc = StringUtils.substringBetween(apiScript,
					"$:", "{");
			apiScript = StringUtils.replaceOnce(apiScript, "$:" + apiScriptFunc
					+ "{", "$:" + naturalnameReal + "{");
			jmw.setJppmidscriptapi(apiScript);
			if (formx.getJppmid() == null || "".equals(formx.getJppmid())) {// 创建
				jppMiddwareDIY.create(jmw, pathname);
			} else {// 修改
				jppMiddwareDIY.modify(jmw);

			}
			request.setAttribute("done", "ok");
			request.setAttribute("name", "自定义:" + jmw.getJppmidname());
			request.setAttribute("id", jmw.getJppmid());
			

		}
		request.setAttribute("ismodify",ismodify);
		return mapping.findForward("view");

	}
}
