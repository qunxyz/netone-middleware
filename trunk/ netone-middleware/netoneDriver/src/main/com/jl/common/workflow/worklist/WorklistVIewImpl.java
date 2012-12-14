package com.jl.common.workflow.worklist;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppHandleIfc;
import com.jl.common.app.AppObj;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;
import com.jl.common.workflow.TWfRelevant;
import com.jl.common.workflow.TWfWorklistExt;
import com.jl.common.workflow.WfEntry;

public final class WorklistVIewImpl implements WorklistViewIfc {


	public int count(String clientId, String appname, boolean mode,
			String listType, QueryColumn query) throws Exception {

		// Ԥ��װ�ع��������
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String opemode = "('01','02')";
		if (!mode) {
			opemode = "('03','04')";
		}
		String processidStr = "";
		String processidstr_detail="";//ĳ�˲鿴��������Ȩ��
		String naturalname_detail = "";//busswf...
		boolean multiAppname = false;
		int sizecolumn = 0;
		Map wf2dycfg = null;
		if (StringUtils.isNotEmpty(appname)) {

			String appnameall[] = appname.split(",");
			if (appnameall.length > 1) {
				multiAppname = true;
			} else {
				if (appnameall.length == 1) {
					List columnx = listQueryColumn(appname);
					sizecolumn = columnx.size();
					wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
				}

			}
			StringBuffer but = new StringBuffer();
			for (int i = 0; i < appnameall.length; i++) {
				AppObj app = AppEntry.iv().loadApp(appnameall[i]);
				but.append(",'" + app.getWorkflowCode_() + "'");
				naturalname_detail = app.getWorkflowCode_();
			}

			processidStr = "and w1.processid in(" + but.toString().substring(1)
					+ ")";
			processidstr_detail ="w1.processid in(" + but.toString().substring(1)
			+ ")";
		} else {
			multiAppname = true;
		}

		String urlEnd = "";
		// ���������ɵĴ�������
		String loadworklist = "";
		String loadworklist_detail="";//����Ȩ���ṩ��ʾ�������й���
		String condition = "";
		boolean flag = false;
		if (query != null && !query.getValue().equals("")) {
			flag=true;
			if (!query.isTime()) {
				condition = condition + " and " + query.getId() + " like '%"
						+ query.getValue() + "%' ";
			} else {
				String fromtime = StringUtils.substringBefore(query.getValue(),
						"_");
				String totime = StringUtils.substringAfter(query.getValue(),
						"_");
				if (!fromtime.trim().equals("")) {
					condition = condition + " and " + query.getId() + ">='" + fromtime
							+ "'";
				}
				if (!totime.trim().equals("")) {
					condition = condition + " and " + query.getId() + "<='" + totime + "'";
				}
			}
		}
		String conditionx = null; 
		if (query != null && query.getOrder() != null
				&& !"".equals(query.getOrder())) {
			String str =condition;
			if(StringUtils.isEmpty(str)){
				str="";
			}
			condition = str;
			if(flag){
				conditionx=str;
			}else{
				conditionx="";
			}
			
		}

		if ("01".equals(listType)) {
			// ����
			if (opemode.equals("('03')")) {
				loadworklist = "select distinct w1.runtimeid from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01','02')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");

			} else if (opemode.equals("('04')")) {// �Ķ�
				loadworklist = "select distinct w1.runtimeid  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", "1=1");

			} else {
				// ��������
				loadworklist = "select distinct w1.runtimeid  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='"
						+ clientId
						+ "' and w2.statusnow='01"
						+ "' "
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");
			}
		} else if ("02".equals(listType)) {
			urlEnd = "&query=look&cuibang=true";
			// ���н�������δ�鵵
			loadworklist="select distinct w1.runtimeid "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in(select runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition;	
			
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else if ("03".equals(listType)) {
			// ���н����������Ѿ��鵵
			urlEnd = "&query=look";
			loadworklist="select distinct w1.runtimeid "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition ;	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else {
			// ���и�������
			loadworklist="select distinct w1.runtimeid "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where runtimeid in(select runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition ;	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");

		}
		
		
		List list =null;

		if("adminx".equals(clientId)){
			list =wfview.coreSqlview(loadworklist_detail);
		}else{
			if(!multiAppname){
				String workflowcode=AppEntry.iv().loadApp(appname).getWorkflowCode_();
				boolean admin=SecurityEntry.iv().permission(clientId, workflowcode);
				if(admin){
					list =wfview.coreSqlview(loadworklist_detail);
				}
			}
			list =wfview.coreSqlview(loadworklist);
		}
		
		return list.size();
	}

	public List<QueryColumn> listQueryColumn(String appname) {

		List listdata = new ArrayList();
		String appnames[] = StringUtils.split(appname, ",");
		try {

			int i = 0;
			if (appnames == null || appnames.length != 1) {
				// ���û������Ӧ�ÿ�ܻ���Ӧ�ÿ����1������£�ϵͳĬ��ֻȡһ���������
				QueryColumn col = new QueryColumn();
				col.setId("d" + i);
				col.setWidth("40%");
				col.setIndex(i++);
				col.setName("��������");
				listdata.add(col);

				QueryColumn col1 = new QueryColumn();
				col1.setId("w2.actname");
				col1.setWidth("20%");
				col1.setIndex(i++);
				col1.setName("��ǰ�ڵ�");
				listdata.add(col1);

				QueryColumn col2 = new QueryColumn();
				col2.setId("w2.commitername");
				col2.setWidth("20%");
				col2.setIndex(i++);
				col2.setName("�ύ��");
				listdata.add(col2);

				QueryColumn col3 = new QueryColumn();
				col3.setId("w1.starttime");
				col3.setWidth("20%");
				col3.setIndex(i++);
				col3.setName("����ʱ��");
				col3.setTime(true);
				listdata.add(col3);

			} else {
				AppObj app = AppEntry.iv().loadApp(appname);
				String columnx = null;
				if (!"".equals(appname)) {
					columnx = app.getWorklistColumn();
				}
				String[] column = null;

				if (columnx != null) {
					column = columnx.split(",");
				}
				// ���ֻ����1��Ӧ�ÿ����ôϵͳȥ��ϸ�Ļ�ô������ʾ����
				List list = AppEntry.iv().wf2dyformBindCfg(appname);
				TWfRelevant object = null;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					object = (TWfRelevant) iterator.next();
					String length = object.getLength();// ��̨��ȡ���ô����б���ʾ�ֶγ���
					QueryColumn col = new QueryColumn();
					col.setWidth(length);// ǰ̨��ȡ��ȡ���ô����б���ʾ�ֶγ���
					col.setId("d" + i);
					col.setIndex(i++);
					col.setName(object.getRevname());
					listdata.add(col);
				}
				// columnxΪ������ʾ[��ǰ�ڵ�]��[�ύ��]��[����ʱ��]�����ڵ�
				if (StringUtils.isEmpty(columnx)) {
					QueryColumn col1 = new QueryColumn();
					String actname = object.getActname();
					col1.setWidth(actname);
					col1.setId("w2.actname");
					col1.setIndex(i++);
					col1.setName("��ǰ�ڵ�");
					listdata.add(col1);

					QueryColumn col2 = new QueryColumn();
					String commitername = object.getCommitername();
					col2.setWidth(commitername);
					col2.setId("w2.commitername");
					col2.setIndex(i++);
					col2.setName("�ύ��");
					listdata.add(col2);

					QueryColumn col3 = new QueryColumn();
					String starttime = object.getStarttime();
					col3.setWidth(starttime);
					col3.setId("w1.starttime");
					col3.setIndex(i++);
					col3.setName("����ʱ��");
					col3.setTime(true);
					listdata.add(col3);

				} else {

					// ����
					// actname:[��ǰ�ڵ�]��commitername:[�ύ��]��starttime:[����ʱ��]�����ڵ�,
					// ͨ��column����Ĳ���(��˳�򣺵�ǰ�ڵ㣬�ύ�ߣ�����ʱ��)���в���worklist[xxx,xxx,xxx]�������˵�ֵ��null������worklist[null,xxx,xxx]

					if (!column[0].equals("null")) {
						QueryColumn col1 = new QueryColumn();
						String actname = object.getActname();
						col1.setWidth(actname);
						col1.setId("w2.actname");
						col1.setIndex(i++);
						col1.setName("��ǰ�ڵ�");
						listdata.add(col1);
					} else {

					}

					if (!column[1].equals("null")) {
						QueryColumn col2 = new QueryColumn();
						String commitername = object.getCommitername();
						col2.setWidth(commitername);
						col2.setId("w2.commitername");
						col2.setIndex(i++);
						col2.setName("�ύ��");
						listdata.add(col2);
					} else {

					}

					if (!column[2].equals("null")) {
						QueryColumn col3 = new QueryColumn();
						String starttime = object.getStarttime();
						col3.setWidth(starttime);
						col3.setId("w1.starttime");
						col3.setIndex(i++);
						col3.setName("����ʱ��");
						col3.setTime(true);
						listdata.add(col3);
					} else {

					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listdata;
	}

	public QueryColumn loadQueryColumn(String appname, int index) {
		List listdata = listQueryColumn(appname);
		if (listdata.size() < index) {
			index = 0;
		}
		return (QueryColumn) listdata.get(index);
	}

	public List<DataObj> worklist(String clientId, String appname,
			boolean mode, int from, int size, String listType, QueryColumn query)
			throws Exception {

		// Ԥ��װ�ع��������
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String opemode = "('01','02')";
		if (!mode) {
			opemode = "('03','04')";
		}
		String processidStr = "";
		String processidstr_detail="";//ĳ�˲鿴��������Ȩ��
		String naturalname_detail = "";//busswf...
		boolean multiAppname = false;
		int sizecolumn = 0;
		Map wf2dycfg = null;
		if (StringUtils.isNotEmpty(appname)) {

			String appnameall[] = appname.split(",");
			if (appnameall.length > 1) {
				multiAppname = true;
			} else {
				if (appnameall.length == 1) {
					List columnx = listQueryColumn(appname);
					sizecolumn = columnx.size();
					wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
				}

			}
			StringBuffer but = new StringBuffer();
			for (int i = 0; i < appnameall.length; i++) {
				AppObj app = AppEntry.iv().loadApp(appnameall[i]);
				but.append(",'" + app.getWorkflowCode_() + "'");
				naturalname_detail = app.getWorkflowCode_();
			}

			processidStr = "and w1.processid in(" + but.toString().substring(1)
					+ ")";
			processidstr_detail ="w1.processid in(" + but.toString().substring(1)
			+ ")";
		} else {
			multiAppname = true;
		}

		String urlEnd = "";
		// ���������ɵĴ�������
		String loadworklist = "";
		String loadworklist_detail="";//����Ȩ���ṩ��ʾ�������й���
		String condition = "";
		boolean flag = false;
		if (query != null && !query.getValue().equals("")) {
			flag=true;
			if (!query.isTime()) {
				condition = condition + " and " + query.getId() + " like '%"
						+ query.getValue() + "%' ";
				if("w2.actname".equals(query.getId())){
					condition = condition + " and w2.statusnow = '01'";
				}
			} else {
				String fromtime = StringUtils.substringBefore(query.getValue(),
						"_");
				String totime = StringUtils.substringAfter(query.getValue(),
						"_");
				if (!fromtime.trim().equals("")) {
					condition = condition + " and " + query.getId() + ">='" + fromtime
							+ "'";
				}
				if (!totime.trim().equals("")) {
					condition = condition + " and " + query.getId() + "<='" + totime + "'";
				}
			}
		}
		String conditionx = null; 
		if (query != null && query.getOrder() != null
				&& !"".equals(query.getOrder())) {
			String str =condition;
			if(StringUtils.isEmpty(str)){
				str="";
			}
			condition = str ;
			if(flag){
				conditionx=str;
			}else{
				conditionx="";
			}
			
		}

		if ("01".equals(listType)) {
			// ����
			if (opemode.contains("'03'")) {
				loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01','02')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition + " order by w1.STARTTIME desc   limit " + from + "," + size;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", "1=1");

			} else if (opemode.contains("'04'")) {// �Ķ�
				loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition + " order by w1.STARTTIME desc  limit " + from + "," + size;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", "1=1");

			} else {
				// ��������
				loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='"
						+ clientId
						+ "' and w2.statusnow='01"
						+ "' "
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition + " order by w1.STARTTIME desc  limit " + from + "," + size;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");
			}
		} else if ("02".equals(listType)) {
			urlEnd = "&query=look&cuibang=true";
			// ���н�������δ�鵵
			loadworklist="select w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.donetime donetime,w2.createtime createtime,"+
			"w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition + " order by w1.runtimeid,w1.starttime desc limit "+from+","+size;	
			
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else if ("03".equals(listType)) {
			// ���н����������Ѿ��鵵
			urlEnd = "&query=look";
			loadworklist="select w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.donetime donetime,w2.createtime createtime,"+
			"w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition + " order by w1.runtimeid,w1.starttime desc limit "+from+","+(size+100);	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else {
			// ���и�������
			loadworklist="select w4.statusnow statusx, w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.donetime donetime,w2.createtime createtime,"+
			"w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3,netone.t_wf_runtime w4 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid and w1.runtimeid=w4.runtimeid "+ processidStr + condition + " order by w1.runtimeid,w1.starttime desc limit "+from+","+(size+100);	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		}
		
		List list =null;
		if("adminx".equals(clientId)){
			list =wfview.coreSqlview(loadworklist_detail);
		}else{
			list =wfview.coreSqlview(loadworklist);
		}
		
		
		List dataClear=new ArrayList();
		if("01".equals(listType)){
			dataClear=list;
		}else{
			String runtimeidpre="";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String runtimeid = (String) object.get("runtimeid");
				if(runtimeidpre.equals(runtimeid)){
					continue;
				}
				runtimeidpre=runtimeid;
				dataClear.add(object);
			}
			if(dataClear.size()>size){
				dataClear=dataClear.subList(0, size);
			}
		}
		
//		if(SecurityEntry.iv().permission(clientId, naturalname_detail)){
//			list=wfview.coreSqlview(loadworklist_detail);
//		}else{
//			
//		}
		// ���������ɵĻʵ������ر�����Ϣ����TWfWorklistExt���󷵻�
		List listWorklist = new ArrayList();
		for (Iterator iterator = dataClear.iterator(); iterator.hasNext();) {
			List data = new ArrayList();
			List dataid = new ArrayList();
			DataObj dataX = new DataObj();

			Map object = (Map) iterator.next();
			String actid = (String) object.get("actid");
			String runtimeid = (String) object.get("runtimeid");
			String workcode = (String) object.get("workcode");
			String startime = (String) object.get("starttime");
			String donetime = (String) object.get("donetime");
			String createtime = (String) object.get("createtime");
			String userinfo = (String) object.get("userinfo");
			String processidx = (String) object.get("processid");
			String types = (String) object.get("types");
			String statusinfo=(String) object.get("statusx");
			if("01".equals(statusinfo)){
				statusinfo="[������]";
			}else if("02".equals(statusinfo)){
				statusinfo="[�ѹ鵵]";
			}else{
				statusinfo="";
			}
			boolean sync = "1".equals((String) object.get("sync"));

			TWfWorklistExt wfext = new TWfWorklistExt();
			dataX.setExt(wfext);
			Activity act = WfEntry.iv().loadProcess(processidx).getActivity(
					actid);
			String timeuse=this.timeUse(workcode);
			if(act==null){
				act =new Activity();
				act.setName("δ֪�ڵ�"+statusinfo+":"+timeuse+"��");
			}else{
				act.setName(act.getName()+statusinfo+":"+timeuse+"��");
			}
			
			// ������̵�������ر���
//			List listRev = wfview.fetchRelevantVar((String) object
//					.get("runtimeid"));
			Map map=wfview.fetchRelevantvarMap(runtimeid);

			// ���Ĭ�ϵ����̹ؼ��������ò���
			List linkToDyColumn = new ArrayList();
			if (!multiAppname) {
				
				DataField []datax=wfview.loadProcess(processidx).getDataField();
				for(int j=0;j<datax.length;j++){
					Object info=map.get(datax[j].getId());
					if(info==null){
						continue;
					}
					TWfRelevantvar name = (TWfRelevantvar)info;
					String filedid = name.getDatafieldid();
					boolean iskey = false;
					for (int i = 0; i < AppHandleIfc._CORE_KEY_VAR.length; i++) {
						if (AppHandleIfc._CORE_KEY_VAR[i]
								.equalsIgnoreCase(filedid)) {
							String valuenow = name.getValuenow();
							BeanUtils.setProperty(wfext, filedid, valuenow);
							iskey = true;
							break;
						}
					}
					if (!iskey) {
						String valuenow = name.getValuenow();
						if (valuenow == null || valuenow.equals("null")) {
							valuenow = "";
						}
						if (wf2dycfg != null) {
							TWfRelevant revx = (TWfRelevant) wf2dycfg.get(name
									.getDatafieldid());
							if (revx != null) {
								String script = revx.getScript();
								if (StringUtils.isNotEmpty(script)) {
									script = StringUtils.replace(script,
											"#value#", valuenow);
									WorkflowConsole console = (WorkflowConsole) RmiEntry
											.iv("wfhandle");
											try {
									valuenow = console.exeScript(script, "");
										} catch (Exception e) {
																		e.printStackTrace();
									}
								
								}

							}
						}
						if(!name.getDatafieldid().startsWith("r_")){
						data.add(valuenow);
						dataid.add(name.getDatafieldid());
						}
					}

				}

			} else {
				TWfRelevantvar name = null;

				try {
					name = wfview.fetchRelevantVar(runtimeid, "worklisttitle");
				} catch (Exception e) {
					e.printStackTrace();
				}
				String bussid = wfview.fetchRelevantVar(runtimeid, "bussid")
						.getValuenow();

				String bussurl = wfview.fetchRelevantVar(runtimeid, "bussurl")
						.getValuenow();
				wfext.setBussid(bussid);
				wfext.setBussurl(bussurl);

				if (name != null) {
					String valuenow = name.getValuenow();
					if (valuenow == null || valuenow.equals("null")) {
						valuenow = "";
					}
					data.add(valuenow);
					dataid.add(name.getDatafieldid());
				} else {
					data.add("�޴����������");
					dataid.add("d0");
				}

			}

			String modetip = "";
			if (sync) {
				modetip = "ͬ��";
			}

			String operateMode = "";
			if ("01".equals(types)) {
				operateMode = "" + modetip;
			} else if ("02".equals(types)) {
				operateMode = "����" + "|" + modetip;
			} else {
				operateMode = "����";
			}
			// ��������� �ύ�� ����ʱ�� ��ǰ�ڵ� �������·�������
			AppObj app = null;
			String columnx = null;
			if (!"".equals(appname)) {
				app = AppEntry.iv().loadApp(appname);
				columnx = app.getWorklistColumn();
			}
			String[] column = null;
			int x = 0;
			if (columnx != null) {
				column = columnx.split(",");

				if (!column[0].equals("null")) {
					x++;
				}
				if (!column[1].equals("null")) {
					x++;
				}

				if (!column[2].equals("null")) {
					x++;
				}
			}
			if (columnx != null) {
				if (sizecolumn != 0 && data.size() != (sizecolumn - x)) {
					// ����������ֶΣ���ô��Ҫ���Ͽ��ֶΣ���Ϊ�ɵ�������ر�����û�ж�Ӧ��ֵ
					if (data.size() < sizecolumn - x) {
						int sizex = data.size();
						for (int i = 0; i < sizecolumn - x - sizex; i++) {
							data.add("");
							dataid.add("xx" + i);
						}
					} else {
						// ����������ֶΣ���ô��Ҫɾ��������ֶ�
						int sizex = data.size();
						for (int i = 0; i < data.size() - (sizecolumn - x); i++) {
							data.remove(sizex - 1 - i);
							dataid.remove(sizex - 1 - i);
						}
					}
				}

			} else {
				// sizecolumn=sizecolumn+x;
				// ����� �޸�������ϵͳ�󶨱���ʱ���������ȷ������
				if (sizecolumn != 0 && data.size() != (sizecolumn - 3)) {
					// ����������ֶΣ���ô��Ҫ���Ͽ��ֶΣ���Ϊ�ɵ�������ر�����û�ж�Ӧ��ֵ
					if (data.size() < sizecolumn - 3) {
						int sizex = data.size();
						for (int i = 0; i < sizecolumn - 3 - sizex; i++) {
							data.add("");
							dataid.add("xx" + i);
						}
					} else {
						// ����������ֶΣ���ô��Ҫɾ��������ֶ�
						int sizex = data.size();
						for (int i = 0; i < data.size() - (sizecolumn - 3); i++) {
							data.remove(sizex - 1 - i);
							dataid.remove(sizex - 1 - i);
						}
					}
				}

			}
			if (columnx != null) {
				if (!column[0].equals("null")) {
					String actname = "��ʧ��ǰ�ڵ�" + operateMode;
					actname = act.getName() + operateMode;
					data.add(actname);
					dataid.add("w2.actname");
				}
				if (!column[1].equals("null")) {
					data.add(StringUtils.substringBefore(userinfo, "["));
					dataid.add("w2.commitername");
				} else {

				}
				if (!column[2].equals("null")) {
					data.add(StringUtils.substring(startime, 0, 19));
					dataid.add("w1.starttime");
				} else {

				}

			} else {
				data.add(act.getName() + operateMode);
				dataid.add("w2.actname");
				String usercode=StringUtils.substringBetween(userinfo, "[","]");
				
				data.add(StringUtils.substringBefore(userinfo, "["));
				dataid.add("w2.commitername");
				data.add(StringUtils.substring(startime, 0, 19));
				dataid.add("w1.starttime");
//				data.add(statusinfo);
//				dataid.add("w1.status");
			}

			dataX.setData((String[]) data.toArray(new String[0]));
			dataX.setId((String[]) dataid.toArray(new String[0]));

			String look = "";
			if ("00".equals(listType)){
				look="&query=look";
			}
			dataX.setUrl(wfext.getBussurl() + wfext.getBussid() + "&workcode="
					+ workcode + "&operatemode=" + types + urlEnd
					+ "&commiter="
					+ StringUtils.substringBetween(userinfo, "[", "]")+look);
			listWorklist.add(dataX);// ��ӷ��������Ļ����

		}
		
		for (Iterator iterator = listWorklist.iterator(); iterator.hasNext();) {
			DataObj object = (DataObj) iterator.next();
			object.getId();
		}
		return listWorklist;

	}
	
	private String getUserPhone(String usercode){
		try{
		List list=DbTools.queryData("select phone phone from netone.t_cs_user where usercode='"+usercode+"'");
		return ((Map)list.get(0)).get("phone").toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public void addTimeUse(String starttime,String endtime,Activity act){
		if(StringUtils.isEmpty(endtime)){
			return;
		}
		try{
		long starttimeV=Timestamp.valueOf(starttime).getTime();
		long endtimeV=Timestamp.valueOf(endtime).getTime();
		long use=(endtimeV-starttimeV)/(1000*60);
		act.setName(act.getName()+"(ʹ��"+use+"��)");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int fetchQueryColumnIndex(String appname, String columnid)
			throws Exception {
		List listdata = listQueryColumn(appname);
		for (Iterator iterator = listdata.iterator(); iterator.hasNext();) {
			QueryColumn object = (QueryColumn) iterator.next();
			if (columnid.equals(object.getId())) {
				return object.getIndex();
			}
		}
		throw new RuntimeException("��Ч������");
	}

	public static void main(String[] args) throws Exception {
		WorklistViewIfc wf = new WorklistVIewImpl();
		int x = wf.count("liyi", "APPFRAME.APPFRAME.NDYD", true, "01", null);
		System.out.println(x);

		List list = wf.worklist("liyi", "APPFRAME.APPFRAME.NDYD", true, 0, 10,
				"01", null);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DataObj object = (DataObj) iterator.next();
			System.out.println();
		}

		List listx = wf.listQueryColumn("APPFRAME.APPFRAME.NDYD");
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			QueryColumn object = (QueryColumn) iterator.next();
			System.out.println();
		}

		int index = wf.fetchQueryColumnIndex("APPFRAME.APPFRAME.NDYD",
				"participant");
		System.out.println(index);
	}
	
	private String timeUse(String workcode){
		String sql="select starttime stx,endtime enx from netone.t_wf_runtime where runtimeid in(select runtimeid from netone.t_wf_worklist where workcode='"+workcode+"')";
		List list=DbTools.queryData(sql);
		Map map=(Map)list.get(0);
		String stx=(String)map.get("stx");
		String enx=(String)map.get("enx");
		long time1=Timestamp.valueOf(stx).getTime();
		long time2=System.currentTimeMillis();
		if(enx!=null){
			time2=Timestamp.valueOf(enx).getTime();
		}
		return String.valueOf((time2-time1)/(1000*60*60*24));
	}


	public List<String> worklistOnlyLsh(String clientId, String appname,
			boolean mode, int from, int size, String listType, QueryColumn query)
			throws Exception {
		// Ԥ��װ�ع��������
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String opemode = "('01','02')";
		if (!mode) {
			opemode = "('03','04')";
		}
		String processidStr = "";
		String processidstr_detail="";//ĳ�˲鿴��������Ȩ��
		String naturalname_detail = "";//busswf...
		boolean multiAppname = false;
		int sizecolumn = 0;
		Map wf2dycfg = null;
		if (StringUtils.isNotEmpty(appname)) {

			String appnameall[] = appname.split(",");
			if (appnameall.length > 1) {
				multiAppname = true;
			} else {
				if (appnameall.length == 1) {
					List columnx = listQueryColumn(appname);
					sizecolumn = columnx.size();
					wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
				}

			}
			StringBuffer but = new StringBuffer();
			for (int i = 0; i < appnameall.length; i++) {
				AppObj app = AppEntry.iv().loadApp(appnameall[i]);
				but.append(",'" + app.getWorkflowCode_() + "'");
				naturalname_detail = app.getWorkflowCode_();
			}

			processidStr = "and w1.processid in(" + but.toString().substring(1)
					+ ")";
			processidstr_detail ="w1.processid in(" + but.toString().substring(1)
			+ ")";
		} else {
			multiAppname = true;
		}

		String urlEnd = "";
		// ���������ɵĴ�������
		String loadworklist = "";
		String loadworklist_detail="";//����Ȩ���ṩ��ʾ�������й���
		String condition = "";
		boolean flag = false;
		if (query != null && !query.getValue().equals("")) {
			flag=true;
			if (!query.isTime()) {
				condition = condition + " and " + query.getId() + " like '%"
						+ query.getValue() + "%' ";
			} else {
				String fromtime = StringUtils.substringBefore(query.getValue(),
						"_");
				String totime = StringUtils.substringAfter(query.getValue(),
						"_");
				if (!fromtime.trim().equals("")) {
					condition = condition + " and " + query.getId() + ">='" + fromtime
							+ "'";
				}
				if (!totime.trim().equals("")) {
					condition = condition + " and " + query.getId() + "<='" + totime + "'";
				}
			}
		}
		String conditionx = null; 
		if (query != null && query.getOrder() != null
				&& !"".equals(query.getOrder())) {
			String str =condition;
			if(StringUtils.isEmpty(str)){
				str="";
			}
			condition = str;
			if(flag){
				conditionx=str;
			}else{
				conditionx="";
			}
			
		}

		if ("01".equals(listType)) {
			// ����
			if (opemode.equals("('03')")) {
				loadworklist = "select distinct w3.lsh lshx from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01','02')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition +" limit "+from+","+size ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");

			} else if (opemode.equals("('04')")) {// �Ķ�
				loadworklist = "select  distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition+" limit "+from+","+size ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", "1=1");

			} else {
				// ��������
				loadworklist = "select distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='"
						+ clientId
						+ "' and w2.statusnow='01"
						+ "' "
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition +" limit "+from+","+size ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");
			}
		} else if ("02".equals(listType)) {
			urlEnd = "&query=look&cuibang=true";
			// ���н�������δ�鵵
			loadworklist="select w3.lsh lshx "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition+" limit "+from+","+size ;	
			
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else if ("03".equals(listType)) {
			// ���н����������Ѿ��鵵
			urlEnd = "&query=look";
			// ���н�������δ�鵵
			loadworklist="select distinct w3.lsh lshx "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition +" limit "+from+","+size ;	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else {
			// ���и�������
			loadworklist="select distinct w3.lsh lshx "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition+" limit "+from+","+size ;	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");

		}
		
		
		List list =null;

		if("adminx".equals(clientId)){
			list =wfview.coreSqlview(loadworklist_detail);
		}else{
			if(!multiAppname){
				String workflowcode=AppEntry.iv().loadApp(appname).getWorkflowCode_();
				boolean admin=SecurityEntry.iv().permission(clientId, workflowcode);
				if(admin){
					list =wfview.coreSqlview(loadworklist_detail);
				}
			}
			list =wfview.coreSqlview(loadworklist);
		}
		List listfinal=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String lsh=(String)object.get("lshx");
			listfinal.add(lsh);
		}
		return listfinal;
	}

	public int countOnlyLsh(String clientId, String appname, boolean mode,
			String listType, QueryColumn query) throws Exception {
		// Ԥ��װ�ع��������
		CupmRmi cupm = null;
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			cupm = (CupmRmi) RmiEntry.iv("cupm");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String opemode = "('01','02')";
		if (!mode) {
			opemode = "('03','04')";
		}
		String processidStr = "";
		String processidstr_detail="";//ĳ�˲鿴��������Ȩ��
		String naturalname_detail = "";//busswf...
		boolean multiAppname = false;
		int sizecolumn = 0;
		Map wf2dycfg = null;
		if (StringUtils.isNotEmpty(appname)) {

			String appnameall[] = appname.split(",");
			if (appnameall.length > 1) {
				multiAppname = true;
			} else {
				if (appnameall.length == 1) {
					List columnx = listQueryColumn(appname);
					sizecolumn = columnx.size();
					wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
				}

			}
			StringBuffer but = new StringBuffer();
			for (int i = 0; i < appnameall.length; i++) {
				AppObj app = AppEntry.iv().loadApp(appnameall[i]);
				but.append(",'" + app.getWorkflowCode_() + "'");
				naturalname_detail = app.getWorkflowCode_();
			}

			processidStr = "and w1.processid in(" + but.toString().substring(1)
					+ ")";
			processidstr_detail ="w1.processid in(" + but.toString().substring(1)
			+ ")";
		} else {
			multiAppname = true;
		}

		String urlEnd = "";
		// ���������ɵĴ�������
		String loadworklist = "";
		String loadworklist_detail="";//����Ȩ���ṩ��ʾ�������й���
		String condition = "";
		boolean flag = false;
		if (query != null && !query.getValue().equals("")) {
			flag=true;
			if (!query.isTime()) {
				condition = condition + " and " + query.getId() + " like '%"
						+ query.getValue() + "%' ";
			} else {
				String fromtime = StringUtils.substringBefore(query.getValue(),
						"_");
				String totime = StringUtils.substringAfter(query.getValue(),
						"_");
				if (!fromtime.trim().equals("")) {
					condition = condition + " and " + query.getId() + ">='" + fromtime
							+ "'";
				}
				if (!totime.trim().equals("")) {
					condition = condition + " and " + query.getId() + "<='" + totime + "'";
				}
			}
		}
		String conditionx = null; 
		if (query != null && query.getOrder() != null
				&& !"".equals(query.getOrder())) {
			String str =condition;
			if(StringUtils.isEmpty(str)){
				str="";
			}
			condition = str;
			if(flag){
				conditionx=str;
			}else{
				conditionx="";
			}
			
		}

		if ("01".equals(listType)) {
			// ����
			if (opemode.equals("('03')")) {
				loadworklist = "select distinct w3.lsh lshx from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01','02')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");

			} else if (opemode.equals("('04')")) {// �Ķ�
				loadworklist = "select  distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='"
						+ clientId
						+ "' and w2.statusnow in ('01')"
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", "1=1");

			} else {
				// ��������
				loadworklist = "select distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='"
						+ clientId
						+ "' and w2.statusnow='01"
						+ "' "
						+ processidStr
						+ " and w2.types in "
						+ opemode
						+ condition  ;
				loadworklist_detail=StringUtils.replace(loadworklist, "w2.usercode='"+clientId+"'", " 1=1 ");
			}
		} else if ("02".equals(listType)) {
			urlEnd = "&query=look&cuibang=true";
			// ���н�������δ�鵵
			loadworklist="select w3.lsh lshx "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in( select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition ;	
			
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else if ("03".equals(listType)) {
			// ���н����������Ѿ��鵵
			urlEnd = "&query=look";
			// ���н�������δ�鵵
			loadworklist="select distinct w3.lsh lshx "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition  ;	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");
		} else {
			// ���и�������
			loadworklist="select distinct w3.lsh lshx "+

			"from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 "+

			"where w1.RUNTIMEID in("+
			"select runtimeid from netone.t_wf_runtime where runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in("+
			"select workcode from netone.t_wf_participant where USERCODE='"+clientId+"')))"+
			"and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid "+ processidStr + condition;	
			loadworklist_detail=StringUtils.replace(loadworklist, " USERCODE='"+clientId+"'", " 1=1 ");

		}
		
		
		List list =null;

		if("adminx".equals(clientId)){
			list =wfview.coreSqlview(loadworklist_detail);
		}else{
			if(!multiAppname){
				String workflowcode=AppEntry.iv().loadApp(appname).getWorkflowCode_();
				boolean admin=SecurityEntry.iv().permission(clientId, workflowcode);
				if(admin){
					list =wfview.coreSqlview(loadworklist_detail);
				}
			}
			list =wfview.coreSqlview(loadworklist);
		}

		return list.size();
	}
}
