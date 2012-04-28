package com.jl.common.report.parse;

import java.util.List;

import com.jl.common.report.obj.core.Read_report;
import com.jl.common.report.obj.core.table.Read_td;
import flex.messaging.io.ArrayList;

/**
 * 解析报表的XML
 * @author yj  <br>

 */
public class readhead {
	public static List readhead(Read_report reportobj)
	{
		List lst=new ArrayList();
		
		for (int i = 0; i < reportobj.getTablelist().size(); i++) {
			if (reportobj.getTablelist().get(i).getIshead()=="tou");
			{
				
				List<Read_td> hlist=reportobj.getTablelist().get(i).getTdlist();
				for (int j = 0; j < hlist.size(); j++) {
					try {
						lst.add(hlist.get(j).getTdclr().getText());
					} catch (Exception e) {
						System.out.println("无控件");
					}
					
				}
			}
		}
		
		List<Read_td> hlist=reportobj.getTablelist().get(0).getTdlist();
	    
		
		for (int i = 0; i < hlist.size(); i++) {
			try {
				lst.add(hlist.get(i).getTdclr().getText());
			} catch (Exception e) {
				System.out.println("无控件");
			}
			
		}
		
		return  lst;
	}
}
