package oe.cav.web.design.form.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import oe.cav.web.design.form.TemplateOperation;

import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.struts.ActionEvent;

public class CreateTemplate {
	static String templatecreatedhead = "";

	static String templatecreatedyend = "";

	public static InputStream todo(HttpServletRequest ae, String basepath) {

		String formcode = (String) ae.getParameter("formcode");
		String contextpath = ae.getContextPath();
		String parmaer = "?formcode=" + formcode + "&fatherlsh=1";

		String apppath = TemplateOperation.createUrl + parmaer;

		String tempalteFile = "templateCreateHead.txt";

		if (templatecreatedhead.equals("")) {
			templatecreatedhead = TemplateOperation
					.readtemplatehead(tempalteFile);
		}
		String urlThis = basepath + contextpath + apppath;

		String htmlInfo = TemplateOperation.buildUrl(urlThis);
		String body = StringUtils.substringAfter(htmlInfo,
				"<!-- Head properties -->");
		htmlInfo = templatecreatedhead + body;
		// 将动态表单中与资源相关的路径改成外部的路径
		String thisResourcepath = contextpath + "/PagelistRightSvl?";
		String outerResourcepath = "<%=basePath%>" + "/PagelistRightSvl?";
		htmlInfo = StringUtils.replace(htmlInfo, thisResourcepath,
				outerResourcepath);

		return new ByteArrayInputStream(htmlInfo.getBytes());
	}
}
