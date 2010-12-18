package oe.security3a.sso.onlineuser ;

/**
 * 当前在线用户信息的封装类
 *
 * @author hls
 * @version 1.0
 */
public class OnlineUser implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String userid ;

    private String loginname ;

    private String loginseq ;

    private String loginip ;

    private String loginhost ;
    
    //隶属于
    private String belongto;

    public String getBelongto() {
		return belongto;
	}


	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}


	public OnlineUser()
    {
    }


    public void setUserid( String userid )
    {
        this.userid = userid ;
    }


    public void setLoginname( String loginname )
    {
        this.loginname = loginname ;
    }


    public void setLoginseq( String loginseq )
    {
        this.loginseq = loginseq ;
    }


    public void setLoginip( String loginip )
    {
        this.loginip = loginip ;
    }


    public void setLoginhost( String loginhost )
    {
        this.loginhost = loginhost ;
    }


    public String getUserid()
    {
        return userid ;
    }


    public String getLoginname()
    {
        return loginname ;
    }


    public String getLoginseq()
    {
        return loginseq ;
    }


    public String getLoginip()
    {
        return loginip ;
    }


    public String getLoginhost()
    {
        return loginhost ;
    }


    public String toString()
    {
        return loginname ;
    }
}
