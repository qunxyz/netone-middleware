//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocell;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.infocell.InfoCellDao;
import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoCellEditAction extends Action {

	// 编辑资讯元
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		Security ser = new Security(request);
		request.setAttribute("nologin", "2");
		InfoCellForm form = (InfoCellForm) actionform;

		String editFlag = request.getParameter("editFlag");// 编辑标志位

		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");

		if ("update".equals(editFlag)) {// 更新资讯元
			try {
				TCmsInfocell cmsinfocell = new TCmsInfocell();
				cmsinfocell.setCellid(form.getCellid());
				cmsinfocell.setCellname(form.getCellname());
				cmsinfocell.setBelongto(form.getBelongto());
				if ("on".equals(form.getIntime()))
					cmsinfocell.setIntime(CellInfo._IN_TIME);
				else
					cmsinfocell.setIntime(form.getIntime());
				// 加入安全
				cmsinfocell.setParticipant(ser.getUserLoginName());

				boolean done = infocellDao.update(cmsinfocell, form.getBody());

				if (done) {
					request.setAttribute("done", "成功");
				} else {
					request.setAttribute("done", "失败，您没有该操作权限");
				}

				request.setAttribute("updateFlag", "true");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// // 刷新布局界面上修改J++脚本
			// try {
			// response.getWriter().print(
			// "<script>self.opener.refreshdiv('" + form.getCellid()
			// + "');window.close();</script>");
			// return null;
			//
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		} else { // 显示

			// 根据id设置咨讯元的初始值
			String id = request.getParameter("id");
			TCmsInfocell cmsinfocell = infocellDao.view(id);

			form.setCellid(String.valueOf(cmsinfocell.getCellid()));
			form.setCellname(cmsinfocell.getCellname());
			form.setBelongto(cmsinfocell.getBelongto());
			if (CellInfo._IN_TIME.equals(cmsinfocell.getIntime()))
				form.setIntime("on");

			form.setBody(cmsinfocell.getBody());
			request.setAttribute("done", "");

			EnvService env = null;
			try {
				env = (EnvService) RmiEntry.iv("envinfo");
				String url = env.fetchEnvValue("$jppview*")
						+ cmsinfocell.getCellid();
				String name = cmsinfocell.getCellname();
				String natrualname = cmsinfocell.getNaturalname();
				String tree = env.fetchEnvValue("$resourceadd*") + "&appnaturalname=JPP&name="
						+ name + "&naturalname=" + natrualname + "&url=" + url;
				request.setAttribute("addtotree", tree);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return mapping.findForward("celledit");

	}
}
