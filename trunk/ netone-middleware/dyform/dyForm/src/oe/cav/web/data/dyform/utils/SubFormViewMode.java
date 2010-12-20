package oe.cav.web.data.dyform.utils;

import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;

public class SubFormViewMode {

	public static String selectedViewMode(List listsub, TCsBus object) {

		StringBuffer buff = new StringBuffer();
		for (Iterator iterator2 = listsub.iterator(); iterator2.hasNext();) {
			TCsForm objectsub = (TCsForm) iterator2.next();
			buff.append("<option value='" + objectsub.getFormcode() + "'>"
					+ objectsub.getFormname() + "</option>");
		}
		String head = "<option value='0'>--×Ó±íµ¥--</option>";
		return "<select onChange=\"if(this.value!='0'){window.open('/dyForm/data/data/sublist.do?subform='+this.value+'&lsh="
				+ object.getLsh()
				+ "&formcode="
				+ object.getFormcode()
				+ "&mode=view');}\">" + head + buff + "</select>";
	}

	public static String linkViewMode(List listsub, TCsBus object) {

		StringBuffer buff = new StringBuffer();
		for (Iterator iterator2 = listsub.iterator(); iterator2.hasNext();) {
			TCsForm objectsub = (TCsForm) iterator2.next();
			String mode = objectsub.getExtendattribute();
			if (FormDao.FORM_VIEWMODE_LIST.equals(mode)) {
				buff.append("<a href='/dyForm/data/data/sublist.do?subform="
						+ objectsub.getFormcode() + "&lsh=" + object.getLsh()
						+ "&formcode=" + object.getFormcode()
						+ "&mode=manage' target='_blank'>["
						+ objectsub.getFormname() + "]</a>");
			} else if (FormDao.FORM_VIEWMODE_LISTSMALL.equals(mode)) {
				buff
						.append("<a href='/dyForm/data/data/sublistShare.do?subform="
								+ objectsub.getFormcode()
								+ "&lsh="
								+ object.getLsh()
								+ "&formcode="
								+ object.getFormcode()
								+ "&mode=useview' target='_blank'>["
								+ objectsub.getFormname() + "]</a>");
			} else if (FormDao.FORM_VIEWMODE_LISTVIEW.equals(mode)) {
				buff
						.append("<a href='/dyForm/data/data/sublistShare.do?subform="
								+ objectsub.getFormcode()
								+ "&lsh="
								+ object.getLsh()
								+ "&formcode="
								+ object.getFormcode()
								+ "&mode=onlyview' target='_blank'>["
								+ objectsub.getFormname() + "]</a>");
			} else if (FormDao.FORM_VIEWMODE_CREATE.equals(mode)) {
				buff
						.append("<a href='/dyForm/data/showdata/createview.do?fatherlsh="
								+ object.getLsh()
								+ "&formcode="
								+ object.getFormcode()
								+ "' target='_blank'>["
								+ objectsub.getFormname() + "]</a>");
			} else if (FormDao.FORM_VIEWMODE_VIEW.equals(mode)) {
				buff.append("<a href='/dyForm/data/showdata/display.do?"
						+ "lsh=" + object.getLsh() + "&formcode="
						+ object.getFormcode() + "' target='_blank'>["
						+ objectsub.getFormname() + "]</a>");
			} else if (FormDao.FORM_VIEWMODE_LISTVIEWX.equals(mode)) {
				buff
						.append("<a href='/dyForm/data/showdata/sublistview.do?subform="
								+ objectsub.getFormcode()
								+ "&lsh="
								+ object.getLsh()
								+ "&formcode="
								+ object.getFormcode()
								+ "' target='_blank'>["
								+ objectsub.getFormname() + "]</a>");
			} else {
				buff.append("<a href='/dyForm/data/data/sublist.do?subform="
						+ objectsub.getFormcode() + "&lsh=" + object.getLsh()
						+ "&formcode=" + object.getFormcode()
						+ "' target='_blank'>[" + objectsub.getFormname()
						+ "]</a>");
			}

		}
		return buff.toString();
	}

}
