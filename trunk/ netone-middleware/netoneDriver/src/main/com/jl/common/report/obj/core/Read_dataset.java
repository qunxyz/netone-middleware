package com.jl.common.report.obj.core;

import java.util.ArrayList;
import java.util.List;

import com.jl.common.workflow.DbTools2;



public class Read_dataset  extends Read_clr{

	private String sqlstr;

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
		
	}


	public static List datalist(String sqlstr)
	{
		try{
		return DbTools2.queryData(sqlstr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList();

	}
	
}
