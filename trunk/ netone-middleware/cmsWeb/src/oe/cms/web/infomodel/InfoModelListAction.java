//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.BuildCmsJpp;
import oe.cms.BuildStaticPage;
import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.blog.LayoutX;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;
import oe.cms.runtime.XHtmlCachepool;
import oe.cms.runtime.XHtmlHistoryCachePool;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.frame.web.util.WebTip;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelListAction extends Action {

	// ��Ѷģ���б�
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String init = (String) request.getParameter("init");
		response.setHeader("Content-type", "text/html; charset=GBK");
		Security ser = new Security(request);

		if ("all".equals(init)) {

			XHtmlCachepool.removeInfoCacheAll();
			BuildCmsJpp.todo(request);

			try {
				response.getWriter().print(
						"<script>alert('�����ʼ���ɹ���');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("hist".equals(init)) {

			XHtmlHistoryCachePool.serialHistory();
			try {
				response
						.getWriter()
						.print(
								"<script>alert('����ϵͳ��Ϣ�Ѿ����棡');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("static".equals(init)) {

			BuildStaticPage.initWeb();
			try {
				response
						.getWriter()
						.print(
								"<script>alert('���о�̬ҳ���Ѿ����棡');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("order".equals(init)) {

			ModelDao modeldao = (ModelDao) CmsEntry.fetchDao("modelDao");
			modeldao.serialRich(RichLevel._LEVEL_A);
			modeldao.initview(RichLevel._LEVEL_A);
			modeldao.serialRich(RichLevel._LEVEL_B);
			modeldao.initview(RichLevel._LEVEL_B);
			modeldao.serialRich(RichLevel._LEVEL_C);
			modeldao.initview(RichLevel._LEVEL_C);
			try {
				response.getWriter().print(
						"<script>alert('���������');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else if ("model".equals(init)) {

			LayoutX layoutX = (LayoutX) CmsEntry.fetchDao("layoutX");
			layoutX.initTemplate();
			try {
				response.getWriter().print(
						"<script>alert('��ɳ�ʼ����');window.close();</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		RequestParamMap paramMap = new RequestParamMap(request);
		RequestUtil.setParamMapToRequest(request, paramMap);

		List modellist = new ArrayList(); // ��ϢԪ�б�List
		String flag = paramMap.getParameter("flag"); // ��־λ
		PageInfo pginfo = null;// ��ҳ��Ϣ
		String searchName = "";// ��ѯ���Ƶ�ֵ
		if ("delete".equals(flag)) { // ɾ����ϢԪ
			ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
			String id = request.getParameter("id"); // ��ϢԪID
			try {

				boolean done = modelDao.delete(id, ser.getUserLoginName());
				if (done) {
					request.setAttribute("done", "�ɹ�");
				} else {
					request.setAttribute("done", "ʧ�ܣ���û�иò���Ȩ��");
				}
			} catch (Exception e) {

			}
			// // ��session��ȡ��ѯ����
			// searchName = (String) request.getSession().getAttribute(
			// "modelsearchName");
			//
			// int dataNumber = searchModelNum(searchName);// �ܼ�¼��
			//
			// pginfo = new PageInfo(dataNumber, request, "MODEL");// ��ҳ��Ϣ
			// // ���ݲ�ѯ����,��¼λ�� �����б�
			// modellist = searchModelList(searchName,
			// pginfo.getPageStartIndex(),
			// pginfo.getPageItemCount());
			WebTip.htmlInfo("�ɹ�", true, response);
			return null;
		} else {
			request.setAttribute("done", "");
			if (PageInfo.isPageEvent(request)
					&& request.getSession().getAttribute("modelsearchName") != null) {
				// ��ҳ

				pginfo = new PageInfo(request, "MODEL");

				// ��session��ȡ��ѯ����
				searchName = (String) request.getSession().getAttribute(
						"modelsearchName");
				// ���ݲ�ѯ����,��¼λ�� �����б�
				modellist = searchModelList(searchName, pginfo
						.getPageStartIndex(), pginfo.getPageItemCount());

			} else {// ��ѯ
				// ��ѯ���Ƶ�ֵ
				searchName = paramMap.getParameter("searchName") == null ? ""
						: paramMap.getParameter("searchName");

				int dataNumber = searchModelNum(searchName);// �ܼ�¼��

				pginfo = new PageInfo(dataNumber, request, "MODEL");// ��ҳ��Ϣ
				// �����ѯ������Session ��
				request.getSession()
						.setAttribute("modelsearchName", searchName);
				// ���ݲ�ѯ����,��¼λ�� �����б�
				modellist = searchModelList(searchName, pginfo
						.getPageStartIndex(), pginfo.getPageItemCount());
			}
		}

		// UserDao.userViewRichx(modellist);
		request.setAttribute("cmsinfomodellist", modellist);

		return mapping.getInputForward();
	}

	/**
	 * ���ݲ�ѯ����ϢԪ���Ʋ�ѯ ȡ�� ��ϢԪ�б�
	 * 
	 * @param name
	 *            //��ѯ����ϢԪ����
	 * @return
	 */
	private List searchModelList(String name, int from, int to) {
		List list = new ArrayList();
		if (name == null || name.trim().equals("")) {
			list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					new TCmsInfomodel(), null, from, to);
		} else {
			list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					new TCmsInfomodel(), null, from, to,
					" and modelname like '%" + name + "%' ");
		}
		return list;

	}

	/**
	 * ���ݲ�ѯ����ϢԪ���Ʋ�ѯ ȡ�� ��ϢԪ�б������
	 * 
	 * @param name
	 *            //��ѯ����ϢԪ����
	 * @return
	 */
	private int searchModelNum(String name) {
		int i = 0;
		if (name == null || name.trim().equals("")) {
			i = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(new TCmsInfomodel(), null);
		} else {
			i = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(new TCmsInfomodel(), null,
							" and modelname like '%" + name + "%' ");
		}
		return i;

	}

}
