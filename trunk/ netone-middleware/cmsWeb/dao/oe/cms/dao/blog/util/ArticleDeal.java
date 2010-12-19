package oe.cms.dao.blog.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;


import oe.cms.CmsEntry;

import oe.cms.dao.blog.Blog;

/**
 * Oec Netone设计工具上，添加文章使用的
 * 
 * @author chen.jia.xun
 * 
 */
public class ArticleDeal {

	public static String makeBody(HttpServletRequest request) {

		String name = request.getParameter("name");
		name = WebStr.encode(request, name);
		String boarticle = request.getParameter("article");
		boarticle = WebStr.encode(request, boarticle);

		String diystyle = request.getParameter("diystyle");
		String bodystyle = request.getParameter("diystyleText");

		String articleTitle = "";
		String articleBody = "";
		if ("1".equals(diystyle)) {
			articleTitle = diyStyleTitleDo(request, name);
		} else {
			articleTitle = sysStyleTitleDo(request, name);
		}
		if ("1".equals(bodystyle)) {
			articleBody = diyStyleBodyDo(request, boarticle);
		} else {
			articleBody = sysStyleBodyDo(request, boarticle);
		}

		return articleTitle + articleBody;

	}

	private static String diyStyleTitleDo(HttpServletRequest request,
			String text) {
		// 标题部分
		// 颜色
		String p_color = request.getParameter("p_color");
		// 字体效果
		String p_valuea = request.getParameter("p_valuea");
		// 字体
		String p_valueb = request.getParameter("p_valueb");
		// 字体大小
		String p_valuec = request.getParameter("p_valuec");
		// 字体样式
		String p_valued = request.getParameter("p_valued");
		String headStyle = makeScript(p_valuea, p_valueb, p_valuec, p_valued,
				p_color);

		String titleReal = "<font style='" + headStyle + "'>" + text
				+ "</font>";

		return titleReal;

	}

	private static String diyStyleBodyDo(HttpServletRequest request, String text) {

		// 颜色
		String p_color1 = request.getParameter("p_color1");
		// 字体效果
		String p_value1 = request.getParameter("p_value1");
		// 字体
		String p_value2 = request.getParameter("p_value2");
		// 字体大小
		String p_value3 = request.getParameter("p_value3");
		// 字体样式
		String p_value4 = request.getParameter("p_value4");

		String bodyStyle = makeScript(p_value1, p_value2, p_value3, p_value4,
				p_color1);

		String bodyReal = "<font style='" + bodyStyle + "'>" + text + "</font>";
		return bodyReal;

	}

	private static String makeScript(String p_valuea, String p_valueb,
			String p_valuec, String p_valued, String p_color) {
		String headstylex = "";
		if ("Bold".equals(p_valued)) {
			headstylex = "font-weight:Bold";
		} else if ("Italic".equals(p_valued)) {
			headstylex = "font-style:italic";
		} else if ("Bold-Italic".equals(p_valued)) {
			headstylex = "font-style:italic;font-weight:Bold";
		}
		return "color:" + p_color + ";text-decoration:" + p_valuea
				+ ";font-family:" + p_valueb + ";font-size:" + p_valuec + ";"
				+ headstylex;
	}

	private static String sysStyleTitleDo(HttpServletRequest request,
			String text) {
		String headstyle = request.getParameter("headstyle");

		return "<font class='" + headstyle + "'>" + text + "</font>";

	}

	private static String sysStyleBodyDo(HttpServletRequest request, String text) {

		String bodystyle = request.getParameter("bodystyle");

		return "<font class='" + bodystyle + "'>" + text + "</font>";

	}

}
