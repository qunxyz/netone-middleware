package oe.teach.web.turnpage;

import java.lang.reflect.InvocationTargetException;

import oe.teach.mid.buss.OecStudent;

import org.apache.commons.beanutils.BeanUtils;

public class UtilMap {

	public static void toMap(Object to, Object from) {
		OecStudent oecstu = new OecStudent();
		try {
			BeanUtils.copyProperties(to, from);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
