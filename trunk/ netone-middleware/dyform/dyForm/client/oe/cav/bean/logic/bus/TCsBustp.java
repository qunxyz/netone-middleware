package oe.cav.bean.logic.bus;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TCsBustp implements Serializable {

    /** identifier field */
    private String tpid;

    /** persistent field */
    private String systemid;

    /** persistent field */
    private String tpmc;

    /** persistent field */
    private String wjdx;

    /** nullable persistent field */
    private String scsj;

    /** nullable persistent field */
    private String bz;

    /** nullable persistent field */
    private String lsh;

    /** nullable persistent field */
    private String dispsize;

    /** nullable persistent field */
    private String linkurl;

    private String participant;
    /** full constructor */
    public TCsBustp(String systemid, String tpmc, String wjdx, String scsj, String bz, String lsh, String dispsize, String linkurl) {
        this.systemid = systemid;
        this.tpmc = tpmc;
        this.wjdx = wjdx;
        this.scsj = scsj;
        this.bz = bz;
        this.lsh = lsh;
        this.dispsize = dispsize;
        this.linkurl = linkurl;
    }

    /** default constructor */
    public TCsBustp() {
    }

    /** minimal constructor */
    public TCsBustp(String systemid, String tpmc, String wjdx) {
        this.systemid = systemid;
        this.tpmc = tpmc;
        this.wjdx = wjdx;
    }

    public String getTpid() {
        return this.tpid;
    }

    public void setTpid(String tpid) {
        this.tpid = tpid;
    }

    public String getSystemid() {
        return this.systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getTpmc() {
        return this.tpmc;
    }

    public void setTpmc(String tpmc) {
        this.tpmc = tpmc;
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

    public String getDispsize() {
        return this.dispsize;
    }

    public void setDispsize(String dispsize) {
        this.dispsize = dispsize;
    }

    public String getLinkurl() {
        return this.linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("tpid", getTpid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TCsBustp) ) return false;
        TCsBustp castOther = (TCsBustp) other;
        return new EqualsBuilder()
            .append(this.getTpid(), castOther.getTpid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTpid())
            .toHashCode();
    }

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

}
