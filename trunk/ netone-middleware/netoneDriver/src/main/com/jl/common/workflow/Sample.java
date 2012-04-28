package com.jl.common.workflow;

import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;
import java.util.UUID;

public class Sample {

	public static void main(String[] args) throws Exception {
		
//		
//		WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
//		
//		String abc="1.0d/100d";
//		
//		String rs=console.exeScript( abc,"");
//		System.out.println(rs);
		String processid = "BUSSWF.BUSSWF.NDYD.GCWZQG";
//		String clientId = "adminx";
//		String mode = "1";
//		String ddid = "1";
//		String ddurl = "http://www.google.com";
//		String runtimeid = WfEntry.iv().newProcess(processid, clientId, mode,"",
//				ddid, ddurl);
//
//		WfEntry.iv().runProcess(runtimeid);
//		List listx=WfEntry.iv().listActiveID(processid);
//		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
//			TWfActive object = (TWfActive) iterator.next();
//			
//		}
//		List list = WfEntry.iv().worklist(clientId);
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
//			System.out.println(object.getCustomer() + "," + object.getActiveName()
//					+ "," + object.getStarttime()+","+object.getWorkcode());
//			
//			System.out.println(WfEntry.iv().next(object.getWorkcode(), clientId));
//			//System.out.println(WfEntry.iv().back(object.getWorkcode(), clientId));
//
//		}
		
		String runtimeid=WfEntry.iv().getRuntimeIdByWorkcode("402881e7304b1b5001304b2eec86000d");
		List list=WfEntry.iv().listAllDoneWorklistByRuntimeid(runtimeid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
			
		}
		
		for (int i = 0; i < list.size(); i++) {
			TWfWorklistExt object = (TWfWorklistExt)list.get(i);
			if(i+1<list.size()){
				TWfWorklistExt objectx = (TWfWorklistExt)list.get(i+1);
				object.setParticipant(objectx.getParticipant());
			}
			
		}

	}
}
