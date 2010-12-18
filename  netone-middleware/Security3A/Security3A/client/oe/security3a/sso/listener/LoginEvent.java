package oe.security3a.sso.listener ;

import javax.servlet.http.* ;

import oe.security3a.sso.SsoToken;


/**
 * 用户登录事件，提供HttpServletRequest，HttpServletResponse
 *
 * @author not attributable
 * @version 1.0
 */
public class LoginEvent {

    private HttpServletRequest request ;
    private HttpServletResponse response ;
    private String loginUser ;
    private SsoToken st  ;
    
    public LoginEvent(HttpServletRequest request , HttpServletResponse response , String loginUser, SsoToken st)
    {
        this.request = request ;
        this.response = response ;
        this.loginUser = loginUser ;
        this.st = st ;
    }

    public HttpServletRequest getHttpRequest()
    {
        return request ;
    }


    public HttpServletResponse getHttpResponse()
    {
        return response ;
    }


    public String getLoginUser()
    {
        return loginUser ;
    }
    
    public SsoToken getSsoToken(){
    	return st ;
    }
    
}
