package oe.security3a.seucore.obj ;

import java.util.* ;


public class OptrLogInfo implements java.io.Serializable {

    public static final String Result_Success = "success";

    public static final String Result_Failure = "failure";

    public static final String Result_Exception = "exception";

    private String logid ;

    private String userid ;

    private Date optrtime ;

    private String optrid ;

    private String result ;

    private String seq_id ;

    private String ip ;

    private String remark ;

    private String host ;

    public OptrLogInfo()
    {
    }


    public void setLogid( String logid )
    {
        this.logid = logid ;
    }


    public void setUserid( String userid )
    {
        this.userid = userid ;
    }


    public void setOptrtime( Date optrtime )
    {
        this.optrtime = optrtime ;
    }


    public void setOptrid( String optrid )
    {
        this.optrid = optrid ;
    }


    public void setResult( String result )
    {
        this.result = result ;
    }


    public void setSeq_id( String seq_id )
    {
        this.seq_id = seq_id ;
    }


    public void setIp( String ip )
    {
        this.ip = ip ;
    }


    public void setRemark( String remark )
    {
        this.remark = remark ;
    }


    public void setHost( String host )
    {
        this.host = host ;
    }


    public String getLogid()
    {
        return logid ;
    }


    public String getUserid()
    {
        return userid ;
    }


    public Date getOptrtime()
    {
        return optrtime ;
    }


    public String getOptrid()
    {
        return optrid ;
    }


    public String getResult()
    {
        return result ;
    }


    public String getSeq_id()
    {
        return seq_id ;
    }


    public String getIp()
    {
        return ip ;
    }


    public String getRemark()
    {
        return remark ;
    }


    public String getHost()
    {
        return host ;
    }
}
