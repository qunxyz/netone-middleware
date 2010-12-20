package oe.cav.web.design.form.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import oe.cav.web.design.form.TemplateOperation;

import org.apache.commons.lang.StringUtils;

public class QueryTemplate {

	static String templatequeryhead = "";

	static String templatequeryend = "";

	public static InputStream todo(HttpServletRequest ae, String currentip) {
		String formcode = (String) ae.getParameter("formcode");
		String contextpath = ae.getContextPath();
		String contextIp = ae.getLocalAddr();
		int port = ae.getLocalPort();
		String parmaer = "?formcode=" + formcode + "&fatherlsh=1";

		String apppath = TemplateOperation.queryUrl + parmaer;

		String tempalteFile = "templateQueryHead.txt";

		if (templatequeryhead.equals("")) {
			templatequeryhead = TemplateOperation
					.readtemplatehead(tempalteFile);
		}

		String urlThis = currentip + contextpath + apppath;

		String htmlInfo = TemplateOperation.buildUrl(urlThis);
		String body = StringUtils.substringAfter(htmlInfo,
				"<!-- Head properties -->");
		htmlInfo = templatequeryhead + body;

		htmlInfo = htmlInfo.replace(
				"checkAndCommit('/dyForm/data/data/list.do?quetip=true')",
				"opener.close();checkAndCommit('<%=basePath%>dylist?pagename=<%=pagename%>')");

		return new ByteArrayInputStream(htmlInfo.getBytes());
	}

}
