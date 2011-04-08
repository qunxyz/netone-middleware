package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.frame.orm.OrmerEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author yan.mou.xie
 * 
 */
public class ExtHtmlStreamHandlerImpl implements HtmlStreamHandler 
{
	Log log = LogFactory.getLog(ExtHtmlStreamHandlerImpl.class);

	public String toPortal(
							String cellid,
							String mode,
							HttpServletRequest request) 
	{
		if (cellid == null) 
		{
			return "��Ч��PortalԪ��";
		}
		
		TCmsInfocell page = null;
		
		// ֧�ֻ���
		page = XHtmlCachepool4Ext.fetchPage(cellid, request);
		
		log.debug(page.getBody());
		
		return page.getBody();
	}

}
