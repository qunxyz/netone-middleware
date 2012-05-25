package oe.mid.web.rspage.pagelist.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import org.apache.commons.beanutils.BeanUtils;

public class MultiSelectDsRole
  implements IAjaxService
{
  public String execute(HttpServletRequest request, RequestParamMap map)
    throws Exception
  {
    OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
    OnlineUser oluser = olmgr.getOnlineUser(request);
    String code = oluser.getBelongto();

    ResourceRmi rsrmi = null;
    StringBuffer sb = new StringBuffer();
    try
    {
      rsrmi = (ResourceRmi)RmiEntry.iv("resource");
      UmsProtectedobject upox = rsrmi.loadResourceById(map.getParameter("id"));
      UmsRole role = new UmsRole();
      role.setBelongingness(upox.getNaturalname());
      List list = rsrmi.fetchRole(role, null, "");
      
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    	  UmsRole cl = (UmsRole) iterator.next();
        StringBuffer but = new StringBuffer();

        sb.append("name=" + cl.getName() + but + ",");
        String naturalname = null;
        try {
          naturalname = BeanUtils.getProperty(cl, "id");
        } catch (Exception e) {
          naturalname = cl.getNaturalname();
        }
        sb.append("naturalname=" + naturalname + ";");	
      }

    }
    catch (NotBoundException e)
    {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}