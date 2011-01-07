package oe.web.tag.workflow;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <code> oe.web.tag.workflow.ListWorklist.java
 * <p><li>��ʾ���еĻ�ڵ�</li></p>
 * <description></description>
 ��  author chen.hao
 *</code>
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.hao
 */
public class ListWorklistTag extends SimpleTagSupport {
	/** ��������ʵ��ID */
	private String runtimeId;

	/** �����������ñ��� */
	private String outVar;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(runtimeId)) {
			throw new RuntimeException("the runtimeid is null");
		}
		if (StringUtils.isEmpty(outVar)) {
			outVar = "result";
		}

		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

			// ������ͼ�������
			WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
			List result = view.listWorklist(this.runtimeId);

			super.getJspContext().setAttribute(outVar, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
