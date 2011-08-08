package oe.midware.workflow.runtime;

import java.io.InputStream;

import oe.midware.workflow.xpdl.model.pkg.XPDLPackage;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * �������ģ�����
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface XpdlModelHandler {

	String fetchXpdlpackageDoc(String xpdlfilename);

	XPDLPackage fetchXpdlpackage(String xpdlfilename);

	/**
	 * ͨ��processid���������ģ�����
	 * 
	 * @param proccode
	 * @return
	 */
	WorkflowProcess fetchXpdlWorkflowByProcessid(String id);

	/**
	 * ͨ��processid���������ģ�����
	 * 
	 * @param proccode
	 * @return
	 */
	WorkflowProcess fetchXpdlWorkflowByProcessid(InputStream input);

}
