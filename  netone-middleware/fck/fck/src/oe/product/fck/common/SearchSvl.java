package oe.product.fck.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class SearchSvl extends HttpServlet {
	
	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		// ��ڲ���
		String pagename = request.getParameter("pagename");
		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		String search = "";
		String parentNaturalname="FCK.FCK";
		String conditionPre = null;
		List list = new ArrayList();
		UmsProtectedobject upo = new UmsProtectedobject();
		Map map = new HashMap();
		search = request.getParameter("searchText");
		if(StringUtils.isNotBlank(search)){
			conditionPre = " and extendattribute like '%"+search+"%' and naturalname like '%"+parentNaturalname+"%'";
			list = rsrmi.fetchResource(upo, map, conditionPre);
		}

		request.setAttribute("upolist", list);

		// ��� ���Ƶ���Դҳ�ĵ�ַ��Ĭ�ϴ�����resourceweb.properties�л��,���Ĭ����û����ô
		// ת�����Դҳ�л��

		pagename = pagename + ".search";
		String pageurl = null;
		try {
			pageurl = web.getString(pagename);
		} catch (Exception e) {
		}
		if (pageurl == null) {// ����Դҳ�л��
			UmsProtectedobject upoPage = null;
			try {
				upoPage = rsrmi.loadResourceByNatural(pagename);
			} catch (Exception e) {

			}
			if (upoPage != null) {
				pageurl = "/" + upoPage.getId() + ".jsp";
			} else {
				response.setCharacterEncoding("GBK");
				return;
			}
		}
		request.getRequestDispatcher(pageurl).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	public static void main(String[] args) {
		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		String search = "";
		String parentNaturalname="FCK.FCK";
		String conditionPre = null;
		List list = new ArrayList();
		UmsProtectedobject upo = new UmsProtectedobject();
		Map map = new HashMap();
		search = "a";
		if(StringUtils.isNotBlank(search)){
			conditionPre = " and extendattribute like '%"+search+"%' and naturalname like '%"+parentNaturalname+"%'";
			try {
				list = rsrmi.fetchResource(upo, map, conditionPre);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			System.out.println(object.getName());
		}
		System.out.println("----------------");


	}

}
