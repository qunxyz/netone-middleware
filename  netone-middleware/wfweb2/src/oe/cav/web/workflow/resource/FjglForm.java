package oe.cav.web.workflow.resource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FjglForm extends ActionForm{
	
	private String xpdlContent;
	
	//在流程设计中选择子流程中使用
	private String choiceSync;


	/** persistent field */
	private String procrealname;

	/** persistent field */
	private String procstorename;

	/** persistent field */
	private String processid;

	/** persistent field */
	private String systemid;

	/** persistent field */
	private String filesizes;

	/** nullable persistent field */
	private String created;

	/** nullable persistent field */
	private String description;

	private String procstatus;

	/** upload file from local */
	private FormFile theFile;

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilesizes() {
		return filesizes;
	}

	public void setFilesizes(String filesizes) {
		this.filesizes = filesizes;
	}



	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcrealname() {
		return procrealname;
	}

	public void setProcrealname(String procrealname) {
		this.procrealname = procrealname;
	}

	public String getProcstorename() {
		return procstorename;
	}

	public void setProcstorename(String procstorename) {
		this.procstorename = procstorename;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public FormFile getTheFile() {
		return theFile;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

	public String getProcstatus() {
		return procstatus;
	}

	public void setProcstatus(String procstatus) {
		this.procstatus = procstatus;
	}

	public String getXpdlContent() {
		return xpdlContent;
	}

	public void setXpdlContent(String xpdlContent) {
		this.xpdlContent = xpdlContent;
	}


	public String getChoiceSync() {
		return choiceSync;
	}

	public void setChoiceSync(String choiceSync) {
		this.choiceSync = choiceSync;
	}

}
