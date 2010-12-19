package oe.cms.xhtml;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

public class XhtmlTag extends SimpleTagSupport {
	private String var;

	public void doTag() throws JspException {
		Ormer ormer = OrmerEntry.fetchOrmer();

		try {
			HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
					.fetchBean("htmlStreamHandler");
			String rs = htmlStreamHandler.toPortal(var, "2", null);
			getJspContext().getOut().write(rs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setVar(String var) {
		this.var = var;
	}
}
