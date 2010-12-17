package oe.mid.web.rspage.common;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


public class XMLFuncTreeSvl2 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public XMLFuncTreeSvl2() {
		super();
	}
	
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml; charset=UTF-8");
		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		String cb = request.getParameter("cb");
		String parentid = request.getParameter("parentid");
		if (parentid == null) {
			throw new ServletException("没有获取到父节点ID!");
		}
		List list = rsrmi.subResource(parentid);
		Collections.reverse(list);
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			UmsProtectedobject f = (UmsProtectedobject) iter.next();
			f.setExtendattribute(null);

				String text = f.getName();
				if (cb != null) {
					text = "{cb}0{tt}" + text;
				}
				String src = "XMLFuncTreeSvl2?parentid=" + f.getId();
				if (cb != null) {
					src = src + "&cb=y";
				}
				boolean isLeaf = false;
				Element e = root.addElement("tree").addAttribute("text", text)
						.addAttribute(
								"action",
								"javascript:nodeAction('" + f.getId() + "','"
										+ f.getAppid() + "','" + f.getOu()
										+ "','" + f.getNaturalname() + "','"
										+ f.getParentdir() + "','"
										+ f.getActionurl() + "','"
										+ f.getExtendattribute() + "')");
				if (!isLeaf) {
					e.addAttribute("src", src);
				}
			}

		XMLWriter xw = new XMLWriter(response.getWriter());
		xw.write(doc);
		xw.flush();
		xw.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {

	}

}
