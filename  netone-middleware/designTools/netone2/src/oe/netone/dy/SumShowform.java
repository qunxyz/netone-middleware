package oe.netone.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.derby.tools.sysinfo;
import org.apache.poi.hssf.record.formula.functions.Mina;

import oe.bi.datasource.SumUtilIfc;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.netone.SumShow;

public class SumShowform {
	/**
	 * 视图表单 返回sql语句 字段、类型 xuwei(2012-4-25)
	 * 
	 * @param request
	 * @param response
	 */

	public String Viewform(String formid) {
		SumShowform ssf = new SumShowform();
		String[] strarr = ssf.Drivaer().split("#");
		String driver1 = strarr[0];
		String url1 = strarr[1];
		String loginname1 = strarr[2];
		String password1 = strarr[3];
		SumShow ss = new SumShow();
		return ss.Viewform(driver1, url1, loginname1, password1, formid);
	}
 
	// 视图表单slq是否正确
	public String detection(String sql) {
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
		String sqlview = StringUtils.upperCase(sql);
		SumShowform ssf = new SumShowform();
		String[] strarr = ssf.Drivaer().split("#");
		String driver1 = strarr[0];
		String url1 = strarr[1];
		String loginname1 = strarr[2];
		String password1 = strarr[3];

		String rs = "";
		List columnlist = new ArrayList();
		String columnname = null;
		String columntype = null;
		try {
			Map infox = sumutil.getColumnNameBySQL(new String[] { driver1,
					url1, loginname1, password1 }, sqlview);
			columnlist = (List) infox.get("column");
			columnname = (String) infox.get("name");
			columntype = (String) infox.get("types");
			rs = sumutil.checkColumnlist(columnlist);
		} catch (Exception e) {
			rs = e.getMessage();
		}
		if (!rs.equals("")) {
			rs="SQL视图有错误!请先修订:" + rs; 
			return rs;
		}

		return columnname+"$_$"+columntype;
	}

	// 获取到驱动
	public String Drivaer() {
		ResourceRmi resourceRmi = null;
		UmsProtectedobject upo = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			upo = resourceRmi
					.loadResourceByNatural("DATASOURCE.DATASOURCE.DYFORM");
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

		String string = upo.getExtendattribute();
		return string;
	}

}
