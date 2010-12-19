package oe.cms;

import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.datasource.XMLParser;
import oe.cms.runtime.core.WiParser;
import oe.cms.runtime.ruleparser.XHtmlParser;
import oe.frame.orm.Ormer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 功能入口
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class CmsEntry {

	private static Log _log = LogFactory.getLog(CmsEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"cms_config.xml");

	private static AbstractApplicationContext dao = new ClassPathXmlApplicationContext(
			"cms_dao.xml");

	public static Object fetchBean(String functionname) {
		if (!appContext.containsBean(functionname)) {
			_log.error("没有定义该功能" + functionname);
			throw new RuntimeException("没有定义该功能" + functionname);
		}
		return appContext.getBean(functionname);
	}

	public static Object fetchDao(String functionname) {
		if (!dao.containsBean(functionname)) {
			_log.error("没有定义该功能" + functionname);
			throw new RuntimeException("没有定义该功能" + functionname);
		}
		return dao.getBean(functionname);
	}

	public static void main(String[] args) {
		XHtmlParser infocellDao = (XHtmlParser) fetchBean("xHtmlParser");
		infocellDao.xHtmlParser("fsdfdsf", null);
	}

}
