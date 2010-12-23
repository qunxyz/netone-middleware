//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.exceltodb;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.datasource.SumUtilIfc;
import oe.bi.wizard.ExcelToDb.ExcelToDbDao;
import oe.bi.wizard.ExcelToDb.ExcelToDbDaoImpl;
import oe.frame.orm.util.IdServer;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 06-28-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ExcelToDbAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(ExcelToDbAction.class);

	private static int _PRE_PAGE = 10;// 默认分页

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

		ExcelToDbForm eo = (ExcelToDbForm) form;
		request.setAttribute("eo", eo);
		// 跳转页面
		String task = request.getParameter("task");
		// 进入First.jsp
		if ("First".equals(task)) {
			return mapping.findForward("First");
			// 进入Step.jsp
		} else if ("Step".equals(task)) {
			try {
				ExcelToDbDao etd = new ExcelToDbDaoImpl();
				URL url = new URL(eo.getFilename());
				String[] sheetname = etd.getSheetName(url);
				request.setAttribute("sheetname", sheetname);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("Step");
			// 进入Stpe2.jsp
		} else if ("Step2".equals(task)) {
			if (StringUtils.isEmpty(eo.getTablename())) {
				String tablename = "exl_" + IdServer.uuid();
				eo.setTablename(tablename);
			}
			String sheetname = eo.getSheetname();
			try {
				URL url = new URL(eo.getFilename());
				ExcelToDbDao etd = new ExcelToDbDaoImpl();
				List list = etd.getList(url, sheetname);
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Map map = (Map) iter.next();
					request.setAttribute("map", map);
					break;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("Step2");
			// 进入Done.jsp
		} else if ("Done".equals(task)) {
			String tablename = eo.getTablename();
			String sheetname = eo.getSheetname();
			String checkedkey = eo.getCheckedkey();
			String filename = eo.getFilename();
			String[] checkedkeys = StringUtils.split(checkedkey, ",");
			StringBuffer insertsql = new StringBuffer("Insert into "
					+ tablename + "(");
			StringBuffer querysql = new StringBuffer("Select * from "
					+ tablename + " where 1=1");
			eo.setSqlview(querysql.toString());
			for (int i = 0; i < checkedkeys.length; i++) {
				String name = StringUtils.substringBetween(checkedkeys[i], "[",
						"]");
				if (i == 0) {
					insertsql.append(name);
				} else {
					insertsql.append("," + name);
				}
			}
			insertsql.append(")values(");
			for (int i = 0; i < checkedkeys.length; i++) {
				if (i == 0) {
					insertsql.append("?");
				} else {
					insertsql.append(",?");
				}
			}
			insertsql.append(")");
			// 创建表
			try {
				sumutil.excelTableCreate(tablename, checkedkeys);
				sumutil.excelInAdddata(insertsql.toString(), filename,
						sheetname, checkedkeys);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 插入表数据

			return mapping.findForward("Done");
			// 完成操作
		} else if ("Finish".equals(task)) {
			// 分页信息
			PageInfo pageinfo = null;
			Map maplist = null;
			try {
				maplist = sumutil.excelInSqlExe(eo.getSqlview());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (maplist != null) {
				if (maplist.size() == 2) {
					if (PageInfo.isPageEvent(request)) {
						pageinfo = new PageInfo(request, "datalist");
					} else {
						long dataNumber = ((List) maplist.get("list")).size();// 总记录数
						pageinfo = new PageInfo((int) dataNumber, _PRE_PAGE,
								request, "datalist");// 分页信息
					}
					// 根据分页的参数查询本页的相关数据
					List listData = (List) maplist.get("list");
					List newlist = new ArrayList();
					if (listData.size() > 0) {
						newlist = ((List) maplist.get("list")).subList(pageinfo
								.getPageStartIndex(), pageinfo
								.getPageEndIndex() + 1);
					}

					request.setAttribute("list", newlist);
					request.setAttribute("columnlist", ((List) maplist
							.get("columnlist")));
				} else if (maplist.size() == 1) {
					request.setAttribute("num", ((List) maplist.get("list"))
							.get(0));
				}
			}
			return mapping.findForward("Done");
		}
		return null;
	}
}
