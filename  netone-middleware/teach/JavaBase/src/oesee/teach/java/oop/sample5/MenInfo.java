package oesee.teach.java.oop.sample5;

import java.io.Serializable;

public class MenInfo implements Serializable {

	private String id;

	private String name;

	private int age;

	private boolean sex;

	private String tel;

	private String nativex;

	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNativex() {
		return nativex;
	}

	public void setNativex(String nativex) {
		this.nativex = nativex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
