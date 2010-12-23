package oe.bi.common;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.wizard.WizardDao;
import oe.frame.web.util.WebStr;

import oe.rmi.client.RmiEntry;

public class BiViewsvl extends HttpServlet {


	public void init() throws ServletException {
		initrmi();
	}

	WizardDao wd = null;

	private void initrmi() {
		
		

		try {
			wd = (WizardDao) RmiEntry.iv("bihandle");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lsh = request.getParameter("lsh");
		ChoiceInfo forminfo = wd.fromXml(lsh);
		String xmlinfo = wd.toXml(forminfo);
		// �����ַ�����ʹ֮��WAS�¿���������ʾ���ģ���JBOSS��Ҫ��ȷ��ʾ��������ֻ�轫��һ��ȥ������
		// realname = java.net.URLEncoder.encode(realname, "UTF-8");
		// if (realname.length() > 150) {
		// // ����request��locale �ó����ܵı��룬���Ĳ���ϵͳͨ����gb2312
		// String guessCharset = "gb2312";
		// realname = new String(realname.getBytes(guessCharset), "ISO8859-1");
		// }

		String filename = lsh + ".xml";
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ WebStr.gbkToiso8859(filename));
		PrintWriter out = response.getWriter();
		out.print(xmlinfo);
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
