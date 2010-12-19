package oe.cms.xhtml2.view.core;

import org.apache.commons.lang.StringUtils;

/**
 * 弹出窗口 <br>
 * 使用方法:jw.xhtmlinfo(Object arg0, Object arg1) <br>
 * 参数 arg0,对应的咨讯元ID(不允许为空)<br>
 * 参数 arg1,对应的样式信息，样式信息的格式例子 height=300,left=0, width=300, top=0,scrollbars=yes
 * 如果为空那么系统采用默认的样式
 * 
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public final class OpenDetailInfo {

	private final static String scrpit = "<script> window.open('5url99xyz', 'targetsd12#2*6', 'dispInfo99933xfsd,toolbar=no, menubar=no,  resizable=no,location=no, status=no')</script> ";

	private final static String urlReal = "/cms/detailinfo.jsp?cellid=";

	private final static String dispInfoDefault = "height=300, width=350, top=0, left=0, scrollbars=yes";

	private final static String urlKey = "5url99xyz";

	private final static String dispKey = "dispInfo99933xfsd";

	private final static String target = "targetsd12#2*6";

	/**
	 * 弹出窗口
	 */
	public static String xhtmlinfo(String cellid, String dispinfo,
			String targetis, String contextpath) {

		String newInfo = StringUtils.replaceOnce(scrpit, urlKey, contextpath
				+ urlReal + cellid);

		if (dispinfo == null || dispinfo.trim().equals("")) {
			newInfo = StringUtils
					.replaceOnce(newInfo, dispKey, dispInfoDefault);
		} else {
			newInfo = StringUtils.replaceOnce(newInfo, dispKey, dispinfo);
		}
		newInfo = StringUtils.replaceOnce(newInfo, target, targetis);
		return newInfo;
	}

}
