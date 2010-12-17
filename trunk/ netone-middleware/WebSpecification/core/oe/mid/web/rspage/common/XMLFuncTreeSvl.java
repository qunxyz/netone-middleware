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
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * Mar 7, 2009 2:39:35 PM<br>
 * 
 * 增加权限判断
 * 
 * modify wu.shang.zhan<br>
 *
 */

public class XMLFuncTreeSvl extends HttpServlet {

	static Log log = LogFactory.getLog(XMLFuncTreeSvl.class);

	private static final long serialVersionUID = 1L;

	public XMLFuncTreeSvl() {
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
		Security sec = new Security(request);
		String cb = request.getParameter("cb");
		String parentid = request.getParameter("parentid");
		if (parentid == null) {
			throw new ServletException("没有获取到父节点ID!");
		}
		// 这里得到的是所有的子资源，没有进行权限判断
		List list = rsrmi.subResource(parentid);
		Collections.reverse(list);
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			// 进行权限判断,因为只是查看，需要有3的权限
			UmsProtectedobject f = (UmsProtectedobject) iter.next();
			f.setExtendattribute(null);
			boolean b = false;
			if (ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(f
					.getInclusion())) {
				b = true;
			}
			if (b) {
				if (sec.check(f.getNaturalname(), LogUtil._READ)) {
					String text = f.getName();
					if (cb != null) {
						text = "{cb}0{tt}" + text;
					}
					String src = "XMLFuncTreeSvl?parentid=" + f.getId();
					if (cb != null) {
						src = src + "&cb=y";
					}
					boolean isLeaf = false;
					Element e = root.addElement("tree").addAttribute("text",
							text).addAttribute(
							"action",
							"javascript:nodeAction('" + f.getId() + "','"
									+ f.getAppid() + "','" + f.getOu() + "','"
									+ f.getNaturalname() + "','"
									+ f.getParentdir() + "','"
									+ f.getActionurl() + "','"
									+ f.getExtendattribute() + "')");
					if (!isLeaf) {
						e.addAttribute("src", src);
					}
				}
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
