package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsRole2role2 implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String relationalrolemainid;

    /** nullable persistent field */
    private String relationalrolesubid;

    /** nullable persistent field */
    private String relationtype;

    /** full constructor */
    public UmsRole2role2(String relationalrolemainid, String relationalrolesubid, String relationtype) {
        this.relationalrolemainid = relationalrolemainid;
        this.relationalrolesubid = relationalrolesubid;
        this.relationtype = relationtype;
    }

    /** default constructor */
    public UmsRole2role2() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelationalrolemainid() {
        return this.relationalrolemainid;
    }

    public void setRelationalrolemainid(String relationalrolemainid) {
        this.relationalrolemainid = relationalrolemainid;
    }

    public String getRelationalrolesubid() {
        return this.relationalrolesubid;
    }

    public void setRelationalrolesubid(String relationalrolesubid) {
        this.relationalrolesubid = relationalrolesubid;
    }

    public String getRelationtype() {
        return this.relationtype;
    }

    public void setRelationtype(String relationtype) {
        this.relationtype = relationtype;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UmsRole2role2) ) return false;
        UmsRole2role2 castOther = (UmsRole2role2) other;
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
