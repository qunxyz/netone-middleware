package oe.cav.web.design.form.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.web.design.form.TemplateOperation;

import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.struts.ActionEvent;

public class ListTemplate {
	static String templatelist = "";

	public static InputStream todo(HttpServletRequest ae,
			List listColumn) {
		String formcode = (String) ae.getParameter("formcode");

		String tempalteFile = "templateListBody.txt";

		if (templatelist.equals("")) {
			templatelist = TemplateOperation.readtemplatehead(tempalteFile);
		}

		StringBuffer butColumn = new StringBuffer();
		for (Iterator iter = listColumn.iterator(); iter.hasNext();) {
			TCsColumn element = (TCsColumn) iter.next();
			butColumn.append("<td height='21'>${getCol."
					+ element.getColumnid() + "}</td>\n\r");
		}

		String htmlInfo = StringUtils.replace(templatelist, "$$listcolumn$$",
				butColumn.toString());
		htmlInfo = StringUtils.replace(htmlInfo, "$$formcode$$", formcode);

		return new ByteArrayInputStream(htmlInfo.getBytes());
	}
}
