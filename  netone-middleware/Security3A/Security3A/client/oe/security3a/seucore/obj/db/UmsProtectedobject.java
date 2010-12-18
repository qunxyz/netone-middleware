package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsProtectedobject implements Serializable {

	/** identifier field */
	private String id;

	/** nullable persistent field */
	private String objecttype;

	/** persistent field */
	private String name;

	/** nullable persistent field */
	private String naturalname;

	/** nullable persistent field */
	private String active;

	/** nullable persistent field */
	private String description;

	/** nullable persistent field */
	private Long appid;

	/** nullable persistent field */
	private String parentdir;

	/** nullable persistent field */
	private String actionurl;

	/** nullable persistent field */
	private String ou;

	private String extendattribute;

	private String inclusion;

	private Long aggregation;

	private String reference;

	private Date created;

	/** full constructor */
	public UmsProtectedobject(String objecttype, String name,
			String naturalname, String active, String description, Long appid,
			String parentdir, String actionurl, String ou) {
		this.objecttype = objecttype;
		this.name = name;
		this.naturalname = naturalname;
		this.active = active;
		this.description = description;
		this.appid = appid;
		this.parentdir = parentdir;
		this.actionurl = actionurl;
		this.ou = ou;
	}

	/** default constructor */
	public UmsProtectedobject() {
	}

	/** minimal constructor */
	public UmsProtectedobject(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjecttype() {
		return this.objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
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

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAppid() {
		return this.appid;
	}

	public void setAppid(Long appid) {
		this.appid = appid;
	}

	public String getParentdir() {
		return this.parentdir;
	}

	public void setParentdir(String parentdir) {
		this.parentdir = parentdir;
	}

	public String getActionurl() {
		return this.actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String getOu() {
		return this.ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof UmsProtectedobject))
			return false;
		UmsProtectedobject castOther = (UmsProtectedobject) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public Long getAggregation() {
		return aggregation;
	}

	public void setAggregation(Long aggregation) {
		this.aggregation = aggregation;
	}

	public String getInclusion() {
		return inclusion;
	}

	public void setInclusion(String inclusion) {
		this.inclusion = inclusion;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
