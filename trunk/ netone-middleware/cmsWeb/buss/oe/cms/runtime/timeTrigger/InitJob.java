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

import javax.servlet.http.HttpServletRequest;

import oe.cms.BuildCmsJpp;
import oe.cms.BuildStaticPage;
import oe.cms.runtime.XHtmlCachepool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p>
 * This is just a simple job that gets fired off many times by example 1
 * </p>
 * 
 * @author Bill Kratzer
 */
public final class InitJob implements Job {
	private static Log _log = LogFactory.getLog(InitJob.class);

	/**
	 * Quartz requires a public empty constructor so that the scheduler can
	 * instantiate the class whenever it needs.
	 */
	public InitJob() {
	}

	/**
	 * <p>
	 * Called by the <code>{@link org.quartz.Scheduler}</code> when a
	 * <code>{@link org.quartz.Trigger}</code> fires that is associated with
	 * the <code>Job</code>.
	 * </p>
	 * 
	 * @throws JobExecutionException
	 *             if there is an exception while executing the job.
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		_log.info("计时点到，启动计划任务.......");
		try {
			_log.info(".......清空JPP缓存");
			XHtmlCachepool.removeInfoCacheAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			_log.info(".......初始化JPP缓存");
			HttpServletRequest requ = (HttpServletRequest)context.get("request");
			BuildCmsJpp.todo(requ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		_log.info(".......初始化所有用户和网站的静态页面");
		BuildStaticPage.initWeb();

	}
}