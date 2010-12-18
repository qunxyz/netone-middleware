package oe.security3a.seucore.obj.db;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UmsOperationLog implements Serializable {

    /** identifier field */
    private String logid;

    /** nullable persistent field */
    private String userid;

    /** nullable persistent field */
    private Date operatetime; 

    /** nullable persistent field */
    private String operationid;

    /** nullable persistent field */
    private String resultinfo;

    /** nullable persistent field */
    private String logseqid;

    /** nullable persistent field */
    private String userip;

    /** nullable persistent field */
    private String userhost;

    /** nullable persistent field */
    private String remark;

    /** full constructor */
    public UmsOperationLog(String userid, Date operatetime, String operationid, String resultinfo, String logseqid, String userip, String userhost, String remark) {
        this.userid = userid;
        this.operatetime = operatetime;
        this.operationid = operationid;
        this.resultinfo = resultinfo;
        this.logseqid = logseqid;
        this.userip = userip;
        this.userhost = userhost;
        this.remark = remark;
    }

    /** default constructor */
    public UmsOperationLog() {
    }

    public String getLogid() {
        return this.logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getOperatetime() {
        return this.operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public String getOperationid() {
        return this.operationid;
    }

    public void setOperationid(String operationid) {
        this.operationid = operationid;
    }

    public String getResultinfo() {
        return this.resultinfo;
    }

    public void setResultinfo(String resultinfo) {
        this.resultinfo = resultinfo;
    }

    public String getLogseqid() {
        return this.logseqid;
    }

    public void setLogseqid(String logseqid) {
        this.logseqid = logseqid;
    }

    public String getUserip() {
        return this.userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getUserhost() {
        return this.userhost;
    }

    public void setUserhost(String userhost) {
        this.userhost = userhost;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("logid", getLogid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UmsOperationLog) ) return false;
        UmsOperationLog castOther = (UmsOperationLog) other;
        return new EqualsBuilder()
            .append(this.getLogid(), castOther.getLogid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLogid())
            .toHashCode();
    }

}
