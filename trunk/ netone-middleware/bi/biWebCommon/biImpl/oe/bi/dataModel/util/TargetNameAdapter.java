package oe.bi.dataModel.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.dataModel.obj.TargetColumn;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.TargetElement;


public class TargetNameAdapter {

	public static void adapte(ChoiceInfo choice, String[] id, String[] name) {
		List list = choice.getTargetElement();
		Map map = new HashMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TargetElement element = (TargetElement) iter.next();
			String targetid = element.getId();
			String targetname = element.getName();
			if (targetname != null && !targetname.equals("")) {
				map.put(targetid, targetname);
			}
		}

		for (int i = 0; i < id.length; i++) {
			String namepre = (String) map.get(id[i]);
			if (namepre != null) {
				name[i] = namepre;
			}
		}
	}

}
