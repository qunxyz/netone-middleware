package com.jl.scheduler.job;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jl.common.SpringBeanUtil;
import com.jl.common.TimeUtil;
import com.jl.common.workflow.WfEntry;

/**
 * 过期任务
 * 
 * @version 1.0.0 2011-7-22 上午11:42:16
 * @history
 */
public class ExpireJob {
	private final Logger LOG = Logger.getLogger(ExpireJob.class);

	/**
	 * 过期调度 由定时任务来做
	 * 
	 * @throws Exception
	 */
	public void execute() {
		WfEntry.iv().outTimeAlarm();
	}

	public static void main(String[] args) {
		ExpireJob job = (ExpireJob) SpringBeanUtil.getInstance().getBean(
				"expireJob");
		job.execute();
	}
}
