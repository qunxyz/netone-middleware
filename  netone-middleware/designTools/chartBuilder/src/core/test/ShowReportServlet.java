package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowReportServlet extends HttpServlet {

	private static final long serialVersionUID = 6411608340909499528L;

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String format = request.getParameter("format");
		if (format == null || format.length() == 0)
			return;
		try {
			if (format.equals("html")) {
				// 生成HTML格式报表
				response.setContentType("text/html; charset=GBK");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				TestGroupReport
						.getHTMLReport(TestGroupReport.getReport(), baos);
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<body>");
				out.println(new String(baos.toByteArray(), "GBK"));
				out.println("</body>");
				out.println("</html>");
				baos.close();
			} else if (format.equals("excel")) {
				// 生成Excel格式报表
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				TestGroupReport.getExcelReport(TestGroupReport.getReport(), os);
			} else if (format.equals("csv")) {
				// 生成CSV格式报表
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				TestGroupReport.getCSVReport(TestGroupReport.getReport(), os);
			} else if (format.equals("pdf")) {
				// 生成PDF格式报表
				response.setContentType("application/pdf");
				OutputStream os = response.getOutputStream();
				TestGroupReport.getPDFReport(TestGroupReport.getReport(), os);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Clean up resources
	public void destroy() {
	}
}
