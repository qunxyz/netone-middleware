package com.jl.common.workflow;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jl.common.ComparatorDept;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.commons.collections.SequencedHashMap;

public class WfReportUtil {
	
	
	/**
	 * 领导视图明细展示(领导视图php页面会传递过来流程id)
	 * @param listkey
	 * @return
	 * @throws Exception
	 */
	public static List leaderViewDetail(String listkey)throws Exception{
		List ltdata=new ArrayList();
		String runtimelist=(String)WebCache.getCache(listkey);
		System.out.println("listkey:"+listkey+","+runtimelist);
		int count=0;
		if(StringUtils.isNotEmpty(runtimelist)){
			String []runtimearr=runtimelist.split(",");
			StringBuffer but=new StringBuffer();
			for (int i = 0; i < runtimearr.length; i++) {
				if(StringUtils.isNotEmpty(runtimearr[i])){
					but.append(",'"+runtimearr[i]+"'");
					count++;
				}
			}
			if(but.length()>0){
				String sql="SELECT t1.lsh lsh,t1.appname appname,t1.d0 formtitle,t2.runtimeid runid,t2.workcode workcode,t2.ACTIVITYID actid,t2.starttime starttime,t3.username username FROM netone.t_wf_relevantvar_tmp t1, netone.t_wf_worklist t2,netone.t_wf_participant t3 WHERE t3.types='01' and t1.runtimeid=t2.runtimeid and t2.workcode=t3.workcode and t1.runtimeid in("
					+but.substring(1)+")  ORDER BY t2.runtimeid,t2.STARTTIME DESC";
				//System.out.println("sql:"+sql);
				List list=DbTools.queryData(sql);
				ResourceRmi rs=null;
				try {
					rs = (ResourceRmi)RmiEntry.iv("resource");
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String runtimefilter="";
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map object = (Map) iterator.next();
					String runtimeid =(String)object.get("runid");
					if(runtimeid.equals(runtimefilter)){
						continue;
					}
					runtimefilter=runtimeid;
					String appname=(String)object.get("appname");
					String actid=(String)object.get("actid");
					
					try{
						UmsProtectedobject upo2=rs.loadResourceByNatural(appname);
						object.put("appnameext", upo2.getName());
						
						UmsProtectedobject upo=rs.loadResourceByNatural(appname+"."+actid.toUpperCase());
						if(upo!=null){
							object.put("actname", upo.getName());
						}else{
							object.put("actname", "未知节点");
						}
						
						
					}catch(Exception e){
						e.printStackTrace();
						object.put("actname", "未知节点");
					}
					ltdata.add(object);

				}
			}
		}
		return ltdata;
	}
	
