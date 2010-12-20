package oe.cav.web.design.form.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import oe.cav.bean.logic.column.TCsColumn;

import oe.cav.web.design.form.TemplateOperation;

import org.apache.commons.lang.StringUtils;


public class ReadMeTemplate {
	static String templatelist = "";

	public static InputStream todo(HttpServletRequest ae,
			List listColumn) {
		String formcode = (String) ae.getParameter("formcode");

		String tempalteFile = "ReadMe.txt";
	
		if (templatelist.equals("")) {
			templatelist = TemplateOperation.readtemplatehead(tempalteFile);
		}

		String htmlInfo =StringUtils.replace(templatelist, "$$formcode$$", formcode);

		return new ByteArrayInputStream(htmlInfo.getBytes());
	}
}
