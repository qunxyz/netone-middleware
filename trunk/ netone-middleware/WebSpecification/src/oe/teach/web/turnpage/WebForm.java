package oe.teach.web.turnpage;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class WebForm extends ActionForm {
	private String stuid;

	private String stuname;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

}
