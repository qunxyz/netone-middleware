package oe.teach.web.turnpage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.page.PageInfo;

import oe.teach.mid.buss.OecStuDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ڱ�׼��Servlet+JSPʵ�ֵ�ҵ�����ݹ���Ӧ��<br>
 * ��Ӧ����������һ��������ҵ�����µ�ҵ��Ӧ��ģʽ,�ڸ�ģʽ�д������¼�����Ҫ����� 1) �м��DAO :
 * �����buss[oe.teach.web.turnpage.buss��] 2) ǰ̨WEB����:
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class ListAction extends Action {

	private static int _PRE_PAGE = 10;// Ĭ�Ϸ�ҳ

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OecStuDao bussdao = (OecStuDao) OrmerEntry.fetchDAO("bussdao");
		WebForm formReal = (WebForm) form;

		String condition = "";
		if (formReal.getStuname() != null) {
			condition = " and stuname like '%" + formReal.getStuname() + "%'";
		}

		PageInfo pginfo = null;// ��ҳ��Ϣ

		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("first") != null) {// ÿ�η�ҳ����

			pginfo = new PageInfo(request, "flist");

		} else { // ��ѯ���ߵ�һ��ҳ�潻��
			int dataNumber = bussdao.totalNum(condition);// �ܼ�¼��
			pginfo = new PageInfo(dataNumber, _PRE_PAGE, request, "flist");// ��ҳ��Ϣ
			pginfo.setNowpage(1);
			request.getSession().setAttribute("first", "yes");
		}
		// ���ݷ�ҳ�Ĳ�����ѯ��ҳ���������
		List list = bussdao.query(condition, pginfo.getPageStartIndex(), pginfo
				.getPageStartIndex()
				+ pginfo.getPageItemCount() + 1);

		request.setAttribute("listinfo", list);

		return mapping.getInputForward();
	}

}
