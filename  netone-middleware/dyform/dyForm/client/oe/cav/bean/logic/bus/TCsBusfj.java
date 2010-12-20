package oe.cav.bean.logic.bus;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCsBusfj implements Serializable {

    /** identifier field */
    private String fjid;

    /** persistent field */
    private String systemid;

    /** persistent field */
    private String fjmc;

    /** persistent field */
    private String wjdx;

    /** nullable persistent field */
    private String scsj;

    /** nullable persistent field */
    private String bz;

    /** nullable persistent field */
    private String lsh;
    
    private String participant;

    public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	/** full constructor */
    public TCsBusfj(String systemid, String fjmc, String wjdx, String scsj, String bz, String lsh) {
        this.systemid = systemid;
        this.fjmc = fjmc;
        this.wjdx = wjdx;
        this.scsj = scsj;
        this.bz = bz;
        this.lsh = lsh;
    }

    /** default constructor */
    public TCsBusfj() {
    }

    /** minimal constructor */
    public TCsBusfj(String systemid, String fjmc, String wjdx) {
        this.systemid = systemid;
        this.fjmc = fjmc;
        this.wjdx = wjdx;
    }





    public String getFjmc() {
        return this.fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getWjdx() {
        return this.wjdx;
    }

    public void setWjdx(String wjdx) {
        this.wjdx = wjdx;
    }

    public String getScsj() {
        return this.scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getLsh() {
        return this.lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getFjid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TCsBusfj) ) return false;
        TCsBusfj castOther = (TCsBusfj) other;
        return new EqualsBuilder()
            .append(this.getFjid(), castOther.getFjid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getFjid())
            .toHashCode();
    }

	public String getFjid() {
		return fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

}
