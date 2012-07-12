package com.jl.scheduler.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jl.common.SpringBeanUtil;
import com.jl.dao.CommonDAO;
import com.jl.service.LeaderViewService;
import com.jl.service.impl.LeaderViewServiceImpl;

import oe.frame.web.WebCache;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

public class leaderView {

	/**
	 * @param args
	 */
	//private static CommonDAO commonDAO =  (CommonDAO) SpringBeanUtil.getInstance().getBean("commonDAO");

	public static void todo(){
		LeaderViewService ls = (LeaderViewService) SpringBeanUtil.getInstance().getBean("LeaderViewService");
		ls.dataCache();	
	}
	public static void main(String[] args) {
		todo();
	}
}
