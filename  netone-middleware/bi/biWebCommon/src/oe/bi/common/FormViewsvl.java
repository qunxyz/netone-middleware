package oe.bi.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.mid.io.CommonIoTools;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class FormViewsvl extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	// Initialize global variables
	public void init() throws ServletException {
		initrmi();
	}

	DyFormDesignService dys = null;

	DyFormService dysc = null;

	private void initrmi() {
		if (dys == null) {
			try {
				dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (dysc == null) {
			try {
				dysc = (DyFormService) RmiEntry.iv("dyhandle");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String formcode = request.getParameter("formcode");
		String forminfo = dys.formDescription(formcode);
		// 设置字符集，使之在WAS下可以正常显示中文，在JBOSS中要正确显示中文名，只需将这一段去掉即可
		// realname = java.net.URLEncoder.encode(realname, "UTF-8");
		// if (realname.length() > 150) {
		// // 根据request的locale 得出可能的编码，中文操作系统通常是gb2312
		// String guessCharset = "gb2312";
		// realname = new String(realname.getBytes(guessCharset), "ISO8859-1");
		// }

		String filename = formcode + ".xml";
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ WebStr.gbkToiso8859(filename));
		PrintWriter out = response.getWriter();
		out.print(forminfo);
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
