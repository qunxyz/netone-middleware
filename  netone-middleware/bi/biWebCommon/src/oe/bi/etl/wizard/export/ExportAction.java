//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.export;

/**
 * µ¼³ö
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.datasource.SumUtilIfc;
import oe.bi.wizard.ExcelToDb.XmlObjListAndMDD;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.bus.res.doc.common.XmlObj;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.midware.doc.excel.ExcelHandlerImp;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.MultiDimData;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * MyEclipse Struts Creation date: 06-28-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class ExportAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(ExportAction.class);

	// --------------------------------------------------------- Methods
	public static final String _COLUMNX = ",LSH,FORMCODE,PARTICIPANT,FATHERLSH,STATUSINFO,EXTENDATTRIBUTE,HIT,";

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
		String task = request.getParameter("task");

		if ("show".equals(task)) {

			ResourceRmi rsrmi = null;

			try {

				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				String chkid = request.getParameter("chkid");
				UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
				DyFormDesignService dfd = (DyFormDesignService) RmiEntry
						.iv("dydesign");
				DyObj objx = dfd.fromDy(upo.getExtendattribute());
				TCsForm tcform = objx.getFrom();
				String tablename = tcform.getDescription();
				List columnlist = objx.getColumn();
				StringBuffer sql = new StringBuffer("Select ");
				StringBuffer names = new StringBuffer();
				StringBuffer othernames = new StringBuffer();
				for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
					TCsColumn column = (TCsColumn) iter.next();
					if (column.isUseable()) {
						String othername = column.getColumncode();
						String name = column.getColumnid();
						sql.append(name + " " + othername + ",");
						names.append(name + ",");
						othernames.append(othername + ",");
					}
				}
				String sqlview = sql.toString();
				String name = StringUtils.substringBeforeLast(names.toString(),
						",");
				String othername = StringUtils.substringBeforeLast(othernames
						.toString(), ",");
				sqlview = StringUtils.substringBeforeLast(sqlview, ",")
						+ " from " + tablename + " where 1=1";
				request.setAttribute("sqlview", sqlview);
				request.setAttribute("name", name);
				request.setAttribute("othername", othername);
			} catch (NotBoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return mapping.findForward("Exportexcel");
		} else if ("DoExport".equals(task)) {
			SumUtilIfc sumutil = null;
			try {
				sumutil = sumutil = (SumUtilIfc) RmiEntry.iv("biData");
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
			response.setContentType("text/html; charset=GBK");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ IdServer.uuid() + ".xls");
			String sqlview = request.getParameter("sqlview");
			String name = request.getParameter("name");
			String othername = request.getParameter("othername");

			MultiDimData view = new MultiDimData();
			Map<String, String> map1 = new LinkedHashMap<String, String>();
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			String[] names = StringUtils.split(name, ",");
			String[] othernames = StringUtils.split(othername, ",");
			if (othernames != null && othernames.length > 0) {
				for (int i = 0; i < othernames.length; i++) {
					map1.put(names[i], names[i] + "[" + othernames[i] + "]");
					map2.put(names[i], "string");
				}
			}
			view.setDataColumnName(map1);
			view.setDataColumnType(map2);
			List list = null;
			try {
				list = sumutil.exportDyData(sqlview, map1);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			view.setDatavalue(list);
			List xmlobjlist = XmlObjListAndMDD.toXmlObjList(view);
			for (Iterator iter = xmlobjlist.iterator(); iter.hasNext();) {
				XmlObj element = (XmlObj) iter.next();
				element.setWidth((short) 0x2800);
			}
			List value = view.getDatavalue();
			ExcelHandler excelHandler = new ExcelHandlerImp();
			try {
				excelHandler.writeExcel(value, xmlobjlist, null, "bi", response
						.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("Exportexcel");
		}
		return null;
	}

}
