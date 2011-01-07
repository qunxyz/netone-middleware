package oe.web.tag.workflow;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <code> oe.web.tag.workflow.LoadRelevantvar.java
 * <p><li>����������ݵ�ID���������ݶ���ʵ��</li></p>
 * <description></description>
 ��  author chen.hao
 *</code>
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.hao
 */
public class LoadRelevantvarTag extends SimpleTagSupport {
	/** ������ݵ�ID */
	private String varcode;

	/** �����������ñ��� */
	private String outVar;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(varcode)) {
			throw new RuntimeException("the varcode is null");
		}
		if (StringUtils.isEmpty(outVar)) {
			outVar = "result";
		}

		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

			// ������ͼ�������
			WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
			TWfRelevantvar result = view.loadRelevantvar(this.varcode);

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
	 * @return the varcode
	 */
	public String getVarcode() {
		return varcode;
	}

	/**
	 * @param varcode
	 *            the varcode to set
	 */
	public void setVarcode(String varcode) {
		this.varcode = varcode;
	}

}
