package oe.cav.bean.logic.bus;

import oe.cav.bean.logic.form.FormDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * �������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class FormEntry {

	private static Log _log = LogFactory.getLog(FormEntry.class);

	private static AbstractApplicationContext appContext = new ClassPathXmlApplicationContext(
			"form_config.xml");

	public static Object fetchBean(String functionname) {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext("form_config.xml");
		}

		if (!appContext.containsBean(functionname)) {
			_log.error("û�ж���ù���" + functionname);
			throw new RuntimeException("û�ж���ù���" + functionname);
		}
		return appContext.getBean(functionname);
	}

	public static void main(String[] args) {
		FormDao formDao = (FormDao) fetchBean("formDao");
		formDao.fetchColumnList(null);
	}

}
