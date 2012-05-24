package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.mid.web.rspage.common.WriteIoInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * �޸���Դ���� �����޸Ľ���ǰ �� �����ύ�󶼽����ж�
 * 
 * Mar 8, 2009 7:48:26 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */
public class ModifySvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public ModifySvl() {
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
		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		// ��ڲ���
		String pagename = request.getParameter("pagename");
		String task = request.getParameter("task");
		// ҳ�����
		String chkid = request.getParameter("chkid");// ѡ��id
		String id = request.getParameter("id");// ���
		String naturalname = request.getParameter("naturalname");// ����
		String name = request.getParameter("name");// ��������
		String actionurl = request.getParameter("actionurl");// ����url
		String objecttype = request.getParameter("objecttype");// ����
		String active = request.getParameter("active");// ��Ч
		String extendattribute = request.getParameter("extendattribute");// ��չ����
		String description = request.getParameter("description");
		String aggregation=request.getParameter("aggregation");
		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}

		// �����޸�ҳ��
		if ("edit".equals(task)) {
			// ����Ȩ���ж�
			UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
			Security sec = new Security(request);
			if (!sec.check(upo.getNaturalname(), LogUtil._EDIT)) {
				// û�в���Ȩ��
				request.setAttribute("ModifySuccess", "noPermission");
			} else {
				request.setAttribute("upo", upo);
			}
			// ִ���޸Ĳ���
		} else if ("editsave".equals(task)) {
			if (null == id || "".equals(id.trim())) {
				request.setAttribute("ModifySuccess", "n");
			} else {
				UmsProtectedobject upo = rsrmi.loadResourceById(id);

				Security sec = new Security(request);
				if (!sec.check(upo.getNaturalname(), LogUtil._EDIT)) {
					// û���޸ĵ�Ȩ��
					request.setAttribute("ModifySuccess", "noPermission");
					// �Ǽ���־
				} else {
					upo.setName(name);
					upo.setNaturalname(naturalname);
					upo.setActionurl(actionurl);
					upo.setExtendattribute(extendattribute);
					upo.setDescription(description);
					if (StringUtils.isEmpty(active)) {
						active = "0";
					}
					upo.setActive(active);
					if (!StringUtils.isEmpty(objecttype)) {
						upo.setObjecttype(objecttype);
					}
					upo.setExtendattribute(extendattribute);
					if(StringUtils.isNotEmpty(aggregation)){
						try{
						Long aggrex=Long.parseLong(aggregation);
						upo.setAggregation(aggrex);
						}catch(Exception e){
							
						}
						
					}
					request.setAttribute("upo", upo);
					if (rsrmi.updateResource(upo)) {
						String serilaizer = request
								.getParameter("needSerilaizer");
						if ("1".equals(serilaizer)) {
							String realpath = getServletContext().getRealPath(
									"");
							boolean security = "1".equals(active);
							WriteIoInfo.todo(objecttype, upo.getId(),
									extendattribute, realpath, security);
						}
						request.setAttribute("ModifySuccess", "y");
						// �Ǽ���־
						sec.log(upo.getNaturalname(), LogUtil._EDIT,
								LogUtil._EDIT_SUCCESS);
					} else {
						request.setAttribute("ModifySuccess", "n");
					}
				}
			}

		}
		// ��� ���Ƶ���Դҳ�ĵ�ַ��Ĭ�ϴ�����resourceweb.properties�л��,���Ĭ����û����ô
		// ת�����Դҳ�л��

		pagename = pagename + ".modify";
		String pageurl = null;
		try {
			pageurl = web.getString(pagename);
		} catch (Exception e) {
		}
		if (pageurl == null) {// ����Դҳ�л��
			UmsProtectedobject upoPage = null;
			try {
				upoPage = rsrmi.loadResourceByNatural(pagename);
			} catch (Exception e) {

			}
			if (upoPage != null) {
				pageurl = "/" + upoPage.getId() + ".jsp";
			} else {
				response.setCharacterEncoding("GBK");
				WebTip.htmlInfo("pagename��" + pagename + " ��Ч,��������Ϣ�ҷ�ҳ����Ϣ",
						true, response);
				return;
			}
		}
		request.getRequestDispatcher(pageurl).forward(request, response);
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

	}

}
