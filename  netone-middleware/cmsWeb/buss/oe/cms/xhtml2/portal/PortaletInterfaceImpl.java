package oe.cms.xhtml2.portal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;
import oe.cms.runtime.XHtmlHistoryCachePool;
import oe.cms.xhtml2.PortaletInterface;
import oe.cms.xhtml2.portal.core.PortaletDiv;
import oe.frame.orm.OrmerEntry;

public final class PortaletInterfaceImpl implements PortaletInterface {

	public String selectHisPage(String daystr) {
		int day = Integer.parseInt(daystr);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 默认取当天日期
		Calendar c = Calendar.getInstance(); // 当时的日期和时间
		int d = c.get(Calendar.DAY_OF_MONTH); // 取出“日”数
		d = d + day; // 将“日”减一，即得到前一天
		c.set(Calendar.DAY_OF_MONTH, d); // 将“日”数设置回去,如果为０，则自动取上个月最后一天
		String dateTime = sdf.format(c.getTime());

		String str1 = "<div align=\"left\">"
				+ "选择历史页面 <input type='text' id='timeselect' name='timeselect' onFocus='calendar();' value='"
				+ dateTime
				+ "' readonly/>&nbsp;"
				+ "<input type='button' class='butt' value='确定' target='_blank' onclick=\"document.frames.item('iframe1').location='/cmsWeb/showModelAction.do?date='+ document.getElementById('timeselect').value \" /><br>"
				+ "<iframe id='iframe1' scrolling='auto'  frameborder='0' height='100%' width='100%' src='/cmsWeb/showModelAction.do?date="
				+ dateTime + "'></iframe></div>";
		return str1;
	}

	public String selectHisPor(String arg0, String arg1Str) {
		int arg1 = Integer.parseInt(arg1Str);
		TCmsInfocell infocell = (TCmsInfocell) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsInfocell.class, arg0);
		return XHtmlHistoryCachePool.fetchHistory(infocell, String
				.valueOf(arg1));
	}

	public String insertPorInfo(String arg0) {

		HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
				.fetchBean("htmlStreamHandler");
		return htmlStreamHandler.toPortal(arg0, "2", null);
	}

	public String makePorEle(String tableinfo, String titleinfo,
			String titlebgcolor, String bodyinfo, String bodybgcolor) {

		return PortaletDiv.fetchDiv(tableinfo, titleinfo, titlebgcolor,
				bodyinfo, bodybgcolor);
	}

}
