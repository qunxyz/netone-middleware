package oe.cms.web.infomodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.CmsEntry;

import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelListRichAction extends Action {

	// ��Ѷģ���б�
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String levelId = request.getParameter("levelId");
		if (levelId == null || levelId.equals("")) {
			levelId = RichLevel._LEVEL_C;
		}

		response.setHeader("Content-type", "text/html; charset=GBK");
		//
		// RequestParamMap paramMap = new RequestParamMap(request);
		// RequestUtil.setParamMapToRequest(request, paramMap);

		List modellist = new ArrayList(); // ��ϢԪ�б�List

		PageInfo pginfo = null;// ��ҳ��Ϣ
		String searchName = "";// ��ѯ���Ƶ�ֵ

		String nopageeach = request.getParameter("nopageeach");

		request.setAttribute("done", "");
		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("modelsearchName") != null) {
			// ��ҳ

			pginfo = new PageInfo(request, "MODEL");

			// // ��session��ȡ��ѯ����
			// searchName = (String) request.getSession().getAttribute(
			// "modelsearchName");
			// ���ݲ�ѯ����,��¼λ�� �����б�
			modellist = searchModelList(levelId, pginfo.getPageStartIndex(),
					pginfo.getPageStartIndex() + pginfo.getPageItemCount());

		} else {// ��ѯ

			int dataNumber = searchModelNum(levelId);// �ܼ�¼��

			pginfo = new PageInfo(dataNumber, request, "MODEL");// ��ҳ��Ϣ
			// // �����ѯ������Session ��
			// request.getSession().setAttribute("modelsearchName", searchName);
			// ���ݲ�ѯ����,��¼λ�� �����б�
			if (nopageeach != null) {
				modellist = searchModelList(levelId, 0, Integer
						.parseInt(nopageeach));
			} else {
				modellist = searchModelList(levelId,
						pginfo.getPageStartIndex(), pginfo.getPageItemCount());
			}
		}

		request.setAttribute("cmsinfomodellist", modellist);

		if (RichLevel._LEVEL_A.equals(levelId)) {
			return mapping.findForward("richA");
		} else if (RichLevel._LEVEL_B.equals(levelId)) {
			return mapping.findForward("richB");
		}
		return mapping.findForward("richC");
	}

	/**
	 * ���ݲ�ѯ����ϢԪ���Ʋ�ѯ ȡ�� ��ϢԪ�б�
	 * 
	 * @param name
	 *            //��ѯ����ϢԪ����
	 * @return
	 */
	private List searchModelList(String levelid, int from, int to) {

		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		return dao.richview(levelid, from, to);
	}

	/**
	 * ���ݲ�ѯ����ϢԪ���Ʋ�ѯ ȡ�� ��ϢԪ�б������
	 * 
	 * @param name
	 *            //��ѯ����ϢԪ����
	 * @return
	 */
	private int searchModelNum(String levelid) {
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		return dao.totalRichview(levelid);
	}

}
