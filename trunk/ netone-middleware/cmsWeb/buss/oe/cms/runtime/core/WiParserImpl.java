package oe.cms.runtime.core;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.datasource.WiDivInfo;
import oe.cms.datasource.WiDivInfoAccess;

import org.apache.commons.lang.StringUtils;

public class WiParserImpl implements WiParser {

	public String performWiParser(String wiinfo) {
		if (wiinfo == null || wiinfo.trim().length() == 0) {
			return "";
		}
		WiDivInfoAccess widivacc = (WiDivInfoAccess) CmsEntry
				.fetchBean(CmsBean._WI_DIV_ACCESS);
		WiDivInfo widiv = widivacc.fetchDivInfo(wiinfo);
		// 定义咨讯元在主界面上的布局
		String potrolinfo = StringUtils
				.replaceOnce(_POTROL_DIV, _TOP_INFO, "1");
		potrolinfo = StringUtils.replaceOnce(potrolinfo, _LEFT_INFO, "1");

		// 定义咨讯元的iD
		potrolinfo = StringUtils.replace(potrolinfo, _DIV_ID, widiv.getDivid());

		// 定义咨讯对应的集成界面的URL
		potrolinfo = StringUtils.replaceOnce(potrolinfo, _SRC_INFO, widiv
				.getUrl());
		// 定义咨讯元的宽度
		potrolinfo = StringUtils.replaceOnce(potrolinfo, _WIDTH_INFO, widiv
				.getWidth());
		// 定义咨讯元的高度
		potrolinfo = StringUtils.replaceOnce(potrolinfo, _HEIGHT_INFO, widiv
				.getHeight());
		// 定义咨讯元集成界面的宽度
		potrolinfo = StringUtils.replaceOnce(potrolinfo, _DIV_LEFT, widiv
				.getFxy()[0]);
		// 定义咨讯元集成界面的高度
		potrolinfo = StringUtils.replaceOnce(potrolinfo, _DIV_TOP, widiv
				.getFxy()[1]);

		return potrolinfo;

	}
	
	public static void main(String []arg){
		WiParser wi=new WiParserImpl();
		String xxx=wi.performWiParser("foffset,100:100;toffset,300:300;url,http://www.sina.com;");
		System.out.println(xxx);
	}

}
