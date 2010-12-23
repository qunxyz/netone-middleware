package oe.bi.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.wizard.summarydata.SummaryDataForm;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.frame.web.util.WebTip;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;



public class CopyBussFormSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CopyBussFormSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		SummaryDataForm so = new SummaryDataForm();
		ResourceRmi rsrmi = null;
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String naturalname = request.getParameter("naturalname");
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(naturalname);
			so.setFormname(upo.getName());
			so.setFormnaturalname(upo.getNaturalname());
			DyFormDesignService dfd = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			DyObj objx = dfd.fromDy(upo.getExtendattribute());
			TCsForm tcform = objx.getFrom();
			so.setLevel(tcform.getDimlevel());
			so.setSelpoint(tcform.getTimelevel());
			so.setSqlview(tcform.getSqlinfo());
			so.setDatasource(tcform.getSystemid());
			List columnlist = objx.getColumn();
			StringBuffer sbchinesenames = new StringBuffer();
			StringBuffer sbcnames = new StringBuffer();
			StringBuffer sbctypes = new StringBuffer();
			for (Iterator iter = columnlist.iterator(); iter.hasNext();) {
				TCsColumn column = (TCsColumn) iter.next();
				if ("BELONGX".equalsIgnoreCase(column.getColumnid())) {
					so.setDimdata(column.getValuelist());
				}
				if (column.isUseable()) {
					String chinesename = column.getColumname();
					String htmltype = column.getHtmltype();
					String othername = column.getColumnid();
					sbchinesenames.append(chinesename + ",");
					sbcnames.append(othername + ",");
					sbctypes.append(htmltype + ",");
				}
			}
			so.setChinesename(StringUtils.substringBeforeLast(sbchinesenames
					.toString(), ","));
			so.setCname(StringUtils.substringBeforeLast(sbcnames.toString(),
					","));
			so.setCtype(StringUtils.substringBeforeLast(sbctypes.toString(),
					","));

			TCsForm tcsform = new TCsForm();
			String formname = so.getFormname();
			tcsform.setFormname(formname);
			tcsform.setSystemid(so.getDatasource());
			tcsform.setCreated((new Date(System.currentTimeMillis()))
					.toString());

			tcsform.setSqlinfo(so.getSqlview());
			tcsform.setDimlevel(so.getLevel());
			tcsform.setTimelevel(so.getSelpoint());
			tcsform.setTypeinfo("BUSSENV.BUSSENV.DYSER.SQLFORM");
			String id = request.getParameter("id");
			String pagepath = rsrmi.loadResourceById(id).getNaturalname();
			String[] tablename = dfd.create(tcsform, pagepath);
			tcsform.setDescription(tablename[0]);
			tcsform.setFormcode(tablename[1]);

			String[] cnames = StringUtils.split(so.getCname(), ",");
			String[] ctypes = StringUtils.split(so.getCtype(), ",");
			String[] chinesenames = StringUtils.split(so.getChinesename(), ",");
			String belongxname = "";
			String createdname = "";
			for (int i = 0; i < chinesenames.length; i++) {
				TCsColumn column = new TCsColumn();
				column.setColumnid(cnames[i]);
				column.setColumname(chinesenames[i]);
				column.setFormcode(tcsform.getFormcode());
				column.setHtmltype(ctypes[i]);

				if ("BELONGX".equals(cnames[i])) {
					belongxname = column.getColumname();
				} else if ("TIMEX".equals(cnames[i])) {
					createdname = column.getColumname();
				} else {
					column.setViewtype(ColumnExtendInfo._TYPE_NORMAL);
					dfd.addColumn(column);
				}
			}

			// 修改字段
			DyObj dyobj = dfd.fromDy(tablename[1]);
			List list = dyobj.getColumn();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();
				if ("BELONGX".equalsIgnoreCase(element.getColumnid())) {
					element.setValuelist(so.getDimdata());
					element.setColumname(belongxname);
					dfd.updateColumn(element);
				} else if ("TIMEX".equalsIgnoreCase(element.getColumnid())) {
					element.setColumname(createdname);
					dfd.updateColumn(element);
				}
			}
			String formcode = dyobj.getFrom().getFormcode();
			WebTip.htmlInfo("复制成功[formcode:" + formcode + "]", true, true,
					response);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
