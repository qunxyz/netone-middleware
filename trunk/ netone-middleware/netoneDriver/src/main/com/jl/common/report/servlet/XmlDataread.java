package com.jl.common.report.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

import org.apache.commons.lang.StringUtils;

import com.jl.common.netone.view.Dyviewxml;
import com.jl.common.report.obj.core.Read_dataset;
import com.jl.common.report.obj.core.Read_report;
import com.jl.common.report.obj.core.Read_table;
import com.jl.common.report.obj.core.RecordPools;
import com.jl.common.workflow.DbTools2;



public class XmlDataread {

	public static RecordPools readXMLSql(Read_report r_report,String condition)
	{
		RecordPools rps=new RecordPools();
		for (int i = 0; i < r_report.getDatasetlist().size(); i++) {
			String id=r_report.getDatasetlist().get(i).getId();
			String sqlstr=r_report.getDatasetlist().get(i).getSqlstr();
			rps.getDataset().put(id, datalist(sqlstr,condition));
			//System.out.println(id+":::"+sqlstr);
		}
		for (int i = 0; i < r_report.getRecordlist().size(); i++) {
			String id=r_report.getRecordlist().get(i).getId();
			String sqlstr=r_report.getRecordlist().get(i).getSqlstr();
			rps.getRecord().put(id, datalist(sqlstr,condition));
			//System.out.println(id+":::"+sqlstr);
			
			for (int j = 0; j < r_report.getRecordlist().get(i).getTdlist().size(); j++) {
				if(r_report.getRecordlist().get(i).getTdlist().get(j).getTdclr().getClass().getName()=="reportread.Read_dataset")
				{
					Read_dataset r_d=(Read_dataset)r_report.getRecordlist().get(i).getTdlist().get(j).getTdclr();
					String did=r_d.getId();
					String dsqlstr=r_d.getSqlstr();
					rps.getDataset().put(did, datalist(dsqlstr,condition));
					//System.out.println(did+":::"+dsqlstr);
				}
			}
			
		}	
	  for (Iterator iterator = r_report.getTablelist().iterator(); iterator.hasNext();) {
		 Read_table table = (Read_table) iterator.next();
		 if(StringUtils.isNotEmpty(table.getSqlstr())){
			if(!table.getSqlstr().equals("-NULL-")){
			    String id=table.getId();
			    String sqlstr=table.getSqlstr();
			    String ishead=table.getIshead();
			    if(ishead.equals("tou")){
				   rps.getTable().put(id, datalist(sqlstr,condition));
			    }
			    if(ishead.equals("wei")){
					rps.getTablewei().put(id, datalist(sqlstr,condition));
				}
			}
		 }
	  }
		return rps;
	}
	public static List datalist(String sqlstr,String condition)
	{	
		List list=new ArrayList();
		if(sqlstr!=null&&sqlstr.startsWith("DYVIEWPROC.DYVIEWPROC")){
			try{
			//是过程表单
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			String viewproc=rs.loadResourceByNatural(sqlstr).getExtendattribute();
			String script=Dyviewxml.readXML(viewproc).getScript();
			//参考规范，过程视图中的SQL语句如果需要条件会在sql中加入  $(condition)
			script=StringUtils.replace(script, "$(condition)", condition);
			
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			list=(List)console.exeScript(script);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			list=DbTools2.queryData(sqlstr+condition);
				
		}
		return list;

	}
}
