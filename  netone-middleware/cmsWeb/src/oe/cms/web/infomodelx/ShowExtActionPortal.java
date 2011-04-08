package oe.cms.web.infomodelx;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.runtime.HtmlStreamHandler;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ExtpPortal,应用界面,主要用于发布门户信息, 该应用中其实还可以根据参数或者权限来控制每个界面的可见与否,或者能否编辑....
 * 
 * @author yan.mou.xie
 * 
 */
public class ShowExtActionPortal extends Action {

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");

		String id = request.getParameter("id"); //ModelID页组ID
		if (id == null || id.equals("")) {
			String naturalname = request.getParameter("name");
			if (naturalname != null && !naturalname.equals("")) {
				ResourceRmi rs;
				try {
					rs = (ResourceRmi) RmiEntry.iv("resource");
					id = rs.loadResourceByNatural(naturalname)
							.getExtendattribute();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		String mode = request.getParameter("portalmode"); // 预览1、预览2、预览3
		// 获取页组信息
		TCmsInfomodel usermodel = null;
		try {
			usermodel = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 支持外部 样式传递
		String style = request.getParameter("style");
		if (style != null && !style.equals("")) {
			usermodel.setStyleinfo(style);
		} else {
			String sytleinfox = usermodel.getUserid();
			if (sytleinfox != null || !sytleinfox.equals("")) {
				String styleinfo = StringUtils.substringBetween(sytleinfox,
						"[", "]");
				usermodel.setStyleinfo(styleinfo);
			}
		}
		
		List widthlist = null;
		String theme = "ext-all"; // ext的默认样式
			
		if (usermodel.getExtendattribute() == null
				|| usermodel.getExtendattribute().equals("")) {
			widthlist = new ArrayList();
		} else {
			widthlist = Arrays
					.asList(usermodel.getExtendattribute().split(","));
			
			// 获取extcss
			theme = StringUtils.substringAfter(usermodel.getExtendattribute(), "theme:");
			// 去掉可能存在的逗号，和之后的字串
			if(StringUtils.contains(theme, ","))
			{
				theme = StringUtils.substringBefore(theme, ",");
			}
			
			System.out.println("theme="+theme);
			request.setAttribute("theme", theme);
		}
		request.setAttribute("widthlist", widthlist.get(0));
				
		HashSet groupset = new HashSet(); // 用于存放InfoCellid页ID
		List sortList = new ArrayList();
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			
			// 从页组中获取页ID,来获取cell页
			List grouplist = usermodel.fetchInfocell(); // 解析XML来获取

			Iterator iter = grouplist.iterator();
			while (iter.hasNext()) {
				CellInfo cell = (CellInfo) iter.next();
				String rs = StringUtils.substringBetween(cell
						.getExtendattribute(), "[", "]");
				// 检查是否可见
				boolean check = cupm.checkUserPermission("0000", oluser
						.getLoginname(), rs, "3");
				if (!check) {
					continue;
				}
				groupset.add(cell.getInfoCellid().trim());
				
				sortList.add(cell);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int size = groupset.size();
		
		// 存放该组所有的页
		List pages = new ArrayList();
		List finalPages = new ArrayList();
		
		// 取出所有的cell页
		List allCell = getAllCells();
		
		if (size != 0) 
		{
			Iterator iter = allCell.iterator();
			while (iter.hasNext()) {
				TCmsInfocell cell = (TCmsInfocell) iter.next();
				
				if (groupset.contains(cell.getCellid().toString())) {
					
					iter.remove();
					
					// 读取内容
					HtmlStreamHandler htmlh = (HtmlStreamHandler) CmsEntry
					.fetchBean("extHtmlStreamHandler");

					String htmlsrc = htmlh.toPortal(cell.getCellid().toString(), mode, request);
					String htmlsrc1=htmlsrc.replaceAll("'", "\"");
					String htmlsrc2 =htmlsrc1.replaceAll("\n", "");
					String htmlsrc3=htmlsrc2.replaceAll("\r", "");
					
					cell.setBody(htmlsrc3);
					
					// 处理 Portal标题中的链接，该链接信息定义在页cell 的 extendattribute中
					String linkvalue = cell.getExtendattribute();
					String linkInfo = "";
					if (linkvalue != null) {
						String[] linkvaluex = StringUtils.split(linkvalue, "#");
						if (linkvaluex.length == 2) {
							linkInfo = "<a href=\"" + linkvaluex[1]
									+ "\" target=\"_blank\"><font class=\"HeaderLink\">" + linkvaluex[0] + "</font></a>";
							// 这边更换扩展属性为 标题link
							cell.setExtendattribute(linkInfo);
						}
					}
					
					pages.add(cell);
					
					if (usermodel.getExtendattribute() == null
							|| usermodel.getExtendattribute().equals("")) {
						usermodel.setExtendattribute("01");
					}
				}
			}
			for (Iterator iteratorx = sortList.iterator(); iteratorx.hasNext();) {
				CellInfo obj = (CellInfo) iteratorx.next();
				for (Iterator iterator = pages.iterator(); iterator.hasNext();) {
					TCmsInfocell object = (TCmsInfocell) iterator.next();
					System.out.println("getCellId="+object.getCellid());
					if(obj.getInfoCellid().equals(object.getCellid()))
					{
						System.out.println("getCellname="+object.getCellname());
						finalPages.add(object);
					}
				}
			}
			

		}
		
		
		request.setAttribute("pages", finalPages);

		String description = usermodel.getDescription();
		String layout = StringUtils.substringBetween(description, "$[", "]");

		String modelWidth = "1024";
		String modelHeight = "1000";
		if (layout != null && layout.length() > 0) {
			String width = StringUtils.substringBetween(layout, "width:", ";");
			String height = StringUtils
					.substringBetween(layout, "height:", ";");
			if (width != null && width.length() > 0) {
				modelWidth = width;
			}
			if (height != null && height.length() > 0) {
				modelHeight = height;
			}
		}

		request.setAttribute("modelWidth", modelWidth);
		request.setAttribute("modelHeight", modelHeight);
		request.setAttribute("model", usermodel);
		request.setAttribute("portalmode", mode);
		request.setAttribute("modelid", id);

		return mapping.findForward("extdesign");

	}

	private List getAllCells() {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsInfocell cinfo = new TCmsInfocell();
		List list = ormer.fetchQuerister().queryObjects(cinfo, null);
		return list;
	}

}
