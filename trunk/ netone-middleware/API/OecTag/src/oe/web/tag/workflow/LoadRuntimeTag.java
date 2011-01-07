package oe.web.tag.workflow;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <code> oe.web.tag.workflow.LoadRuntime.java
 * <p><li>装载流程实例</li></p>
 * <description></description>
 ×  author chen.hao
 *</code>
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.hao
 */
public class LoadRuntimeTag extends SimpleTagSupport {
	/** 流程运行实例ID */
	private String runtimeid;

	/** 处理结果的引用变量 */
	private String outVar;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(runtimeid)) {
			throw new RuntimeException("the runtimeid is null");
		}
		if (StringUtils.isEmpty(outVar)) {
			outVar = "result";
		}

		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

			// 流程视图操作句柄
			WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
			TWfRuntime result = view.loadRuntime(this.runtimeid);

			super.getJspContext().setAttribute(outVar, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the runtimeid
	 */
	public String getRuntimeid() {
		return runtimeid;
	}

	/**
	 * @param runtimeid
	 *            the runtimeid to set
	 */
	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	/**
	 * @return the outVar
	 */
	public String getOutVar() {
		return outVar;
	}

	/**
	 * @param outVar
	 *            the outVar to set
	 */
	public void setOutVar(String outVar) {
		this.outVar = outVar;
	}

}
