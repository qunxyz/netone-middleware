package oe.cms.runtime.timeTrigger;

/* 
 * Copyright 2005 OpenSymphony 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import oe.cms.BuildCmsJpp;
import oe.cms.EnvEntryInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * This Example will demonstrate all of the basics of scheduling capabilities of
 * Quartz using Cron Triggers.
 * 
 * @author Bill Kratzer
 */
public final class InTimeTriggerImpl implements InTimeTrigger {
	private static Log _log = LogFactory.getLog(InTimeTriggerImpl.class);

	public void initStart(HttpServletRequest requ) {
		try {
			if ("true".equals(EnvEntryInfo.env.fetchEnvValue("init"))) {
				BuildCmsJpp.todo(requ);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Scheduler initdo() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		String initTime = EnvEntryInfo.env.fetchEnvValue("inittime");
		initTime = initTime == null || "".equals(initTime) ? DEFAULT_TRIGER
				: initTime;
		JobDetail job1 = new JobDetail("job1", "group1", InitJob.class);
		CronTrigger trigger1 = new CronTrigger("trigger1", "group1", "job1",
				"group1", initTime);
		sched.addJob(job1, true);

		sched.scheduleJob(trigger1);
		return sched;
	}

	public Scheduler serildo() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		String serailTime = EnvEntryInfo.env.fetchEnvValue("serialtime");
		serailTime = serailTime == null || "".equals(serailTime) ? DEFAULT_TRIGER
				: serailTime;
		JobDetail job2 = new JobDetail("job2", "group2", SerialJob.class);
		CronTrigger trigger2 = new CronTrigger("trigger2", "group2", "job2",
				"group2", serailTime);
		sched.addJob(job2, true);

		sched.scheduleJob(trigger2);
		return sched;
	}

	public Scheduler zipdo() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		String zipTime =EnvEntryInfo.env.fetchEnvValue("ziptime");
		zipTime = zipTime == null || "".equals(zipTime) ? DEFAULT_TRIGER
				: zipTime;
		JobDetail job2 = new JobDetail("job3", "group3", ZipJob.class);
		CronTrigger trigger2 = new CronTrigger("trigger3", "group3", "job3",
				"group3", zipTime);
		sched.addJob(job2, true);

		sched.scheduleJob(trigger2);
		return sched;
	}

}