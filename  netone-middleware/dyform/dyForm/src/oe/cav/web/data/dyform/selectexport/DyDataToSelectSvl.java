package oe.cav.web.data.dyform.selectexport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;

public class DyDataToSelectSvl extends HttpServlet {

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String formcode = request.getParameter("formcode");
		CreateApp.todo(formcode);
		WebTip.htmlInfo("Done", true, response);
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
