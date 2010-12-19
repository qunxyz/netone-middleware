package oe.cms.cfg;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 提示信息对象
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class TCmsRecordtip implements Serializable {

	/** identifier field */
	private String tipid;

	/** nullable persistent field */
	private String tipinfo;

	/** persistent field */
	private String cellid;

	/** persistent field */
	private String recordid;

	/** persistent field */
	private String writer;

	/** nullable persistent field */
	private Date created;

	/** full constructor */
	public TCmsRecordtip(String tipid, String tipinfo, String cellid,
			String recordid, String writer, Date created) {
		this.tipid = tipid;
		this.tipinfo = tipinfo;
		this.cellid = cellid;
		this.recordid = recordid;
		this.writer = writer;
		this.created = created;
	}

	/** default constructor */
	public TCmsRecordtip() {
	}

	/** minimal constructor */
	public TCmsRecordtip(String tipid, String cellid, String recordid,
			String writer) {
		this.tipid = tipid;
		this.cellid = cellid;
		this.recordid = recordid;
		this.writer = writer;
	}

	public String getTipid() {
		return this.tipid;
	}

	public void setTipid(String tipid) {
		this.tipid = tipid;
	}

	public String getTipinfo() {
		return this.tipinfo;
	}

	public void setTipinfo(String tipinfo) {
		this.tipinfo = tipinfo;
	}

	public String getCellid() {
		return this.cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public String getRecordid() {
		return this.recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String toString() {
		return new ToStringBuilder(this).append("tipid", getTipid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCmsRecordtip))
			return false;
		TCmsRecordtip castOther = (TCmsRecordtip) other;
		return new EqualsBuilder()
				.append(this.getTipid(), castOther.getTipid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getTipid()).toHashCode();
	}
}
