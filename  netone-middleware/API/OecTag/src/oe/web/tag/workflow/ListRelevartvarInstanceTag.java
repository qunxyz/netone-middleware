package oe.web.tag.workflow;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <code> oe.web.tag.workflow.ListRelevartvarInstance.java
 * <p><li>获得一个流程运行实例的相关数据对象</li></p>
 * <description></description>
 ×  author chen.hao
 *</code>
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.hao（cj）
 * @version 1.0.1 date 2008-11-25 modified by zhang.chao.yi（Jim）
 */
public class ListRelevartvarInstanceTag extends SimpleTagSupport {
	/** 流程运行实例ID */
	private String runtimeId;

	/** 数据对象ID */
	private String dataid;

	/** 处理结果的引用变量 */
	private String outVar;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(runtimeId)) {
			throw new RuntimeException("the runtimeId is null");
		}
		if (StringUtils.isEmpty(outVar)) {
			outVar = "result";
		}

		try {
			if (StringUtils.isEmpty(dataid)) {// 判断是否有dataid参数

				WorkflowConsole console = (WorkflowConsole) RmiEntry
						.iv("wfhandle");

				// 流程视图操作句柄
				WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
				List result = view.listRelevartvarInstance(this.runtimeId);

				super.getJspContext().setAttribute(outVar, result);
			} else {
				WorkflowConsole console = (WorkflowConsole) RmiEntry
						.iv("wfhandle");

				// 流程视图操作句柄
				WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
				TWfRelevantvar rel = (TWfRelevantvar) view
						.listRelevartvarInstance(this.runtimeId, this.dataid);

				super.getJspContext().setAttribute(outVar, rel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the runtimeId
	 */
	public String getRuntimeId() {
		return runtimeId;
	}

	/**
	 * @param runtimeId
	 *            the runtimeId to set
	 */
	public void setRuntimeId(String runtimeId) {
		this.runtimeId = runtimeId;
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

	/**
	 * @param dataid
	 *            the dataid to set
	 */
	public String getDataid() {
		return dataid;
	}

	/**
	 * @return the dataid
	 */
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

}
