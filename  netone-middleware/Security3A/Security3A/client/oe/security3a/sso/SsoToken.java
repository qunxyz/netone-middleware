package oe.security3a.sso ;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import oe.security3a.sso.util.Encryption;


/**
 * 封装sso server发放的token， 用于用户的登录验证。
 *
 * @author not attributable
 * @version 1.0
 */
public class SsoToken {

    private String userId ;

    private String loginName ;

    private String loginseq ;

    private String loginip ;

    private String loginhost ;

    private String sessionId ;
    
    private long limitTime ;

    private boolean tokenValidated ;

    private boolean firstToken ;
    
    private String code;//隶属于

    public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public SsoToken()
    {
        tokenValidated = false ;
        firstToken = true ;
    }


    public void setLoginName( String loginName )
    {

        this.loginName = loginName ;
    }


    public void setSessionId( String sessionId )
    {

        this.sessionId = sessionId ;
    }


    public void setLimitTime( long limitTime )
    {
        this.limitTime = limitTime ;
    }


    public void setTokenValidated( boolean tokenValidated )
    {
        this.tokenValidated = tokenValidated ;
    }


    public void setFirstToken( boolean firstToken )
    {
        this.firstToken = firstToken ;
    }


    public void setLoginhost( String loginhost )
    {
        this.loginhost = loginhost ;
    }


    public void setLoginip( String loginip )
    {
        this.loginip = loginip ;
    }


    public void setLoginseq( String loginseq )
    {
        this.loginseq = loginseq ;
    }


    public void setUserid( String userId )
    {
        this.userId = userId ;
    }


    public String getLoginName()
    {

        return loginName ;
    }


    public String getSessionId()
    {

        return sessionId ;
    }


    public long getLimitTime()
    {

        return limitTime ;
    }


    public boolean isTokenValidated()
    {
        return tokenValidated ;
    }


    public boolean isFirstToken()
    {
        return firstToken ;
    }


    public String getLoginhost()
    {
        return loginhost ;
    }


    public String getLoginip()
    {
        return loginip ;
    }


    public String getLoginseq()
    {
        return loginseq ;
    }


    public String getUserid()
    {
        return userId ;
    }


    public String tokenToString()
    {
        StringBuffer sb = new StringBuffer() ;
        sb.append( userId + "," ) ;
        sb.append( loginName + "," ) ;
        sb.append( loginseq + "," ) ;
        sb.append( loginip + "," ) ;
        sb.append( loginhost + "," ) ;
        sb.append( sessionId + "," ) ;
        sb.append( limitTime + "," ) ;
        sb.append( tokenValidated + "," ) ;
        sb.append( firstToken + "," ) ;
        sb.append( code );//增加的隶属于字段

        String str = sb.toString() ;
        try {
			str = URLEncoder.encode(str,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        try
        {
            str = Encryption.encry( str , "ssov11" , true) ;
        }
        catch ( Exception ex )
        {
            ex.printStackTrace() ;
        }
        return str ;
    }


    public void parse( String token )
    {
        try
        {
            String value = Encryption.encry(token,"ssov11",false) ;
            try {
            	value = URLDecoder.decode(value,"GBK");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
            String[] str = value.split( "," ) ;
            this.userId =  str[0] ;
            this.loginName = str[1] ;
            this.loginseq = str[2];
            this.loginip = str[3];
            this.loginhost = str[4];
            this.sessionId = str[5] ;
            this.limitTime = Long.parseLong( str[6] ) ;
            this.tokenValidated = Boolean.getBoolean( str[7] ) ;
            this.firstToken = Boolean.getBoolean( str[8] ) ;
            this.code = str[9] ;
        }
        catch ( Exception ex1 )
        {
            ex1.printStackTrace() ;
        }
    }
}
