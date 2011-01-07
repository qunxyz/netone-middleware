package oe.web.tag.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.jsp.JspTagException;

import javax.servlet.jsp.tagext.TagSupport;

import oe.cav.bean.logic.bus.TCsBus;

import oe.frame.web.WebCache;
import oe.frame.web.page.PageInfo;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class DyDataTag extends TagSupport {

	static ResourceBundle rs = null;
	
	static int cachetime=0;

	static {
		try {
			cachetime=Integer.parseInt(rs.getString("cachetime"))*60000;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String formcode;

	private String fatherlsh;

	private String condition;

	private int prepage = 10;

	private String dataname;

	public int doEndTag() throws JspTagException {

		String cachekey = formcode + fatherlsh + condition;

		if (formcode == null) {
			return EVAL_PAGE;
		}
		if (fatherlsh == null) {
			fatherlsh = "1";
		}
		TCsBus bus = new TCsBus();
		bus.setFormcode(formcode);
		bus.setFatherlsh(fatherlsh);

		DyFormService dy = null;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			List listData = null;

			HttpServletRequest request = (HttpServletRequest) this.pageContext
					.getRequest();

			PageInfo pginfo = null;// 分页信息

			if (PageInfo.isPageEvent(request)
					&& request.getSession().getAttribute("first") != null) {// 每次翻页交互

				pginfo = new PageInfo(request, "flist");

			} else { // 查询或者第一次页面交互

				int dataNumber = dy.queryDataNum(bus, condition);// 总记录数

				pginfo = new PageInfo(dataNumber, prepage, request, "flist");// 分页信息
				pginfo.setNowpage(1);
				request.getSession().setAttribute("first", "yes");
			}

			if (WebCache.containCache(cachekey)) {
				listData = (List) WebCache.getCache(cachekey);
			} else {
				// 根据分页的参数查询本页的相关数据
				listData = dy.queryData(bus, pginfo.getPageStartIndex(), pginfo
						.getPageStartIndex()
						+ prepage, condition);
				WebCache.setCache(cachekey, listData, new Date(System
						.currentTimeMillis() + cachetime));
			}

			request.setAttribute(dataname, listData);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getFatherlsh() {
		return fatherlsh;
	}

	public void setFatherlsh(String fatherlsh) {
		this.fatherlsh = fatherlsh;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public int getPrepage() {
		return prepage;
	}

	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}

}
