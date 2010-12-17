package oe.frame.web.util;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Web反馈提示工具类
 * 
 * @author chen.jia.xun(Robanco) 该工具默认的字符集是GBK
 * 
 */
public class WebTip {
	/**
	 * 构造提示
	 * 
	 * @param cando
	 * @return
	 */
	public static String tip(boolean cando) {
		String tipInfo = null;
		if (cando) {
			tipInfo = "<script type='text/javascript'>"
					+ "alert('操作成功！');</script>";
		} else {
			tipInfo = "<script type='text/javascript'>"
					+ "alert('您没有操作权限');</script>";
		}

		return tipInfo;
	}

	/**
	 * js提示
	 * 
	 * @param info
	 *            提示内容
	 * @return
	 */
	public static String tipSpi(String info) {

		String tipInfo = "<script type='text/javascript'>" + "alert('" + info
				+ "！');</script>";

		return tipInfo;
	}

	/**
	 * 自动返回打印html执行结果
	 * 
	 * @param info
	 *            提示内容
	 * @param needclose
	 *            是否关闭本窗口
	 * @param response
	 */
	public static void htmlInfo(String info, boolean needclose,
			HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		try {

			response.getWriter().print(tipSpi(info));
			if (needclose) {
				response.getWriter().print(
						"<script type='text/javascript'>"
								+ "window.close();</script>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 自动返回打印html执行结果
	 * 
	 * @param info
	 *            提示信息
	 * @param needclose
	 *            是否关闭本窗口
	 * @param response
	 */
	public static void htmlInfo(String info, boolean needclose,
			boolean needreflashFather, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		try {

			response.getWriter().print(tipSpi(info));
			if (needreflashFather) {
				response.getWriter().print(
						"<script type='text/javascript'>"
								+ "window.opener.location.reload();</script>");
			}
			if (needclose) {
				response.getWriter().print(
						"<script type='text/javascript'>"
								+ "window.close();</script>");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 往客户端浏览器打印输出信息
	 * 
	 * @param infoOri
	 *            信息内容html或者js
	 * @param response
	 *            http 输出流
	 */
	public static void htmlInfoOri(String infoOri, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(infoOri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
