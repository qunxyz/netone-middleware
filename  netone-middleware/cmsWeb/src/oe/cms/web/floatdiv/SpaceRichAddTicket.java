package oe.cms.web.floatdiv;

import java.io.IOException;

import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.dao.infomodel.ModelDao;

public class SpaceRichAddTicket extends HttpServlet {

	public static final String TODAY_TICKET = "mytodayticket";

	public static final int maxage = 8640000;

	/**
	 * Constructor of the object.
	 */
	public SpaceRichAddTicket() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=GBK");
		int ticketnum = giveTicket(request, response);
		if (ticketnum >= 6) {
			response
					.getWriter()
					.print(
							"<script>alert('��л���Ĳ��룬��ϧ����6�ε�ͶƱ�����Ѿ�����,���������֧����ϲ���ĳ���ռ�');window.close();</script>");
			return;
		}
		String levels = request.getParameter("levels");
		String modelid = request.getParameter("modelid");
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		int order = dao.fetchOrder(levels, modelid);
		dao.supportThis(levels, order);
		String tipinfoToday = "��л����֧��,���ջ���" + (6 - ticketnum - 1)
				+ "��ͶƱ���ᣬ����������ϲ���ĳ���ռ�ͶƱ��!";
		response
				.getWriter()
				.print(
						"<script>alert('"
								+ tipinfoToday
								+ "');window.close();self.opener.location.reload();</script>");

	}

	private int giveTicket(HttpServletRequest request,
			HttpServletResponse response) {

		Date date = new Date(System.currentTimeMillis());
		String dateinfo = date.toString();
		String[] cookies = readCookie(request);
		int todayTickets = 0;
		// �����ǵ�һ�ε�½������Cookies��ʧ���������ʼ��Cookies
		if (cookies == null || cookies.length != 2) {
			todayTickets = 0;
			addCookies("" + todayTickets + "#" + dateinfo, response);
			return 0;
		}
		cookies = readCookie(request);
		// ���cookies�е�������Ϣ
		String today = cookies[1];
		// ���cookies�еı���ͶƱ���
		String todayTicket = cookies[0];
		try {
			todayTickets = Integer.parseInt(todayTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ������Ǳ��գ���ô��ʼ��ͶƱ���
		if (!dateinfo.equals(today)) {
			todayTickets = 0;
			addCookies("" + todayTickets + "#" + dateinfo, response);
		}
		// ͶƱ
		if (todayTickets < 6) {
			todayTickets++;
			addCookies("" + todayTickets + "#" + dateinfo, response);
			// return "��л����֧��,���ջ���"+todayTickets+"��ͶƱ���ᣬ����������ϲ���ĳ���ռ�ͶƱ��!";
		}
		return todayTickets;

	}

	private void addCookies(String tiket, HttpServletResponse rep) {

		String cookiestr = null;
		try {
			cookiestr = java.net.URLEncoder.encode(tiket, "GBK");

		} catch (Exception ex) {

		}

		Cookie cookie = new Cookie(TODAY_TICKET, cookiestr);
		cookie.setMaxAge(maxage);
		cookie.setPath("/");
		rep.addCookie(cookie);
	}

	public static String[] readCookie(HttpServletRequest req) {
		Cookie usercookie = null;
		Cookie[] cookie = req.getCookies();
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals(TODAY_TICKET)) {
					usercookie = cookie[i];
					break;
				}
			}
		}
		if (usercookie != null) {
			String[] p = usercookie.getValue().split("%23");// %23Ϊ#
			return p;
		} else {
			return null;
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
