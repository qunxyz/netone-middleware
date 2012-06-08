package com.jl.common.report.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jl.common.report.obj.core.Getdatalist;
import com.jl.common.report.obj.core.Read_columns;
import com.jl.common.report.obj.core.Read_record;
import com.jl.common.report.obj.core.Read_report;
import com.jl.common.report.obj.core.Read_table;
import com.jl.common.report.obj.core.RecordPools;

public class Readdata {
	public static Getdatalist getreportdata(Read_report reportobj,
			RecordPools Pools) throws Exception {
		Getdatalist dat = new Getdatalist();
		List<Read_record> arr = reportobj.getRecordlist();
		List<String> columslist = new ArrayList<String>();
		Map datamap = new HashMap();
		for (int i = 0; i < arr.size(); i++) {
			for (int j = 0; j < arr.get(i).getTdlist().size(); j++) {
				if (arr.get(i).getTdlist().get(j).getTdclr().getClass()
						.getName().equals(Read_columns.class.getName())) {
					Read_columns r_c = (Read_columns) arr.get(i).getTdlist()
							.get(j).getTdclr();
					columslist.add(r_c.getColumns().toLowerCase());
					List datas = (List) Pools.getRecord().get(
							arr.get(i).getId());

					List newdata = new ArrayList();

					for (int k = 0; k < datas.size(); k++) {
						newdata.add(((Map) datas.get(k)).get(r_c.getColumns().toLowerCase()));
					}
					datamap.put(r_c.getColumns().toLowerCase(), newdata);
				}
			}
			dat.setColumnslist(columslist);
			dat.setRecorddatalist(datamap);

		}
		return dat;
	}
	public static Getdatalist gettabledata(Read_report reportobj,
			RecordPools Pools) throws Exception {
		Getdatalist dat = new Getdatalist();
		List<Read_table> arr = reportobj.getTablelist();
		List<String> columslist = new ArrayList<String>();
		Map datamap = new HashMap();
		String ssa = "tou";
		for (int i = 0; i < arr.size(); i++) {
			if (ssa.equals(arr.get(i).getIshead())) {
			for (int j = 0; j < arr.get(i).getTdlist().size(); j++) {
				if (arr.get(i).getTdlist().get(j).getTdclr().getClass()
						.getName().equals(Read_columns.class.getName())) {
					Read_columns r_c = (Read_columns) arr.get(i).getTdlist()
							.get(j).getTdclr();
					columslist.add(r_c.getId().toLowerCase());
					List datas = (List) Pools.getTable().get(
							arr.get(i).getId().toLowerCase());

					List newdata = new ArrayList();
                     if(datas!=null){
						for (int k = 0; k < datas.size(); k++) {
							newdata.add(((Map) datas.get(k)).get(r_c.getColumns().toLowerCase()));
						}
                     }
					datamap.put(r_c.getId().toLowerCase(), newdata);
				}
			}
			dat.setColumnslist(columslist);
			dat.setRecorddatalist(datamap);
		    }
		}
		return dat;
	}
	
	public static Getdatalist gettablewiedata(Read_report reportobj,
			RecordPools Pools) throws Exception {
		Getdatalist dat = new Getdatalist();
		List<Read_table> arr = reportobj.getTablelist();
		List<String> columslist = new ArrayList<String>();
		Map datamap = new HashMap();
		String ssa = "wei";
		for (int i = 0; i < arr.size(); i++) {
			
			if (ssa.equals(arr.get(i).getIshead())) {
			for (int j = 0; j < arr.get(i).getTdlist().size(); j++) {
				if (arr.get(i).getTdlist().get(j).getTdclr().getClass()
						.getName().equals(Read_columns.class.getName())) {
					Read_columns r_c = (Read_columns) arr.get(i).getTdlist()
							.get(j).getTdclr();
					columslist.add(r_c.getId().toLowerCase());
					List datas = (List) Pools.getTablewei().get(
							arr.get(i).getId());

					List newdata = new ArrayList();
					 if(datas!=null){
						for (int k = 0; k < datas.size(); k++) {
							newdata.add(((Map) datas.get(k)).get(r_c.getColumns().toLowerCase()));
						}
					  }
					datamap.put(r_c.getId().toLowerCase(), newdata);
				}
			}
			dat.setColumnslist(columslist);
			dat.setRecorddatalist(datamap);
	     	}
		}
		return dat;
	}
}
