package oe.bi.web.view.tree;

import java.io.IOException; 
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlTreeSvl extends HttpServlet {

	private static final long serialVersionUID = 9044430679825626309L;

	static Log log = LogFactory.getLog(XmlTreeSvl.class);

	/**
	 * Constructor of the object.
	 */
	public XmlTreeSvl() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
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

		response.setContentType("text/xml; charset=GBK");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");

		String path = request.getRequestURI();

		String filename = request.getParameter("file");
		String id = request.getParameter("id");

		Document treedoc = null;
		try {
			treedoc = XmlTreeMgr.getInstance().getDocument(filename);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		Node node = treedoc.selectSingleNode("//tree[@id='" + id + "']");

		// 创建返回客户端的xml文件；
		Document redoc = DocumentHelper.createDocument();
		Element root = redoc.addElement("tree");
		
		if (node != null) {

			Element treeE = (Element) node;

			List childlist = treeE.selectNodes("tree");

			// 添加xml文件中配置的treeE的子节点
			Iterator iter = childlist.iterator();
			while (iter.hasNext()) {
				Element itere = (Element) iter.next();
				String etext = itere.attributeValue("text");
				String eid = itere.attributeValue("id");
				String esrc = path + "?file=" + filename + "&id=" + eid;
				String eaction = "javascript:nodeAction('" + eid + "');";

				Element newe = root.addElement("tree");
				newe.addAttribute("text","{cb}0"+"{tt}"+etext);
				newe.addAttribute("src", esrc);
				newe.addAttribute("action", eaction);
				newe.addAttribute("onload", "alert('onload')");
			}

			// 当该节点允许子节点时，查询子节点并添加。
			String children = treeE.attributeValue("children");
			String treeEid = treeE.attributeValue("id");
			if (children.equals("y")) {
				XmlTreeDataGeter dategeter = XmlTreeMgr.getInstance()
						.getXmlTreeDataGeter(filename);
				List list = dategeter.getChildrenById(treeEid);
				if (list != null) {
					Iterator listiter = list.iterator();
					while (listiter.hasNext()) {
						XmlTreeObj xtobj = (XmlTreeObj) iter.next();
						String etext = xtobj.getText();
						String eid = xtobj.getId();
						String esrc = path + "?file=" + filename + "&id=" + eid;
						String eaction = "javascript:nodeAction('" + eid + "');";

						Element newe = root.addElement("tree");
						newe.addAttribute("text", etext);
						newe.addAttribute("src", esrc);
						newe.addAttribute("action", eaction);
					}
				}
			}
		}

		// log.debug(redoc.asXML());

		PrintWriter out = response.getWriter();
		OutputFormat outf = OutputFormat.createPrettyPrint();
		outf.setEncoding("GBK");
		XMLWriter xmlw = new XMLWriter(out, outf);
		xmlw.write(redoc);
		xmlw.close();
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

		doGet(request, response);
	}

}
