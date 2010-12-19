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
 * 根据模板创建 模板的应用表单（在模板管理测试中使用）
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

		// 父目录的资源ID
		String id = request.getParameter("id");
		// 要拷贝的页组Naturalname
		String sourcename = request.getParameter("copysource");

		ResourceRmi rsrmi = null;
		try {
			// --- 创建新页组业务模型对象 ---
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			// --- 复制所有的页组资源目录 ---
			rsrmi.addFormCopyResource(id, new String[] { sourcename }, 300);
			// --- 复制所有的页组资源目录 --- done ----

			UmsProtectedobject upo = rsrmi.loadResourceByNatural(sourcename);
			String infomodelid = upo.getExtendattribute();
			if (infomodelid == null || infomodelid.equals("")) {
				WebTip.htmlInfo("非页组无法复制", true, response);
				return;
			}

			TCmsInfomodel inf = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(infomodelid));
			// 重新设置ID表示拷贝复制
			inf.setModelid(new Long(System.currentTimeMillis()));
			OrmerEntry.fetchOrmer().fetchSerializer().create(inf);
			Long newModelid = inf.getModelid();
			// 更新新的资源页组对应的模型id
			String newModelGroupNatrualname = rsrmi.loadResourceById(id)
					.getNaturalname()
					+ "." + StringUtils.substringAfterLast(sourcename, ".");
			UmsProtectedobject newModelupo = rsrmi
					.loadResourceByNatural(newModelGroupNatrualname);
			newModelupo.setExtendattribute(newModelid.toString());
			rsrmi.updateResource(newModelupo);
			// --- 创建新页组业务模型对象 --- done ----

			// --- 复制创建新的页业务对象 --- start ---
			List pages = rsrmi.subResource(upo.getId());
			Map oldNewPageIdCompare = new HashMap();
			List newPages = new ArrayList();
			for (Iterator iterator = pages.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				String cellidold = object.getExtendattribute();
				// 创建新页
				TCmsInfocell cell = (TCmsInfocell) OrmerEntry.fetchOrmer()
						.fetchQuerister().loadObject(TCmsInfocell.class,
								cellidold);
				cell.setCellid(IdServer.uuid());
				cell.setBelongto(newModelGroupNatrualname);
				// --- 还要设置上新的naturalname
				OrmerEntry.fetchOrmer().fetchSerializer().create(cell);
				newPages.add(cell);
				oldNewPageIdCompare.put(cellidold, cell.getCellid());
			}
			// --- 复制创建新的页业务对象 --- done ---

			// --- 将新的页业务对象信息存入 页资源中去--- start----
			List subPages = rsrmi.subResource(newModelupo.getId());
			// 旧页的业务ID与新页的业务ID的对应关系（用于最后替换Infomodel模型中的XML信息）
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
			// --- 将新的页业务对象信息存入 页资源中去--- done----

			List oldsubpage = rsrmi.subResource(upo.getId());
			// 旧页业务ID与页名称对应 （用于最后替换Infomodel模型中的XML信息）
			Map oldpageIdAndnameCompare = new HashMap();
			for (Iterator iterator = oldsubpage.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				oldpageIdAndnameCompare.put(object.getExtendattribute(), object
						.getName()
						+ "[" + object.getNaturalname() + "]");
			}

			// --- 新的业务模型中XML描述内容更新--- start ----
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
				// 更新infocellid 的信息
				scriptLayout = StringUtils.replace(scriptLayout, oldIdExp,
						newIdExp);
				// 更新extendattribute 的信息
				scriptLayout = StringUtils.replace(scriptLayout, oldNameExp,
						newNameExp);
			}
			inf.setInfoxml(scriptLayout);
			OrmerEntry.fetchOrmer().fetchSerializer().update(inf);
			// --- 新的业务模型中XML描述内容更新--- done ----
		} catch (NotBoundException e) {
			e.printStackTrace();
			WebTip.htmlInfo("非页组无法复制", true, response);
			return;
		}

		WebTip.htmlInfo("复制成功", true, true, response);

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
