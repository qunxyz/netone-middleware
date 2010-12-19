package oe.cms.dao.infomodel;

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

import java.util.Iterator;
import java.util.List;

import oe.frame.orm.OrmerEntry;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;

/**
 * <p>
 * This is just a simple job that gets fired off many times by example 1
 * </p>
 * 
 * @author Bill Kratzer
 */
public final class BComeRichSeriJob implements Job {

	/**
	 * Quartz requires a public empty constructor so that the scheduler can
	 * instantiate the class whenever it needs.
	 */
	public BComeRichSeriJob() {
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

		System.out.println("��ʱ�㵽�������ƻ�����.......����B�ռ�");
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");

		String comeANum = dao.fetchcomeAlevelNum();

		// ��C������B
		int comeANumValue = Integer.parseInt(comeANum);
		List list = dao.fetchList(RichLevel._LEVEL_C);
		if (list.size() < comeANumValue) {
			comeANumValue = list.size();
		}
		for (int i = 0; i < comeANumValue; i++) {
			TCmsInfomodel modelPre = (TCmsInfomodel) list.get(i);
			modelPre.setLevels(RichLevel._LEVEL_B);
			OrmerEntry.fetchOrmer().fetchSerializer().update(modelPre);
		}
		// ����װ��AB����Ŀռ�
		dao.initview(RichLevel._LEVEL_C);
		dao.initview(RichLevel._LEVEL_B);

	}
}