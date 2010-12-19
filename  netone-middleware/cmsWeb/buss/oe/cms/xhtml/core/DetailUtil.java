package oe.cms.xhtml.core;

import javax.servlet.http.HttpServletRequest;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

public class DetailUtil {

	public static String fetchInfo(String cellid, HttpServletRequest request) {

		HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
				.fetchBean("htmlStreamHandler");
		return htmlStreamHandler.toPortal(cellid, "2", request);

	}
}
