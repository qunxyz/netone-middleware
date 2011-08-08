package oe.common.workflow;

import java.util.Iterator;
import java.util.List;

public class Sample {

	public static void main(String[] args) throws Exception {
		String processid = "BUSSWF.BUSSWF.ZYDRP2.ALLOCATECARGO";
		String clientId = "adminx";
		String mode = "1";
		String ddid = "1";
		String ddurl = "http://www.google.com";
		String runtimeid = WfEntry.iv().newProcess(processid, clientId, mode,"",
				ddid, ddurl);

		WfEntry.iv().runProcess(runtimeid);

		List list = WfEntry.iv().worklist(clientId);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
			System.out.println(object.getCustomer() + "," + object.getActiveName()
					+ "," + object.getStarttime()+","+object.getWorkcode());
			
			System.out.println(WfEntry.iv().next(object.getWorkcode(), clientId));
			//System.out.println(WfEntry.iv().back(object.getWorkcode(), clientId));

		}

	}
}
