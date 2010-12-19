package oe.cms.datasource;

import java.util.HashMap;
import java.util.Map;

import oe.cms.runtime.core.WiParser;

import org.apache.commons.lang.StringUtils;

public class WiDivInfoAccessImpl implements WiDivInfoAccess {

	public Map parserExtendAttribute(String wiinfo) {
		Map ext = new HashMap();
		if (wiinfo == null) {
			return ext;
		}

		String[] extendsinfo = StringUtils.split(wiinfo, ";");
		for (int i = 0; i < extendsinfo.length; i++) {
			String[] extendsPreInfo = StringUtils.split(extendsinfo[i], ",");
			if (extendsPreInfo.length == 2) {
				ext.put(extendsPreInfo[0], extendsPreInfo[1]);
			}
		}
		return ext;
	}

	private String[] parserxy(String xy) {
		if (xy == null || xy.equals("")) {
			return new String[] { "0", "0" };
		}
		String[] xyvalue = StringUtils.split(xy, ":");
		if (xyvalue.length != 2) {
			return new String[] { "0", "0" };
		}
		return xyvalue;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public WiDivInfo fetchDivInfo(String wiinfo) {
		Map ext = parserExtendAttribute(wiinfo);
		String fxy = (String) ext.get(_EXT_KEY_FOFFSET);
		String txy = (String) ext.get(_EXT_KEY_TOFFSET);
		String url = (String) ext.get(_EXT_KEY_URL);

		String[] fxyvalue = parserxy(fxy);
		String[] txyvalue = parserxy(txy);
		int widthinfo = Integer.parseInt(txyvalue[0])
				- Integer.parseInt(fxyvalue[0]);
		int heightinfo = Integer.parseInt(txyvalue[1])
				- Integer.parseInt(fxyvalue[1]);

		String divid = WiParser._DIV_ID + System.currentTimeMillis();

		WiDivInfo wiDivInfo = new WiDivInfo();
		wiDivInfo.setFxy(fxyvalue);
		wiDivInfo.setTxy(txyvalue);
		wiDivInfo.setUrl(url);
		wiDivInfo.setDivid(divid);
		wiDivInfo.setHeight(String.valueOf(heightinfo));
		wiDivInfo.setWidth(String.valueOf(widthinfo));
		return wiDivInfo;
	}

}
