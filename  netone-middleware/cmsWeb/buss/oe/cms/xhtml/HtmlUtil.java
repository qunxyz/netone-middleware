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
 * Html������
 * 
 * ����html
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
	 * ���Table��Htmlģ��
	 * 
	 * @param info
	 *            ������� ��ά����
	 * @param title
	 *            ������
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
		// ������е�ָ���Ƿ�һ��
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
	 * ��2ά��������(�����෽ʽ��ʾ3ά��Ϣ),ת��3ά ������� ����: <br>
	 * 1��,1��,2��,2��<br>
	 * ����,ɽ��,����,�Ϻ� <br>
	 * 1,2,3,x<br>
	 * 
	 * ת�����Ϊ<br>
	 * 1��,1,2 2��,3,x
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
			return "��Ч��ά����";
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
	 * ��ñ����Tableͷ��Ϣ�ģ�
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
		// ������е�ָ���Ƿ�һ��
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
	 * ����ͨ��ͼ��
	 * 
	 * @param dimvaluelist
	 *            ά��ֵ����
	 * @param targetvaluelist
	 *            ָ����������,��ά�����ݶ�Ӧ�ŵ�N��ָ��
	 * @param dimName
	 *            ά�ȵ�����
	 * @param targetname
	 *            ָ����������
	 * @param charttype
	 *            չʾ��ͼ������ Verticalbar3D,Line,BarLine,CombinedBarLine
	 * @param title
	 *            ����
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
	 * ˫���ָ��ͨ��
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
	 * ��ϸ����
	 * 
	 * @param title
	 *            ���ݱ���
	 * @param cellid
	 *            ��ѶԪID
	 * @return
	 */
	public String fetchDetail(String title, String cellid) {

		if (title == null || title.trim().length() == 0) {
			title = "��ϸ��Ϣ";
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
	 * �༭֪ʶ��
	 * 
	 * @param title
	 *            ���ݱ���
	 * @param cellid
	 *            ��ѶԪID
	 * @return
	 */
	public String fetchWiki(String title, String wikiid) {

		if (title == null || title.trim().length() == 0) {
			title = "֪ʶ��";
		}
		String linkpath = "/wiki/Wiki.jsp?page=" + wikiid;

		return "<a href=\"" + linkpath + "\" target=\"_blank\">" + title
				+ "</a>";
	}

	/**
	 * ���ͼƬ����
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
	 * ����ļ���������
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
	 * �����ʷ��Ϣ
	 * 
	 * @return
	 */
	public String fetchHistory(String dayKey) {
		int day = 0;
		try {
			day = Integer.parseInt(dayKey);
		} catch (Exception e) {

		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Ĭ��ȡ��������
		Calendar c = Calendar.getInstance(); // ��ʱ�����ں�ʱ��
		int d = c.get(Calendar.DAY_OF_MONTH); // ȡ�����ա���
		d = d + day; // �����ա���һ�����õ�ǰһ��
		c.set(Calendar.DAY_OF_MONTH, d); // �����ա������û�ȥ,���Ϊ�������Զ�ȡ�ϸ������һ��
		String dateTime = sdf.format(c.getTime());

		String str1 = "<div align=\"left\">"
				+ "ѡ����ʷҳ�� <input type='text' id='timeselect' name='timeselect' onFocus='calendar();' value='"
				+ dateTime
				+ "' readonly/>&nbsp;"
				+ "<input type='button' class='butt' value='ȷ��' target='_blank' onclick=\"document.frames.item('iframe1').location='/cmsWeb/showModelAction.do?date='+ document.getElementById('timeselect').value \" /><br>"
				+ "<iframe id='iframe1' scrolling='auto'  frameborder='0' height='100%' width='100%' src='/cmsWeb/showModelAction.do?date="
				+ dateTime + "'></iframe></div>";
		return str1;
	}

	/**
	 * �����Ϣ
	 * 
	 * @param value
	 *            �������
	 * @param cellid
	 *            ��ѶԪID
	 * @return
	 */
	public String fetchMark(String value, String cellid) {
		String[] info = fetchMarkTip(value, cellid);
		return "<label title=\"" + info[0] + "\" onClick=\"" + info[1] + "\">"
				+ value + "</label>";

	}

	/**
	 * �����Ϣ
	 * 
	 * @param value
	 *            �������
	 * @param cellid
	 *            ��ѶԪID
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
		String title = "��ע����: " + tipInfo + "&#10";
		Date date = ttip.getCreated();
		if (date == null) {
			date = new Date(System.currentTimeMillis());
		}
		String user = ttip.getWriter() == null ? "" : ttip.getWriter();
		SimpleDateFormat dataf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateInfo = dataf.format(date);

		title += "����: " + dateInfo + "&#10��Ա: " + user;

		String linkpath = "/cmsWeb/recordtipmodiView.do?sqlid=" + cellid
				+ "&recordid=" + value;

		String info = "javascript:window.open ('"
				+ linkpath
				+ "', '_blank', 'height=200, width=330, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')";

		return new String[] { title, info };

	}

	/**
	 * ���ֵ�༭���(������ע)
	 * 
	 * @param value
	 *            ���к�
	 * @param cellid
	 *            ��ѶԪID
	 * @param defaultValue
	 *            Ĭ�Ϸ���ֵ
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
		// �����ע��Ϣ��title����ע��ֵ
		String[] infox = fetchMarkTip(title, cellid);

		return "<img onClick=\"" + info
				+ "\" src=\"/cmsWeb/image/tipx.gif\"></img>"
				+ "<label title=\"" + infox[0] + "\" onClick=\"" + infox[1]
				+ "\">" + title + "</label>";
	}

	/**
	 * �����ʵ����ֵ
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
	 * �����ʵ����ֵ
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
	 * �޸���ֵ����ɫ
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
	 * ���������Cell����(��Html��Ϣ)
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
	 * ��Ӧָ���ά�ȵĸ���
	 * 
	 * @param dim
	 * @param tar
	 * @return ��һ��Ԫ���� ͳһ��ά�ȣ����������ǰ��ղ���˳���ָ��
	 */
	public String[][] adeptDimTar(String[][] dim, String[][] tar) {
		return AdpetDimTar.adeptDimTarCommon(dim, tar);
	}

	/**
	 * �����ʷ��Ϣ
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
