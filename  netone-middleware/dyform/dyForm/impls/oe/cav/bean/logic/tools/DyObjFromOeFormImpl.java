package oe.cav.bean.logic.tools;

import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.FormEntry;

import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��OeForm�еõ�����DyObj����Ķ���������DyObj
 * 
 * @author admin
 * 
 */
public class DyObjFromOeFormImpl implements DyObjFromOeForm {

	static Log log = LogFactory.getLog(DyObjFromOeFormImpl.class);

	public DyObj[] parser(String systemid) {
		FormDao formDao = (FormDao) FormEntry.fetchBean("formDao");
		if (systemid == null || systemid.equals("")) {
			log.error("���ݿ�������Ϊ��");
			return null;
		} else {
			DyObj[] dfo = null;
			// ���system

			// ���form�б�
			TCsForm obj = new TCsForm();
			List formlist =formDao.queryObjects(obj);
			if (formlist == null || formlist.size() == 0) {
				log.error("δȡ���κα�form");
				return null;
			} else {
				dfo = new DyObj[formlist.size()];
				// ���form
				
				int i = 0;
				for (Iterator iter = formlist.iterator(); iter.hasNext();) {
					TCsForm tcf = (TCsForm) iter.next();
					// ���column
					List formcolumn = formDao.fetchColumnList(tcf.getFormcode());
					// ��װDyObj����
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
