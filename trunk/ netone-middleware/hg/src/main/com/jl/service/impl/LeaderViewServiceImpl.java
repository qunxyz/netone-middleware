package com.jl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.enums.LetterCodedLabeledEnum;

import oe.frame.web.WebCache;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import com.jl.common.SpringBeanUtil;
import com.jl.dao.CommonDAO;
import com.jl.entity.leaderViewPojo;
import com.jl.service.BaseService;
import com.jl.service.LeaderViewService;

public class LeaderViewServiceImpl extends BaseService implements
		LeaderViewService {
	public CommonDAO commonDAOMirror;

	public void setcommonDAOMirror(CommonDAO commonDAOMirror) {
		this.commonDAOMirror = commonDAOMirror;
	}

	public void dataCache() {
		long start_time = System.currentTimeMillis();
		System.out.println("start count...");
		System.out.println("start_time="+start_time);
		// CommonDAO commonDAO = (CommonDAO)
		// SpringBeanUtil.getInstance().getBean("commonDAO");
		List list_naturalname = null;
		List list_did = null;
		Map map_data = new HashMap();
		WorkflowView wfview = null;
		String naturalname = null;
		// 获取naturalname
		//String sql_naturalname = "select distinct SUBSTRING_INDEX(naturalname,'.',4) naturalname from netone.ums_protectedobject where naturalname like 'BUSSWF.BUSSWF.NDYD.%'";
		String sql_naturalname="SELECT naturalname FROM (SELECT DISTINCT SUBSTRING_INDEX(naturalname,'.',5) naturalname FROM netone.ums_protectedobject WHERE naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%') v WHERE "
		+" v.naturalname NOT LIKE 'BUSSWF.BUSSWF.NDYD.%.TRACKACTION%' AND  v.naturalname NOT LIKE 'BUSSWF.BUSSWF.NDYD.%.TURNINGPOINT%' "
		+" AND v.naturalname NOT LIKE 'BUSSWF.BUSSWF.NDYD.%.ROUTE%'";
		// 获取部门id
		String sql_did = "SELECT DISTINCT"
		  +" CONCAT('',SUBSTRING_INDEX(SUBSTRING_INDEX(T3.nLevelnAME,']',3),'[',-1)) NLEVELNAME_,"
		  +"SUBSTRING(T3.nLevelCode,39,32) did" 
		+" FROM netone.t_wf_participant t1"
		+" LEFT JOIN iss.t_user t2"
		+" ON t1.usercode = t2.userCode"
		+" LEFT JOIN iss.t_department t3"
		+" ON t2.departmentId = t3.departmentId"
		+" LEFT JOIN netone.t_wf_worklist t4"
		+" ON t1.workcode = t4.WORKCODE"
		+" WHERE t1.types = '01'"
		+" AND t4.EXECUTESTATUS = '01'"
		+" AND T3.nLevel>=2" 
		+" GROUP BY NLEVELNAME_";
		//System.out.println("sql="+sql_did);
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			list_naturalname = wfview.coreSqlview(sql_naturalname);
			list_did = wfview.coreSqlview(sql_did);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//构造key
		String ndid = null;
		String naturlname = null;
		String departname= null;
		
		for (Iterator iteratorx = list_did.iterator(); iteratorx.hasNext();) {
			Map object = (Map) iteratorx.next();
			//执行all的sql
			List week_all = new ArrayList();//下拉为all时的sql
			List dearling_all = new ArrayList();//下拉为all时的sql
			List after28_all = new ArrayList();//下拉为all时的sql
			ndid = (String) object.get("did");
			String did ="[0][297236c57ea1410d841db89adbfd3f08]"+"["+ndid+"]%"; 
			Map map = new HashMap();
			String name="all"+ndid;//all 
			try {
				week_all = (List) commonDAOMirror.select("leaderView.week_all", did);
				dearling_all = (List) commonDAOMirror.select("leaderView.dearling_all", did);
				after28_all = (List) commonDAOMirror.select("leaderView.after28_all", did);
				
				//结果对象存入map
				map.put("week_all", week_all);
				map.put("dearling_all", dearling_all);
				map.put("after28_all", after28_all);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("this all process error ...");
			}
			//all时表格数据
			WebCache.setCache(name+"week",Integer.toString(week_all.size()),null);
			WebCache.setCache(name+"dearling",Integer.toString(dearling_all.size()), null);
			WebCache.setCache(name+"after28",Integer.toString(after28_all.size()),null);
			
			//明细值
			WebCache.setCache(name, map, null);// 用"all"+did做key
			//System.out.println("total--key_name="+name+"---week_all="+week_all.size()+"--dearling_all="+dearling_all.size()+"--after28_all="+after28_all.size());
			//System.out.println("value--key_name="+name+"---week_all="+week_all.toString()+"--dearling_all="+dearling_all.toString()+"--after28_all="+after28_all.toString());
		}
		
		//选定流程===============================================================
		
		for (Iterator iterator = list_did.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			ndid = (String) object.get("did");
			String did ="[0][297236c57ea1410d841db89adbfd3f08]"+"["+ndid+"]%";
			departname = (String) object.get("NLEVELNAME_");
			List week = new ArrayList();//获取week集合
			List dearling = new ArrayList();///获取dearling集合
			List after28 = new ArrayList();//获取after28集合
			String name = null;
			
//			int total_week=0;
//			int total_dearling=0;
//			int total_after28=0;
			
			for (Iterator iterator2 = list_naturalname.iterator(); iterator2
					.hasNext();) {
				Map object2 = (Map) iterator2.next();
				Map map = new HashMap();
				naturlname = object2.get("naturalname").toString();
				String str=StringUtils.substringAfterLast(naturlname, ".");
				
				if("JTZTSP".equals(str)){
					str="NDYD";
				}else if("QSLC".equals(str)){
					str="QSLCC";
				}else if("YGZWTZ1".equals(str)){
					str="YGZWTZ";
				}else if("YYDCSQLC".equals(str)){
					str="YYDCSQ";
				}else if("YYZJSQLC".equals(str)){
					str="YYZJSQ";
				}else if("ZDGZWISQ".equals(str)){
					str="GZZDWHSQ";
				}else if("MGSJCZSJ".equals(str)){
					str="MGSJKFSQ";
				}else if("VPNSP".equals(str)){
					str="VPNZFSP";
				}
				String appname=null;
				if(naturlname.indexOf("NRSP")>0){
					appname="APPFRAME.APPFRAME.CMSAPP."+str;
				}else{
					appname="APPFRAME.APPFRAME."+str;
				}
				map_data.put("naturalname1", naturlname);
				map_data.put("did", did);
				map_data.put("appname", appname);
				try {
					// System.out.println(commonDAO+"--");
					week = (List) commonDAOMirror.select("leaderView.week",
							map_data);
					dearling = (List) commonDAOMirror.select(
							"leaderView.dearling", map_data);
					after28 = (List) commonDAOMirror.select(
							"leaderView.after28", map_data);
					name = naturlname + ndid;
					
					//结果对象存入map
					map.put("week", week);
					map.put("dearling", dearling);
					map.put("after28", after28);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//
//				total_week+=week.size();
//				total_dearling+=dearling.size();
//				total_after28+=after28.size();
				
				//表格数字
				WebCache.setCache(name+"week",Integer.toString(week.size()),null);
				WebCache.setCache(name+"dearling", Integer.toString(dearling.size()), null);
				WebCache.setCache(name+"after28",Integer.toString(after28.size()),null);
				
				//明细值
				WebCache.setCache(name, map, null);// 用naturalname+did做key
				
				//System.out.println("natural="+naturlname+"---did="+did+"---naturalname="+departname);
				//System.out.println("count="+name+"---week_all="+week.size()+"--dearling_all="+dearling.size()+"--after28_all="+after28.size());
				//System.out.println("List="+week.toString()+"---"+dearling.toString()+"---"+after28.toString());
				
			}//inner for end
//			WebCache.setCache("all"+ndid+"week",Integer.toString(total_week) , null);
//			WebCache.setCache("all"+ndid+"dearling",Integer.toString(total_dearling), null);
//			WebCache.setCache("all"+ndid+"after28",Integer.toString(total_after28), null);
			
		}
		long end_time = System.currentTimeMillis();
		System.out.println("count end ...");
		System.out.println("===timer="+(end_time-start_time)/1000);
		//
	}

	
}
