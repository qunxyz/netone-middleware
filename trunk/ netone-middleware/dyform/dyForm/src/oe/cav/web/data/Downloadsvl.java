package oe.cav.web.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Downloadsvl extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fileid = request.getParameter("fileid");
		String filename = request.getParameter("filename");
		String formcode = request.getParameter("formcode");
		// filename = WebStr.encode(request, filename);
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			if (fileid != null && formcode != null) {

				response.setContentType("application/x-msdownload");
				response.setHeader("Accept-Ranges", "bytes");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + filename + "\"");
				os = response.getOutputStream();
				// String url =EnvEntry.getEnvInfo("formFileRoot")+ formcode +
				// "/" + fileid;
				String url = "locahost";
				fis = new FileInputStream(url);
				byte[] buf = new byte[2048];
				int b = 0;
				while ((b = fis.read(buf)) != -1) {
					os.write(buf, 0, b);
				}

			}
		} catch (Exception e) {
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<script type='text/javascript'>");
			out.println("alert('" + filename + "²»´æÔÚ£¡');");
			out.println("history.back();");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			fis.close();
			os.close();
		}
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
