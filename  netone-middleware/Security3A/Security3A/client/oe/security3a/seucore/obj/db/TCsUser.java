package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCsUser implements Serializable {

	/** identifier field */
	private String usercode;

	/** nullable persistent field */
	private String systemid;

	/** nullable persistent field */
	private String name;

	/** nullable persistent field */
	private String statusinfo;

	/** nullable persistent field */
	private String extendinfo;

	/** nullable persistent field */
	private String passwordx;

	/** nullable persistent field */
	private String types;

	/** nullable persistent field */
	private String created;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private String email;

	/** nullable persistent field */
	private String addressinfo;

	/** nullable persistent field */
	private String fox;

	/** nullable persistent field */
	private String phone;

	/** nullable persistent field */
	private String bussiness;

	/** nullable persistent field */
	private String participant;

	/** nullable persistent field */
	private String sex;

	/** nullable persistent field */
	private String ids;

	/** nullable persistent field */
	private String major;

	/** nullable persistent field */
	private String otherinfo;

	/** full constructor */
	public TCsUser(String systemid, String name, String statusinfo,
			String extendinfo, String passwordx, String types, String created,
			String description, String email, String addressinfo, String fox,
			String phone, String bussiness, String participant, String sex,
			String ids, String major, String otherinfo) {
		this.systemid = systemid;
		this.name = name;
		this.statusinfo = statusinfo;
		this.extendinfo = extendinfo;
		this.passwordx = passwordx;
		this.types = types;
		this.created = created;
		this.description = description;
		this.email = email;
		this.addressinfo = addressinfo;
		this.fox = fox;
		this.phone = phone;
		this.bussiness = bussiness;
		this.participant = participant;
		this.sex = sex;
		this.ids = ids;
		this.major = major;
		this.otherinfo = otherinfo;

	}

	/** default constructor */
	public TCsUser() {
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getSystemid() {
		return this.systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatusinfo() {
		return this.statusinfo;
	}

	public void setStatusinfo(String statusinfo) {
		this.statusinfo = statusinfo;
	}

	public String getExtendinfo() {
		return this.extendinfo;
	}

	public void setExtendinfo(String extendinfo) {
		this.extendinfo = extendinfo;
	}

	public String getPasswordx() {
		return this.passwordx;
	}

	public void setPasswordx(String passwordx) {
		this.passwordx = passwordx;
	}

	public String getTypes() {
		return this.types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressinfo() {
		return this.addressinfo;
	}

	public void setAddressinfo(String addressinfo) {
		this.addressinfo = addressinfo;
	}

	public String getFox() {
		return this.fox;
	}

	public void setFox(String fox) {
		this.fox = fox;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBussiness() {
		return this.bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	public String getParticipant() {
		return this.participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getOtherinfo() {
		return this.otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public String toString() {
		return new ToStringBuilder(this).append("usercode", getUsercode())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCsUser))
			return false;
		TCsUser castOther = (TCsUser) other;
		return new EqualsBuilder().append(this.getUsercode(),
				castOther.getUsercode()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getUsercode()).toHashCode();
	}

}
