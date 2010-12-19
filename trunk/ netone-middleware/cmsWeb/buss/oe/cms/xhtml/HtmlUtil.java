package oe.cms.xhtml;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsRecordtip;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;
import oe.cms.runtime.XHtmlHistoryCachePool;
import oe.cms.xhtml.core.TableUtil;
import oe.cms.xhtml.core.graph.Graph2SeveletAdpet;
import oe.cms.xhtml.core.graph.GraphSeveletAdpet;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

import org.apache.commons.lang.StringUtils;

/**
 * Html工具箱
 * 
 * 对象：html
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class HtmlUtil {
	public HttpServletRequest request;

	public HtmlUtil(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获得Table的Html模板
	 * 
	 * @param info
	 *            表格数据 二维数组
	 * @param title
	 *            表格标题
	 * 
	 * @return
	 */
	public String fetchTable(String[][] info, String[] title) {
		if (info == null || info.length == 0) {
			return "";
		}
		int maxLen = 0;
		for (int i = 0; i < info.length; i++) {
			if (info[i].length > maxLen) {
				maxLen = info[i].length;
			}
		}
		// 检查所有的指标是否一致
		String[][] targetInfoReal = new String[info.length][maxLen];
		for (int i = 0; i < info.length; i++) {
			System.arraycopy(info[i], 0, targetInfoReal[i], 0, info[i].length);
			if (info[i].length < maxLen) {
				for (int j = info[i].length; j < maxLen; j++) {
					targetInfoReal[i][j] = "0";
				}
			}
		}

		return TableUtil.fetchTable(targetInfoReal, title);
	}

	public static String fetchTableRowXToY(String[][] info) {
		if (info.length < 1 || info[0] == null) {
			return "";
		}
		String[][] newInfo = new String[info[0].length][info.length];
		for (int i = 0; i < info.length; i++) {
			for (int j = 0; j < info[0].length; j++) {
				newInfo[j][i] = info[i][j];
			}
		}
		return fetchTableRow(newInfo);
	}

	/**
	 * 将2维表格的数据(以冗余方式表示3维信息),转化3维 表格数据 比如: <br>
	 * 1月,1月,2月,2月<br>
	 * 北京,山海,北京,上海 <br>
	 * 1,2,3,x<br>
	 * 
	 * 转化后变为<br>
	 * 1月,1,2 2月,3,x
	 * 
	 * 
	 * @param tableDim1
	 * @param tableDim2
	 * @param tableTar
	 * @param loopnum
	 * @return
	 */
	public static String fetchTableRowFrom2DimTo3Dim(String[] tableDim1,
			String[] tableDim2, String[] tableTar, int loopnum) {
		if (tableDim1 == null
				|| tableDim2 == null
				|| tableTar == null
				|| (tableDim1.length != tableDim2.length && tableDim2.length != tableTar.length)) {
			return "无效二维数据";
		}

		String[][] newAll = new String[tableTar.length / loopnum][loopnum + 1];
		for (int i = 0; i < newAll.length; i++) {
			newAll[i][0] = tableDim1[i * loopnum];
			for (int j = 1; j < loopnum + 1; j++) {
				newAll[i][j] = tableTar[i * loopnum + j - 1];
			}
		}
		return fetchTableRowXToY(newAll);
	}

	/**
	 * 获得表格（无Table头信息的）
	 * 
	 * @param info
	 * @return
	 */
	public static String fetchTableRow(String[][] info) {
		if (info == null || info.length == 0) {
			return "";
		}
		int maxLen = 0;
		for (int i = 0; i < info.length; i++) {
			if (info[i] != null && info[i].length > maxLen) {
				maxLen = info[i].length;
			}
		}
		// 检查所有的指标是否一致
		String[][] targetInfoReal = new String[info.length][maxLen];
		for (int i = 0; i < targetInfoReal.length; i++) {
			Arrays.fill(targetInfoReal[i], "0");
		}
		for (int i = 0; i < info.length; i++) {
			if (info[i] != null && info[i].length != 0) {
				System.arraycopy(info[i], 0, targetInfoReal[i], 0,
						info[i].length);
			}
		}
		return TableUtil.fetchTableRow(targetInfoReal);
	}

	/**
	 * 单轴通用图表
	 * 
	 * @param dimvaluelist
	 *            维度值数组
	 * @param targetvaluelist
	 *            指标数组数组,与维度数据对应着的N个指标
	 * @param dimName
	 *            维度的名称
	 * @param targetname
	 *            指标名称数组
	 * @param charttype
	 *            展示的图表类型 Verticalbar3D,Line,BarLine,CombinedBarLine
	 * @param title
	 *            标题
	 * @return
	 */
	public String fetchGraph(String[] dimvaluelist, String[][] targetvaluelist,
			String dimName, String[] targetname, String charttype,
			String title, String xoffset, String yoffset) {
		String dimvalueListLink = LinkMake.makeLink(dimvaluelist);
		String[] targetvaluelistLink = LinkMake.makeLink(targetvaluelist);
		return GraphSeveletAdpet.fetchGraph(dimvalueListLink,
				targetvaluelistLink, dimName, targetname, charttype, title,
				xoffset, yoffset);
	}

	/**
	 * 双轴多指标通用
	 * 
	 * @param dimvaluelist
	 * @param dimName
	 * @param targetvaluelistLeft
	 * @param targetvaluelistRight
	 * @param targetnameLeft
	 * @param targetnameLRight
	 * @param title
	 * @param is3D
	 * @return
	 */
	public String fetchGraph2Common(String[] dimvaluelist, String dimName,
			String[][] targetvaluelistLeft, String[][] targetvaluelistRight,
			String[] targetnameLeft, String[] targetnameLRight, String title,
			boolean is3D, String xoffset, String yoffset) {
		String dimvalueListLink = LinkMake.makeLink(dimvaluelist);

		String[] targetValuelinkLeft = new String[targetvaluelistLeft.length];
		for (int i = 0; i < targetValuelinkLeft.length; i++) {
			targetValuelinkLeft[i] = LinkMake.makeLink(targetvaluelistLeft[i]);
		}

		String[] targetValuelinkRight = new String[targetvaluelistRight.length];
		for (int i = 0; i < targetValuelinkRight.length; i++) {
			targetValuelinkRight[i] = LinkMake
					.makeLink(targetvaluelistRight[i]);
		}
		String charType = is3D ? "3D" : "2D";

		return Graph2SeveletAdpet.fetchGraphX(dimvalueListLink,
				targetValuelinkLeft, targetValuelinkRight, dimName,
				targetnameLeft, targetnameLRight, charType, title, xoffset,
				yoffset);
	}

	/**
	 * 详细内容
	 * 
	 * @param title
	 *            内容标题
	 * @param cellid
	 *            咨讯元ID
	 * @return
	 */
	public String fetchDetail(String title, String cellid) {

		if (title == null || title.trim().length() == 0) {
			title = "详细信息";
		}
		//		
		// Ormer ormer = CmsEntry.fetchOrmer();
		// TCmsInfocell cellinfo = (TCmsInfocell) ormer.fetchQuerister()
		// .loadObject(TCmsInfocell.class, new Long(cellid));
		// XHtmlCachepool.fetchInfo(cellinfo);
		String linkpath = "/cmsWeb/cms/detailinfo.jsp?cellid=" + cellid;

		return "<a href=\"" + linkpath + "\" target=\"_blank\">" + title
				+ "</a>";

	}

	/**
	 * 编辑知识库
	 * 
	 * @param title
	 *            内容标题
	 * @param cellid
	 *            咨讯元ID
	 * @return
	 */
	public String fetchWiki(String title, String wikiid) {

		if (title == null || title.trim().length() == 0) {
			title = "知识库";
		}
		String linkpath = "/wiki/Wiki.jsp?page=" + wikiid;

		return "<a href=\"" + linkpath + "\" target=\"_blank\">" + title
				+ "</a>";
	}

	/**
	 * 获得图片链接
	 * 
	 * @param imgid
	 * @param style
	 * @return
	 */
	public String fetchImg(String imgid, String style) {
		if (style != null) {
			return "<img src=\"/fileupload/fi/downloadAction.do?fileid="
					+ imgid + "\" " + style + ">";
		}
		return "<img src=\"/fileupload/fi/downloadAction.do?fileid=" + imgid
				+ "\" >";
	}

	/**
	 * 获得文件下载链接
	 * 
	 * @param fileid
	 * @param filename
	 * @return
	 */
	public String fetchFile(String fileid, String filename) {
		String url = "/fileupload/fi/downloadAction.do?fileid=" + fileid;
		return "<a href=\"" + url + "\" target=\"_blank\">" + filename + "</a>";
	}

	/**
	 * 获得历史信息
	 * 
	 * @return
	 */
	public String fetchHistory(String dayKey) {
		int day = 0;
		try {
			day = Integer.parseInt(dayKey);
		} catch (Exception e) {

		}
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

	/**
	 * 标记信息
	 * 
	 * @param value
	 *            标记内容
	 * @param cellid
	 *            咨讯元ID
	 * @return
	 */
	public String fetchMark(String value, String cellid) {
		String[] info = fetchMarkTip(value, cellid);
		return "<label title=\"" + info[0] + "\" onClick=\"" + info[1] + "\">"
				+ value + "</label>";

	}

	/**
	 * 标记信息
	 * 
	 * @param value
	 *            标记内容
	 * @param cellid
	 *            咨讯元ID
	 * @return
	 */
	private String[] fetchMarkTip(String value, String cellid) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);
		List list = ormer.fetchQuerister().queryObjects(ttip, null);
		if (list != null && list.size() != 0) {
			ttip = (TCmsRecordtip) list.get(0);
		}
		String tipInfo = ttip.getTipinfo() == null ? "" : ttip.getTipinfo();
		String title = "批注内容: " + tipInfo + "&#10";
		Date date = ttip.getCreated();
		if (date == null) {
			date = new Date(System.currentTimeMillis());
		}
		String user = ttip.getWriter() == null ? "" : ttip.getWriter();
		SimpleDateFormat dataf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateInfo = dataf.format(date);

		title += "日期: " + dateInfo + "&#10人员: " + user;

		String linkpath = "/cmsWeb/recordtipmodiView.do?sqlid=" + cellid
				+ "&recordid=" + value;

		String info = "javascript:window.open ('"
				+ linkpath
				+ "', '_blank', 'height=200, width=330, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')";

		return new String[] { title, info };

	}

	/**
	 * 获得值编辑入口(带有批注)
	 * 
	 * @param value
	 *            序列号
	 * @param cellid
	 *            咨讯元ID
	 * @param defaultValue
	 *            默认返回值
	 * @return
	 */
	public String fetchValueInfo(String value, String cellid,
			String defaultValue) {

		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);

		List list = ormer.fetchQuerister().queryObjects(ttip, null);
		if (list != null && list.size() != 0) {
			ttip = (TCmsRecordtip) list.get(0);
		}
		String title = ttip.getTipinfo() == null ? defaultValue : ttip
				.getTipinfo();

		String linkpath = "/cmsWeb/recordtipmodiView.do?valuedo=1&sqlid="
				+ cellid + "&recordid=" + value;

		String info = "javascript:window.open ('"
				+ linkpath
				+ "', '_blank', 'height=200, width=330, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')";
		// 获得批注信息，title是批注的值
		String[] infox = fetchMarkTip(title, cellid);

		return "<img onClick=\"" + info
				+ "\" src=\"/cmsWeb/image/tipx.gif\"></img>"
				+ "<label title=\"" + infox[0] + "\" onClick=\"" + infox[1]
				+ "\">" + title + "</label>";
	}

	/**
	 * 获得真实的数值
	 * 
	 * @param value
	 * @param cellid
	 * @return
	 */
	public String fetchValue(String value, String cellid) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);

		List list = ormer.fetchQuerister().queryObjects(ttip, null);
		if (list != null && list.size() != 0) {
			ttip = (TCmsRecordtip) list.get(0);
		}
		String tipinfo = ttip.getTipinfo();
		return tipinfo == null ? "" : tipinfo;
	}

	/**
	 * 获得真实的数值
	 * 
	 * @param value
	 * @param cellid
	 * @return
	 */
	public String fetchValue(String value, String cellid, String defaultValue) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);

		List list = ormer.fetchQuerister().queryObjects(ttip, null);
		if (list != null && list.size() != 0) {
			ttip = (TCmsRecordtip) list.get(0);
		}
		String tipinfo = ttip.getTipinfo();
		return tipinfo == null ? defaultValue : tipinfo;
	}

	/**
	 * 修改数值的颜色
	 * 
	 * @param valueExpress
	 * @param color
	 * @return
	 */
	public String modifyValueColor(String valueExpress, String color) {
		if (valueExpress == null || valueExpress.equals("")) {
			return "";
		}
		return StringUtils.replaceOnce(valueExpress, "<font color='writh'>",
				"<font color='" + color + "'>");
	}

	/**
	 * 插入编译后的Cell代码(即Html信息)
	 * 
	 * @param cellid
	 * @return
	 */
	public String insertCell(String cellid) {

		HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
				.fetchBean("htmlStreamHandler");
		return htmlStreamHandler.toPortal(cellid, "2", request);
	}

	/**
	 * 适应指标和维度的个数
	 * 
	 * @param dim
	 * @param tar
	 * @return 第一个元素是 统一的维度，后面依次是按照参数顺序的指标
	 */
	public String[][] adeptDimTar(String[][] dim, String[][] tar) {
		return AdpetDimTar.adeptDimTarCommon(dim, tar);
	}

	/**
	 * 获得历史信息
	 * 
	 * @param cellid
	 * @param day
	 * @return
	 */
	public String fetchHisData(String cellid, String day) {
		TCmsInfocell infocell = (TCmsInfocell) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsInfocell.class, cellid);
		return XHtmlHistoryCachePool.fetchHistory(infocell, day);
	}

	private Map mapApp = new HashMap();

	public void setApplicationData(String key, String value) {
		mapApp.put(key, value);
	}

	public String getApplicationData(String key) {
		return (String) mapApp.get(key);
	}

}
