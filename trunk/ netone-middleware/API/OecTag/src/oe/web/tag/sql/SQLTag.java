package oe.web.tag.sql;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class SQLTag extends TagSupport {
	private String sql;

	private String sqlcount;

	private String ds;

	private String dataname;

	private int prepage = 10;

	public int doEndTag() throws JspException {
		Connection con = null;
		if (ds == null || ds.equals("")) {
			throw new RuntimeException("数据源不允许为空");
		}

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();

		PageInfo pginfo = null;// 分页信息

		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("first") != null) {// 每次翻页交互

			pginfo = new PageInfo(request, "flist");

		} else { // 查询或者第一次页面交互
			int count = 0;// 总记录默认为0

			List countSet = DbTools.queryData(this.sqlcount, getConnection());
			if (countSet != null && countSet.size() > 0) {
				Map map = (Map) countSet.get(0);

				Set keySet = map.keySet();
				for (Iterator itr = keySet.iterator(); itr.hasNext();) {
					String countKey = (String) itr.next();
					Object countObj = map.get(countKey);
					if (countObj != null) {
						count = Integer.parseInt(countObj.toString());
					}
				}
			}

			pginfo = new PageInfo(count, prepage, request, "flist");// 分页信息
			pginfo.setNowpage(1);
			request.getSession().setAttribute("first", "yes");
		}

		String startIndex = String.valueOf(pginfo.getPageStartIndex());
		String pageSize = String.valueOf(prepage);
		String endIndex = String.valueOf(pginfo.getPageEndIndex());

		sql = StringUtils.replaceOnce(sql, "#startIndex", startIndex);
		sql = StringUtils.replaceOnce(sql, "#pageSize", pageSize);
		sql = StringUtils.replaceOnce(sql, "#endIndex", endIndex);

		List data = DbTools.queryData(sql, getConnection());

		request.setAttribute(dataname, data);

		return EVAL_PAGE;
	}

	private Connection getConnection() {
		Connection con = null;

		ResourceRmi rsrmi;
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(ds);
			String extendattribute = upo.getExtendattribute();
			if (extendattribute == null || extendattribute.equals("")) {
				con = OrmerEntry.fetchDS().getConnection();
			} else {
				String[] extInfo = extendattribute.split("#");
				if (extInfo.length != 4) {
					throw new RuntimeException("无效数据源:" + ds);
				}
				con = DbTools.fetchConnection(extInfo[0], extInfo[1],
						extInfo[2], extInfo[3]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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

	public String getSqlcount() {
		return sqlcount;
	}

	public void setSqlcount(String sqlcount) {
		this.sqlcount = sqlcount;
	}

}
