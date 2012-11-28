package com.jl.web.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AppAction extends AbstractAction {

	public void view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			String coords = request.getParameter("coords");
			String mappoint = request.getParameter("mappoint");
			String picurl = request.getParameter("picurl");
			System.out.println(mappoint);
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

}
