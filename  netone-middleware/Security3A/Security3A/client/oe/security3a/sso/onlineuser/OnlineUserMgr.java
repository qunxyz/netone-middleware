package oe.security3a.sso.onlineuser ;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import oe.security3a.seucore.obj.Clerk;



/**
 * ��ǰ�����û��Ĳ����ӿ�
 *
 * @author not attributable
 * @version 1.0
 */
public interface OnlineUserMgr {

    /**
     * ��ȡ��ǰ�û�(jspʹ��)
     * @param request HttpServletRequest
     * @return OnlineUser
     */
    public OnlineUser getOnlineUser( HttpServletRequest request );


    /**
     * ��ȡ��ǰ�û�(webstartʹ��)
     * @param ssosessionid String
     * @return OnlineUser
     */
    public OnlineUser getOnlineUser(String ssosessionid);


    /**
     * ��ȡ��ǰ�û�(appletʹ��)
     * @param applet JApplet
     * @return OnlineUser
     */
    public OnlineUser getOnlineUser( JApplet applet );

    
    /**
     * ��ȡ�����û�����ϸ��Ϣ��(jspʹ��)
     * @param request
     * @return
     */
    public Clerk getDetailOnlineUser(HttpServletRequest request );
    
    
    /**
     * ��ȡ�����û�����ϸ��Ϣ��(appletʹ��)
     * @param request
     * @return
     */
    public Clerk getDetailOnlineUser(JApplet applet);
    
    
    /**
     * ��ȡ�����û�����ϸ��Ϣ��(webstartʹ��)
     * @param request
     * @return
     */
    public Clerk getDetailOnlineUser(String ssosessionid);

}
