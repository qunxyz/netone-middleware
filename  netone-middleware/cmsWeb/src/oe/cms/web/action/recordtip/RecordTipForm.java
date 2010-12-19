package oe.cms.web.action.recordtip;

/** 
 * @author liuzl
 * Creation date: 08-03-2006
 * XDoclet definition:
 */
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RecordTipForm extends ActionForm {
	private Long tipid;

	private String tipinfo;

	private String sqlid;

	private String writer;

	private String columnid;

	private String recordid;

	private Date created;

	public RecordTipForm() {
		tipinfo = null;
		sqlid = null;
		writer = null;
		columnid = null;
		recordid = null;
		created = null;
	}

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public String getSqlid() {
		return sqlid;
	}

	public void setSqlid(String sqlid) {
		this.sqlid = sqlid;
	}

	public String getTipinfo() {
		return tipinfo;
	}

	public void setTipinfo(String tipinfo) {
		this.tipinfo = tipinfo;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		tipinfo = null;
		sqlid = null;
		writer = null;
		columnid = null;
		recordid = null;
		created = null;
	}

	public Long getTipid() {
		return tipid;
	}

	public void setTipid(Long tipid) {
		this.tipid = tipid;
	}

}
