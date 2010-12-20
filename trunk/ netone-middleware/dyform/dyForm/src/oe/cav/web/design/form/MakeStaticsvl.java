package oe.cav.web.design.form;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.bus.BusExtendInfo;
import oe.cav.bean.logic.bus.BussDao;
import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.web.util.BuildFormStaticPage;

public class MakeStaticsvl extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String realpath = request.getRealPath("") + "/fm/";
		String formcode = request.getParameter("formcode");
		if (formcode == null) {
			return;
		}
		// 创建第一级静态页面
		BuildFormStaticPage.buildForumLevel1(realpath, formcode);
		// /////////////////////////////////////

		BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		TCsBus bus = new TCsBus();
		bus.setFormcode(formcode);
		bus.setFatherlsh("1");
		bus.setStatusinfo(BusExtendInfo._STATUS_NORMAL);
		List list = bussDao.queryObjects(bus);
		int count = 0;
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCsBus busPre = (TCsBus) itr.next();
			// 创建第二级静态页面
			BuildFormStaticPage.buildForumLevel2(realpath, busPre.getLsh(),
					busPre.getFormcode(), busPre.getParticipant());

			TCsBus busNext = new TCsBus();
			busNext.setFatherlsh(busPre.getLsh());
			busNext.setStatusinfo(BusExtendInfo._STATUS_NORMAL);
			List nextList = bussDao.queryObjects(busNext);

			for (Iterator itrnext = nextList.iterator(); itrnext.hasNext();) {
				TCsBus busNextNext = (TCsBus) itrnext.next();
				count++;
				// 创建第三级静态页面
				BuildFormStaticPage.buildForumLevel3(realpath, busNextNext
						.getLsh(), busNextNext.getFatherlsh(), busNextNext
						.getFormcode(), busNextNext.getParticipant());
			}

		}

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<script type='text/javascript'>");
		out.println("alert('初始化成功! 共:" + count + "条帖子');window.close();");
		out.println("</script>");
		out.println("</body>");
		out.println("</html>");
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Clean up resources
	public void destroy() {
	}
}
