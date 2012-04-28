package oe.cms.web.frames;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.validator.Var;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * ҳ��ExtJsչ�ֳ���
 * 
 * @author yan.mou.xie<br>
 *         mail:ymx@foxmail.com<br>
 *         tel:13850114381<br>
 */
public class ExtProletAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Func.print_r(request);
		String ajax = request.getParameter("ajax");
		String node = request.getParameter("node");
		String mode = request.getParameter("mode");
		String root = request.getParameter("listPath");
		String Dption = request.getParameter("Dption");
		if(StringUtils.isNotEmpty(Dption)){
			if (StringUtils.isEmpty(root)) {
				return null;
			}
			try {
				StringBuffer jsonBf = new StringBuffer();
				String sp = "";
				String html = "";
				String Actionurl = "";
				if ("FRAMEPG.FRAMEPG".equals(root)) {
					html = "1";
				}else{
					ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
					/*Dption:��ȡ��������*/
					html = rsrmi.loadResourceByNatural(root).getDescription();
					/*Actionurl:���õ�ַ*/
					Actionurl = rsrmi.loadResourceByNatural(root).getActionurl();
					}
					jsonBf.append(sp);
					jsonBf.append("{");
					jsonBf.append("Dption:'" + html + "',");
					jsonBf.append("Actionurl:'" + Actionurl + "'");
					jsonBf.append("}");
					
					PrintWriter pww = response.getWriter();
					// ���json
					System.out.println("[" + jsonBf.toString() + "]");
					pww.write("[" + jsonBf.toString() + "]");
					pww.flush();
					pww.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
		
		if (StringUtils.isNotEmpty(ajax)) {
			if ("1".equals(node)) // �״δ�ӡ��
			{
				show(root, request, response, true);
			} else {
				show(node, request, response, true);
			}
		} else {
			if ("4".equals(mode)) // ����Ԥ��2
			{
				show(root, request, response, false);
				return mapping.findForward("extportalframe2");
			} else {
				return mapping.findForward("extportalframe1");
			}
		}
	  }
		return null;
	}

	private void show(String naturalName, HttpServletRequest request,
			HttpServletResponse response, boolean json) {
		StringBuffer jsonBuffer = new StringBuffer();
		String split = "";
		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject rootElement = rsrmi
					.loadResourceByNatural(naturalName);

			List<UmsProtectedobject> nextlist = rsrmi.subResource(rootElement
					.getId());

			Collections.reverse(nextlist);

			Map<String, List<UmsProtectedobject>> map = new LinkedHashMap<String, List<UmsProtectedobject>>();
			for (Iterator iterator = nextlist.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				List<UmsProtectedobject> nextNextlist = rsrmi
						.subResource(object.getId());
				Collections.reverse(nextNextlist);
				map.put(object.getNaturalname(), nextNextlist);
				
				jsonBuffer.append(split);
				jsonBuffer.append("{");
				jsonBuffer.append("id:'" + object.getNaturalname() + "',");
				jsonBuffer.append("text:'" + object.getName() + "'");
				
				if (nextNextlist.size() != 0) {
					jsonBuffer.append(",leaf: false");
				} else {
					jsonBuffer.append(",leaf: true");
				}
				jsonBuffer.append("}");

				split = ",";
			}

			if (json) {
				// ���json
				System.out.println("[" + jsonBuffer.toString() + "]");
				response.setContentType("text/json;charset=UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write("[" + jsonBuffer.toString() + "]");
				pw.flush();
				pw.close();
			} else {
				request.setAttribute("childrenlist", nextlist);
				request.setAttribute("map", map);
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
