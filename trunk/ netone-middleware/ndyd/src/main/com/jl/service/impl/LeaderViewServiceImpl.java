package com.jl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.enums.LetterCodedLabeledEnum;

import oe.frame.web.WebCache;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import com.jl.common.SpringBeanUtil;
import com.jl.common.workflow.WfReportUtil;
import com.jl.dao.CommonDAO;
import com.jl.entity.leaderViewPojo;
import com.jl.service.BaseService;
import com.jl.service.LeaderViewService;

public class LeaderViewServiceImpl extends BaseService implements
		LeaderViewService {
	public CommonDAO commonDAOMirror;

	public void setcommonDAOMirror(CommonDAO commonDAOMirror) {
		this.commonDAOMirror = commonDAOMirror;
	}

	public void dataCache() {
		WfReportUtil.leaderViewCache("DEPT.DEPT.297236c57ea1410d841db89adbfd3f08", "BUSSWF.BUSSWF.NDYD");
	}

	
}
