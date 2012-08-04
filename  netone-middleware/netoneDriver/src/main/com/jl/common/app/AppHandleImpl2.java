package com.jl.common.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.web.WebCache;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AnalysisAppSecond;
import com.jl.common.app.impl2.AnalysisAppThree;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.app.impl2.AppSecond;
import com.jl.common.app.impl2.AppThree;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfRelevant;
import com.jl.common.workflow.WfEntry;

/**
 * 基于新的Flex配置框架下的应用解析
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class AppHandleImpl2 implements AppHandleIfc {

	public AppObj loadApp(String naturalname) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = rs.loadResourceByNatural(naturalname);
		AppFirst list = AnalysisAppFirst.readXML(upo.getExtendattribute());
		AppObj appobj = new AppObj();
		appobj.setDyformCode_(list.getFormcode());
		appobj.setDyformName_(list.getFormname());
		appobj.setWorkflowCode_(list.getProcessid());
		appobj.setWorkflowName_(list.getProcessname());
		appobj.setWorklistsize(list.getWorklistsize());
		appobj.setFormtitle(list.getFormtitle());
		appobj.setFormendtitle(list.getFormtitle());
		
		UmsProtectedobject upo2=new UmsProtectedobject();
		upo2.setNaturalname("BUSSFORM.BUSSFORM.%");
		upo2.setExtendattribute(list.getFormcode());
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if(formlist.size()==1){
			String name=((UmsProtectedobject)formlist.get(0)).getNaturalname();
			appobj.setFormnatualname(name);
		}
		
		String worklistDefaultColumn = list.getWorklistDefaultColumn();
		if (StringUtils.isNotEmpty(worklistDefaultColumn)) {
			appobj.setWorklistColumn(StringUtils.substringBetween(
					worklistDefaultColumn, "[", "]"));
		}

		// TODO Auto-generated method stub
		return appobj;
	}

	public TWfActive loadCfgActive(String naturalname, String actid,
			String commiter, String runtimeid) throws Exception {
		String key=runtimeid+actid+commiter+naturalname;
		if(WebCache.containCache(key)){
			return (TWfActive)WebCache.getCache(key);
		}
		
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		String naturalnamex = naturalname + "." + actid;
		UmsProtectedobject upo = rmi.loadResourceByNatural(naturalnamex);

		AppObj app = this.loadApp(naturalname);

		WorkflowProcess wf = WfEntry.iv().loadProcess(app.getWorkflowCode_());
		Activity act = wf.getActivity(actid);
 
		AppSecond object = AnalysisAppSecond.readXML(upo.getExtendattribute());

		TWfActive actx= loadCfgActiveCore(act, commiter, object, runtimeid);
		long time=System.currentTimeMillis() + 1800000L;
		Date dateinfo = new Date(time);
		WebCache.setCache(key, actx,dateinfo );
		return actx;
	}

	public List wf2dyformBindCfg(String naturalname) throws Exception {
		String processidStr = "";
		if (naturalname == null || naturalname.equals("")) {
			return new ArrayList();
		}
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");

		AppObj app = this.loadApp(naturalname);
		WorkflowProcess wf = WfEntry.iv().loadProcess(app.getWorkflowCode_());
		DataField[] data = wf.getDataField();
		List list = new ArrayList();
		for (int i = 0; i < data.length; i++) {
			String fileid = data[i].getId();
			boolean isKeyvar = false;
			for (int j = 0; j < _CORE_KEY_VAR.length; j++) {
				// 需要忽略掉流程内部关键变量
				if (_CORE_KEY_VAR[j].equalsIgnoreCase(fileid)) {
					isKeyvar = true;
					break;
				}
			}
			if (isKeyvar)
				continue;

			if (fileid.startsWith("r_")) {
				continue;
			}

			UmsProtectedobject upox = rmi.loadResourceByNatural(naturalname
					+ "." + data[i].getId().toUpperCase());
			AppThree tre = AnalysisAppThree.readXML(upox.getExtendattribute());
			TWfRelevant rev = new TWfRelevant();
			rev.setRev2column(StringUtils.substringBetween(tre.getFormcolumn(),
					"[", "]"));
			rev.setRev2formcode(app.getDyformCode_());
			rev.setRevid(fileid);
			rev.setRevname(data[i].getName());
			String script = tre.getScript();
			if (script != null && script.contains("return ")) {
				rev.setScript(script);
			}
			if (StringUtils.isNotEmpty(tre.getLength())) {
				rev.setLength(tre.getLength());
			} else {
				rev.setLength("100");
			}
			if (StringUtils.isNotEmpty(tre.getActname())) {
				rev.setActname(tre.getActname());
			} else {
				rev.setActname("100");
			}
			if (StringUtils.isNotEmpty(tre.getCommitername())) {
				rev.setCommitername(tre.getCommitername());
			} else {
				rev.setCommitername("100");
			}
			if (StringUtils.isNotEmpty(tre.getStarttime())) {
				rev.setStarttime(tre.getStarttime());
			} else {
				rev.setStarttime("100");
			}

			list.add(rev);
		}
		return list;
	}

	public Map wf2dyformBindCfg2(String naturalname) throws Exception {
		List list = wf2dyformBindCfg(naturalname);
		Map map = new HashMap();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfRelevant object = (TWfRelevant) iterator.next();
			map.put(object.getRevid(), object);
		}
		return map;
	}

	public List wf2participantBindCfg(String naturalname, String commiter,
			String runtimeid) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		AppObj app = this.loadApp(naturalname);
		WorkflowProcess wf = WfEntry.iv().loadProcess(app.getWorkflowCode_());
		Activity[] act = wf.getActivity();
		List list = new ArrayList();
		for (int i = 0; i < act.length; i++) {
			UmsProtectedobject upo = rmi.loadResourceByNatural(naturalname
					+ "." + act[i].getId());
			AppSecond object = AnalysisAppSecond.readXML(upo
					.getExtendattribute());
			TWfActive actxy = loadCfgActiveCore(act[i], commiter, object,
					runtimeid);
			list.add(actxy);
		}
		return list;
	}

	private String countWorkItem(String particiapntArr){
		if(StringUtils.isEmpty(particiapntArr)){
			return particiapntArr;
		}
		String []datax=StringUtils.split(particiapntArr,",");
		
		for (int i = 0; i < datax.length; i++) {
			String info=StringUtils.substringBetween(datax[i],"[","]");
		try{
			List list=WfEntry.iv().useCoreView().coreSqlview("select count(*) cou from netone.t_wf_participant where usercode='"+info+"' and statusnow='01'");
			String data= ((Map)list.get(0)).get("cou").toString();
			String deptname=SecurityEntry.iv().loadUser(info).getDeptname();

			particiapntArr=StringUtils.replace(particiapntArr, "["+info+"]", "(/部门:"+deptname+"/工作量:"+data+")["+info+"]");
	
		}catch(Exception e){
			e.printStackTrace();
		}
		}

		return particiapntArr;
	}
	
	private TWfActive loadCfgActiveCore(Activity act, String commiter,
			AppSecond as, String runtimeid) throws Exception {
		TWfActive actx = new TWfActive();

		actx.setId(act.getId());
		actx.setName(act.getName());
		String objtype = as.getParticipantmode();
		actx.setParticipantmode(objtype);
		String extinfo = as.getParticipantvalue();
		if (!_PARTICIPANT_MODE_HUMAN.equals(objtype)
				&& StringUtils.isNotEmpty(commiter)
				&& StringUtils.isNotEmpty(runtimeid)) {
			// 除了人员外全部都不支持多选择，这里需要做个处理

			if (extinfo != null) {
				String[] extinfoarr = extinfo.split(",");

				if (_PARTICIPANT_MODE_DEPT.equals(objtype)) {
					actx.setParticipant(SecurityEntry.iv().listUserByDeptId(
							extinfoarr));
				} else if (_PARTICIPANT_MODE_ROLE.equals(objtype)) {
					actx.setParticipant(SecurityEntry.iv().listUserByRoleId(
							extinfoarr, commiter, false));
				} else if (this._PARTICIPANT_MODE_TEAM.equals(objtype)) {
					actx.setParticipant(SecurityEntry.iv().listUserByTeamId(
							extinfoarr));
				} else if (this._PARTICIPANT_MODE_FLOWROLE.equals(objtype)) {
					actx.setParticipant(SecurityEntry.iv().listUserByRoleId(
							extinfoarr, commiter, true));
				} else if (this._PARTICIPANT_MODE_CREATER.equals(objtype)) {
					String rev = WfEntry.iv().getVarByRuntimeId(runtimeid,
							TWfConsoleIfc._DEFAULT_REV_KEY_CUSTOMER);
					actx.setParticipant(rev);
				}
			}
		} else {
			actx.setParticipant(extinfo);
		}

		//追加人员工作量
		String addWorkItemParticipant=countWorkItem(actx.getParticipant());
		actx.setParticipant(addWorkItemParticipant);
		
		// 无论是选部门、角色、组还是流程角色最终都表现为人的选择
		actx.setParticipantmode(_PARTICIPANT_MODE_HUMAN);
		if (this._PARTICIPANT_MODE_CREATER.equals(objtype)) {
			actx.setParticipantmode(this._PARTICIPANT_MODE_CREATER);
		}
		String sm = as.getSyncmode();

		actx.setNeedsync("3".equals(sm));
		actx.setSingleman("1".equals(sm));
		actx.setAutoroute(!"1".equals(as.getIsmanual()));

		String coworker = as.getCoworkmode();

		if ("2".equals(coworker)) {
			actx.setNeedAssistant(true);
		} else if ("3".equals(coworker)) {
			actx.setNeedReader(true);
		} else if ("4".equals(coworker)) {
			actx.setNeedReader(true);
			actx.setNeedAssistant(true);
		}
		actx.setNeedTree("1".equals(as.getNeedtree()));
		actx.setFormEdit("1".equals(as.getNeedformedit()));
		actx.setEditcolumn(as.getFormedit());
		actx.setFobitzb("1".equals(as.getFobidzb()));

		boolean distrubsubmit = false;
		if ("1".equals(as.getZibiaodanmoshi())) {
			distrubsubmit = true;
		}
		actx.setSyncto(distrubsubmit);

		Map subformmode = new HashMap();// 控制子表单的
		subformmode.put("MAINFORM", as.getMAINFORM());
		String str = as.getSubfrommode();
		String [] arr=str.split(",");
		if (str != null) {
			for (int i = 0; i < arr.length; i++) {
					subformmode.put(i, arr[i]);  // 子表单模式，0 编辑，1 只读，2 隐藏
			}
		}
        actx.setFilefunction(as.getFilemanage()); //是否启用附件功能
        actx.setFiletext(as.getFiletext());  //附件提示
		actx.setSubformmode(subformmode);
		actx.setSubcolumn(as.getSubcolumn());
		return actx;
	}

	public boolean canCreate(String natrualname, String userid)
			throws Exception {

		String processid = this.loadApp(natrualname).getWorkflowCode_();
		Activity[] act = WfEntry.iv().loadProcess(processid).getActivity();
		// 寻找首节点
		String firstActname = null;
		for (int i = 0; i < act.length; i++) {
			if (act[i].isStartActivity()) {
				firstActname = act[i].getId();
				break;
			}
		}
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		String naturalnamex = natrualname + "." + firstActname;
		UmsProtectedobject upo = rmi.loadResourceByNatural(naturalnamex);

		AppSecond as = AnalysisAppSecond.readXML(upo.getExtendattribute());

		String objtype = as.getParticipantmode();
		String userlist = "";

		if (!_PARTICIPANT_MODE_HUMAN.equals(objtype)) {
			// 除了人员外全部都不支持多选择，这里需要做个处理
			String extinfo = as.getParticipantvalue();
			if (extinfo != null) {
				String[] extinfoarr = extinfo.split(",");
				if (_PARTICIPANT_MODE_DEPT.equals(objtype)) {
					userlist = SecurityEntry.iv().listUserByDeptId(extinfoarr);
				} else if (_PARTICIPANT_MODE_ROLE.equals(objtype)) {
					userlist = SecurityEntry.iv().listUserByRoleId(extinfoarr,
							null, false);
				} else if (this._PARTICIPANT_MODE_TEAM.equals(objtype)) {
					userlist = SecurityEntry.iv().listUserByTeamId(extinfoarr);
				} else if (this._PARTICIPANT_MODE_FLOWROLE.equals(objtype)) {
					userlist = SecurityEntry.iv().listUserByRoleId(extinfoarr,
							userid, true);
				} else if (this._PARTICIPANT_MODE_CREATER.equals(objtype)) {
					return true;
				}
			}
		} else {
			userlist = upo.getExtendattribute();
		}

		if (StringUtils.isNotEmpty(userlist)) {
			String userlistx[] = userlist.split(",");
			if (userlistx != null && userlistx.length > 0) {
				for (int i = 0; i < userlistx.length; i++) {
					if (userlistx[i].equals(userid)) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

}
