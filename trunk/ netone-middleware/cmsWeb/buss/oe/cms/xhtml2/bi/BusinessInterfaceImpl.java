package oe.cms.xhtml2.bi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oe.cms.cfg.TCmsRecordtip;
import oe.cms.xhtml2.BusinessInterface;
import oe.cms.xhtml2.DataInterface;
import oe.cms.xhtml2.ViewInterface;
import oe.cms.xhtml2.view.core.MarkTIp;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 业务功能实现
 * 
 * @author chen.jia.xun
 * 
 */
public class BusinessInterfaceImpl implements BusinessInterface {

	private DataInterface di;

	private ViewInterface vi;

	public String oecForum(String urlx, String limitrecordnumber, String keyTip) {
		int limitrecordnum = Integer.parseInt(limitrecordnumber);
		String urlInfo = urlx;

		String infonext = di.insect(urlInfo, "gb2312");
		List list = new ArrayList();
		String infox = StringUtils
				.substringBetween(infonext, "$_</font>", "_$");

		while (infox != null && infox.length() > 0) {
			list.add(infox);
			if (list.size() > limitrecordnum) {
				break;
			}
			infonext = StringUtils.substringAfter(infonext, "_$");
			infox = StringUtils.substringBetween(infonext, "$_", "_$");

		}
		String[] tiinfo = (String[]) list.toArray(new String[0]);
		String[] finalValue = new String[limitrecordnum];
		int len = finalValue.length > tiinfo.length ? tiinfo.length
				: finalValue.length;
		Arrays.fill(finalValue, "-");
		for (int i = 0; i < len; i++) {
			// String subinfo = StringUtils.substringBetween(tiinfo[i],
			// "target='_blank'<font size='2'>", "</font></a>");
			// String newsubinfo = limitDispSenc(subinfo, wordnum);
			// finalValue[i] = StringUtils.replace(tiinfo[i], subinfo,
			// newsubinfo);
			finalValue[i] = "-" + tiinfo[i];
		}
		return vi.dispRowOnly(new String[][] { finalValue });
	}

	// 预先过滤掉相关的样式信息
	public String limitDispSenc(String senstance, int limit) {
		if (senstance == null) {
			return "";
		}

		Pattern pat = Pattern.compile(_fontStyle);
		Matcher mat = pat.matcher(senstance);

		List list = new ArrayList();
		List listValue = new ArrayList();
		while (mat.find()) {
			String prevalue = mat.group();

			list.add(prevalue);
			listValue.add(new Integer(mat.start()));
			senstance = StringUtils.replaceOnce(senstance, prevalue, "");
		}

		List list1 = new ArrayList();
		String xset = senstance;
		int index1 = 0;
		while ((index1 = StringUtils.indexOf(xset, _fontStyleE)) > 0) {
			list1.add(new Integer(index1));
			xset = StringUtils.substringAfter(xset, _fontStyleE);
			senstance = StringUtils.replaceOnce(senstance, _fontStyleE, "");
		}

		List list2 = new ArrayList();
		String xset2 = senstance;
		int index2 = 0;
		while ((index2 = StringUtils.indexOf(xset2, _strongStyle)) > 0) {
			list2.add(new Integer(index2));
			xset2 = StringUtils.substringAfter(xset2, _strongStyle);
			senstance = StringUtils.replaceOnce(senstance, _strongStyle, "");
		}

		List list3 = new ArrayList();
		String xset3 = senstance;
		int index3 = 0;
		while ((index3 = StringUtils.indexOf(xset3, _strongStyleE)) > 0) {
			list3.add(new Integer(index3));
			xset3 = StringUtils.substringAfter(xset3, _strongStyleE);
			senstance = StringUtils.replaceOnce(senstance, _strongStyleE, "");
		}

		int count = 0;

		for (int i = 0; i < senstance.length(); i++) {
			String pre = senstance.substring(i, i + 1);
			if (pre.matches("[A-Za-z0-9]+")) {
				count++;
			} else {
				count = count + 2;
			}
			if (count > limit) {
				senstance = senstance.substring(0, i);
				break;
			}
		}

		for (Iterator itr = list3.iterator(); itr.hasNext();) {
			int index = ((Integer) itr.next()).intValue();

			senstance = insertStr(index, senstance, _strongStyleE);
		}

		for (Iterator itr = list2.iterator(); itr.hasNext();) {
			int index = ((Integer) itr.next()).intValue();

			senstance = insertStr(index, senstance, _strongStyle);
		}

		for (Iterator itr = list1.iterator(); itr.hasNext();) {
			int index = ((Integer) itr.next()).intValue();

			senstance = insertStr(index, senstance, _fontStyleE);
		}

		int i = 0;
		for (Iterator itr = list.iterator(); itr.hasNext(); i++) {
			int index = ((Integer) listValue.get(i)).intValue();
			String value = (String) itr.next();
			senstance = insertStr(index, senstance, value);
		}

		return senstance;
	}

	private String insertStr(int index, String senstance, String word) {
		String ahead = StringUtils.substring(senstance, 0, index);
		String back = StringUtils.substring(senstance, index, senstance
				.length());
		return ahead + word + back;
	}

	public DataInterface getDi() {
		return di;
	}

	public void setDi(DataInterface di) {
		this.di = di;
	}

	public ViewInterface getVi() {
		return vi;
	}

	public void setVi(ViewInterface vi) {
		this.vi = vi;
	}

	public String vote(String value, String[] titles, String cellid,
			String defaultValue) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);

		List list = ormer.fetchQuerister().queryObjects(ttip,null);
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
		String[] infox = MarkTIp.fetchMarkTip(title, cellid);

		return "<img onClick=\"" + info
				+ "\" src=\"/cmsWeb/image/tipx.gif\"></img>"
				+ "<label title=\"" + infox[0] + "\" onClick=\"" + infox[1]
				+ "\"><font color='black'>" + title + "</font></label>";
	}

}
