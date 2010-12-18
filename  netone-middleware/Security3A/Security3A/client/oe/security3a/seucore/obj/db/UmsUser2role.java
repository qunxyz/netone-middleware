package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsUser2role implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String userid;

    /** nullable persistent field */
    private String roleid;

    /** full constructor */
    public UmsUser2role(String userid, String roleid) {
        this.userid = userid;
        this.roleid = roleid;
    }

    /** default constructor */
    public UmsUser2role() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoleid() {
        return this.roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UmsUser2role) ) return false;
        UmsUser2role castOther = (UmsUser2role) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
