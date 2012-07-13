package oe.cms.web.frames;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


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
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (StringUtils.isEmpty(reqmap.getParameter("task"))) {
			try {
				UmsProtectedobject rootElement = rsrmi.loadResourceByNatural(root);

				List<UmsProtectedobject> nextlist = rsrmi.subResource(rootElement.getId());

				Collections.reverse(nextlist);

				Map<String, List<UmsProtectedobject>> map = new LinkedHashMap<String, List<UmsProtectedobject>>();
				Map<String, List<UmsProtectedobject>> Mapone = new LinkedHashMap<String, List<UmsProtectedobject>>();
				for (Iterator iterator = nextlist.iterator(); iterator.hasNext();) {
					UmsProtectedobject object = (UmsProtectedobject) iterator.next();
					List<UmsProtectedobject> nextNextlist = rsrmi.subResource(object.getId());
					Collections.reverse(nextNextlist);
					map.put(object.getNaturalname(), nextNextlist);
					
					for (Iterator itera = nextNextlist.iterator(); itera.hasNext();) {
						UmsProtectedobject obj = (UmsProtectedobject) itera.next();
						List<UmsProtectedobject> objNextlist = rsrmi.subResource(obj.getId());
						Collections.reverse(nextNextlist);
						Mapone.put(obj.getNaturalname(), objNextlist);
					}
					
				}
				request.setAttribute("childrenlist", nextlist);
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
