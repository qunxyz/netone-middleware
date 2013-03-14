package com.jl.common.workflow.worklist;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public final class WorklistVIewImpl
  implements WorklistViewIfc
{
  public int count(String clientId, String appname, boolean mode, String listType, QueryColumn query)
    throws Exception
  {
    CupmRmi cupm = null;
    WorkflowView wfview = null;
    try {
      wfview = (WorkflowView)RmiEntry.iv("wfview");
      cupm = (CupmRmi)RmiEntry.iv("cupm");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    String opemode = "('01','02')";
    if (!mode) {
      opemode = "('03','04')";
    }
    String processidStr = "";
    String processidstr_detail = "";
    String naturalname_detail = "";
    boolean multiAppname = false;
    int sizecolumn = 0;
    Map wf2dycfg = null;
    boolean hasadminAble=false;
    if (StringUtils.isNotEmpty(appname)) {
      String[] appnameall = appname.split(",");
      if (appnameall.length > 1) {
        multiAppname = true;
      }
      else if (appnameall.length == 1) {
        List columnx = listQueryColumn(appname);
        sizecolumn = columnx.size();
        wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
      }

      if(!multiAppname){
    	  hasadminAble=SecurityEntry.iv().permission(clientId, appname);
      }
      StringBuffer but = new StringBuffer();
      for (int i = 0; i < appnameall.length; i++) {
        AppObj app = AppEntry.iv().loadApp(appnameall[i]);
        but.append(",'" + app.getWorkflowCode_() + "'");
        naturalname_detail = app.getWorkflowCode_();
      }

      processidStr = "and w1.processid in(" + but.toString().substring(1) + 
        ")";
      processidstr_detail = "w1.processid in(" + but.toString().substring(1) + 
        ")";
    } else {
      multiAppname = true;
    }

       String urlEnd = "";

    String loadworklist = "";
    String loadworklist_detail = "";
    String condition = "";
    boolean flag = false;
    if ((query != null) && (!query.getValue().equals(""))) {
      flag = true;
      if (!query.isTime()) {
        condition = condition + " and " + query.getId() + " like '%" + 
          query.getValue() + "%' ";
      } else {
        String fromtime = StringUtils.substringBefore(query.getValue(), 
          "_");
        String totime = StringUtils.substringAfter(query.getValue(), 
          "_");
        if (!fromtime.trim().equals("")) {
          condition = condition + " and " + query.getId() + ">='" + fromtime + 
            "'";
        }
        if (!totime.trim().equals("")) {
          condition = condition + " and " + query.getId() + "<='" + totime + "'";
        }
      }
    }

    if ((query != null) && (query.getOrder() != null) && 
      (!"".equals(query.getOrder()))) {
      String str = condition;
      if (StringUtils.isEmpty(str)) {
        str = "";
      }
      condition = str;
    }

    if ("01".equals(listType))
    {
      if (opemode.equals("('03')")) {
        loadworklist = "select distinct w1.runtimeid from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01','02')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
      else if (opemode.equals("('04')")) {
        loadworklist = "select distinct w1.runtimeid  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", "1=1");
      }
      else
      {
        loadworklist = "select distinct w1.runtimeid  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='" + 
          clientId + 
          "' and w2.statusnow='01" + 
          "' " + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
    } else if ("02".equals(listType)) {
      urlEnd = "&query=look&cuibang=true";

      loadworklist = "select distinct w1.runtimeid from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in(select runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition;

      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    } else if ("03".equals(listType))
    {
      urlEnd = "&query=look";
      loadworklist = "select distinct w1.runtimeid from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition;
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }
    else {
      loadworklist = "select distinct w1.runtimeid from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where runtimeid in(select runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition;
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }

    List list = null;

    if ("adminx".equals(clientId)) {
      list = wfview.coreSqlview(loadworklist_detail);
    } else if(hasadminAble&&listType.equals("00")){
    	  list = wfview.coreSqlview(loadworklist_detail);
    }
    else {
      if (!multiAppname) {
        String workflowcode = AppEntry.iv().loadApp(appname).getWorkflowCode_();
        boolean admin = SecurityEntry.iv().permission(clientId, workflowcode);
        if (admin) {
          list = wfview.coreSqlview(loadworklist_detail);
        }
      }
      list = wfview.coreSqlview(loadworklist);
    }

    return list.size();
  }

  public List<QueryColumn> listQueryColumn(String appname)
  {
    List listdata = new ArrayList();
    String[] appnames = StringUtils.split(appname, ",");
    try
    {
      int i = 0;
      if ((appnames == null) || (appnames.length != 1))
      {
        QueryColumn col = new QueryColumn();
        col.setId("d" + i);
        col.setWidth("40%");
        col.setIndex(i++);
        col.setName("工单标题");
        listdata.add(col);

        QueryColumn col1 = new QueryColumn();
        col1.setId("w2.actname");
        col1.setWidth("20%");
        col1.setIndex(i++);
        col1.setName("当前节点");
        listdata.add(col1);

        QueryColumn col2 = new QueryColumn();
        col2.setId("w2.commitername");
        col2.setWidth("20%");
        col2.setIndex(i++);
        col2.setName("提交者");
        listdata.add(col2);

        QueryColumn col3 = new QueryColumn();
        col3.setId("w1.starttime");
        col3.setWidth("20%");
        col3.setIndex(i++);
        col3.setName("创建时间");
        col3.setTime(true);
        listdata.add(col3);
      }
      else {
        AppObj app = AppEntry.iv().loadApp(appname);
        String columnx = null;
        if (!"".equals(appname)) {
          columnx = app.getWorklistColumn();
        }
        String[] column = (String[])null;

        if (columnx != null) {
          column = columnx.split(",");
        }

        List list = AppEntry.iv().wf2dyformBindCfg(appname);
        TWfRelevant object = null;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
          object = (TWfRelevant)iterator.next();
          String length = object.getLength();
          QueryColumn col = new QueryColumn();
          col.setWidth(length);
          col.setId("d" + i);
          col.setIndex(i++);
          col.setName(object.getRevname());
          listdata.add(col);
        }

        if (StringUtils.isEmpty(columnx)) {
          QueryColumn col1 = new QueryColumn();
          String actname = object.getActname();
          col1.setWidth(actname);
          col1.setId("w2.actname");
          col1.setIndex(i++);
          col1.setName("当前节点");
          listdata.add(col1);

          QueryColumn col2 = new QueryColumn();
          String commitername = object.getCommitername();
          col2.setWidth(commitername);
          col2.setId("w2.commitername");
          col2.setIndex(i++);
          col2.setName("提交者");
          listdata.add(col2);

          QueryColumn col3 = new QueryColumn();
          String starttime = object.getStarttime();
          col3.setWidth(starttime);
          col3.setId("w1.starttime");
          col3.setIndex(i++);
          col3.setName("申请时间");
          col3.setTime(true);
          listdata.add(col3);
        }
        else
        {
          if (!column[0].equals("null")) {
            QueryColumn col1 = new QueryColumn();
            String actname = object.getActname();
            col1.setWidth(actname);
            col1.setId("w2.actname");
            col1.setIndex(i++);
            col1.setName("当前节点");
            listdata.add(col1);
          }

          if (!column[1].equals("null")) {
            QueryColumn col2 = new QueryColumn();
            String commitername = object.getCommitername();
            col2.setWidth(commitername);
            col2.setId("w2.commitername");
            col2.setIndex(i++);
            col2.setName("提交者");
            listdata.add(col2);
          }

          if (!column[2].equals("null")) {
            QueryColumn col3 = new QueryColumn();
            String starttime = object.getStarttime();
            col3.setWidth(starttime);
            col3.setId("w1.starttime");
            col3.setIndex(i++);
            col3.setName("申请时间");
            col3.setTime(true);
            listdata.add(col3);
          }
        }

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return listdata;
  }

  public QueryColumn loadQueryColumn(String appname, int index) {
    List listdata = listQueryColumn(appname);
    if (listdata.size() < index) {
      index = 0;
    }
    return (QueryColumn)listdata.get(index);
  }

  public List<DataObj> worklist(String clientId, String appname, boolean mode, int from, int size, String listType, QueryColumn query)
    throws Exception
  {
    WorkflowView wfview = null;
    try {
      wfview = (WorkflowView)RmiEntry.iv("wfview");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    String opemode = "('01','02')";
    if (!mode) {
      opemode = "('03','04')";
    }
    String processidStr = "";
    String processidstr_detail = "";
    String naturalname_detail = "";
    boolean multiAppname = false;
    int sizecolumn = 0;
    Map wf2dycfg = null;
    boolean hasadminAble=false;
    if (StringUtils.isNotEmpty(appname))
    {
      String[] appnameall = appname.split(",");
      if (appnameall.length > 1) {
        multiAppname = true;
      }
      else if (appnameall.length == 1) {
        List columnx = listQueryColumn(appname);
        sizecolumn = columnx.size();
        wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
      }

      StringBuffer but = new StringBuffer();
      for (int i = 0; i < appnameall.length; i++) {
        AppObj app = AppEntry.iv().loadApp(appnameall[i]);
        but.append(",'" + app.getWorkflowCode_() + "'");
        naturalname_detail = app.getWorkflowCode_();
      }

      processidStr = "and w1.processid in(" + but.toString().substring(1) + 
        ")";
      processidstr_detail = "w1.processid in(" + but.toString().substring(1) + 
        ")";
    } else {
      multiAppname = true;
    }
    
    if(!multiAppname){
      String workflow=AppEntry.iv().loadApp(appname).getWorkflowCode_();
  	  hasadminAble=SecurityEntry.iv().permission(clientId, workflow);
    }

    String urlEnd = "";

    String loadworklist = "";
    String loadworklist_detail = "";
    String condition = "";
    boolean flag = false;
    if ((query != null) && (!query.getValue().equals(""))) {
      flag = true;
      if (!query.isTime()) {
        condition = condition + " and " + query.getId() + " like '%" + 
          query.getValue() + "%' ";
      } else {
        String fromtime = StringUtils.substringBefore(query.getValue(), 
          "_");
        String totime = StringUtils.substringAfter(query.getValue(), 
          "_");
        if (!fromtime.trim().equals("")) {
          condition = condition + " and " + query.getId() + ">='" + fromtime + 
            "'";
        }
        if (!totime.trim().equals("")) {
          condition = condition + " and " + query.getId() + "<='" + totime + "'";
        }
      }
    }
    String conditionx = null;
    if ((query != null) && (query.getOrder() != null) && 
      (!"".equals(query.getOrder()))) {
      String str = condition;
      if (StringUtils.isEmpty(str)) {
        str = "";
      }
      condition = str;
      if (flag)
        conditionx = str;
      else {
        conditionx = "";
      }

    }

    if ("01".equals(listType))
    {
      if (opemode.contains("'03'")) {
        loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01','02')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition + " order by w1.STARTTIME desc  limit " + from + "," + size;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", "1=1");
      }
      else if (opemode.contains("'04'")) {
        loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition + " order by w1.STARTTIME desc limit " + from + "," + size;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", "1=1");
      }
      else
      {
        loadworklist = "select  w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='" + 
          clientId + 
          "' and w2.statusnow='01" + 
          "' " + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition + " order by w1.STARTTIME desc limit " + from + "," + size;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
    } else if ("02".equals(listType)) {
      urlEnd = "&query=look&cuibang=true";

      loadworklist = "select w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.donetime donetime,w2.createtime createtime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition + " order by w1.starttime desc limit " + from + "," + size;

      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    } else if ("03".equals(listType))
    {
      urlEnd = "&query=look";
      loadworklist = "select w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.donetime donetime,w2.createtime createtime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition + " order by w1.starttime desc limit " + from + "," + (size + 100);
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }
    else {
      loadworklist = "select w4.statusnow statusx, w1.processid processid,w1.activityid actid,w1.runtimeid runtimeid,w1.workcode workcode,w1.starttime starttime,w2.donetime donetime,w2.createtime createtime,w2.actname actname,concat(w2.commitername,'[',w2.commitercode,']') userinfo,w2.types,w2.sync,w3.* from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3,netone.t_wf_runtime w4 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid and w1.runtimeid=w4.runtimeid " + processidStr + condition + " order by w1.starttime desc limit " + from + "," + (size + 100);
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }

    List list = null;
    if ("adminx".equals(clientId)){
      list = wfview.coreSqlview(loadworklist_detail);}
    else if(hasadminAble&&listType.equals("00")){
	  list = wfview.coreSqlview(loadworklist_detail);
    }
    else {
      list = wfview.coreSqlview(loadworklist);
    }

    List dataClear = new ArrayList();
    if ("01".equals(listType)) {
      dataClear = list;
    } else {
      Map map=new HashMap();
      for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
        Map object = (Map)iterator.next();
        String runtimeid = (String)object.get("runtimeid");
        if (map.containsKey(runtimeid)) {
          continue;
        }
        map.put(runtimeid, runtimeid);
        dataClear.add(object);
      }
      if (dataClear.size() > size) {
        dataClear = dataClear.subList(0, size);
      }

    }

    List listWorklist = new ArrayList();
    for (Iterator iterator = dataClear.iterator(); iterator.hasNext(); ) {
      List data = new ArrayList();
      List dataid = new ArrayList();
      DataObj dataX = new DataObj();

      Map object = (Map)iterator.next();
      String actid = (String)object.get("actid");
      String runtimeid = (String)object.get("runtimeid");
      String workcode = (String)object.get("workcode");
      String startime = (String)object.get("starttime");
      String donetime = (String)object.get("donetime");
      String createtime = (String)object.get("createtime");
      String userinfo = (String)object.get("userinfo");
      String processidx = (String)object.get("processid");
      String types = (String)object.get("types");
      String statusinfo = (String)object.get("statusx");
      if ("01".equals(statusinfo))
        statusinfo = "[处理中]";
      else if ("02".equals(statusinfo))
        statusinfo = "[已归档]";
      else {
        statusinfo = "";
      }
      boolean sync = "1".equals((String)object.get("sync"));
      dataX.setStarttime(startime);
      TWfWorklistExt wfext = new TWfWorklistExt();
      dataX.setExt(wfext);
      Activity act = WfEntry.iv().loadProcess(processidx).getActivity(
        actid);
      String timeuse = timeUse(workcode);
      if (act == null) {
        act = new Activity();
        act.setName("未知节点" + statusinfo + ":" + timeuse + "小时");
      } else {
        act.setName(act.getName() + statusinfo + ":" + timeuse + "小时");
      }

      Map map = wfview.fetchRelevantvarMap(runtimeid);

      List linkToDyColumn = new ArrayList();
      if (!multiAppname)
      {
        DataField[] datax = wfview.loadProcess(processidx).getDataField();
        for (int j = 0; j < datax.length; j++) {
          Object info = map.get(datax[j].getId());
          if (info == null) {
            continue;
          }
          TWfRelevantvar name = (TWfRelevantvar)info;
          String filedid = name.getDatafieldid();
          boolean iskey = false;
          for (int i = 0; i < AppHandleIfc._CORE_KEY_VAR.length; i++)
          {
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
            if ((valuenow == null) || (valuenow.equals("null"))) {
              valuenow = "";
            }
            if (wf2dycfg != null) {
              TWfRelevant revx = (TWfRelevant)wf2dycfg.get(
                name.getDatafieldid());
              if (revx != null) {
                String script = revx.getScript();
                if (StringUtils.isNotEmpty(script)) {
                  script = StringUtils.replace(script, 
                    "#value#", valuenow);
                  WorkflowConsole console = (WorkflowConsole)
                    RmiEntry.iv("wfhandle");
                  try {
                    valuenow = console.exeScript(script, "");
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }

            }

            if (!name.getDatafieldid().startsWith("r_")) {
              data.add(valuenow);
              dataid.add(name.getDatafieldid());
            }
          }
        }
      }
      else
      {
        TWfRelevantvar name = null;
        try
        {
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
          if ((valuenow == null) || (valuenow.equals("null"))) {
            valuenow = "";
          }
          data.add(valuenow);
          dataid.add(name.getDatafieldid());
        } else {
          data.add("无待办标题配置");
          dataid.add("d0");
        }

      }

      String modetip = "";
      if (sync) {
        modetip = "同步";
      }

      String operateMode = "";
      if ("01".equals(types))
        operateMode = modetip;
      else if ("02".equals(types))
        operateMode = "抄送|" + modetip;
      else {
        operateMode = "抄阅";
      }

      AppObj app = null;
      String columnx = null;
      if (!"".equals(appname)) {
        app = AppEntry.iv().loadApp(appname);
        columnx = app.getWorklistColumn();
      }
      String[] column = (String[])null;
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
        if ((sizecolumn != 0) && (data.size() != sizecolumn - x))
        {
          if (data.size() < sizecolumn - x) {
            int sizex = data.size();
            for (int i = 0; i < sizecolumn - x - sizex; i++) {
              data.add("");
              dataid.add("xx" + i);
            }
          }
          else {
            int sizex = data.size();
            for (int i = 0; i < data.size() - (sizecolumn - x); i++) {
              data.remove(sizex - 1 - i);
              dataid.remove(sizex - 1 - i);
            }

          }

        }

      }
      else if ((sizecolumn != 0) && (data.size() != sizecolumn - 3))
      {
        if (data.size() < sizecolumn - 3) {
          int sizex = data.size();
          for (int i = 0; i < sizecolumn - 3 - sizex; i++) {
            data.add("");
            dataid.add("xx" + i);
          }
        }
        else {
          int sizex = data.size();
          for (int i = 0; i < data.size() - (sizecolumn - 3); i++) {
            data.remove(sizex - 1 - i);
            dataid.remove(sizex - 1 - i);
          }
        }

      }

      if (columnx != null) {
        if (!column[0].equals("null")) {
          String actname = "丢失当前节点" + operateMode;
          actname = act.getName() + operateMode;
          data.add(actname);
          dataid.add("w2.actname");
        }
        if (!column[1].equals("null")) {
          data.add(StringUtils.substringBefore(userinfo, "["));
          dataid.add("w2.commitername");
        }

        if (!column[2].equals("null")) {
          data.add(StringUtils.substring(startime, 0, 19));
          dataid.add("w1.starttime");
        }

      }
      else
      {
        data.add(act.getName() + operateMode);
        dataid.add("w2.actname");
        String usercode = StringUtils.substringBetween(userinfo, "[", "]");

        data.add(StringUtils.substringBefore(userinfo, "["));
        dataid.add("w2.commitername");
        data.add(StringUtils.substring(startime, 0, 19));
        dataid.add("w1.starttime");
      }

      dataX.setData((String[])data.toArray(new String[0]));
      dataX.setId((String[])dataid.toArray(new String[0]));

      String look = "";
      if ("00".equals(listType)) {
        look = "&query=look";
      }
      dataX.setUrl(wfext.getBussurl() + wfext.getBussid() + "&workcode=" + 
        workcode + "&operatemode=" + types + urlEnd + 
        "&commiter=" + 
        StringUtils.substringBetween(userinfo, "[", "]") + look);
      listWorklist.add(dataX);
    }
    return listWorklist;
    //return reSortWorklist(listWorklist);
  }
  /**
   * 先前做的流程节点合并需要先以runtimeid来排序，从而导致时间无法倒序，所以重新加个算法对待办列表进行二次按时间排序
   */
  private List reSortWorklist(List listWorklist ){
	    List timegroup=new ArrayList();
	    Map maptimeGroup=new HashMap();
	    for (Iterator iterator = listWorklist.iterator(); iterator.hasNext(); ) {
	      DataObj object = (DataObj)iterator.next();
	      String starttime=object.getStarttime();
	      long timeValue=0;
	      try{
	    	  timeValue=Timestamp.valueOf(starttime).getTime();
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
	      if(maptimeGroup.containsKey(timeValue)){
	    	 for(int i=0;i<100;i++){//有可能两个节点的时间相同那么修改时间，为了安全起见最多循环100次
	    		 timeValue=timeValue+1;
	    		 if(!maptimeGroup.containsKey(timeValue)){
	    			 break;
	    		 }
	    	 }
	      }
	      timegroup.add(timeValue);
	      maptimeGroup.put(timeValue, object);
	    }
	    Long [] timegroupArr=(Long[])timegroup.toArray(new Long[0]);
	    Arrays.sort(timegroupArr);
	    
	    List newSort=new ArrayList();
	    for (int i = timegroupArr.length-1; i >=0 ; i--) {
			Object obj=maptimeGroup.get(timegroupArr[i]);
			newSort.add(obj);
		}

	    return newSort;
  }

  private String getUserPhone(String usercode)
  {
    try {
      List list = DbTools.queryData("select phone phone from netone.t_cs_user where usercode='" + usercode + "'");
      return ((Map)list.get(0)).get("phone").toString();
    } catch (Exception e) {
      e.printStackTrace();
    }return "";
  }

  public void addTimeUse(String starttime, String endtime, Activity act)
  {
    if (StringUtils.isEmpty(endtime))
      return;
    try
    {
      long starttimeV = Timestamp.valueOf(starttime).getTime();
      long endtimeV = Timestamp.valueOf(endtime).getTime();
      long use = (endtimeV - starttimeV) / 60000L;
      act.setName(act.getName() + "(使用" + use + "分)");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int fetchQueryColumnIndex(String appname, String columnid) throws Exception
  {
    List listdata = listQueryColumn(appname);
    for (Iterator iterator = listdata.iterator(); iterator.hasNext(); ) {
      QueryColumn object = (QueryColumn)iterator.next();
      if (columnid.equals(object.getId())) {
        return object.getIndex();
      }
    }
    throw new RuntimeException("无效索引号");
  }

  public static void main(String[] args) throws Exception {
    WorklistViewIfc wf = new WorklistVIewImpl();
    int x = wf.count("liyi", "APPFRAME.APPFRAME.NDYD", true, "01", null);
    System.out.println(x);

    List list = wf.worklist("liyi", "APPFRAME.APPFRAME.NDYD", true, 0, 10, 
      "01", null);
    for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
      DataObj object = (DataObj)iterator.next();
      System.out.println();
    }

    List listx = wf.listQueryColumn("APPFRAME.APPFRAME.NDYD");
    for (Iterator iterator = listx.iterator(); iterator.hasNext(); ) {
      QueryColumn object = (QueryColumn)iterator.next();
      System.out.println();
    }

    int index = wf.fetchQueryColumnIndex("APPFRAME.APPFRAME.NDYD", 
      "participant");
    System.out.println(index);
  }

  private String timeUse(String workcode) {
    String sql = "select starttime stx,endtime enx from netone.t_wf_runtime where runtimeid in(select runtimeid from netone.t_wf_worklist where workcode='" + workcode + "')";
    List list = DbTools.queryData(sql);
    Map map = (Map)list.get(0);
    String stx = (String)map.get("stx");
    String enx = (String)map.get("enx");
    long time1 = Timestamp.valueOf(stx).getTime();
    long time2 = System.currentTimeMillis();
    if (enx != null) {
      time2 = Timestamp.valueOf(enx).getTime();
    }
    return String.valueOf((time2 - time1) / 3600000L);
  }

  public List<String> worklistOnlyLsh(String clientId, String appname, boolean mode, int from, int size, String listType, QueryColumn query)
    throws Exception
  {
    CupmRmi cupm = null;
    WorkflowView wfview = null;
    try {
      wfview = (WorkflowView)RmiEntry.iv("wfview");
      cupm = (CupmRmi)RmiEntry.iv("cupm");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    String opemode = "('01','02')";
    if (!mode) {
      opemode = "('03','04')";
    }
    String processidStr = "";
    String processidstr_detail = "";
    String naturalname_detail = "";
    boolean multiAppname = false;
    int sizecolumn = 0;
    Map wf2dycfg = null;
    if (StringUtils.isNotEmpty(appname))
    {
      String[] appnameall = appname.split(",");
      if (appnameall.length > 1) {
        multiAppname = true;
      }
      else if (appnameall.length == 1) {
        List columnx = listQueryColumn(appname);
        sizecolumn = columnx.size();
        wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
      }

      StringBuffer but = new StringBuffer();
      for (int i = 0; i < appnameall.length; i++) {
        AppObj app = AppEntry.iv().loadApp(appnameall[i]);
        but.append(",'" + app.getWorkflowCode_() + "'");
        naturalname_detail = app.getWorkflowCode_();
      }

      processidStr = "and w1.processid in(" + but.toString().substring(1) + 
        ")";
      processidstr_detail = "w1.processid in(" + but.toString().substring(1) + 
        ")";
    } else {
      multiAppname = true;
    }

    String urlEnd = "";

    String loadworklist = "";
    String loadworklist_detail = "";
    String condition = "";
    boolean flag = false;
    if ((query != null) && (!query.getValue().equals(""))) {
      flag = true;
      if (!query.isTime()) {
        condition = condition + " and " + query.getId() + " like '%" + 
          query.getValue() + "%' ";
      } else {
        String fromtime = StringUtils.substringBefore(query.getValue(), 
          "_");
        String totime = StringUtils.substringAfter(query.getValue(), 
          "_");
        if (!fromtime.trim().equals("")) {
          condition = condition + " and " + query.getId() + ">='" + fromtime + 
            "'";
        }
        if (!totime.trim().equals("")) {
          condition = condition + " and " + query.getId() + "<='" + totime + "'";
        }
      }
    }
    String conditionx = null;
    if ((query != null) && (query.getOrder() != null) && 
      (!"".equals(query.getOrder()))) {
      String str = condition;
      if (StringUtils.isEmpty(str)) {
        str = "";
      }
      condition = str;
      if (flag)
        conditionx = str;
      else {
        conditionx = "";
      }

    }

    if ("01".equals(listType))
    {
      if (opemode.equals("('03')")) {
        loadworklist = "select distinct w3.lsh lshx from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01','02')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition + " limit " + from + "," + size;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
      else if (opemode.equals("('04')")) {
        loadworklist = "select  distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition + " limit " + from + "," + size;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", "1=1");
      }
      else
      {
        loadworklist = "select distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='" + 
          clientId + 
          "' and w2.statusnow='01" + 
          "' " + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition + " limit " + from + "," + size;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
    } else if ("02".equals(listType)) {
      urlEnd = "&query=look&cuibang=true";

      loadworklist = "select w3.lsh lshx from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition + " limit " + from + "," + size;

      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    } else if ("03".equals(listType))
    {
      urlEnd = "&query=look";

      loadworklist = "select distinct w3.lsh lshx from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition + " limit " + from + "," + size;
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }
    else {
      loadworklist = "select distinct w3.lsh lshx from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition + " limit " + from + "," + size;
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }

    List list = null;

    if ("adminx".equals(clientId)) {
      list = wfview.coreSqlview(loadworklist_detail);
    } else {
      if (!multiAppname) {
        String workflowcode = AppEntry.iv().loadApp(appname).getWorkflowCode_();
        boolean admin = SecurityEntry.iv().permission(clientId, workflowcode);
        if (admin) {
          list = wfview.coreSqlview(loadworklist_detail);
        }
      }
      list = wfview.coreSqlview(loadworklist);
    }
    List listfinal = new ArrayList();
    for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
      Map object = (Map)iterator.next();
      String lsh = (String)object.get("lshx");
      listfinal.add(lsh);
    }
    return listfinal;
  }

  public int countOnlyLsh(String clientId, String appname, boolean mode, String listType, QueryColumn query)
    throws Exception
  {
    CupmRmi cupm = null;
    WorkflowView wfview = null;
    try {
      wfview = (WorkflowView)RmiEntry.iv("wfview");
      cupm = (CupmRmi)RmiEntry.iv("cupm");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    String opemode = "('01','02')";
    if (!mode) {
      opemode = "('03','04')";
    }
    String processidStr = "";
    String processidstr_detail = "";
    String naturalname_detail = "";
    boolean multiAppname = false;
    int sizecolumn = 0;
    Map wf2dycfg = null;
    if (StringUtils.isNotEmpty(appname))
    {
      String[] appnameall = appname.split(",");
      if (appnameall.length > 1) {
        multiAppname = true;
      }
      else if (appnameall.length == 1) {
        List columnx = listQueryColumn(appname);
        sizecolumn = columnx.size();
        wf2dycfg = AppEntry.iv().wf2dyformBindCfg2(appname);
      }

      StringBuffer but = new StringBuffer();
      for (int i = 0; i < appnameall.length; i++) {
        AppObj app = AppEntry.iv().loadApp(appnameall[i]);
        but.append(",'" + app.getWorkflowCode_() + "'");
        naturalname_detail = app.getWorkflowCode_();
      }

      processidStr = "and w1.processid in(" + but.toString().substring(1) + 
        ")";
      processidstr_detail = "w1.processid in(" + but.toString().substring(1) + 
        ")";
    } else {
      multiAppname = true;
    }

    String urlEnd = "";

    String loadworklist = "";
    String loadworklist_detail = "";
    String condition = "";
    boolean flag = false;
    if ((query != null) && (!query.getValue().equals(""))) {
      flag = true;
      if (!query.isTime()) {
        condition = condition + " and " + query.getId() + " like '%" + 
          query.getValue() + "%' ";
      } else {
        String fromtime = StringUtils.substringBefore(query.getValue(), 
          "_");
        String totime = StringUtils.substringAfter(query.getValue(), 
          "_");
        if (!fromtime.trim().equals("")) {
          condition = condition + " and " + query.getId() + ">='" + fromtime + 
            "'";
        }
        if (!totime.trim().equals("")) {
          condition = condition + " and " + query.getId() + "<='" + totime + "'";
        }
      }
    }
    String conditionx = null;
    if ((query != null) && (query.getOrder() != null) && 
      (!"".equals(query.getOrder()))) {
      String str = condition;
      if (StringUtils.isEmpty(str)) {
        str = "";
      }
      condition = str;
      if (flag)
        conditionx = str;
      else {
        conditionx = "";
      }

    }

    if ("01".equals(listType))
    {
      if (opemode.equals("('03')")) {
        loadworklist = "select distinct w3.lsh lshx from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01','02')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
      else if (opemode.equals("('04')")) {
        loadworklist = "select  distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS IN('01','02') AND w2.usercode='" + 
          clientId + 
          "' and w2.statusnow in ('01')" + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", "1=1");
      }
      else
      {
        loadworklist = "select distinct w3.lsh lshx  from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE left join netone.t_wf_relevantvar_tmp w3 on w1.runtimeid=w3.runtimeid where w1.EXECUTESTATUS='01' and w2.usercode='" + 
          clientId + 
          "' and w2.statusnow='01" + 
          "' " + 
          processidStr + 
          " and w2.types in " + 
          opemode + 
          condition;
        loadworklist_detail = StringUtils.replace(loadworklist, "w2.usercode='" + clientId + "'", " 1=1 ");
      }
    } else if ("02".equals(listType)) {
      urlEnd = "&query=look&cuibang=true";

      loadworklist = "select w3.lsh lshx from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.EXECUTESTATUS='01' and w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='01' and runtimeid in( select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition;

      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    } else if ("03".equals(listType))
    {
      urlEnd = "&query=look";

      loadworklist = "select distinct w3.lsh lshx from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where STATUSNOW='02' and runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where STATUSNOW='02' and USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition;
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }
    else {
      loadworklist = "select distinct w3.lsh lshx from  netone.t_wf_worklist w1,netone.t_wf_participant w2 ,netone.t_wf_relevantvar_tmp w3 where w1.RUNTIMEID in(select runtimeid from netone.t_wf_runtime where runtimeid in(select distinct runtimeid from netone.t_wf_worklist where workcode in(select workcode from netone.t_wf_participant where USERCODE='" + 
        clientId + "')))" + 
        "and w1.workcode=w2.workcode and w1.runtimeid=w3.runtimeid " + processidStr + condition;
      loadworklist_detail = StringUtils.replace(loadworklist, " USERCODE='" + clientId + "'", " 1=1 ");
    }

    List list = null;

    if ("adminx".equals(clientId)) {
      list = wfview.coreSqlview(loadworklist_detail);
    } else {
      if (!multiAppname) {
        String workflowcode = AppEntry.iv().loadApp(appname).getWorkflowCode_();
        boolean admin = SecurityEntry.iv().permission(clientId, workflowcode);
        if (admin) {
          list = wfview.coreSqlview(loadworklist_detail);
        }
      }
      list = wfview.coreSqlview(loadworklist);
    }

    return list.size();
  }
}