package oe.frame.web.page;

import javax.servlet.http.HttpServletRequest;

/**
 * WEB��ҳ�������,��Ҫ��� page.js�����
 * 
 * @author hls
 * 
 */
public class PageInfo {

	public static final String KEY = "page_pginfo";

	public static final String Event_KEY = "page_event";

	public static final int _DEFAULT_PERPAGE_COUNT = 18;

	private String pginfoid;

	// �ܼ�¼��
	private int itemCount;

	// ÿҳ��ʾ��
	private int perPage;

	// ��ҳ��
	private int pageCount;

	// ��ǰҳ
	private int nowpage;

	public PageInfo(HttpServletRequest request, String pginfoid) {
		this.pginfoid = pginfoid;
		if (request != null) {
			request.setAttribute(KEY, this);
			String strpageinfo = request.getParameter(KEY);
			if (strpageinfo != null && !strpageinfo.equals("")) {
				parseStr(strpageinfo);
			}
		}
		validateSession(request);
	}

	public PageInfo(int itemCount, HttpServletRequest request, String pginfoid) {
		this(itemCount, _DEFAULT_PERPAGE_COUNT, request, pginfoid);
	}

	public PageInfo(int itemCount, int perPage, HttpServletRequest request,
			String pginfoid) {
		init(itemCount, perPage);
		this.pginfoid = pginfoid;
		if (request != null) {
			request.setAttribute(KEY, this);
		}
		validateSession(request);
	}

	private void validateSession(HttpServletRequest request) {
		if (!isPageEvent(request)) {
			Object obj = request.getSession().getAttribute(pginfoid);
			if (obj != null) {
				PageInfo pi = (PageInfo) obj;
				setPerPage(pi.getPerPage());
				setNowpage(pi.getNowpage());
				request.getSession().setAttribute(pginfoid, this);
			}
		} else {
			request.getSession().setAttribute(pginfoid, this);
		}
	}

	public static boolean isPageEvent(HttpServletRequest request) {
		String param = request.getParameter(Event_KEY);
		if (param != null && param.equalsIgnoreCase("y")) {
			return true;
		} else {
			return false;
		}
	}

	public void init(int itemCount, int perPage) {
		this.itemCount = itemCount;
		this.perPage = perPage;
		this.nowpage = 1;
		if (itemCount % perPage == 0) {
			this.pageCount = (itemCount / perPage);
		} else {
			this.pageCount = (itemCount / perPage) + 1;
		}
	}

	/**
	 * 
	 * @param pginfostr
	 *            ��ʽΪ���ܼ�¼������ҳ����ÿҳ��ʾ������ǰҳ��
	 */
	public void parseStr(String pginfostr) {
		String[] pis = pginfostr.split(",");
		if (pis.length == 4) {
			this.itemCount = Integer.parseInt(pis[0]);
			this.pageCount = Integer.parseInt(pis[1]);
			setPerPage(Integer.parseInt(pis[2]));
			setNowpage(Integer.parseInt(pis[3]));
		}
	}

	public int getItemCount() {
		return itemCount;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		if (nowpage > pageCount) {
			this.nowpage = 1;
		} else {
			this.nowpage = nowpage;
		}
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		init(itemCount, perPage);
	}

	/**
	 * ��ǰ��ҳ�Ŀ�ʼ��¼index, ��0��ʼ����
	 * 
	 * @return -1��ʾ��������
	 */
	public int getPageStartIndex() {
		int index = (nowpage - 1) * perPage;
		if (index < itemCount) {
			return index;
		}
		return -1;
	}

	public int getPageEndIndex() {
		int index = nowpage * perPage - 1;
		if (index >= itemCount) {
			return itemCount - 1;
		} else {
			return index;
		}
	}

	/**
	 * ��ȡ��ǰҳ�ļ�¼��
	 * 
	 * @return
	 */
	public int getPageItemCount() {

		return getPageEndIndex() - getPageStartIndex() + 1;

	}

	/**
	 * 
	 * @return ��ʽΪ���ܼ�¼������ҳ����ÿҳ��ʾ������ǰҳ��
	 */
	public String getPginfostr() {
		StringBuffer sb = new StringBuffer();
		sb.append(itemCount + ",");
		sb.append(pageCount + ",");
		sb.append(perPage + ",");
		sb.append(nowpage + ",");
		return sb.toString();
	}

}
