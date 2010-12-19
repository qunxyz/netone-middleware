package oe.cms;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfocell;
import oe.cms.runtime.HtmlStreamHandler;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.WebCache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildCmsJpp {
	private static Log _log = LogFactory.getLog(BuildCmsJpp.class);

	public static void todo(HttpServletRequest req) {
		WebCache.initCache();
		_log.info(" 初始化 portlet.....");
		Ormer ormer = OrmerEntry.fetchOrmer();

		TCmsInfocell cell = new TCmsInfocell();
		List list = ormer.fetchQuerister().queryObjects(cell, null);
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfocell cellPre = (TCmsInfocell) itr.next();
			_log.debug("初始化:" + cellPre.getCellname() + " ID:"
					+ cellPre.getCellid());
			HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
					.fetchBean("htmlStreamHandler");
			htmlStreamHandler.toPortal(cellPre.getCellid(), "2", req);
			System.out.println(".....done");
		}
		System.out.println(" 初始化 portlet.....Done");
	}
	
	public static void main(String[] args) {
		// 初始化缓存
		WebCache.initCache();
	}
}
