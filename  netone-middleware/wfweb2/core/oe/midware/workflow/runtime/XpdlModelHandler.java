package oe.midware.workflow.runtime;

import java.io.InputStream;

import oe.midware.workflow.xpdl.model.pkg.XPDLPackage;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 获得流程模板对象
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
	 * 通过processid来获得流程模板对象
	 * 
	 * @param proccode
	 * @return
	 */
	WorkflowProcess fetchXpdlWorkflowByProcessid(String id);

	/**
	 * 通过processid来获得流程模板对象
	 * 
	 * @param proccode
	 * @return
	 */
	WorkflowProcess fetchXpdlWorkflowByProcessid(InputStream input);

}
