package com.jl.common.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.frame.web.WebCache;
import oe.frame.web.util.WebStr;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfConsoleIfc;
import com.jl.common.workflow.TWfRelevant;
import com.jl.common.workflow.WfEntry;

public final class AppHandleImpl implements AppHandleIfc {
	
	public AppObj loadApp(String naturalname) throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		// 支持新的表单定制模式
		AppObj app = new AppObj();
		// 支持新的表单定制模式
		if (StringUtils.isEmpty(naturalname)) {
			return app;
		}
		UmsProtectedobject upo = rs.loadResourceByNatural(naturalname);
		//针对预览用的
		if (naturalname.startsWith("BUSSFORM.BUSSFORM.")) {

			app.setDyformCode_(upo.getExtendattribute());
			app.setWorkflowCode_("BUSSWF.BUSSWF.TMP.SAMPLE");
			return app;
		}
		if (upo != null) {
			String extinfo = upo.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					return AppEntry.iv2().loadApp(naturalname);
				}
			}
		}

		BeanUtils.copyProperties(app, upo);
		String template=app.getDescription();
		if(StringUtils.isNotEmpty(template)){
			app.setDescription(StringUtils.substringBetween(template, "[","]"));
		}
		String actionurl = app.getActionurl();
		if (actionurl != null) {
			String[] appinfo = StringUtils.split(actionurl, ";");
			if (appinfo != null && appinfo.length == 2) {
				app.setDyformCode_(StringUtils.substringBetween(appinfo[1],
						"[", "]"));
				app.setWorkflowCode_(StringUtils.substringBetween(appinfo[0],
						"[", "]"));
			}
		}
		String ext = upo.getExtendattribute();
		if (StringUtils.isNotEmpty(ext)) {
			String worklistcolumn = StringUtils.substringBetween(ext,
					"worklist[", "]");
			if (StringUtils.isNotEmpty(worklistcolumn))
				app.setWorklistColumn(worklistcolumn);
		}

		app.setFormtitle(upo.getName());
		return app;
	}

	public List wf2dyformBindCfg(String naturalname) throws Exception {
		String processidStr = "";
		if (naturalname == null || naturalname.equals("")) {
			return new ArrayList();
		}
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// 支持新的表单定制模式
		UmsProtectedobject upox = rmi.loadResourceByNatural(naturalname);
		if (upox != null) {
			String extinfo = upox.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					return AppEntry.iv2().wf2dyformBindCfg(naturalname);
				}
			}
		}
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

			UmsProtectedobject upo = new UmsProtectedobject();
			try{
				upo=rmi.loadResourceByNatural(naturalname
						+ "." + data[i].getId().toUpperCase());
			}catch(Exception e){
				e.printStackTrace();
			}
			TWfRelevant rev = new TWfRelevant();
			rev.setRev2column(upo.getActionurl());
			rev.setRev2formcode(upo.getExtendattribute());
			rev.setRevid(fileid);
			rev.setRevname(data[i].getName());
			String script = upo.getObjecttype();
			if (script != null && script.contains("return ")) {
				rev.setScript(script);
			}
			// 配置待办列表显示字段长度 格式$length:xxx!;
			if (StringUtils.contains(script, "$length:")) {
				String lengthx = StringUtils.substringBetween(script,
						"$length:", "!;");
				if (StringUtils.isNotEmpty(lengthx)) {
					rev.setLength(lengthx);
				} else {
					lengthx = "100";
					rev.setLength(lengthx);
				}
			}
			// 配置待办列表显示固定字段长度 一共三个
			// 当前节点：actname、提交者：commitername、创建时间：starttime.格式:$actname:xxx!;
			// $commitername:xxx!; $starttime:xxx!;
			if (StringUtils.contains(script, "$actname:")) {
				String actname = StringUtils.substringBetween(script,
						"$actname:", "!;");
				if (StringUtils.isNotEmpty(actname)) {
					rev.setActname(actname);
				} else {
					actname = "100";
					rev.setActname(actname);
				}
			}
			if (StringUtils.contains(script, "$commitername:")) {
				String commitername = StringUtils.substringBetween(script,
						"$commitername:", "!;");
				if (StringUtils.isNotEmpty(commitername)) {
					rev.setCommitername(commitername);
				} else {
					commitername = "100";
					rev.setCommitername(commitername);
				}
			}
			if (StringUtils.contains(script, "$starttime:")) {
				String starttime = StringUtils.substringBetween(script,
						"$starttime:", "!;");
				if (StringUtils.isNotEmpty(starttime)) {
					rev.setStarttime(starttime);
				} else {
					starttime = "100";
					rev.setStarttime(starttime);
				}
			}
			list.add(rev);
		}
		return list;
	}

	public Map wf2dyformBindCfg2(String naturalname) throws Exception {
		String processidStr = "";
		if (naturalname == null || naturalname.equals("")) {
			return new HashMap();
		}
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// 支持新的表单定制模式
		UmsProtectedobject upox = rmi.loadResourceByNatural(naturalname);
		if (upox != null) {
			String extinfo = upox.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					return AppEntry.iv2().wf2dyformBindCfg2(naturalname);
				}
			}
		}
		AppObj app = this.loadApp(naturalname);
		WorkflowProcess wf = WfEntry.iv().loadProcess(app.getWorkflowCode_());
		DataField[] data = wf.getDataField();
		Map map = new HashMap();
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
			UmsProtectedobject upo =new UmsProtectedobject();
            try{
            	upo=rmi.loadResourceByNatural(naturalname
					+ "." + data[i].getId().toUpperCase());
            }catch(Exception e){
            	e.printStackTrace();
            }
			TWfRelevant rev = new TWfRelevant();
			rev.setRev2column(upo.getActionurl());
			rev.setRev2formcode(upo.getExtendattribute());
			rev.setRevid(fileid);
			rev.setRevname(data[i].getName());
			String script = upo.getObjecttype();
			if (script != null && script.contains("return ")) {
				rev.setScript(script);
			}
			// 配置待办列表显示字段长度 格式$length:xxx!;
			if (StringUtils.contains(script, "$length:")) {
				String lengthx = StringUtils.substringBetween(script,
						"$length:", "!;");
				if (StringUtils.isNotEmpty(lengthx)) {
					rev.setLength(lengthx);
				} else {
					lengthx = "100";
					rev.setLength(lengthx);
				}
			}
			// 配置待办列表显示固定字段长度 一共三个
			// 当前节点：actname、提交者：commitername、创建时间：starttime.格式:$actname:xxx!;
			// $commitername:xxx!; $starttime:xxx!;
			if (StringUtils.contains(script, "$actname:")) {
				String actname = StringUtils.substringBetween(script,
						"$actname:", "!;");
				if (StringUtils.isNotEmpty(actname)) {
					rev.setActname(actname);
				} else {
					actname = "100";
					rev.setActname(actname);
				}
			}
			if (StringUtils.contains(script, "$commitername:")) {
				String commitername = StringUtils.substringBetween(script,
						"$commitername:", "!;");
				if (StringUtils.isNotEmpty(commitername)) {
					rev.setCommitername(commitername);
				} else {
					commitername = "100";
					rev.setCommitername(commitername);
				}
			}
			if (StringUtils.contains(script, "$starttime:")) {
				String starttime = StringUtils.substringBetween(script,
						"$starttime:", "!;");
				if (StringUtils.isNotEmpty(starttime)) {
					rev.setStarttime(starttime);
				} else {
					starttime = "100";
					rev.setStarttime(starttime);
				}
			}
			map.put(fileid, rev);
		}
		return map;
	}

	public List<TWfActive> wf2participantBindCfg(String naturalname,
			String commiter, String runtimeid) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// 支持新的表单定制模式
		UmsProtectedobject upox = rmi.loadResourceByNatural(naturalname);
		if (upox != null) {
			String extinfo = upox.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					return AppEntry.iv2().wf2participantBindCfg(naturalname,
							commiter, runtimeid);
				}
			}
		}
		AppObj app = this.loadApp(naturalname);
		WorkflowProcess wf = WfEntry.iv().loadProcess(app.getWorkflowCode_());
		Activity[] act = wf.getActivity();
		List list = new ArrayList();
		for (int i = 0; i < act.length; i++) {
			UmsProtectedobject upo = rmi.loadResourceByNatural(naturalname
					+ "." + act[i].getId());
			TWfActive actxy = loadCfgActiveCore(act[i], commiter, upo,
					runtimeid);
			list.add(actxy);
		}
		return list;
	}

	public TWfActive loadCfgActive(String naturalname, String actid,
			String commiter, String runtimeid) throws Exception {
//		String key=runtimeid+actid+commiter+naturalname;
//		if(WebCache.containCache(key)){
//			return (TWfActive)WebCache.getCache(key);
//		}
		
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// 支持新的表单定制模式
		UmsProtectedobject upox = rmi.loadResourceByNatural(naturalname);
		if (upox != null) {
			String extinfo = upox.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					return AppEntry.iv2().loadCfgActive(naturalname, actid,
							commiter, runtimeid);
				}
			}
		}

		UmsProtectedobject upo = rmi.loadResourceByNatural(naturalname + "."
				+ actid);

		AppObj app = this.loadApp(naturalname);

		WorkflowProcess wf = WfEntry.iv().loadProcess(app.getWorkflowCode_());
		Activity act = wf.getActivity(actid);

		TWfActive actx= loadCfgActiveCore(act, commiter, upo, runtimeid);
	
		//WebCache.setCache(key, actx,dateinfo );
		return actx;
		
	}

	private TWfActive loadCfgActiveCore(Activity act, String commiter,
			UmsProtectedobject upo, String runtimeid) throws Exception {
		TWfActive actx = new TWfActive();

		actx.setId(act.getId());
		actx.setName(act.getName());
		String objtype = upo.getObjecttype();
		actx.setParticipantmode(objtype);
		if (!_PARTICIPANT_MODE_HUMAN.equals(objtype)
				&& StringUtils.isNotEmpty(commiter)
				&& StringUtils.isNotEmpty(runtimeid)) {
			// 除了人员外全部都不支持多选择，这里需要做个处理
			String extinfo = upo.getExtendattribute();
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
				} else if (this._PARTICIPANT_MODE_FLOWROLECREATER
						.equals(objtype)) {
					String rev = WfEntry.iv().getVarByRuntimeId(runtimeid,
							TWfConsoleIfc._DEFAULT_REV_KEY_CUSTOMER);
					String commiterid = StringUtils.substringBetween(rev, "[",
							"]");
					actx.setParticipant(SecurityEntry.iv().listUserByRoleId(
							extinfoarr, commiterid, true));
				}
			}
		} else {
			actx.setParticipant(upo.getExtendattribute());
		}
		if(StringUtils.isNotEmpty(commiter)){
		//追加人员工作量
			String addWorkItemParticipant=addUserAppendInfo(actx.getParticipant(),2);
			actx.setParticipant(addWorkItemParticipant);
		}
		// 无论是选部门、角色、组还是流程角色最终都表现为人的选择
		actx.setParticipantmode(_PARTICIPANT_MODE_HUMAN);
		if (this._PARTICIPANT_MODE_CREATER.equals(objtype)) {
			actx.setParticipantmode(this._PARTICIPANT_MODE_CREATER);
		}

		actx.setNeedsync("3".equals(upo.getActionurl()));
		actx.setSingleman("1".equals(upo.getActionurl()));
		actx.setAutoroute(!"1".equals(upo.getActive()));

		String description = upo.getDescription();
		String syncmode = "";
		boolean needtree = false;
		boolean formedit = false;
		boolean fobitzb = false;
		boolean flag = true;
		Map subformmode = new HashMap();// 控制子表单的
		String perssiomcolumn = "";
		String syncto = "";
		boolean distrubsubmit = false;
		if (description != null) {
			syncmode = StringUtils.substringBefore(description, "-");
			needtree = "1".equals(StringUtils.substringBetween(description,
					"-", "$"));
			fobitzb = "1".equals(StringUtils.substringBetween(description, "@",
					"%"));
			formedit = "1".equals(StringUtils.substringBetween(description,
					"$", "#"));
			String str = StringUtils.substringBetween(description, "+", "!");
			syncto = StringUtils.substringBetween(description, "^", "^");
			if ("1".equals(syncto)) {
				distrubsubmit = true;
			} else {
				distrubsubmit = false;
			}
			if (str != null) {
				for (int i = 0; i < str.length(); i++) {
					if (str.charAt(i) == '1') {
						flag = true;
						subformmode.put(i, flag);
					} else {
						flag = false;
						subformmode.put(i, flag);
					}
				}
			} else {
				subformmode = null;
			}

			perssiomcolumn = StringUtils.substringAfter(description, "#");
			perssiomcolumn = StringUtils.replace(perssiomcolumn, "@1%", "");
			perssiomcolumn = StringUtils.replace(perssiomcolumn, "@0%", "");
			perssiomcolumn = StringUtils.replace(perssiomcolumn, "+0!", "");
			perssiomcolumn = StringUtils.replace(perssiomcolumn, "+1!", "");
			perssiomcolumn = StringUtils.replace(perssiomcolumn, "^1^", "");
			perssiomcolumn = StringUtils.replace(perssiomcolumn, "^0^", "");
		}
		actx.setSyncto(distrubsubmit);
		actx.setSubformmode(subformmode);
		if ("2".equals(syncmode)) {
			actx.setNeedAssistant(true);
		} else if ("3".equals(syncmode)) {
			actx.setNeedReader(true);
		} else if ("4".equals(syncmode)) {
			actx.setNeedReader(true);
			actx.setNeedAssistant(true);
		}
		actx.setNeedTree(needtree);
		actx.setFormEdit(formedit);
		actx.setEditcolumn(perssiomcolumn);
		actx.setFobitzb(fobitzb);
		
		return actx;
	}
	
	private String addWorkItemCount(String particiapntArr){
		if(StringUtils.isEmpty(particiapntArr)){
			return particiapntArr;
		}
		String []datax=StringUtils.split(particiapntArr,",");
		for (int i = 0; i < datax.length; i++) {
			String info=StringUtils.substringBetween(datax[i],"[","]");
		try{
			String sql="select  count(*) cou from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"+info+"' and w2.statusnow='01' and w2.types in ('01','02')";
			List list=WfEntry.iv().useCoreView().coreSqlview(sql);			
			String data= ((Map)list.get(0)).get("cou").toString();
			String deptname=SecurityEntry.iv().loadUser(info).getDeptname();

			particiapntArr=StringUtils.replace(particiapntArr, "["+info+"]", "(/部门:"+deptname+"/待办工单数:"+data+")["+info+"]");
	
		}catch(Exception e){
			e.printStackTrace();
		}
		}

		return particiapntArr;
	}
	
	
	private String addUserPhone(String particiapntArr){
		if(StringUtils.isEmpty(particiapntArr)){
			return particiapntArr;
		}
		String []datax=StringUtils.split(particiapntArr,",");
		
		for (int i = 0; i < datax.length; i++) {
			String info=StringUtils.substringBetween(datax[i],"[","]");
			String replace=StringUtils.substringBefore(datax[i], "[");
		try{
			Client3A client=SecurityEntry.iv().loadUser(info);
			String deptname=client.getDeptname();
			String phone=client.getMobile();
			particiapntArr=StringUtils.replace(particiapntArr, replace+"[", replace+ " " +deptname+"/"+phone+"[");
	
		}catch(Exception e){
			e.printStackTrace();
		}
		}

		return particiapntArr;
	}
	
	private String addUserAppendInfo(String particiapntArr,int mode){
		if(DeBuger.isDebug()){
			particiapntArr=particiapntArr+DeBuger.getDebuger();
		}
		
		if(mode==1){
			return this.addWorkItemCount(particiapntArr);
		}else if(mode==2){
			return this.addUserPhone(particiapntArr);
		}
		return this.addWorkItemCount(particiapntArr);
	}

	public boolean canCreate(String natrualname, String userid)
			throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		// 支持新的表单定制模式
		UmsProtectedobject upox = rmi.loadResourceByNatural(natrualname);
		if (upox != null) {
			String extinfo = upox.getExtendattribute();
			if (StringUtils.isNotEmpty(extinfo)) {
				if (StringUtils.contains(extinfo.trim(), "<?xml")) {
					return AppEntry.iv2().canCreate(natrualname, userid);
				}
			}
		}

		String processid = this.loadApp(natrualname).getWorkflowCode_();
		Activity[] act = WfEntry.iv().loadProcess(processid).getActivity();
		String firstActname = null;
		for (int i = 0; i < act.length; i++) {
			if (act[i].isStartActivity()) {
				firstActname = act[i].getId();
				break;
			}
		}
		UmsProtectedobject upo =null;
		try {
			 upo=rmi.loadResourceByNatural(natrualname
					+ "." + firstActname);
		} catch (Exception e) {
			System.out.println("表单预览时没有应用框架资源");
			e.printStackTrace();
		}
		if(upo==null){
			return true;
		}
		String objtype = upo.getObjecttype();
		String userlist = "";

		if (!_PARTICIPANT_MODE_HUMAN.equals(objtype)) {
			// 除了人员外全部都不支持多选择，这里需要做个处理
			String extinfo = upo.getExtendattribute();
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
