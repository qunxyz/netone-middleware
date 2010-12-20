package oe.cav.bean.logic.tools;

import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.FormEntry;

import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 从OeForm中得到构造DyObj所需的对象来生成DyObj
 * 
 * @author admin
 * 
 */
public class DyObjFromOeFormImpl implements DyObjFromOeForm {

	static Log log = LogFactory.getLog(DyObjFromOeFormImpl.class);

	public DyObj[] parser(String systemid) {
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		if (systemid == null || systemid.equals("")) {
			log.error("数据库名不能为空");
			return null;
		} else {
			DyObj[] dfo = null;
			// 获得system

			// 获得form列表
			TCsForm obj = new TCsForm();
			List formlist =formDao.queryObjects(obj);
			if (formlist == null || formlist.size() == 0) {
				log.error("未取得任何表单form");
				return null;
			} else {
				dfo = new DyObj[formlist.size()];
				// 获得form
				
				int i = 0;
				for (Iterator iter = formlist.iterator(); iter.hasNext();) {
					TCsForm tcf = (TCsForm) iter.next();
					// 获得column
					List formcolumn = formDao.fetchColumnList(tcf.getFormcode());
					// 封装DyObj对象
					dfo[i].setSystemid(systemid);
					dfo[i].setFrom(tcf);
					dfo[i].setColumn(formcolumn);
					dfo[i].setExtendAttribute("");
					i++;
				}
			}
			return dfo;
		}
	}

}
