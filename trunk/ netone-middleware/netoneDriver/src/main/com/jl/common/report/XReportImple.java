package com.jl.common.report;

import java.awt.image.RasterOp;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.report.obj.QueryCondition;
import com.jl.common.report.obj.ReportQueryRef;
import com.jl.common.report.obj.XReportFace;
import com.jl.common.report.servlet.ReportBuilder;
import com.jl.common.workflow.worklist.QueryColumn;

public class XReportImple implements XReportIfc {

	public XReportFace buildFace(String naturalname) {
		// TODO Auto-generated method stub
		ResourceRmi resourceRmi = null;
		XReportFace xrf = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
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

		UmsProtectedobject upo = null;
		try {
			upo = resourceRmi.loadResourceByNatural(naturalname);
			XmlResolutionReportForms xrrf = new XmlResolutionReportForms();
			xrf = xrrf.XmlResolution(upo.getExtendattribute());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xrf;
	}

	public Object buildReport(String types, String naturalname,
			QueryCondition qd, HttpServletResponse rs) {
		XReportImple xr // TODO Auto-generated method stub
		= new XReportImple();
		XReportFace xrf = xr.buildFace(naturalname);

		// 这是实现QueryColumn 这个对象
		Map<String, com.jl.common.report.obj.QueryColumn> mapqc = xrf
				.getColumns();

		// 这是实现ReportQueryRef 这个对象
		Map<String, List<ReportQueryRef>> maprqrf = xrf.getRq();

		QueryColumn qc = null;

		Map<String, String> mapqd = qd.getColumn2Value();
		Map<String, String> map2Ope = qd.getColumn2Ope();
		String selectReportId = qd.getSelectReportId();
		StringBuffer condition = new StringBuffer();

		for (Iterator rqrf = maprqrf.keySet().iterator(); rqrf.hasNext();) {
			Object rqrfkey = rqrf.next();
			List valuerqrf = maprqrf.get(rqrfkey);
			for (int i = 0; i < valuerqrf.size(); i++) {

				ReportQueryRef rqf = (ReportQueryRef) valuerqrf.get(i);
				String col1 = rqf.getRefColumnid();
				String col2 = rqf.getQuerycolumnid();

				todo(condition, mapqd, map2Ope, mapqc, col2, col1);

				// condition = StringUtils.replace(condition, "[" + col2 + "]",
				// col1);
			}
		}

		try {
			// 掉用下一个方法
			System.out.println("condition:" + condition);
			ReportBuilder.generateReport(types, selectReportId, " where 1=1 "
					+ condition, rs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private void todo(StringBuffer condition, Map<String, String> mapqd,
			Map<String, String> map2Ope,
			Map<String, com.jl.common.report.obj.QueryColumn> mapqc,
			String key, String realColumn) {
		String value = (String) mapqd.get(key);
		if (StringUtils.isEmpty(value)) {
			return;
		}
		String valuesign = map2Ope.get(key);

		String columntype = ((com.jl.common.report.obj.QueryColumn) mapqc
				.get(key)).getColumntype();

		boolean fal = ((com.jl.common.report.obj.QueryColumn) mapqc.get(key))
				.isAnd();
		String AndOr = " or ";
		if (fal)
			AndOr = " And ";
		String tmp = "";
		//字符串范围sql
		if (StringUtils.equals((String) columntype, "21")) {
			String[] shuzhi = StringUtils.split((String) value, "$_$");
			if(shuzhi.length==2){
			tmp = AndOr + " ( " + realColumn + "  between  '" + shuzhi[0] + "'  and  '"
				 + shuzhi[1] + "' ) ";
		   }
		} else if (StringUtils.equals((String) columntype, "22")) { //模糊查询sql
			tmp = AndOr + " ( " + realColumn + " like '%" + value.trim() + "%'"
					+ " ) ";
		} else if (StringUtils.equals((String) columntype, "09")) {
			String[] shuzhi = StringUtils.split((String) value, "$_$");
			if(shuzhi.length==2){
			tmp = AndOr + " ( " + realColumn + ">=" + shuzhi[0] + " and " + ""
					+ realColumn + "<=" + shuzhi[1] + ") ";
			}
		} else if (StringUtils.equals((String) columntype, "07")) {
			String[] shuzhi = StringUtils.split((String) value, "$_$");
			if(shuzhi.length==2){
			tmp = AndOr + " ( " + realColumn + ">= '" + shuzhi[0] + "' and "
					+ "" + realColumn + "<='" + shuzhi[1] + "') ";
			}
		} else if ("05".equals(columntype) || "11".equals(columntype)) {// 数值类型
			if (StringUtils.isEmpty(valuesign)) {
				tmp = AndOr + " " + realColumn + "= " + value.trim() + " ";

			} else {
				tmp = AndOr + " " + realColumn + " " + valuesign + value.trim();
			}
		} else {
			if (StringUtils.isEmpty(valuesign)) {
				tmp = AndOr + " " + realColumn + "= '" + value.trim() + "'  ";
			} else {
				tmp = AndOr + " " + realColumn + "" + valuesign + "'"
						+ value.trim() + "'  ";
			}
		}

		condition.append(tmp);
	}

}
