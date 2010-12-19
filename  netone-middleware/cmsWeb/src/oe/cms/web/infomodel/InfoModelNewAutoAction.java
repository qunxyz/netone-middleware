//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.datasource.XMLParser;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelNewAutoAction extends Action {

	// 新增资讯模型
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {

		Security ser = new Security(request);

		String lsh = request.getParameter("lsh");
		ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
		if (!modelDao.checkExist(lsh)) {

			try {
				TCmsInfomodel cmsinfomodel = new TCmsInfomodel();

				cmsinfomodel.setAccessmode("");
				cmsinfomodel.setDescription("");
				cmsinfomodel.setExtendattribute("100.0%");
				cmsinfomodel.setInfoxml(XMLParser._XML_HEAD
						+ XMLParser._DEFAULT_BODY);
				cmsinfomodel.setModelname(lsh);
				cmsinfomodel.setUserid("");
				cmsinfomodel.setParticipant(ser.getUserLoginName());
				cmsinfomodel.setModelid(new Long(lsh));
				String parentdir=request.getParameter("path");
				modelDao.create(cmsinfomodel,parentdir);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		request.setAttribute("modelid", lsh);
		return mapping.getInputForward();

	}
}
