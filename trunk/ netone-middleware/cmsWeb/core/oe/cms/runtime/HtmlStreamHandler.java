package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

/**
 * 信息发布
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface HtmlStreamHandler {

	String _PORTAL_VIEW_TOOLBAR = "1";

	String _PORTAL_VIEW_NOTOOLBAR = "2";

	String _PORTAL_VIEW_NOTOOLBAR_AND_NOBORDER = "3";

	String _PORTAL_EDIT = "4";

	/**
	 * 用于显示页面的容器
	 */
	String _DIV_CONTAINER_USE = "<DIV class='floatdiv'  style=\"BORDER-RIGHT: 1px solid;Left:leftinfopx;top:topinfopx; BORDER-TOP: 1px solid; DISPLAY: block; MARGIN: 1px; OVERFLOW: visible; BORDER-LEFT: 1px solid;  BORDER-BOTTOM: 1px solid;  WORD-WRAP: break-word\">";

	String _DIV_CONTAINER_USE_TOP = "topinfo";

	String _DIV_CONTAINER_USE_LEFT = "leftinfo";

	String _DIV_CONTAINER_USE_CELL_ID = "xcellid";

	String _DIV_CONTAINER_USE_CELL_PATH = "xpagepath";

	/**
	 * 用于设计页面布局使用的显示容器
	 */
	String _DIV_CONTAINER_DESIGN = "<DIV class=\"floatdiv\"   style=\"WIDTH:widthinfo;HEIGHT:heightinfo;left:0px; top:0px; BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; DISPLAY: block; MARGIN: 1px; OVERFLOW: auto; BORDER-LEFT: 1px solid;  BORDER-BOTTOM: 1px solid;  WORD-WRAP: break-word\">";

	// 不带边框的
	String _DIV_CONTAINER_ = "<DIV class=\"floatdiv\"   style=\"WIDTH:widthinfo;HEIGHT:heightinfo;left:0px; top:0px;  DISPLAY: block; MARGIN: 1px; OVERFLOW: visible; WORD-WRAP: break-word\">";

	String _DIV_CONTAINER_DESIGN_WIDTHS = "widthinfo";

	String _DIV_CONTAINER_DESIGN_HEIGHTS = "heightinfo";

	String _DIV_CONTAINER_DESIGN_WIDTH_COLUMN = "HEIGHT:heightinfo;";

	String _DIV_CONTAINER_DESIGN_HEIGHT_COLUMN = "WIDTH:widthinfo;";

	String _DIV_CONTAINER_END = "</DIV>";

	String _DIV_CONTEXT_PATH = "CONTEXTPATH";

	String _DIV_CONTAINER_HEAD = "<div class=\"focusdiv\" style=\"width:200px;height:15px;left:0px; top:0px;\" title=\"拖动\">"

			+ "&nbsp;<a href=\"javascript:;\" title='添加项' onclick=\"window.open('CONTEXTPATH/flowpage/PageItemChoice.jsp?id=xcellid&pagepath=pageitem','_additem', 'height=600, width=800, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');\"><img src='CONTEXTPATH/image/portal/ad_item.gif' BORDER='0' /></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='添加应用' onclick=\"window.open('CONTEXTPATH/flowpage/AppChoice.jsp?id=xcellid', '_blank', 'height=600, width=800, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/ed_pobadd.gif' BORDER='0' /></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='页项管理' onclick=\"window.open('CONTEXTPATH/PagelistRightSvl?pagename=pageitem&appname=xpagepath&cellid=xcellid', '_blank', 'height=600, width=700, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/pobarr.gif' BORDER='0' /></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='属性' onclick=\"window.open('CONTEXTPATH/servlet/AjaxUpdatePageSvl?id=xcellid', '_blank', 'height=400, width=600, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/ed_properties.gif' BORDER='0' /></a>&nbsp;&nbsp;"
			+ "<a href=\"javascript:;\"  title='最大化' onclick=\"window.open('CONTEXTPATH/servlet/PageService?cellid=xcellid', '_blank', 'height=600, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/ed_max.gif' BORDER='0'/></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='关闭'   onclick=\"removeFDiv(this.parentNode);\"><img src='CONTEXTPATH/image/portal/ed_del.gif' BORDER='0'/></a>"
			+ "</div>";

	/** 取消按钮 */
	String _DIV_CONTAINER_HEAD_DISABLE = "<div class=\"focusdiv\" style=\"width:200px;height:15px;left:0px; top:0px;\" title=\"拖动\" >"

		+ "&nbsp;<a href=\"javascript:;\" title='添加项' ><img src='CONTEXTPATH/image/portal/gray/ad_item_gray.gif' BORDER='0' /></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='添加应用' ><img src='CONTEXTPATH/image/portal/gray/ed_pobadd_gray.gif' BORDER='0' /></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='页项管理' ><img src='CONTEXTPATH/image/portal/gray/pobarr_gray.gif' BORDER='0' /></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='属性' ><img src='CONTEXTPATH/image/portal/gray/ed_properties_gray.gif' BORDER='0' /></a>&nbsp;&nbsp;"
		+ "<a href=\"javascript:;\"  title='最大化' ><img src='CONTEXTPATH/image/portal/gray/ed_max_gray.gif' BORDER='0'/></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='关闭'   onclick=\"removeFDiv(this.parentNode);\"><img src='CONTEXTPATH/image/portal/ed_del.gif' BORDER='0'/></a>"
		+ "</div>";
	
	int _DIV_CONTAINER_HEAD_HEIGHT = 25;

	int _DIV_CONTAINER_HEAD_LEFT = 0;

	/**
	 * Portal展现
	 * 
	 * @param groupEntity
	 *            portal对象
	 * @param mode
	 *            显示外观模式(mode=1表示需要实现订制工具条,mode=2表示不需要订制工具条,mode=3表示不需要订制工具条,并且去除portal的外框)
	 * @return
	 */
	String toPortal(String cellid, String mode,
			HttpServletRequest request);

}
