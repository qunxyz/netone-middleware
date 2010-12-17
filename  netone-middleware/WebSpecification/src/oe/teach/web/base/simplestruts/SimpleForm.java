package oe.teach.web.base.simplestruts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class SimpleForm extends ActionForm {
	private String name;

	private int age;

	private String description;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
