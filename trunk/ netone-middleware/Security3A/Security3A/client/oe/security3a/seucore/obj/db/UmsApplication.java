package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsApplication implements Serializable {

	/** identifier field */
	private Long id;

	/** persistent field */
	private String name;

	/** nullable persistent field */
	private String naturalname;

	/** persistent field */
	private String password;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private String active;

	private String apptype;

	private Date created;

	private String extendattribute;

	/** full constructor */
	public UmsApplication(String name, String naturalname, String password,
			String description, String active) {
		this.name = name;
		this.naturalname = naturalname;
		this.password = password;
		this.description = description;
		this.active = active;
	}

	/** default constructor */
	public UmsApplication() {
	}

	/** minimal constructor */
	public UmsApplication(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNaturalname() {
		return this.naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof UmsApplication))
			return false;
		UmsApplication castOther = (UmsApplication) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

}
