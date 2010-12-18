package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsPermission implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long objectid;

    /** nullable persistent field */
    private Long operationid;

    /** nullable persistent field */
    private Long active;

    /** nullable persistent field */
    private String comments;

    /** full constructor */
    public UmsPermission(Long objectid, Long operationid, Long active, String comments) {
        this.objectid = objectid;
        this.operationid = operationid;
        this.active = active;
        this.comments = comments;
    }

    /** default constructor */
    public UmsPermission() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectid() {
        return this.objectid;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
    }

    public Long getOperationid() {
        return this.operationid;
    }

    public void setOperationid(Long operationid) {
        this.operationid = operationid;
    }

    public Long getActive() {
        return this.active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UmsPermission) ) return false;
        UmsPermission castOther = (UmsPermission) other;
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
