package oe.cms.web.common;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;



/**
 * ����ģ�崴�� ģ���Ӧ�ñ�����ģ����������ʹ�ã�
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class CopyPortalSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public CopyPortalSvl() {
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

		// ��Ŀ¼����ԴID
		String id = request.getParameter("id");
		// Ҫ������ҳ��Naturalname
		String sourcename = request.getParameter("copysource");

		ResourceRmi rsrmi = null;
		try {
			// --- ������ҳ��ҵ��ģ�Ͷ��� ---
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			// --- �������е�ҳ����ԴĿ¼ ---
			rsrmi.addFormCopyResource(id, new String[] { sourcename }, 300);
			// --- �������е�ҳ����ԴĿ¼ --- done ----

			UmsProtectedobject upo = rsrmi.loadResourceByNatural(sourcename);
			String infomodelid = upo.getExtendattribute();
			if (infomodelid == null || infomodelid.equals("")) {
				WebTip.htmlInfo("��ҳ���޷�����", true, response);
				return;
			}

			TCmsInfomodel inf = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(infomodelid));
			// ��������ID��ʾ��������
			inf.setModelid(new Long(System.currentTimeMillis()));
			OrmerEntry.fetchOrmer().fetchSerializer().create(inf);
			Long newModelid = inf.getModelid();
			// �����µ���Դҳ���Ӧ��ģ��id
			String newModelGroupNatrualname = rsrmi.loadResourceById(id)
					.getNaturalname()
					+ "." + StringUtils.substringAfterLast(sourcename, ".");
			UmsProtectedobject newModelupo = rsrmi
					.loadResourceByNatural(newModelGroupNatrualname);
			newModelupo.setExtendattribute(newModelid.toString());
			rsrmi.updateResource(newModelupo);
			// --- ������ҳ��ҵ��ģ�Ͷ��� --- done ----

			// --- ���ƴ����µ�ҳҵ����� --- start ---
			List pages = rsrmi.subResource(upo.getId());
			Map oldNewPageIdCompare = new HashMap();
			List newPages = new ArrayList();
			for (Iterator iterator = pages.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				String cellidold = object.getExtendattribute();
				// ������ҳ
				TCmsInfocell cell = (TCmsInfocell) OrmerEntry.fetchOrmer()
						.fetchQuerister().loadObject(TCmsInfocell.class,
								cellidold);
				cell.setCellid(IdServer.uuid());
				cell.setBelongto(newModelGroupNatrualname);
				// --- ��Ҫ�������µ�naturalname
				OrmerEntry.fetchOrmer().fetchSerializer().create(cell);
				newPages.add(cell);
				oldNewPageIdCompare.put(cellidold, cell.getCellid());
			}
			// --- ���ƴ����µ�ҳҵ����� --- done ---

			// --- ���µ�ҳҵ�������Ϣ���� ҳ��Դ��ȥ--- start----
			List subPages = rsrmi.subResource(newModelupo.getId());
			// ��ҳ��ҵ��ID����ҳ��ҵ��ID�Ķ�Ӧ��ϵ����������滻Infomodelģ���е�XML��Ϣ��
			Map oldnewPageIdAndNameCompare = new HashMap();
			for (Iterator iterator = subPages.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				String oldpageid = object.getExtendattribute();
				String newpageid = (String) oldNewPageIdCompare.get(oldpageid);
				object.setExtendattribute(newpageid);
				rsrmi.updateResource(object);
				oldnewPageIdAndNameCompare.put(oldpageid, object.getName()
						+ "[" + object.getNaturalname() + "]");
			}
			// --- ���µ�ҳҵ�������Ϣ���� ҳ��Դ��ȥ--- done----

			List oldsubpage = rsrmi.subResource(upo.getId());
			// ��ҳҵ��ID��ҳ���ƶ�Ӧ ����������滻Infomodelģ���е�XML��Ϣ��
			Map oldpageIdAndnameCompare = new HashMap();
			for (Iterator iterator = oldsubpage.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				oldpageIdAndnameCompare.put(object.getExtendattribute(), object
						.getName()
						+ "[" + object.getNaturalname() + "]");
			}

			// --- �µ�ҵ��ģ����XML�������ݸ���--- start ----
			String scriptLayout = inf.getInfoxml();
			for (Iterator iterator = oldnewPageIdAndNameCompare.keySet()
					.iterator(); iterator.hasNext();) {
				String oldid = (String) iterator.next();
				String newId = (String) oldNewPageIdCompare.get(oldid);
				String oldName = (String) oldpageIdAndnameCompare.get(oldid);
				String newName = (String) oldnewPageIdAndNameCompare.get(oldid);

				String oldIdExp = "infoCellid=\"" + oldid + "\"";
				String oldNameExp = "extendattribute=\"" + oldName + "\"";
				String newIdExp = "infoCellid=\"" + newId + "\"";
				String newNameExp = "extendattribute=\"" + newName + "\"";
				// ����infocellid ����Ϣ
				scriptLayout = StringUtils.replace(scriptLayout, oldIdExp,
						newIdExp);
				// ����extendattribute ����Ϣ
				scriptLayout = StringUtils.replace(scriptLayout, oldNameExp,
						newNameExp);
			}
			inf.setInfoxml(scriptLayout);
			OrmerEntry.fetchOrmer().fetchSerializer().update(inf);
			// --- �µ�ҵ��ģ����XML�������ݸ���--- done ----
		} catch (NotBoundException e) {
			e.printStackTrace();
			WebTip.htmlInfo("��ҳ���޷�����", true, response);
			return;
		}

		WebTip.htmlInfo("���Ƴɹ�", true, true, response);

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
