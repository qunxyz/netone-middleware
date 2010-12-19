package oe.cms.xhtml2.view.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import oe.cms.cfg.TCmsRecordtip;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

public final class MarkTIp {

	/**
	 * �����Ϣ
	 * 
	 * @param value
	 *            �������
	 * @param cellid
	 *            ��ѶԪID
	 * @return
	 */
	public static String[] fetchMarkTip(String value, String cellid) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsRecordtip ttip = new TCmsRecordtip();
		ttip.setRecordid(value);
		ttip.setCellid(cellid);
		List list = ormer.fetchQuerister().queryObjects(ttip,null);
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

}
