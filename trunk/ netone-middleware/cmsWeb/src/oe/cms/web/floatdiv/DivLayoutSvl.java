package oe.cms.web.floatdiv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.datasource.XMLParser;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.form.RequestParamMap;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

/**
 * Oec Netone设计工具上，保存界面设计结果
 * 
 * @author chen.jia.xun
 * 
 */
public class DivLayoutSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2617569956656807075L;

	/**
	 * Constructor of the object.
	 */
	public DivLayoutSvl() {
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

		response.setContentType("text/html; charset=GBK");

		// PrintWriter out = response.getWriter();
		Ormer ormer = OrmerEntry.fetchOrmer();
		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");
		try {
			RequestParamMap paramMap = new RequestParamMap(request);
			String layout = paramMap.getParameter("layout");
			String modelid = paramMap.getParameter("modelid");
			List groupinfolist = new ArrayList();
			String[] layvalues = layout.split("#");
			String layouttype = null;
			String xmlstr = null;
			if (layvalues.length == 2) {
				layouttype = layvalues[1];
				String[] boxes = layvalues[0].split(";");
				for (int i = 0; i < boxes.length; i++) {
					if (!boxes[i].equals("")) {
						String[] groups = boxes[i].split(",");
						for (int j = 0; j < groups.length; j++) {
							if (!groups[j].equals("")) {
								CellInfo gi = new CellInfo();
								gi.setInfoCellid(groups[j]);
								UmsProtectedobject objRS = infocellDao
										.viewRS2(groups[j]);
								if (objRS == null) {
									continue;
								}
								gi.setExtendattribute(objRS.getName() + "["
										+ objRS.getNaturalname() + "]");
								gi.setXoffset(Integer.toString(i + 1));
								gi.setYoffset(Integer.toString(j + 1));
								groupinfolist.add(gi);
							}
						}
					}
				}
				XMLParser xp = (XMLParser) CmsEntry
						.fetchBean(CmsBean._XML_PARSER);
				xmlstr = xp.toXML(groupinfolist.toArray());
			}

			Security ser = new Security(request);
			TCmsInfomodel model = (TCmsInfomodel) ormer.fetchQuerister()
					.loadObject(TCmsInfomodel.class, new Long(modelid));
			model.setInfoxml(xmlstr);
			//为了加入extcss
			String extend = model.getExtendattribute();
			// 去掉原先的100%,
			String others = "";
			if(StringUtils.contains(extend, "%"))
			{
				others = StringUtils.substringAfterLast(extend, "%");
			}
			else if(StringUtils.contains(extend, "null"))
			{
				others = StringUtils.substringAfterLast(extend, "null");
			}
			model.setExtendattribute(layouttype + others);
			model.setParticipant(ser.getUserLoginName());
			boolean todo = ormer.fetchSerializer().update(model);

			// EnvService env = null;
			// try {
			// env = (EnvService) RmiEntry.iv("envinfo");
			// } catch (MalformedURLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (RemoteException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (NotBoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			// if (todo) {
			// out.println("保存成功！");
			// // String curUrl = env.fetchEnvValue("curl");
			// // String cpath = env.fetchEnvValue("cpath");
			// // cpath = StringUtils.replace(cpath, "%20", " ");
			// // String path = cpath + env.fetchEnvValue("userHtmlRootPath");
			// // String url = curUrl + env.fetchEnvValue("userSpaceUrl");
			// // BuildStaticWeb.build(url + modelid, path
			// // + ser.getUserLoginName() + "/", "index.htm");
			// } else {
			// out.println("保存失败！您没有权限");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			// out.println("保存布局失败！");
		}
		// out.flush();
		// out.close();
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

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
