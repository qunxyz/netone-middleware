package oe.midware.workflow.engine;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.ProcessAuto;
import oe.frame.bus.workflow.ProcessEngine;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.ProcessActor;
import oe.frame.bus.workflow.actor.SubProcessActor;
import oe.frame.bus.workflow.actor.datafield.DataFieldUpdateActor;
import oe.frame.bus.workflow.actor.process.ProcessDropActor;

import oe.frame.bus.workflow.actor.process.ProcessInitActor;
import oe.frame.bus.workflow.rule.RuleEngine;
import oe.frame.orm.OrmerEntry;

import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;

import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 流程引擎实现
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessEngineImp implements ProcessEngine {

	private ProcessAuto processAuto;

	private ProcessActor processActor;

	private DataFieldUpdateActor dataFieldUpdateActor;

	private ProcessInitActor processInitActor;

	private ProcessDropActor processDropActor;

	private RuleEngine ruleEngine;

	private SubProcessActor subProcessActor;

	public TWfRuntime newProcess(WorkflowProcess runtime) {
		return processActor.newProcess(runtime);
	}

	public void runProcess(TWfRuntime runtime) {
		processActor.runProcess(runtime);
		processAuto.autoexecute();
	}

	public void commitActivity(TWfWorklist worklist) {
		processActor.commitActivity(worklist);
		processAuto.autoexecute();
	}

	public void commitActivityByManual(TWfWorklist worklist, Activity arg2) {
		processActor.commitActivityByManual(worklist, arg2);
		processAuto.autoexecute();
	}

	public void commitActivityByManual(TWfWorklist worklist, List arg2) {
		processActor.commitActivityByManual(worklist, arg2);
		processAuto.autoexecute();
	}

	public void updateRelevantvars(List rev) {
		dataFieldUpdateActor.execute(rev);
	}

	public void updateRelevantvar(TWfRelevantvar rev) {
		dataFieldUpdateActor.execute(rev);
	}

	public void updateRelevantvarUseLog(TWfRelevantvar arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void updateRelevantvarsUseLog(List arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void initProcess(TWfRuntime arg0) {
		processInitActor.executes(arg0);
	}

	public void dropProcess(TWfRuntime arg0) {
		processDropActor.executes(arg0);
	}

	public boolean judgementRule(String elogicExpress, String runtimeid) {

		return ruleEngine.rule(elogicExpress, runtimeid, null);
	}

	public String exeScript(String elogicExpress, String runtimeid) {
		// TODO Auto-generated method stub
		return ruleEngine.todo(elogicExpress, runtimeid, null);
	}

	public DataFieldUpdateActor getDataFieldUpdateActor() {
		return dataFieldUpdateActor;
	}

	public void setDataFieldUpdateActor(
			DataFieldUpdateActor dataFieldUpdateActor) {
		this.dataFieldUpdateActor = dataFieldUpdateActor;
	}

	public ProcessActor getProcessActor() {
		return processActor;
	}

	public void setProcessActor(ProcessActor processActor) {
		this.processActor = processActor;
	}

	public ProcessAuto getProcessAuto() {
		return processAuto;
	}

	public void setProcessAuto(ProcessAuto processAuto) {
		this.processAuto = processAuto;
	}

	public ProcessDropActor getProcessDropActor() {
		return processDropActor;
	}

	public void setProcessDropActor(ProcessDropActor processDropActor) {
		this.processDropActor = processDropActor;
	}

	public ProcessInitActor getProcessInitActor() {
		return processInitActor;
	}

	public void setProcessInitActor(ProcessInitActor processInitActor) {
		this.processInitActor = processInitActor;
	}

	public RuleEngine getRuleEngine() {
		return ruleEngine;
	}

	public void setRuleEngine(RuleEngine ruleEngine) {
		this.ruleEngine = ruleEngine;
	}

	public SubProcessActor getSubProcessActor() {
		return subProcessActor;
	}

	public void setSubProcessActor(SubProcessActor subProcessActor) {
		this.subProcessActor = subProcessActor;
	}

	@Override
	public Object exeScript(String logicExp) throws RemoteException {
		return ruleEngine.todo(logicExp);
	}

}
