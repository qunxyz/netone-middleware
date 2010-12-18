package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsRole implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String naturalname;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String active;

    /** nullable persistent field */
    private Long appid;

    /** nullable persistent field */
    private String belongingness;

    /** full constructor */
    public UmsRole(String name, String naturalname, String description, String active, Long appid, String belongingness) {
        this.name = name;
        this.naturalname = naturalname;
        this.description = description;
        this.active = active;
        this.appid = appid;
        this.belongingness = belongingness;
    }

    /** default constructor */
    public UmsRole() {
    }

    /** minimal constructor */
    public UmsRole(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNaturalname() {
        return this.naturalname;
    }

    public void setNaturalname(String naturalname) {
        this.naturalname = naturalname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActive() {
        return this.active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getAppid() {
        return this.appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public String getBelongingness() {
        return this.belongingness;
    }

    public void setBelongingness(String belongingness) {
        this.belongingness = belongingness;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UmsRole) ) return false;
        UmsRole castOther = (UmsRole) other;
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
