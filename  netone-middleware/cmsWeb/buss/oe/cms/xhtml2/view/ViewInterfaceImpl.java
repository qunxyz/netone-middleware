package oe.cms.xhtml2.view;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cms.EnvEntryInfo;
import oe.cms.cfg.TCmsRecordtip;
import oe.cms.runtime.ScriptExe;
import oe.cms.xhtml.LinkMake;
import oe.cms.xhtml.core.TableUtil;
import oe.cms.xhtml.core.graph.Graph2SeveletAdpet;
import oe.cms.xhtml.core.graph.GraphSeveletAdpet;
import oe.cms.xhtml2.ViewInterface;
import oe.cms.xhtml2.view.core.DispSelectInfo;
import oe.cms.xhtml2.view.core.DispSelectInfo2;
import oe.cms.xhtml2.view.core.MarkTIp;
import oe.cms.xhtml2.view.core.OpenDetailInfo;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;



public final class ViewInterfaceImpl implements ViewInterface {

	/**
	 * 注意: 不能去引用 CMS 下的jsp文件，这将导致JPP程序无法作为标签单独使用
	 */
	public String openDetailInfo(String cellid, String displayinfo,
			String target) {

		String targetReal = target == null || target.equals("") ? "_blank"
				: target;
		try {
			return OpenDetailInfo.xhtmlinfo(cellid, displayinfo, targetReal,
					EnvEntryInfo.env.fetchEnvValue("jppWebBase"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String linkDetailInfo(String cellid, String title, String target) {
		if (title == null || title.trim().length() == 0) {
			title = "详细信息";
		}

		String linkpath = null;
		try {
			linkpath = EnvEntryInfo.env.fetchEnvValue("jppWebBase")
					+ "cms/detailinfo.jsp?cellid=" + cellid;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String targetReal = target == null || target.equals("") ? "_blank"
				: target;

		return "<a href=\"" + linkpath + "\" target=\"" + targetReal + "\">"
				+ title + "</a>";
	}

	public String selectInfo(String[] arg0, String[] arg1, String[] arg2) {
		return DispSelectInfo2.fetchSelectWithInfo(arg0, arg1, arg2);
	}

	public String selectInfo(String[] arg0, String[] arg1) {
		return DispSelectInfo.fetchSelectWithInfo(arg0, arg1);
	}

	public String dispTable(String[][] arg0, String[] arg1) {

		return TableUtil.fetchTable(arg0, arg1);
	}

	public String dispRowOnly(String[][] arg0) {
		return TableUtil.fetchTableRow(arg0);
	}

	public String fetchGraph2Coordinate(String[] dimvaluelist, String dimName,
			String[][] targetvaluelistLeft, String[][] targetvaluelistRight,
			String[] targetnameLeft, String[] targetnameLRight, String title,
			String is3DStr, String xoffset, String yoffset) {
		boolean is3D = "true".equals(is3DStr);
		String dimvalueListLink = LinkMake.makeLink(dimvaluelist);

		String[] targetValuelinkLeft = new String[targetvaluelistLeft.length];
		for (int i = 0; i < targetValuelinkLeft.length; i++) {
			targetValuelinkLeft[i] = LinkMake.makeLink(targetvaluelistLeft[i]);
		}

		String[] targetValuelinkRight = new String[targetvaluelistRight.length];
		for (int i = 0; i < targetValuelinkRight.length; i++) {
			targetValuelinkRight[i] = LinkMake
					.makeLink(targetvaluelistRight[i]);
		}
		String charType = is3D ? "3D" : "2D";

		return Graph2SeveletAdpet.fetchGraphX(dimvalueListLink,
				targetValuelinkLeft, targetValuelinkRight, dimName,
				targetnameLeft, targetnameLRight, charType, title, xoffset,
				yoffset);
	}

	public String fetchGraph(String[] dimvaluelist, String dimName,
			String[][] targetvaluelist, String[] targetname, String charttype,
			String title, String xoffset, String yoffset) {
		String dimvalueListLink = LinkMake.makeLink(dimvaluelist);
		String[] targetvaluelistLink = LinkMake.makeLink(targetvaluelist);
		return GraphSeveletAdpet.fetchGraph(dimvalueListLink,
				targetvaluelistLink, dimName, targetname, charttype, title,
				xoffset, yoffset);
	}

	public String fetchImg(String imgid, String style) {
		if (style != null) {
			return "<img src=\"/fileupload/fi/downloadAction.do?fileid="
					+ imgid + "\" " + style + ">";
		}
		return "<img src=\"/fileupload/fi/downloadAction.do?fileid=" + imgid
				+ "\" >";
	}

	public String fetchFile(String fileid, String filename) {
		String url = "/fileupload/fi/downloadAction.do?fileid=" + fileid;
		return "<a href=\"" + url + "\" target=\"_blank\">" + filename + "</a>";

	}

	/**
	 * 获得真实的数值
	 * 
	 * @param value
	 * @param cellid
	 * @return
	 */
	public String fetchValue(String value, String cellid, String defaultValue) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);

		List list = ormer.fetchQuerister().queryObjects(ttip, null);
		if (list != null && list.size() != 0) {
			ttip = (TCmsRecordtip) list.get(0);
		}
		String tipinfo = ttip.getTipinfo();
		return tipinfo == null ? defaultValue : tipinfo;
	}

	/**
	 * 获得值编辑入口(带有批注)
	 * 
	 * @param value
	 *            序列号
	 * @param cellid
	 *            咨讯元ID
	 * @param defaultValue
	 *            默认返回值
	 * @return
	 */
	public String fetchValueInfo(String value, String cellid,
			String defaultValue) {

		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);

		List list = ormer.fetchQuerister().queryObjects(ttip, null);
		if (list != null && list.size() != 0) {
			ttip = (TCmsRecordtip) list.get(0);
		}
		String title = ttip.getTipinfo() == null ? defaultValue : ttip
				.getTipinfo();

		String culr = null;
		try {
			culr = EnvEntryInfo.env.fetchEnvValue("curl");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String linkpath = culr
				+ "/cmsWeb/recordtipmodiView.do?valuedo=1&sqlid=" + cellid
				+ "&recordid=" + value;

		String info = "javascript:window.open ('"
				+ linkpath
				+ "', '_blank', 'height=490, width=720, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')";
		// 获得批注信息，title是批注的值
		String[] infox = MarkTIp.fetchMarkTip(title, cellid);

		return "<img onClick=\""
				+ info
				+ "\" src=\"/cmsWeb/image/tipx.gif\" style=\"CURSOR: hand\"></img>"
				+ "<label title=\"" + infox[0] + "\" onClick=\"" + infox[1]
				+ "\"><font color='black'>" + title + "</font></label>";
	}

	public String valueInfo(String value, String cellid, String defaultValue) {

		String culr = null;
		try {
			culr = EnvEntryInfo.env.fetchEnvValue("curl");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String linkpath = culr
				+ "/cmsWeb/recordtipmodiView.do?valuedo=1&sqlid=" + cellid
				+ "&recordid=" + value;

		String info = "javascript:window.open ('"
				+ linkpath
				+ "', '_blank', 'height=490, width=720, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')";

		return "<img onClick=\""
				+ info
				+ "\" src=\"/cmsWeb/image/tipx.gif\" style=\"CURSOR: hand\"></img>";
	}

	public String modifyValueColor(String valueExpress, String color) {
		if (valueExpress == null || valueExpress.equals("")) {
			return "";
		}
		return StringUtils.replaceOnce(valueExpress, "<font color='black'>",
				"<font color='" + color + "'>");
	}

	public String detailInfoCoreUrl(String cellid) {
		try {
			return EnvEntryInfo.env.fetchEnvValue("jppWebBase")
					+ "cms/detailinfo.jsp?cellid=" + cellid;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String outitem(String itemname, Object req) {
		HttpServletRequest request = (HttpServletRequest) req;
		// 特别标示下该字段
		if (WebCache.containCache(WebCache._CACHE_ITEM_WEBITEM + itemname)) {
			return (String) WebCache.getCache(WebCache._CACHE_ITEM_WEBITEM
					+ itemname);
		}

		UmsProtectedobject upo = null;
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			upo = rsrmi.loadResourceByNatural(itemname);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (upo == null) {
			return "";
		}
		String cacheCycle = upo.getDescription();
		String cellid = upo.getId();
		String bodyinfo = upo.getExtendattribute();
		String[] rsinfo = ScriptExe
				.executeScriptCore(cellid, bodyinfo, request);

		if (cacheCycle != null && !cacheCycle.equals("")
				&& rsinfo[0].equals("1")) {
			long currentime = System.currentTimeMillis();
			long cachecycle = currentime
					+ (long) (Double.parseDouble(cacheCycle) * 1000 * 60 * 60);
			WebCache.setCache(WebCache._CACHE_ITEM_WEBITEM + itemname,
					rsinfo[1], new Date(cachecycle));
		}
		return rsinfo[1];
	}

}
