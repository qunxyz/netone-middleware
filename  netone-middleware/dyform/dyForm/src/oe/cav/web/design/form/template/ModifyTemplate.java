package oe.cav.web.design.form.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import oe.cav.web.design.form.TemplateOperation;

import org.apache.commons.lang.StringUtils;

public class ModifyTemplate {

	static String templatemodifyhead = "";

	static String templatemodifyend = "";

	public static InputStream todo(HttpServletRequest ae,String currentip){
		String formcode = (String) ae.getParameter("formcode");
		String contextpath = ae.getContextPath();
		String contextIp = ae.getLocalAddr();
		int port = ae.getLocalPort();
		String parmaer = "?formcode=" + formcode + "&fatherlsh=1";

		String apppath = TemplateOperation.createUrl + parmaer;

		String tempalteFile = "templateModifyHead.txt";

		if (templatemodifyhead.equals("")) {
			templatemodifyhead = TemplateOperation
					.readtemplatehead(tempalteFile);
		}

		
		String urlThis = currentip +  contextpath
				+ apppath;

		String htmlInfo = TemplateOperation.buildUrl(urlThis);
		String body = StringUtils.substringAfter(htmlInfo,
				"<!-- Head properties -->");
		htmlInfo = templatemodifyhead + body;
		// 如果是修改的化,需要修改模板

		String beforeendinfo = StringUtils.substringBefore(htmlInfo,
				"<!-- End properties -->");
		if (templatemodifyend.equals("")) {
			templatemodifyend = TemplateOperation
					.readtemplatehead("templateModifyEnd.txt");
		}
		htmlInfo = beforeendinfo + templatemodifyend;
		htmlInfo = StringUtils.replace(htmlInfo,
				"('/dyForm/data/data/createope.do'",
				"('/dyForm/data/data/modifyope.do'");
		
		// 将动态表单中与资源相关的路径改成外部的路径
		String thisResourcepath=contextpath+"/PagelistRightSvl?";
		String outerResourcepath="<%=basePath%>"+"PagelistRightSvl?";
		htmlInfo=StringUtils.replace(htmlInfo, thisResourcepath, outerResourcepath);

		return new ByteArrayInputStream(htmlInfo.getBytes());
	}
}
