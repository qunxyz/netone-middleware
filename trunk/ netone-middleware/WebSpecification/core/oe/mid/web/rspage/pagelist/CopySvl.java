package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * ��������������ʹ�û���naturalname�ķ�ʽ������
 * 
 * ������־�Ǽǹ��� Mar 10, 2009 4:02:28 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */
public class CopySvl extends HttpServlet {
	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public CopySvl() {
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

		Security sec = new Security(request);
		// ��ڲ���
		String pagename = request.getParameter("pagename");
		String task = request.getParameter("task");
		// ҳ�����
		String id = request.getParameter("id");// ���

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// �½��ӽ��
		if ("copy".equals(task)) {
			String copyrs = request.getParameter("copysource");
			String copylevel = request.getParameter("level");
			if (copylevel == null || copylevel.equals("")) {
				copylevel = "0";
			}
			rsrmi.addFormCopyResource(id, new String[] { copyrs }, Integer
					.parseInt(copylevel));

			WebTip.htmlInfo("�����ɹ�", true, true, response);

			addLogWhenCopy(rsrmi, sec, new String[] { copyrs }, Integer
					.parseInt(copylevel), id);

			return;

			// �����½�ҳ��
		} else {
			request.setAttribute("id", id);
			pagename = pagename + ".copy";
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

	/**
	 * �ڿ��������Ĺ����У��Ǽ���־
	 * 
	 * @param rsrmi
	 * @param naturalname
	 * @param level
	 * @param id
	 * @param code
	 * @param userName
	 * @param ip
	 * @param parentNatural
	 */
	public void addLogWhenCopy(ResourceRmi rsrmi, Security sec, String[] naturalname,
			int level, String id) {
		try {

			if (level >= 0 && naturalname.length > 0) {
				for (int i = 0; i < naturalname.length; i++) {
					UmsProtectedobject upo = rsrmi
							.loadResourceByNatural(naturalname[i]);
					String xnat = StringUtils.substringAfterLast(upo
							.getNaturalname(), ".");

					UmsProtectedobject uponext = new UmsProtectedobject();
					BeanUtils.copyProperties(uponext, upo);
					uponext.setNaturalname(xnat);
					uponext.setParentdir(id);
					Serializable idnext = rsrmi.addResource(uponext);

					List list = rsrmi.subResource(upo.getId());
					String[] naturalnameNext = new String[list.size()];
					int loop = 0;
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						naturalnameNext[loop++] = object.getNaturalname();
					}
					// �Ǽ� ���� ��־
					// ��־������Դ�ڸմ����ɹ�����Դ��ʹ��naturalname
					UmsProtectedobject newUpo = rsrmi.loadResourceById(idnext
							.toString());
					sec.log(newUpo.getNaturalname(), LogUtil._ADD, LogUtil._ADD_SUCCESS);
					addLogWhenCopy(rsrmi,sec, naturalnameNext, --level, idnext
							.toString());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
