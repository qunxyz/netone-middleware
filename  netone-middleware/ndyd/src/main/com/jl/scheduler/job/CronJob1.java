package com.jl.scheduler.job;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jl.common.message.EveryWeekMsg;

/**
 * 定时调试任务
 * 
 * @version 1.0.0
 * @history
 */
public class CronJob1 {
	private final Logger LOG = Logger.getLogger(CronJob1.class);

	public void execute() {
		
		EveryWeekMsg.todo();
	}
	public void contractMgr_todo(){
		ContractMgr.todo();
	}
	public void leaderView_todo(){
		leaderView.todo();
	}
	public void modifyleaderView_todo(){
		ModifyLeaderViewData.modifyData();
	}
}
