package com.jl.scheduler.job;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jl.common.JxlUtilsTemplate;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfConsoleImpl;
import com.jl.common.workflow.WfEntry;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.seucore.obj.Person;

public class ContractMgr {
	/**
	 * 合同管理定时处理类
	 */
	//启动流程 分配人员
	public static String runProcess(String nexthandler,String EXTENDATTRIBUTE,String lsh){
		
		//启动流程
			String processid="BUSSWF.BUSSWF.NDYD.HTTX";
			String mode="APPFRAME.APPFRAME.HTTX";
			String ddurl = "frame.do?method=onEditViewMain&naturalname="
				+ mode + "&lsh=";
			String runtimeid = null;
			String opemode="01";
			List listx = new ArrayList();
			try {
				runtimeid =WfEntry.iv().newProcess(processid, EXTENDATTRIBUTE, mode, "",
						lsh, ddurl);
				WfEntry.iv().runProcess(runtimeid);
				
				listx = WfEntry.iv().useCoreView().fetchRunningWorklist(runtimeid);
				TWfWorklist TWfWorklistx = (TWfWorklist) listx.get(0);

				String workcode = TWfWorklistx.getWorkcode();
		        WfEntry.iv().nextByAuto(workcode, EXTENDATTRIBUTE);
		        
		        listx = WfEntry.iv().useCoreView().fetchRunningWorklist(runtimeid);
		        //分配下一个节点人员
		        for (Iterator iteratorX = listx.iterator(); iteratorX.hasNext();) {
					TWfWorklist worklist = (TWfWorklist) iteratorX.next();
			            TWfActive actinfo = WfEntry.iv().loadRuntimeActive(
							worklist.getProcessid(), worklist.getActivityid(),
							mode, "", worklist.getRuntimeid());
			        String[] persons = {nexthandler};
					for (int i = 0; i < persons.length; i++) {
						WfEntry.iv().specifyParticipantByWorkcode(EXTENDATTRIBUTE,
								worklist.getWorkcode(), persons[i],
								actinfo.isNeedsync(), opemode);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return runtimeid;
	}
	public static void todo() {

		// 读取合同管理表单[合同结束时间]字段
		String sql = "select LSH,column7,flag,nexthandler,PARTICIPANT,mark,EXTENDATTRIBUTE from dyform.dy_111321859523995";
		List listIsReader = new ArrayList();
		WorkflowView wfview=null;
		try {
			 wfview = (WorkflowView) RmiEntry.iv("wfview");
			listIsReader = wfview.coreSqlview(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Iterator iterator = listIsReader.iterator(); iterator.hasNext();) {
			Map obj = (Map) iterator.next();
			String lsh = (String) obj.get("LSH");
			String column7 = (String) obj.get("column7");//合同结束时间
			String flag=(String) obj.get("flag");//标志位0表示是excel导入
			String nexthandler=(String) obj.get("nexthandler");//下一个处理人
			//String PARTICIPANT=(String) obj.get("PARTICIPANT");
			String mark=(String) obj.get("mark");//判断是否已经扫描创建 1表示已经创建
			String EXTENDATTRIBUTE=(String)obj.get("EXTENDATTRIBUTE");//allName格式xxx[xxx]
			
			// 时间分割处理
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 获取当前时间
			String currentdate = sdf.format(new Date());
			String str1[] = currentdate.split("-");
			String str2[] = column7.split("-");
			String currdate = str1[0] + str1[1];
			String plan_endtime = str2[0] + str2[1];
			String currdate_year = str1[0];
			String plan_endtime_year = str2[0];
			String current_month=str1[1];
			String plan_endtime_year_month=str2[1];
			// 获取lsh
			String sql1 = "select runtimeid from netone.t_wf_relevantvar_tmp where lsh='"
					+ lsh + "'";
			// 判断合同结束时间是否大于7个月
			if (currdate_year.equals(plan_endtime_year)) {// 对比年份
				if (Integer.parseInt(plan_endtime) - Integer.parseInt(currdate) <= 7) {
					try {
						 wfview = (WorkflowView) RmiEntry
								.iv("wfview");
						listIsReader = wfview.coreSqlview(sql1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// 获取runtimeid
					String runtimeid = null;
					for (Iterator iterator2 = listIsReader.iterator(); iterator2
							.hasNext();) {
						Map obj1 = (Map) iterator2.next();
						runtimeid = (String) obj1.get("runtimeid");
					}
					TWfConsoleIfc tc = new TWfConsoleImpl();
					try {
						
						//如果flag=0表示用excel，当工单要到期时先启动流程实例
						if("0".equals(flag)){
							runtimeid=runProcess(nexthandler,EXTENDATTRIBUTE,lsh);
						}
						if(!"1".equals(mark)){//如果不等于1则唤醒流程并设置mark为1表示已经执行
						 tc.WakeUpProcess(runtimeid);// 唤醒流程
						 String change_mark="update dyform.dy_111321859523995 set mark='1' where lsh='"+lsh+"'";
						 listIsReader = wfview.coreSqlview(change_mark);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {// 如果年份不同 计划年份大于当前年份
				if (Integer.parseInt(plan_endtime_year)
						- Integer.parseInt(currdate_year) > 0) {
					if (Integer.parseInt(current_month)-12+07>=Integer.parseInt(plan_endtime_year_month)) {
						try {
						       wfview = (WorkflowView) RmiEntry
									.iv("wfview");
							listIsReader = wfview.coreSqlview(sql1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// 获取runtimeid
						String runtimeid = null;
						for (Iterator iterator2 = listIsReader.iterator(); iterator2
								.hasNext();) {
							Map obj1 = (Map) iterator2.next();
							runtimeid = (String) obj1.get("runtimeid");
						}
						TWfConsoleIfc tc = new TWfConsoleImpl();
						try {
							//如果flag=0表示用excel，当工单要到期时先启动流程实例
							if("0".equals(flag)){
								runtimeid=runProcess(nexthandler,EXTENDATTRIBUTE,lsh);
							}
							if(!"1".equals(mark)){//如果不等于1则唤醒流程并设置mark为1表示已经执行
								 tc.WakeUpProcess(runtimeid);// 唤醒流程
								 String change_mark="update dyform.dy_111321859523995 set mark='1' where lsh='"+lsh+"'";
								 listIsReader = wfview.coreSqlview(change_mark);
								}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {// 计划年份小于当前年份
						if (Integer.parseInt(plan_endtime)
								- Integer.parseInt(currdate) <= 7) {
							try {
							        wfview = (WorkflowView) RmiEntry
										.iv("wfview");
								listIsReader = wfview.coreSqlview(sql1);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 获取runtimeid
							String runtimeid = null;
							for (Iterator iterator2 = listIsReader.iterator(); iterator2
									.hasNext();) {
								Map obj1 = (Map) iterator2.next();
								runtimeid = (String) obj1.get("runtimeid");
							}
							TWfConsoleIfc tc = new TWfConsoleImpl();
							try {
								//如果flag=0表示用excel，当工单要到期时先启动流程实例
								if("0".equals(flag)){
									runtimeid=runProcess(nexthandler,EXTENDATTRIBUTE,lsh);
								}
								if(!"1".equals(mark)){//如果不等于1则唤醒流程并设置mark为1表示已经执行
									 tc.WakeUpProcess(runtimeid);// 唤醒流程
									 String change_mark="update dyform.dy_111321859523995 set mark='1' where lsh='"+lsh+"'";
									 listIsReader = wfview.coreSqlview(change_mark);
									}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		todo();
	}
}
