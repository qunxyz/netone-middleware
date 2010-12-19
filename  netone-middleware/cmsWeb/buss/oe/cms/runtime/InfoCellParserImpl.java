package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.runtime.ruleparser.XHtmlParser;

/**
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class InfoCellParserImpl implements InfoCellParser {

	private HttpServletRequest request;

	public String performInfoCellParser(String boyinfo) {

		XHtmlParser xmparser = (XHtmlParser) CmsEntry
				.fetchBean(CmsBean._XHTML_PARSER);
		return xmparser.xHtmlParser(boyinfo, request);

	}

	public void setRequestInfo(HttpServletRequest arg0) {
		this.request = arg0;

	}

}
