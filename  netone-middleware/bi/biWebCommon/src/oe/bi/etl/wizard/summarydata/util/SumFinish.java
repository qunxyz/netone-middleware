package oe.bi.etl.wizard.summarydata.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.wizard.summarydata.SummaryDataForm;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;


public class SumFinish {
	/**
	 * 执行完成操作
	 * 
	 * @param so
	 * @param request
	 */
	public static void main(SummaryDataForm so, HttpServletRequest request) {
		try {
			DyFormDesignService dfd = (DyFormDesignService) RmiEntry
					.iv("dydesign");
			TCsForm tcsform = new TCsForm();
			String formname = so.getFormname();
			tcsform.setFormname(formname);
			tcsform.setSystemid(so.getDatasource());
			tcsform.setCreated((new Date(System.currentTimeMillis()))
					.toString());
			String pagepath = request.getParameter("pagepath");
			tcsform.setSqlinfo(so.getSqlview());
			tcsform.setDimlevel(so.getLevel());
			tcsform.setTimelevel(so.getSelpoint());
			tcsform.setTypeinfo("BUSSENV.BUSSENV.DYSER.SQLFORM");
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
				String type = ctypes[i];
				if ("DECIMAL".equals(ctypes[i]) || "BIGINT".equals(ctypes[i])
						|| "DOUBLE".equals(ctypes[i])
						|| "FLOAT".equals(ctypes[i])) {
					type = "DECIMAL";
				}
				column.setHtmltype(type);

				if ("BELONGX".equals(cnames[i])) {
					belongxname = column.getColumname();
				} else if ("TIMEX".equals(cnames[i])) {
					createdname = column.getColumname();
				} else {
					column.setViewtype(ColumnExtendInfo._TYPE_NORMAL);
					dfd.addColumn(column);
					// list.add(column);
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
					// list.add(column);
				} else if ("TIMEX".equalsIgnoreCase(element.getColumnid())) {
					element.setColumname(createdname);
					dfd.updateColumn(element);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		request.setAttribute("success", "success");
	}
}
