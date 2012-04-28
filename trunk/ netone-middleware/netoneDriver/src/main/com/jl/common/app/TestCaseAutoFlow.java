package com.jl.common.app;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import oe.env.client.EnvService;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.rmi.client.RmiEntry;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.WfEntry;

public class TestCaseAutoFlow {
	static Random ram = new Random();

	public static void todo(DyFormData data, String appname) throws Exception {

		AppObj app = AppEntry.iv().loadApp(appname);
		EnvService env = (EnvService) RmiEntry.iv("envinfo");
		String url = "/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.NDYD&lsh=";

		for (int i = 0; i < 100000; i++) {
			StringBuffer lineUser = new StringBuffer();
			String lsh = DyEntry.iv().addData(app.getDyformCode_(), data);
			String runtimeid = WfEntry.iv().newProcess(app.getWorkflowCode_(),
					"liyi[liyi]", appname, "", lsh, url);
			WfEntry.iv().runProcess(runtimeid);
			int next = Math.abs(ram.nextInt()) % 10 + 1;
			String commmiter = "liyi[liyi]";
			for (int j = 0; j < next; j++) {
				lineUser.append(commmiter + ",");
				commmiter = next(runtimeid, commmiter);
			}
			System.err.println(lineUser.toString());
			Thread.sleep(10);
		}

	}

	private static String next(String runtimeid, String commiter)
			throws Exception {

		List list = WfEntry.iv().listAllRunningWorklistByRuntimeid(runtimeid);
		String nextUser = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();

			List listact = WfEntry.iv().listNextRouteActive(
					object.getProcessid(), object.getActivityid(), runtimeid,
					StringUtils.substringBetween(commiter, "[", "]"));
			if (listact == null || listact.size() == 0) {
				WfEntry.iv().nextToEnd(object.getWorkcode(), commiter);
			} else if (listact.size() == 1) {
				nextUser = nextParticipant((TWfActive) listact.get(0));
				WfEntry.iv().nextByAuto(object.getWorkcode(), commiter);

			} else if (listact.size() > 1) {
				if (System.currentTimeMillis() % 2 == 0) {
					nextUser = nextParticipant((TWfActive) listact.get(1));
					// Ëæ»ú·ÖÖ§
					WfEntry.iv().nextByManual(object.getWorkcode(),
							((TWfActive) listact.get(1)).getId(), commiter);
				} else {
					nextUser = nextParticipant((TWfActive) listact.get(0));

					WfEntry.iv().nextByManual(object.getWorkcode(),
							((TWfActive) listact.get(0)).getId(), commiter);
				}

			}
		}
		List list2 = WfEntry.iv().listAllRunningWorklistByRuntimeid(runtimeid);
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			TWfWorklist object = (TWfWorklist) iterator.next();
			WfEntry.iv().specifyParticipantByWorkcode(commiter,
					object.getWorkcode(), nextUser, false,"01");

		}
		return nextUser;
	}

	private static String nextParticipant(TWfActive actx) {

		String participant = actx.getParticipant();
		String participantx[] = participant.split(",");
		if (participantx.length < 1) {
			throw new RuntimeException(actx.getName() + "lose partipant");
		}
		return participantx[0];
	}
}
