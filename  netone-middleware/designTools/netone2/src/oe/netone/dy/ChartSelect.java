package oe.netone.dy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;

public class ChartSelect{
	
	public static List selectBItree(String path,String type) throws Exception {
		ArrayList<ResourceInfo> aList = new ArrayList();
		ResourceInfo rInfox = new ResourceInfo();
		rInfox.setRname("树图管理");
		  if(type.equals("1")){
			  rInfox.setRname("报表管理");
	        }else{
	        rInfox.setRname("图表管理");
	        }
        if (!StringUtils.contains(path, ".")) {

			path = path + "." + path;
		}
		rInfox.setRcode(path);
        rInfox.setParentid("");
        if(path.equals("REPORTDS")){
			 rInfox.setRname("选择报表");
		 }
		aList.add(rInfox);
		List listx = SecurityEntry.iv().listDirRs(path);
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			ResourceInfo rInfo = new ResourceInfo();
			rInfo.setRname(name.getResourcename());
			rInfo.setRcode(name.getResourcecode());
			rInfo.setObjecttype(name.getTypes());
		    rInfo.setACTIVE("有效");
		    rInfo.setFID(name.getId());
			rInfo.setParentid(name.getParentid());
			aList.add(rInfo);
		}
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			String pid = ((Resource) iterator.next()).getResourcecode();
			List listy = SecurityEntry.iv().listRsInDir(pid, "", 0, 100, "");
			for (Iterator iterator1 = listy.iterator(); iterator1.hasNext();) {
				Resource name1 = (Resource) iterator1.next();
                ResourceInfo rInfo = new ResourceInfo();
				rInfo.setRname(name1.getResourcename());
				rInfo.setRcode(name1.getResourceid());
				rInfo.setObjecttype(name1.getTypes());
				rInfo.setACTIVE("有效");
				rInfo.setFID(name1.getId());
				rInfo.setParentid(pid);
			    List liste	=SecurityEntry.iv().listDirRs(name1.getResourcecode());
			    if(liste !=null)
			    {
			    for (Iterator iterator2 = liste.iterator(); iterator2.hasNext();) {
				    	Resource name2 = (Resource) iterator2.next();
		                ResourceInfo rInfo2 = new ResourceInfo();
						rInfo2.setRname(name2.getResourcename());
						rInfo2.setRcode(name2.getResourceid());
						rInfo2.setObjecttype(name2.getTypes());
						rInfo2.setACTIVE("有效");
						rInfo2.setFID(name2.getId());
						rInfo2.setParentid(pid);
						aList.add(rInfo2);
					    List liste1	=SecurityEntry.iv().listRsInDir(name2.getResourcecode(), "", 0, 100, "");
						if(liste1!=null)
						{  
						 for (Iterator iterator21 = liste1.iterator(); iterator21.hasNext();) {
							Resource name21 = (Resource) iterator21.next();
			                ResourceInfo rInfo21 = new ResourceInfo();
							rInfo21.setRname(name21.getResourcename());
							rInfo21.setRcode(name21.getResourceid());
							rInfo21.setObjecttype(name21.getTypes());
							rInfo21.setACTIVE("有效");
							if(path.equals("REPORTDS")){
								 rInfox.setRname("选择报表");
								}
							rInfo21.setFID(name21.getId());
							rInfo21.setParentid(pid);
							aList.add(rInfo21);
						}
						}
					
			    	  } 
			    	}
			
			}
		}
		for (int i = 0; i < aList.size(); i++) {
			String stateString = ((ResourceInfo) aList.get(i)).rcode;
			String statename = ((ResourceInfo) aList.get(i)).rname;
			for (int j = 1; j < aList.size(); j++)
				if (stateString.equals(((ResourceInfo) aList.get(j)).rcode)) {
					if (!statename.equals(((ResourceInfo) aList.get(j)).rname))
						aList.remove(j);
				}
		}
         for (int i = 0; i < aList.size(); i++) {
			System.out.println(((ResourceInfo) aList.get(i)).getRname()
					+ ((ResourceInfo) aList.get(i)).getRcode());
		}
		return aList;
	}

	public List selectRsInDir(String code,String type) throws Exception {

		List cname1 = new ArrayList();
		List cname0 = new ArrayList();
		List cname= new ArrayList();
		List listy = SecurityEntry.iv().listRsInDir(code, "", 0, 100, "");
		for (int j = 0; j < listy.size(); j++) {
			ResourceInfo rInfo = new ResourceInfo();
			Resource resource = (Resource) listy.get(j);

			ResourceInfo rc = getexatt(resource.getResourcecode());
			String exat = rc.extendattribute;
			if(rc.objecttype==null){
				if(rc.getInclusion()==null || rc.getInclusion().equals("0")){
				rInfo.setExtendattribute(exat);
				rInfo.setRname(resource.getResourcename());
				rInfo.setRcode(resource.getResourcecode());
				cname1.add(rInfo);
				}
			}else{
				if(rc.getInclusion()==null || rc.getInclusion().equals("0")){
			    rInfo.setExtendattribute(exat);
				rInfo.setRname(resource.getResourcename());
				rInfo.setRcode(resource.getResourcecode());
				cname0.add(rInfo);
				}
			}
			
		}
        if(type.equals("1")){
        	cname=cname1;
        }else{
        	cname=cname0;
        }
        return cname;
		
	}
	
	public ResourceInfo getexatt(String rcode) {

		ResourceInfo rsInfo = new ResourceInfo();
		String sqlString = "select extendattribute,objectType,inclusion from ums_protectedobject where NATURALNAME='"
				+ rcode + "'";

		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			rsInfo.extendattribute = (String) object.get("extendattribute");
			rsInfo.objecttype = (String) object.get("objectType");
			rsInfo.setInclusion((String) object.get("inclusion"));
		}
		
		return rsInfo;

	}
	
	
	
}