package oe.web.tag.workflow;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <code> oe.web.tag.workflow.ListRelevantvar.java
 * <p><li>获得指定流程的所有的相关数据对象</li></p>
 * <description></description>
 ×  author zhang.chao.yi
 *          mail: eduzcy@126.com
 *</code>
 * 
 * @version 1.0.0 date 2008-11-25 created by zhang.chao.yi（Jim）
 */
public class ListRelevantvarTag extends SimpleTagSupport {
	/** 流程静态ID */
	private String processid;

	/** 处理结果的引用变量 */
	private String outVar;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(processid)) {
			throw new RuntimeException("the processd is null");
		}
		if (StringUtils.isEmpty(outVar)) {
			outVar = "result";
		}

		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");

			// 流程视图操作句柄
			WorkflowView view = (WorkflowView) RmiEntry.iv("wfview");
			List result = view.listRelevantvar(this.processid);
		
			DataField var = (DataField)result.get(0);
		
			super.getJspContext().setAttribute(outVar, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the processid
	 */
	public String getProcessid() {
		return processid;
	}

	/**
	 * @param processid
	 *            the processid to set
	 */
	public void setProcessid(String processid) {
		this.processid = processid;
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
