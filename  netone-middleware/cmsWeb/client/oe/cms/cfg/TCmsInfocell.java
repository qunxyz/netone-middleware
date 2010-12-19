package oe.cms.cfg;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 咨讯元对象
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class TCmsInfocell implements Serializable {

	/** identifier field */
	private String cellid;

	private String naturalname;

	private String types;

	private String belongto;

	/** nullable persistent field */
	private String cellname;

	/** nullable persistent field */
	private String extendattribute;

	private String intime;

	private String participant;

	private String body;

	private Long width;

	private Long height;

	/** nullable persistent field */
	private Timestamp availablefrom;

	/** nullable persistent field */
	private Timestamp availableto;

	/** nullable persistent field */
	private Double cachecycle;

	/** nullable persistent field */
	private Timestamp created;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/** full constructor */
	public TCmsInfocell(String cellid, String cellname, String extendattribute) {
		this.cellid = cellid;
		this.cellname = cellname;

		this.extendattribute = extendattribute;
	}

	/** default constructor */
	public TCmsInfocell() {
	}

	/** minimal constructor */
	public TCmsInfocell(String cellid) {
		this.cellid = cellid;
	}

	public String getCellid() {
		return this.cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public String getCellname() {
		return this.cellname;
	}

	public void setCellname(String cellname) {
		this.cellname = cellname;
	}

	public String getExtendattribute() {
		return this.extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String toString() {
		return new ToStringBuilder(this).append("cellid", getCellid())
				.toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TCmsInfocell))
			return false;
		TCmsInfocell castOther = (TCmsInfocell) other;
		return new EqualsBuilder().append(this.getCellid(),
				castOther.getCellid()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getCellid()).toHashCode();
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Timestamp getAvailablefrom() {
		return this.availablefrom;
	}

	public void setAvailablefrom(Timestamp availablefrom) {
		this.availablefrom = availablefrom;
	}

	public Timestamp getAvailableto() {
		return this.availableto;
	}

	public void setAvailableto(Timestamp availableto) {
		this.availableto = availableto;
	}

	public Double getCachecycle() {
		return this.cachecycle;
	}

	public void setCachecycle(Double cachecycle) {
		this.cachecycle = cachecycle;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
}
