package oe.security4a.severlet;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oe.frame.orm.util.IdServer;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.SeumanEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.util.CookiesOpe;
import oe.security3a.sso.util.StringUtil;
import org.apache.commons.lang.StringUtils;

public class LoginSvl extends HttpServlet
{
  public static final ResourceBundle messages = ResourceBundle.getBundle(
    "ssoserver", Locale.CHINESE);
  private boolean imagecodeavail;
  private static final String CONTENT_TYPE = "text/html; charset=GBK";
  private int sessiontimeout;
  private String gotourlX = "";

  private String encryptionMode = "default";

  public void init() throws ServletException
  {
    try
    {
      String imagecode = messages.getString("ignoreimagecode");
      this.imagecodeavail = "yes".equals(imagecode);
      int t = Integer.parseInt(messages.getString("sessionTimeOut"));
      this.sessiontimeout = (t * 60*60);
      this.gotourlX = messages.getString("gotourl");

      CupmRmi cupm = (CupmRmi)RmiEntry.iv("cupm");
      this.encryptionMode = cupm.fetchConfig("EncryptionMode");
    }
    catch (Exception e) {
      System.err.println("lose imagecode config");
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html; charset=GBK");
    System.out.println("-----"+request.getParameter("oajump"));
    request.getSession().setAttribute("oajump", request.getParameter("oajump"));

    String username = StringUtil.iso8859toGBK(request
      .getParameter("username"));
    String password = request.getParameter("password");
    String code = request.getParameter("code");
    StringBuffer errormsg = new StringBuffer();

    if ((!this.imagecodeavail) && (!ImageCodeCheck.check(request))) {
      errormsg.append(oe.security3a.sso.LoginInfo._ERROR_1[0]);
      request.setAttribute("errormsg", errormsg.toString());
      request.getRequestDispatcher("sso/login.jsp").forward(request, 
        response);
    } else if (StringUtils.isEmpty(username)) {
      errormsg.append(oe.security3a.sso.LoginInfo._ERROR_2[0]);
      request.setAttribute("errormsg", errormsg.toString());
      request.getRequestDispatcher("sso/login.jsp").forward(request, 
        response);
    } else if (StringUtils.isEmpty(password)) {
      errormsg.append(oe.security3a.sso.LoginInfo._ERROR_3[0]);
      request.setAttribute("errormsg", errormsg.toString());
      request.getRequestDispatcher("sso/login.jsp").forward(request, 
        response);
    } else {
      OnlineUser oluser = new OnlineUser();
      String reqip = request.getRemoteAddr();
      String reqhost = request.getRemoteHost();
      if ((code == null) || ("".endsWith(code.trim())))
        code = "0000";
      try
      {
        CupmRmi cupm = (CupmRmi)RmiEntry.iv("cupm");
        String secu = request.getParameter("secu");
        if ("yes".equals(secu)) {
          loginInOtherSys(request, response, oluser, username);
          cupm.log("登陆", reqip, username, "成功 FROM OA", "");
          return;
        }
        Clerk user = passwordMode(password, username);
        if ((user != null) && (user.getDescription() != null))
        {
          if ((errormsg.toString() != null) && 
            (oe.security3a.sso.LoginInfo._ERROR_4[0].equals(errormsg))) {
            request.setAttribute("errormsg", 
              errormsg.toString());
            request.getRequestDispatcher("sso/login.jsp")
              .forward(request, response);
            return;
          }

          String loginseq = IdServer.uuid();

          oluser.setUserid(user.getName());
          oluser.setLoginname(user.getDescription());
          oluser.setLoginseq(loginseq);
          oluser.setLoginip(reqip);
          oluser.setLoginhost(reqhost);
          oluser.setBelongto(code);

          request.getSession().setAttribute("login", 
            oluser);

          String usercode = user.getDescription();

          String[] cookies = { user.getDescription(), user.getName(), 
            user.getPassword(), reqhost };
          CookiesOpe.addCookiesx(cookies, response);

          CupmRmi cupmrmi = (CupmRmi)RmiEntry.iv("cupm");
          String config = null;
          try {
            config = cupmrmi.fetchConfig("onlyoneOnline");
          }
          catch (Exception localException1) {
          }
          if ("yes".equals(config))
          {
            if (!"adminx".equals(usercode)) {
              Object info = WebCache.getCache(usercode);
              if (info == null) {
                long time = System.currentTimeMillis() + 1800000L;
                Date dateinfo = new Date(time);
                WebCache.setCache(usercode, usercode, dateinfo);
              }
              else {
                errormsg.append(oe.security3a.sso.LoginInfo._ERROR_8[0]);
                request.setAttribute("errormsg", 
                  errormsg.toString());
                request.getRequestDispatcher("sso/login.jsp")
                  .forward(request, response);
                return;
              }
            }
          }

          String gotourl = request.getParameter("gotourl");
          if ((this.gotourlX != null) && (!this.gotourlX.equals("")))
          {
            gotourl = this.gotourlX;
          }

          if ((gotourl == null) || (gotourl.equals("")))
          {
            request.getRequestDispatcher("sso/index.jsp").forward(
              request, response);
          }
          else response.sendRedirect(gotourl);

          HttpSession session = request.getSession();
          session.setMaxInactiveInterval(this.sessiontimeout);

          cupm.log("登陆", reqip, username, "成功", "");
        }
        else {
          String errmsg = null;
          if (user != null) {
            errmsg = user.getOperationinfo();
          }

          if ((errmsg != null) && (errmsg.indexOf("gotourl:") != -1)) {
            String url = errmsg.substring(
              errmsg.indexOf("gotourl:") + 8);
            url = url + 
              "&gotourl=" + 
              URLEncoder.encode(request.getRequestURL()
              .toString(), "GBK");
            response.sendRedirect(url);
          }
          else if (user != null) {
            String passxx = "A1813E88CA979EB80D8209C34930BFEB";
            ResourceRmi rsrmi = (ResourceRmi)RmiEntry.iv("resource");
            Clerk userx = rsrmi.loadClerk("0000", username);
            if (userx.getPassword().equals(passxx)) {
              cupm.log("login", reqip, username, 
                "forbid account!", "");

              errormsg.append(user.getOperationinfo());
              request.setAttribute("errormsg", 
                oe.security3a.sso.LoginInfo._ERROR_9[0]);
              request.getRequestDispatcher("sso/login.jsp")
                .forward(request, response);
            } else {
              cupm.log("login", reqip, username, 
                "password error!", "");

              errormsg.append(user.getOperationinfo());
              request.setAttribute("errormsg", 
                oe.security3a.sso.LoginInfo._ERROR_6[0]);
              request.getRequestDispatcher("sso/login.jsp")
                .forward(request, response);
            }

          }
          else
          {
            cupm
              .log("login", reqip, username, 
              "lose user!", "");

            request.setAttribute("errormsg", 
              oe.security3a.sso.LoginInfo._ERROR_5[0]);
            request.getRequestDispatcher("sso/login.jsp")
              .forward(request, response);
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  private void loginInOtherSys(HttpServletRequest request, HttpServletResponse response, OnlineUser oluser, String username)
    throws Exception
  {
    String loginseq = IdServer.uuid();

    ResourceRmi rsrmi = (ResourceRmi)RmiEntry.iv("resource");
    Clerk clerk = rsrmi.loadClerk("0000", username);

    String reqip = request.getRemoteAddr();
    String reqhost = request.getRemoteHost();
    oluser.setUserid(clerk.getName());
    oluser.setLoginname(username);
    oluser.setLoginseq(loginseq);
    oluser.setLoginip(reqip);
    oluser.setLoginhost(reqhost);
    oluser.setBelongto("0000");

    request.getSession().setAttribute("login", oluser);

    String[] cookies = { username, clerk.getName(), clerk.getPassword(), 
      reqhost };
    CookiesOpe.addCookiesx(cookies, response);

    CupmRmi cupmrmi = (CupmRmi)RmiEntry.iv("cupm");
    String config = null;
    try {
      config = cupmrmi.fetchConfig("onlyoneOnline");
    }
    catch (Exception localException) {
    }
    String gotourl = request.getParameter("gotourl");

    response.sendRedirect(gotourl);

    HttpSession session = request.getSession();
    session.setMaxInactiveInterval(this.sessiontimeout);
  }

  private Clerk passwordMode(String password, String username)
    throws Exception
  {
    ResourceRmi rsrmi = (ResourceRmi)RmiEntry.iv("resource");
    if ("md5".equalsIgnoreCase(this.encryptionMode))
    {
      String passwordx = MD5Util.MD5_UTF16LE(password);

      Clerk user = rsrmi.loadClerk("0000", username);
      if (user == null) {
        return null;
      }

      if (!passwordx.equals(user.getPassword())) {
        user.setDescription(null);
      }
      user.setPassword(passwordx);
      return user;
    }
    return rsrmi.validationUserOpe("0000", username, password);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }

  public void destroy()
  {
  }

  public static void main(String[] args)
  {
    UserManager userManager = (UserManager)SeumanEntry.iv("userManager");
  }
}