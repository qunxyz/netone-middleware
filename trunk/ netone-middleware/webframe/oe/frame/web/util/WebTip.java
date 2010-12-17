package oe.frame.web.util;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Web������ʾ������
 * 
 * @author chen.jia.xun(Robanco) �ù���Ĭ�ϵ��ַ�����GBK
 * 
 */
public class WebTip {
	/**
	 * ������ʾ
	 * 
	 * @param cando
	 * @return
	 */
	public static String tip(boolean cando) {
		String tipInfo = null;
		if (cando) {
			tipInfo = "<script type='text/javascript'>"
					+ "alert('�����ɹ���');</script>";
		} else {
			tipInfo = "<script type='text/javascript'>"
					+ "alert('��û�в���Ȩ��');</script>";
		}

		return tipInfo;
	}

	/**
	 * js��ʾ
	 * 
	 * @param info
	 *            ��ʾ����
	 * @return
	 */
	public static String tipSpi(String info) {

		String tipInfo = "<script type='text/javascript'>" + "alert('" + info
				+ "��');</script>";

		return tipInfo;
	}

	/**
	 * �Զ����ش�ӡhtmlִ�н��
	 * 
	 * @param info
	 *            ��ʾ����
	 * @param needclose
	 *            �Ƿ�رձ�����
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
	 * �Զ����ش�ӡhtmlִ�н��
	 * 
	 * @param info
	 *            ��ʾ��Ϣ
	 * @param needclose
	 *            �Ƿ�رձ�����
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
	 * ���ͻ����������ӡ�����Ϣ
	 * 
	 * @param infoOri
	 *            ��Ϣ����html����js
	 * @param response
	 *            http �����
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
