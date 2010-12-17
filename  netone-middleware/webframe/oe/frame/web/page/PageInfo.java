package oe.frame.web.page;

import javax.servlet.http.HttpServletRequest;

/**
 * WEB分页服务程序,需要配合 page.js来完成
 * 
 * @author hls
 * 
 */
public class PageInfo {

	public static final String KEY = "page_pginfo";

	public static final String Event_KEY = "page_event";

	public static final int _DEFAULT_PERPAGE_COUNT = 18;

	private String pginfoid;

	// 总记录数
	private int itemCount;

	// 每页显示数
	private int perPage;

	// 总页数
	private int pageCount;

	// 当前页
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
	 *            格式为：总记录数，总页数，每页显示数，当前页，
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
	 * 当前分页的开始记录index, 从0开始计算
	 * 
	 * @return -1表示超出界限
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
	 * 获取当前页的记录数
	 * 
	 * @return
	 */
	public int getPageItemCount() {

		return getPageEndIndex() - getPageStartIndex() + 1;

	}

	/**
	 * 
	 * @return 格式为：总记录数，总页数，每页显示数，当前页，
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
