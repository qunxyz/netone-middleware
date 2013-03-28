package oe.cms.web.frames;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;


/**
 * 页框展现程序
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class ProletAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		String root = request.getParameter("listPath");
		// 注册入资源
		ResourceRmi rsrmi = null;
		Client3A user=null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			user=SecurityEntry.iv().onlineUser(request);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		

		if (StringUtils.isEmpty(reqmap.getParameter("task"))) {
			try {
				UmsProtectedobject rootElement = rsrmi.loadResourceByNatural(root);
				
				List<UmsProtectedobject> nextlistTmp = rsrmi.subResource(rootElement.getId());
				List nextList=new ArrayList();
				for (Iterator iterator = nextlistTmp.iterator(); iterator
						.hasNext();) {
					UmsProtectedobject umsProtectedobject = (UmsProtectedobject) iterator
							.next();
					boolean rs=SecurityEntry.iv().permission(user.getClientId(), umsProtectedobject.getNaturalname());
					if(rs)nextList.add(umsProtectedobject);
				}

				Collections.reverse(nextList);

				Map<String, List<UmsProtectedobject>> map = new LinkedHashMap<String, List<UmsProtectedobject>>();
				Map<String, List<UmsProtectedobject>> Mapone = new LinkedHashMap<String, List<UmsProtectedobject>>();
				
				for (Iterator iterator = nextList.iterator(); iterator.hasNext();) {
					UmsProtectedobject object = (UmsProtectedobject) iterator.next();
					List<UmsProtectedobject> nextList2Tmp = rsrmi.subResource(object.getId());
					Collections.reverse(nextList2Tmp);
					List nextList2=new ArrayList();
					for (Iterator iterator2 = nextList2Tmp.iterator(); iterator2
							.hasNext();) {
						UmsProtectedobject umsProtectedobject = (UmsProtectedobject) iterator2
								.next();
						boolean rs=SecurityEntry.iv().permission(user.getClientId(), umsProtectedobject.getNaturalname());
						if(rs)nextList2.add(umsProtectedobject);
					}
					map.put(object.getNaturalname(), nextList2);
					
					for (Iterator itera = nextList2.iterator(); itera.hasNext();) {
						UmsProtectedobject obj = (UmsProtectedobject) itera.next();
						List<UmsProtectedobject> nextList3Tmp = rsrmi.subResource(obj.getId());
						Collections.reverse(nextList3Tmp);
						List nextList3=new ArrayList();
						for (Iterator iterator2 = nextList3Tmp.iterator(); iterator2
								.hasNext();) {
							UmsProtectedobject umsProtectedobject = (UmsProtectedobject) iterator2
									.next();
							boolean rs=SecurityEntry.iv().permission(user.getClientId(), umsProtectedobject.getNaturalname());
							if(rs)nextList3.add(umsProtectedobject);
						}
						
						Mapone.put(obj.getNaturalname(), nextList3);
					}
					
				}
				request.setAttribute("childrenlist", nextList);
				request.setAttribute("map", map);
				request.setAttribute("Mapone", Mapone);
				request.setAttribute("menu", "11");
				String types = request.getParameter("type");
				if ("menu".equals(types)) {
					String initurl=request.getParameter("initurl");
					request.setAttribute("initurl", initurl);
					return mapping.findForward("portalmenu");
				} else if ("frame".equals(types)) {
					return mapping.findForward("portalframe");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 显示内容
			try {
				String naturalname = reqmap.getParameter("task");
				UmsProtectedobject p = rsrmi.loadResourceByNatural(naturalname);
				List list = rsrmi.subResource(p.getId());
				// 如果保护对象的 extendattribute的值为 final 的话，那么不要展现子级信息
				if (!"final".equals(p.getExtendattribute()) && list != null
						&& list.size() > 0) {
					request.setAttribute("list", list);
					request.setAttribute("res", "res");
				} else {
					request.setAttribute("url", p.getActionurl());
				}
				if(naturalname.endsWith(".NEW")){
					response.getWriter().write("<script>window.open('"+p.getActionurl()+"','_blank');</script>");
					return null;
				}
				return mapping.findForward("portalbody");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
