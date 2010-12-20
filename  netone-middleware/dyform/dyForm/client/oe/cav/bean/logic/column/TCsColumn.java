package oe.cav.bean.logic.column;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCsColumn implements Serializable {

	private boolean useable;

	/** identifier field */
	private String columncode;

	/** nullable persistent field */
	private String formcode;

	/** persistent field */
	private String columnid;

	/** persistent field */
	private String columname;

	/** nullable persistent field */
	private String valuelist;

	/** persistent field */
	private String statusinfo;

	/** persistent field */
	private Long indexvalue;

	/** persistent field */
	private String musk;

	/** persistent field */
	private String opemode;

	/** persistent field */
	private String htmltype;

	/** nullable persistent field */
	private String checktype;

	/** persistent field */
	private String viewtype;

	/** nullable persistent field */
	private String extendattribute;

	private String participant;

	/** full constructor */
	public TCsColumn(String formcode, String columnid, String columname,
			String valuelist, String ifman, Long indexvalue, String musk,
			String opemode, String htmltype, String checktype, String viewtype,
			String extendattribute) {
		this.formcode = formcode;
		this.columnid = columnid;
		this.columname = columname;
		this.valuelist = valuelist;
		this.indexvalue = indexvalue;
		this.musk = musk;
		this.opemode = opemode;
		this.htmltype = htmltype;
		this.checktype = checktype;
		this.viewtype = viewtype;
		this.extendattribute = extendattribute;
	}

	public String fetchType() {
		String attributeType = "";
		if (ColumnExtendInfo._HTML_TYPE_BUT.equals(htmltype)
				|| ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltype)
				|| ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltype)) {
			attributeType = "file";
		} else {
			if (ColumnExtendInfo._CHECK_TYPE_NUMBER.equals(checktype)) {
				attributeType = "number";
			} else {
				if (ColumnExtendInfo._HTML_TYPE_DATE.equals(htmltype)
						|| ColumnExtendInfo._HTML_TYPE_DATETIME
								.equals(htmltype)
						|| ColumnExtendInfo._HTML_TYPE_TIME.equals(htmltype)) {
					attributeType = "date";
				} else {
					attributeType = "string";
				}
			}
		}
		return attributeType;
	}

	/** default constructor */
	public TCsColumn() {
	}

	/** minimal constructor */
	public TCsColumn(String columnid, String columname, String ifman,
			Long indexvalue, String musk, String opemode, String htmltype,
			String viewtype) {
		this.columnid = columnid;
		this.columname = columname;
		this.indexvalue = indexvalue;
		this.musk = musk;
		this.opemode = opemode;
		this.htmltype = htmltype;
		this.viewtype = viewtype;
	}

	public String getColumncode() {
		return this.columncode;
	}

	public void setColumncode(String columncode) {
		this.columncode = columncode;
	}

	public String getFormcode() {
		return this.formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getColumnid() {
		return this.columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getColumname() {
		return this.columname;
	}

	public void setColumname(String columname) {
		this.columname = columname;
	}

	public String getValuelist() {
		return this.valuelist;
	}

	public void setValuelist(String valuelist) {
		this.valuelist = valuelist;
	}

	public String getStatusinfo() {
		return statusinfo;
	}

	public void setStatusinfo(String statusinfo) {
		this.statusinfo = statusinfo;
	}

	public Long getIndexvalue() {
		return this.indexvalue;
	}

	public void setIndexvalue(Long indexvalue) {
		this.indexvalue = indexvalue;
	}

	public String getMusk() {
		return this.musk;
	}

	public void setMusk(String musk) {
		this.musk = musk;
	}

	public String getOpemode() {
		return this.opemode;
	}

	public void setOpemode(String opemode) {
		this.opemode = opemode;
	}

	public String getHtmltype() {
		return this.htmltype;
	}

	public void setHtmltype(String htmltype) {
		this.htmltype = htmltype;
	}

	public String getChecktype() {
		return this.checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public String getViewtype() {
		return this.viewtype;
	}

	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String toString() {
		return new ToStringBuilder(this).append("columncode", getColumncode())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCsColumn))
			return false;
		TCsColumn castOther = (TCsColumn) other;
		return new EqualsBuilder().append(this.getColumncode(),
				castOther.getColumncode()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getColumncode()).toHashCode();
	}

	public String getExt9musk() {
		for (int i = 0; i < ColumnExtendInfo._BOOLEAN.length; i++) {
			if (ColumnExtendInfo._BOOLEAN[i][0].equals(this.musk)) {
				return ColumnExtendInfo._BOOLEAN[i][1];
			}
		}
		return musk;
	}

	public String getExt9opemode() {
		for (int i = 0; i < ColumnExtendInfo._BOOLEAN.length; i++) {
			if (ColumnExtendInfo._BOOLEAN[i][0].equals(this.opemode)) {
				return ColumnExtendInfo._BOOLEAN[i][1];
			}
		}
		return opemode;
	}

	public String getExt9viewtype() {
		for (int i = 0; i < ColumnExtendInfo._TYPE_LIST.length; i++) {
			if (ColumnExtendInfo._TYPE_LIST[i][0].equals(this.viewtype)) {
				return ColumnExtendInfo._TYPE_LIST[i][1];
			}
		}
		return viewtype;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public boolean isUseable() {
		return useable;
	}

	public void setUseable(boolean useable) {
		this.useable = useable;
	}

}
