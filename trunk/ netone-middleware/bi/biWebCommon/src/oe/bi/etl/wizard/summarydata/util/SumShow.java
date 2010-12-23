package oe.bi.etl.wizard.summarydata.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.datasource.SumUtilIfc;
import oe.bi.datasource.SumUtilImpl;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

public class SumShow {
	/**
	 * AJAXœ‘ æsql”Ôæ‰
	 * 
	 * @param request
	 * @param response
	 */
	public static void main(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		SumUtilIfc sumutil=null;
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
		String driver = StringUtils.trim(request.getParameter("driver"));
		String url = StringUtils.trim(request.getParameter("url"));
		String loginname = StringUtils.trim(request.getParameter("loginname"));
		String password = StringUtils.trim(request.getParameter("password"));
		String tablename = StringUtils.trim(request.getParameter("tablename"));

		List list = null;
		try {
			list = sumutil.getColumnNameByTableName(new String[] { driver, url,
					loginname, password }, tablename);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer(
				"<select name='columns' onclick='changecol();'>");
		StringBuffer sb2 = new StringBuffer("select ");
		int i = 0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map map = (LinkedHashMap) iterator.next();
			String columnName = map.get("name").toString();
			if (i != list.size() - 1) {
				sb2 = sb2.append(columnName + " " + columnName + ",");
			} else {
				sb2 = sb2.append(columnName + " " + columnName + " ");
			}
			i++;
			String attributeType = map.get("type").toString();
			sb = sb.append("<option value=" + columnName + ">");
			sb = sb.append("◊÷∂Œ√˚:" + columnName + " ◊÷∂Œ¿‡–Õ:" + attributeType);
			sb = sb.append("</option>");
		}
		sb2 = sb2.append("from " + tablename + " where 1=1");
		sb = sb.append("</select>");
		try {
			PrintWriter out = response.getWriter();
			out.write(sb.toString() + "$" + sb2.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
