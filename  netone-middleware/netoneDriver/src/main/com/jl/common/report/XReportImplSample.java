package com.jl.common.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.common.report.obj.QueryColumn;
import com.jl.common.report.obj.QueryCondition;
import com.jl.common.report.obj.Report;
import com.jl.common.report.obj.XReportFace;

public class XReportImplSample implements XReportIfc {

	public XReportFace buildFace(String naturalname) {
		XReportFace x = new XReportFace();
		Map mapx = new HashMap();
		QueryColumn qc1 = new QueryColumn();
		qc1.setColumnid("col1");
		qc1.setColumnname("时间");
		qc1.setColumntype("01");
		qc1.setXoffset(1);
		qc1.setYoffset(1);
		mapx.put("col1", qc1);

		QueryColumn qc2 = new QueryColumn();
		qc2.setColumnid("col2");
		qc2.setColumnname("单据编号");
		qc2.setColumntype("04");
		qc2.setXoffset(1);
		qc2.setYoffset(2);
		mapx.put("col2", qc2);

		QueryColumn qc3 = new QueryColumn();
		qc3.setColumnid("col3");
		qc3.setColumnname("金额");
		qc3.setColumntype("05");
		qc3.setXoffset(1);
		qc3.setYoffset(3);
		mapx.put("col3", qc3);

		QueryColumn qc4 = new QueryColumn();
		qc4.setColumnid("col4");
		qc4.setColumnname("时间范围");
		qc4.setColumntype("06");
		qc4.setXoffset(2);
		qc4.setYoffset(1);
		mapx.put("col4", qc4);

		QueryColumn qc5 = new QueryColumn();
		qc5.setColumnid("col5");
		qc5.setColumnname("数值比较");
		qc5.setColumntype("11");
		qc5.setXoffset(2);
		qc5.setYoffset(2);
		mapx.put("col5", qc5);

		QueryColumn qc6 = new QueryColumn();
		qc6.setColumnid("col6");
		qc6.setColumnname("单选");
		qc6.setColumntype("12");
		qc6.setXoffset(3);
		qc6.setYoffset(1);
		mapx.put("col6", qc6);

		QueryColumn qc7 = new QueryColumn();
		qc7.setColumnid("col7");
		qc7.setColumnname("下拉");
		qc7.setColumntype("18");
		qc7.setXoffset(3);
		qc7.setYoffset(2);
		qc7.setDefaultvalue("1-男,2-女");
		mapx.put("col7", qc7);
		
		QueryColumn qc8 = new QueryColumn();
		qc8.setColumnid("col8");
		qc8.setColumnname("时间范围");
		qc8.setColumntype("06");
		qc8.setXoffset(1);
		qc8.setYoffset(7);
		mapx.put("col8", qc8);

		x.setColumns(mapx);

		Map map = new HashMap();
		Report rp1 = new Report();
		rp1.setRpId("table1");
		rp1.setRpName("报表1");

		Report rp2 = new Report();
		rp2.setRpId("table2");
		rp2.setRpName("报表2");

		Report rp3 = new Report();
		rp3.setRpId("table3");
		rp3.setRpName("报表3");

		map.put("report1", rp1);
		map.put("report2", rp2);
		map.put("report3", rp3);

		x.setReports(map);

		return x;
	}

	public Object buildReport(String naturalname, String type,QueryCondition qd,
			HttpServletResponse rs,HttpServletRequest rq) {
		// TODO Auto-generated method stub
		XReportFace xp = this.buildFace(naturalname);
		Map map1 = qd.getColumn2Value();

		return null;
	}

}
