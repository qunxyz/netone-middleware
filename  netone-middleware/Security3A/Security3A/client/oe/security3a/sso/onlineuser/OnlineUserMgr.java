package oe.security3a.sso.onlineuser ;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.security3a.seucore.obj.Clerk;



/**
 * 当前在线用户的操作接口
 *
 * @author not attributable
 * @version 1.0
 */
public interface OnlineUserMgr {

    /**
     * 获取当前用户(jsp使用)
     * @param request HttpServletRequest
     * @return OnlineUser
     */
    public OnlineUser getOnlineUser( HttpServletRequest request );


    /**
     * 获取当前用户(webstart使用)
     * @param ssosessionid String
     * @return OnlineUser
     */
    public OnlineUser getOnlineUser(String ssosessionid);


    /**
     * 获取当前用户(applet使用)
     * @param applet JApplet
     * @return OnlineUser
     */
    public OnlineUser getOnlineUser( JApplet applet );

    
    /**
     * 获取在线用户的详细信息。(jsp使用)
     * @param request
     * @return
     */
    public Clerk getDetailOnlineUser(HttpServletRequest request );
    
    
    /**
     * 获取在线用户的详细信息。(applet使用)
     * @param request
     * @return
     */
    public Clerk getDetailOnlineUser(JApplet applet);
    
    
    /**
     * 获取在线用户的详细信息。(webstart使用)
     * @param request
     * @return
     */
    public Clerk getDetailOnlineUser(String ssosessionid);

}
