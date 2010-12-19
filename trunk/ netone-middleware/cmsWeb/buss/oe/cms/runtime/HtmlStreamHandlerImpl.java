package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.frame.orm.OrmerEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class HtmlStreamHandlerImpl implements HtmlStreamHandler {

	Log log = LogFactory.getLog(HtmlStreamHandlerImpl.class);

	public String toPortal(String cellid, String mode,
			HttpServletRequest request) {
		if (cellid == null) {
			return "无效的Portal元素";
		}
		TCmsInfocell page = null;
		if (_PORTAL_EDIT.equals(mode)) {
			page = XHtmlCachepool.fetchPageForEdit(cellid, request);
		} else {
			page = XHtmlCachepool.fetchPage(cellid, request);
		}

		/** 取得模块ID Don add 2009-2-21 */
		String modelid = request.getParameter("id");
		String modelid2 = request.getParameter("modelid");

		boolean bool = false;
		if (!StringUtils.isEmpty(modelid)) {

			TCmsInfomodel inf = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(modelid));
			bool = page.getBelongto().equals(inf.getNaturalname());
		} else if (!StringUtils.isEmpty(modelid2)) {
			TCmsInfomodel inf2 = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(modelid2));
			bool = page.getBelongto().equals(inf2.getNaturalname());
		}
		String ahead = "";
		String div_contianer = "";
		// Don modify 追加判断隶属是否相同 2009-2-21
		if ((_PORTAL_VIEW_TOOLBAR.equals(mode) || _PORTAL_EDIT.equals(mode))) {
			ahead = StringUtils.replace(HtmlStreamHandler._DIV_CONTAINER_HEAD,
					HtmlStreamHandler._DIV_CONTAINER_USE_CELL_ID, cellid);

			if (!bool) {
				ahead = StringUtils.replace(
						HtmlStreamHandler._DIV_CONTAINER_HEAD_DISABLE,
						HtmlStreamHandler._DIV_CONTAINER_USE_CELL_ID, cellid);
			} else {
				ahead = StringUtils.replace(
						HtmlStreamHandler._DIV_CONTAINER_HEAD,
						HtmlStreamHandler._DIV_CONTAINER_USE_CELL_ID, cellid);
			}

			div_contianer = HtmlStreamHandler._DIV_CONTAINER_DESIGN;

			div_contianer = StringUtils.replaceOnce(div_contianer,
					HtmlStreamHandler._DIV_CONTAINER_DESIGN_WIDTH_COLUMN, "");

			ahead = StringUtils.replace(ahead,
					HtmlStreamHandler._DIV_CONTAINER_USE_CELL_PATH, page
							.getBelongto()
							+ "." + page.getNaturalname().toUpperCase());

			ahead = StringUtils.replace(ahead,
					HtmlStreamHandler._DIV_CONTEXT_PATH, request
							.getContextPath());

		} else if (_PORTAL_VIEW_NOTOOLBAR.equals(mode)) {
			div_contianer = HtmlStreamHandler._DIV_CONTAINER_DESIGN;
		} else {
			div_contianer = HtmlStreamHandler._DIV_CONTAINER_;
		}

		String container = StringUtils.replace(div_contianer,
				HtmlStreamHandler._DIV_CONTAINER_DESIGN_HEIGHT_COLUMN, "");
		container = StringUtils.replaceOnce(container,
				HtmlStreamHandler._DIV_CONTAINER_DESIGN_WIDTH_COLUMN, "");
		String portalview = container + ahead + page.getBody()
				+ HtmlStreamHandler._DIV_CONTAINER_END;
		log.debug(portalview);
		return portalview;
	}

}
