package com.jl.scheduler.job;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jl.common.SpringBeanUtil;
import com.jl.common.TimeUtil;
import com.jl.common.workflow.WfEntry;

/**
 * ��������
 * 
 * @version 1.0.0 2011-7-22 ����11:42:16
 * @history
 */
public class ExpireJob {
	private final Logger LOG = Logger.getLogger(ExpireJob.class);

	/**
	 * ���ڵ��� �ɶ�ʱ��������
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
