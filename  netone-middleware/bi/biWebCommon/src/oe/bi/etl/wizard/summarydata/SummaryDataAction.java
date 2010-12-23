//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.summarydata;

/**
 * 流程创建
 */
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.datasource.SumUtilIfc;
import oe.bi.etl.wizard.summarydata.util.SumFinish;
import oe.bi.etl.wizard.summarydata.util.SumShow;
import oe.bi.etl.wizard.summarydata.util.SumStep;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SummaryDataAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(SummaryDataAction.class);

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
		SummaryDataForm so = (SummaryDataForm) form;
		request.setAttribute("so", so);
		// 跳转页面
		String task = request.getParameter("task");

		// 进入First.jsp
		if ("First".equals(task)) {
			return mapping.findForward("First");
			// 进入Step.jsp
		} else if ("Step".equals(task)) {
			SumStep.main(so, request);
			return mapping.findForward("Step");
			// 进入Stpe2.jsp
		} else if ("Step2".equals(task)) {
			SumUtilIfc sumutil = null;
			try {
				sumutil = (SumUtilIfc) RmiEntry.iv("biData");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sqlview = StringUtils.upperCase(so.getSqlview());
			so.setSqlview(sqlview);

			String rs = "";
			List columnlist = new ArrayList();
			String columnname=null;
			String columntype=null;
			try {
				Map infox = sumutil.getColumnNameBySQL(new String[] {
						so.getDriver(), so.getUrl(), so.getLoginname(),
						so.getPassword() }, sqlview);
				columnlist = (List) infox.get("column");
				columnname=(String)infox.get("name");
				columntype=(String)infox.get("types");
				rs = sumutil.checkColumnlist(columnlist);
			} catch (Exception e) {
				rs = e.getMessage();
			}

			if (!rs.equals("")) {
				WebTip.htmlInfoOri("<script>alert(\"SQL视图有错误!请先修订:" + rs
						+ "\");history.go(-1);</script>", response);
				return null;
			}
			request.setAttribute("list", columnlist);
			request.setAttribute("cname", StringUtils.substringBeforeLast(columnname, ","));
			request.setAttribute("ctype", StringUtils.substringBeforeLast(columntype, ","));
			return mapping.findForward("Step2");
			// 进入Done.jsp
		} else if ("Done".equals(task)) {
			return mapping.findForward("Done");
			// 完成
		} else if ("Finish".equals(task)) {
			SumFinish.main(so, request);
			return mapping.findForward("Done");
			// AJAX显示sql语句
		} else if ("showsql".equals(task)) {
			SumShow.main(request, response);
		}
		return null;
	}
}
