package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsRolepermission implements Serializable {

    /** identifier field */
    private Long lsh;

    /** persistent field */
    private String roleid;

    /** persistent field */
    private String operationid;

    /** nullable persistent field */
    private String active;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private String dninfo;

    /** full constructor */
    public UmsRolepermission(String roleid, String operationid, String active, String comments, String dninfo) {
        this.roleid = roleid;
        this.operationid = operationid;
        this.active = active;
        this.comments = comments;
        this.dninfo = dninfo;
    }

    /** default constructor */
    public UmsRolepermission() {
    }

    /** minimal constructor */
    public UmsRolepermission(String roleid, String operationid) {
        this.roleid = roleid;
        this.operationid = operationid;
    }

    public Long getLsh() {
        return this.lsh;
    }

    public void setLsh(Long lsh) {
        this.lsh = lsh;
    }

    public String getRoleid() {
        return this.roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getOperationid() {
        return this.operationid;
    }

    public void setOperationid(String operationid) {
        this.operationid = operationid;
    }

    public String getActive() {
        return this.active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDninfo() {
        return this.dninfo;
    }

    public void setDninfo(String dninfo) {
        this.dninfo = dninfo;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("lsh", getLsh())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UmsRolepermission) ) return false;
        UmsRolepermission castOther = (UmsRolepermission) other;
        return new EqualsBuilder()
            .append(this.getLsh(), castOther.getLsh())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLsh())
            .toHashCode();
    }

}
