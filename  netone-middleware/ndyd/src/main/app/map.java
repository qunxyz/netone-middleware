package app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class map extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public map() {
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
		super.doPost(request, response);
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
		PrintWriter out = response.getWriter();

		try {
			String coords = request.getParameter("coords");
			String mappoint = request.getParameter("mappoint");
			String picurl = request.getParameter("picurl");
			String[] d1 = coords.split("\n");
			String[] d2 = mappoint.split("\n");

			if (d1.length > 0) {
				int bgWidth = 800;
				int bgHeight = 800;
				out.println("<img id=\"_mapx" + "\" usemap='#Maparea" + "' v='"
						+ 0 + "," + 0 + "," + bgWidth + "," + bgHeight
						+ "' src='" + picurl + "' width=\"" + bgWidth
						+ "\" height=\"" + bgHeight
						+ "\" style='left:0;top:0;z-index:-1;' >");
				for (int i = 0; i < d1.length; i++) {

					String coords_ = d1[i];
					String hilight_ = "\"stroke\":false,\"fillColor\":\"000000\",\"fillOpacity\":1,\"alwaysOn\":true";
					// ÈÈµãÇøÓò
					out.println("<map name=\"Maparea" + "\"  id=\"Maparea"
							+ "\"><area shape=\"poly\" coords=\'" + coords_
							+ "\' data-maphilight='{" + hilight_ + "}' ");
					out.println(" href=\"javascript:void(0)\" />  ");
					out.println("</map>");

				}
				out.println("</img>");
			}
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			for (int i = 0; i < d2.length; i++) {
				String[] p = d2[i].split(",");
				Integer x = Integer.parseInt(p[0].trim()) + 0;
				Integer y = Integer.parseInt(p[1].trim()) + 0;
				String name = p.length >= 3 ? p[2].trim() : "";
				String url = p.length >= 4 ? p[3].trim() : null;

				if (url != null)
					out
							.println("<img style='background: url("
									+ url
									+ ");background-repeat: no-repeat;border-top-style:Ridge;border-right-style:Ridge;border-left-style:Ridge;border-bottom-style:Ridge;border-width: 7pt;overflow:hidden;position:absolute;border:0px solid #FFCF5F;left:"
									+ x + "px;top:" + y + "px;' src='" + url
									+ "' ></img>");

				out
						.println("<div class='_mapxNav' onmouseout='_focusOut(this)' onmouseover='_focus(this)' title='"
								+ name
								+ "' style='position: absolute; left: "
								+ (x + 18)
								+ "px; top: "
								+ y
								+ "px; z-index: 0;' >");
				out.println("<a class='map' href='javascript:void(0)'>" + name
						+ "</a>");
				out.println("</div>");

			}

			// out.print(coords+mappoint);

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
