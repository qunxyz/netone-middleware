package oe.teach.mid.buss;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OecStudent implements Serializable {

	/** identifier field */
	private Long stuid;

	/** nullable persistent field */
	private String stuname;

	/** nullable persistent field */
	private String description;

	/** full constructor */
	public OecStudent(String stuname, String description) {
		this.stuname = stuname;
		this.description = description;
	}

	/** default constructor */
	public OecStudent() {
	}

	public Long getStuid() {
		return this.stuid;
	}

	public void setStuid(Long stuid) {
		this.stuid = stuid;
	}

	public String getStuname() {
		return this.stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return new ToStringBuilder(this).append("stuid", getStuid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof OecStudent))
			return false;
		OecStudent castOther = (OecStudent) other;
		return new EqualsBuilder()
				.append(this.getStuid(), castOther.getStuid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getStuid()).toHashCode();
	}

}