	public static void leaderViewCache(String rootname,String rootflow){
		try {
			long time=System.currentTimeMillis();
			System.out.println("start leader view cache:");
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			String flowRootId=rs.loadResourceByNatural(rootflow).getId();
			List subX=rs.subResource(flowRootId);
			System.out.println("all_process is:"+subX.size());
			
			//处理所有流程
			List dataa=leaderViewCacheCore(rootname,"all",rs);
			WebCache.setCache(rootname+"all", dataa, null);
			
			Map mapa=leaderViewTotalCache("all");
			WebCache.setCache("lv_proc"+"all", mapa, null);
			
			int count=0;
			for (Iterator iterator = subX.iterator(); iterator.hasNext();) {
				UmsProtectedobject  upo = (UmsProtectedobject ) iterator.next();
				if("1".equals(upo.getInclusion())){
					continue;
				}
				count++;
				List data=leaderViewCacheCore(rootname,upo.getNaturalname(),rs);
				WebCache.setCache(rootname+upo.getNaturalname(), data, null);
				System.out.println("done "+count);
				
				Map map=leaderViewTotalCache(upo.getNaturalname());
				WebCache.setCache("lv_proc"+upo.getNaturalname(), map, null);
			}

			
			System.out.println("time use:"+(System.currentTimeMillis()-time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static List leaderViewCacheCore(String rootname,String processid,ResourceRmi rs)throws Exception{
		
		List listdata=new ArrayList();
		
			String rsid=rs.loadResourceByNatural(rootname).getId();
			UmsProtectedobject upo=new UmsProtectedobject();
			upo.setParentdir(rsid);
			System.out.println("dept total:");
			List dept=rs.queryObjectProtectedObj(upo, null, 0, 1000, " ORDER BY CONVERT(reference,SIGNED) desc");
			System.out.println(dept.size());
			//ComparatorDept comparator=new ComparatorDept();
			//Collections.sort(dept, comparator);
			Map dept_people=new HashMap();
		long time=System.currentTimeMillis();
		for (Iterator iterator = dept.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			String naturalname=object.getNaturalname();
			if(!dept_people.containsKey(naturalname)){
				String people_sql="SELECT usercode FROM netone.t_cs_user WHERE systemid IN(SELECT id FROM netone.ums_protectedobject"+ 
						" WHERE naturalname LIKE '"+naturalname+"%' )";
				System.out.println("people in dept:");
				List listx=DbTools.queryData(people_sql);
				System.out.println(listx.size());
				StringBuffer butuser=new StringBuffer();
				for (Iterator iterator2 = listx.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					butuser.append(",'"+object2.get("usercode")+"'");
				}
				if(butuser.length()>0){
					dept_people.put(naturalname, butuser.substring(1));
				}else{
					dept_people.put(naturalname, "''");
				}
			}
			Map map=new SequencedHashMap();
			//本周内
			String sql_this_week="SELECT DISTINCT runtimeid runid FROM netone.t_wf_worklist WHERE $processid$  EXECUTESTATUS='02' AND workcode IN("+
				"SELECT workcode FROM netone.t_wf_participant WHERE types='01' and statusnow='02' and "+
				" createtime<= DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) -7 DAY)"+  
				" AND createtime>= DATE_SUB(CURDATE(),INTERVAL WEEKDAY(CURDATE()) DAY) and  usercode IN("+dept_people.get(naturalname)+"))";
			if("all".equals(processid)){
				sql_this_week=StringUtils.replace(sql_this_week, "$processid$","");
			}else{
				sql_this_week=StringUtils.replace(sql_this_week, "$processid$","processid='"+processid+"' and ");
			}

			List this_week=DbTools.queryData(sql_this_week);
			
			StringBuffer this_week_but=new StringBuffer();
			for (Iterator iterator2 = this_week.iterator(); iterator2
					.hasNext();) {
				Map object2 = (Map) iterator2.next();
				String record=(String)object2.get("runid");
				this_week_but.append(","+record);
			}
			String key="leader_0"+naturalname+"@"+processid;
			WebCache.setCache(key, this_week_but.toString(), null);
			String leader_0=key+"#"+this_week.size();
	
			//处理中
			String sql_doing="SELECT DISTINCT runtimeid runid FROM netone.t_wf_worklist WHERE $processid$ EXECUTESTATUS='01' and  workcode IN("+
				"SELECT workcode FROM netone.t_wf_participant WHERE types='01' and  statusnow='01' and usercode IN("+dept_people.get(naturalname)+"))";
			if("all".equals(processid)){
				sql_doing=StringUtils.replace(sql_doing, "$processid$","");
				
			}else{
				sql_doing=StringUtils.replace(sql_doing, "$processid$","processid='"+processid+"' and ");
			}

			List this_doingArr=DbTools.queryData(sql_doing);
			
			StringBuffer this_doingbut=new StringBuffer();
			for (Iterator iterator2 = this_doingArr.iterator(); iterator2
					.hasNext();) {
				Map object2 = (Map) iterator2.next();
				String record=(String)object2.get("runid");
				this_doingbut.append(","+record);
			}
			String key1="leader_1"+naturalname+"@"+processid;
			WebCache.setCache(key1, this_doingbut.toString(), null);
			String leader_1=key1+"#"+this_doingArr.size();
			//超48小时
			String sql_over48="SELECT DISTINCT runtimeid runid FROM netone.t_wf_worklist WHERE $processid$ EXECUTESTATUS='01' and  workcode IN("+
				"SELECT workcode FROM netone.t_wf_participant WHERE types='01' and  statusnow='01'  AND (NOW()-DATE_SUB(createtime, INTERVAL -48 HOUR))>0 and usercode IN("+dept_people.get(naturalname)+
				"))";	
			if("all".equals(processid)){
				sql_over48=StringUtils.replace(sql_over48, "$processid$","");
				
			}else{
				sql_over48=StringUtils.replace(sql_over48, "$processid$","processid='"+processid+"' and ");
			}

			List this_over48Arr=DbTools.queryData(sql_over48);
			
			StringBuffer this_over48But=new StringBuffer();
			for (Iterator iterator2 = this_over48Arr.iterator(); iterator2
					.hasNext();) {
				Map object2 = (Map) iterator2.next();
				String record=(String)object2.get("runid");
				this_over48But.append(","+record);
			}
			String key2="leader_2"+naturalname+"@"+processid;
			WebCache.setCache(key2, this_over48But.toString(), null);
			String leader_2=key2+"#"+this_over48Arr.size();
			map.put("deptname", object.getName());
			map.put("this_week", leader_0);
			map.put("this_doing", leader_1);
			map.put("over_48hour", leader_2);
			listdata.add(map);
		}
		System.out.println("deptname-all-done "+(System.currentTimeMillis()-time));
		return listdata;

	}
	
	public static Map leaderViewTotalCache(String processid){
		String condition="1=1";
		if(!"all".equals(processid)&&StringUtils.isNotEmpty(processid)){
			condition=" processid='"+processid+"'";
		}else{
			condition=" processid in (SELECT  naturalname FROM netone.ums_protectedobject WHERE PARENTDIR IN(SELECT id "+
                   "FROM netone.ums_protectedobject "+
                   " WHERE NATURALNAME = 'BUSSWF.BUSSWF.NDYD') and active='1')";
		}
		String sql_doing="select count(*) cou from netone.t_wf_runtime where statusnow='01' and "+condition;
		String sql_all="select count(*) cou from netone.t_wf_runtime where statusnow in('01','02') and "+condition;
		List doing=DbTools.queryData(sql_doing);
		Long doing_count=(Long)((Map)doing.get(0)).get("cou");
		
		List all=DbTools.queryData(sql_all);
		Long all_count=(Long)((Map)all.get(0)).get("cou");
		
		Map data=new HashMap();
		data.put("doing", doing_count);
		data.put("all", all_count);
		
		return data;
		

	}

}
