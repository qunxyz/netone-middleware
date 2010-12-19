package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;

/**
 * ��Ϣ����
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
	 * ������ʾҳ�������
	 */
	String _DIV_CONTAINER_USE = "<DIV class='floatdiv'  style=\"BORDER-RIGHT: 1px solid;Left:leftinfopx;top:topinfopx; BORDER-TOP: 1px solid; DISPLAY: block; MARGIN: 1px; OVERFLOW: visible; BORDER-LEFT: 1px solid;  BORDER-BOTTOM: 1px solid;  WORD-WRAP: break-word\">";

	String _DIV_CONTAINER_USE_TOP = "topinfo";

	String _DIV_CONTAINER_USE_LEFT = "leftinfo";

	String _DIV_CONTAINER_USE_CELL_ID = "xcellid";

	String _DIV_CONTAINER_USE_CELL_PATH = "xpagepath";

	/**
	 * �������ҳ�沼��ʹ�õ���ʾ����
	 */
	String _DIV_CONTAINER_DESIGN = "<DIV class=\"floatdiv\"   style=\"WIDTH:widthinfo;HEIGHT:heightinfo;left:0px; top:0px; BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; DISPLAY: block; MARGIN: 1px; OVERFLOW: auto; BORDER-LEFT: 1px solid;  BORDER-BOTTOM: 1px solid;  WORD-WRAP: break-word\">";

	// �����߿��
	String _DIV_CONTAINER_ = "<DIV class=\"floatdiv\"   style=\"WIDTH:widthinfo;HEIGHT:heightinfo;left:0px; top:0px;  DISPLAY: block; MARGIN: 1px; OVERFLOW: visible; WORD-WRAP: break-word\">";

	String _DIV_CONTAINER_DESIGN_WIDTHS = "widthinfo";

	String _DIV_CONTAINER_DESIGN_HEIGHTS = "heightinfo";

	String _DIV_CONTAINER_DESIGN_WIDTH_COLUMN = "HEIGHT:heightinfo;";

	String _DIV_CONTAINER_DESIGN_HEIGHT_COLUMN = "WIDTH:widthinfo;";

	String _DIV_CONTAINER_END = "</DIV>";

	String _DIV_CONTEXT_PATH = "CONTEXTPATH";

	String _DIV_CONTAINER_HEAD = "<div class=\"focusdiv\" style=\"width:200px;height:15px;left:0px; top:0px;\" title=\"�϶�\">"

			+ "&nbsp;<a href=\"javascript:;\" title='�����' onclick=\"window.open('CONTEXTPATH/flowpage/PageItemChoice.jsp?id=xcellid&pagepath=pageitem','_additem', 'height=600, width=800, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');\"><img src='CONTEXTPATH/image/portal/ad_item.gif' BORDER='0' /></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='���Ӧ��' onclick=\"window.open('CONTEXTPATH/flowpage/AppChoice.jsp?id=xcellid', '_blank', 'height=600, width=800, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/ed_pobadd.gif' BORDER='0' /></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='ҳ�����' onclick=\"window.open('CONTEXTPATH/PagelistRightSvl?pagename=pageitem&appname=xpagepath&cellid=xcellid', '_blank', 'height=600, width=700, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/pobarr.gif' BORDER='0' /></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='����' onclick=\"window.open('CONTEXTPATH/servlet/AjaxUpdatePageSvl?id=xcellid', '_blank', 'height=400, width=600, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/ed_properties.gif' BORDER='0' /></a>&nbsp;&nbsp;"
			+ "<a href=\"javascript:;\"  title='���' onclick=\"window.open('CONTEXTPATH/servlet/PageService?cellid=xcellid', '_blank', 'height=600, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')\"><img src='CONTEXTPATH/image/portal/ed_max.gif' BORDER='0'/></a>&nbsp;"
			+ "<a href=\"javascript:;\"  title='�ر�'   onclick=\"removeFDiv(this.parentNode);\"><img src='CONTEXTPATH/image/portal/ed_del.gif' BORDER='0'/></a>"
			+ "</div>";

	/** ȡ����ť */
	String _DIV_CONTAINER_HEAD_DISABLE = "<div class=\"focusdiv\" style=\"width:200px;height:15px;left:0px; top:0px;\" title=\"�϶�\" >"

		+ "&nbsp;<a href=\"javascript:;\" title='�����' ><img src='CONTEXTPATH/image/portal/gray/ad_item_gray.gif' BORDER='0' /></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='���Ӧ��' ><img src='CONTEXTPATH/image/portal/gray/ed_pobadd_gray.gif' BORDER='0' /></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='ҳ�����' ><img src='CONTEXTPATH/image/portal/gray/pobarr_gray.gif' BORDER='0' /></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='����' ><img src='CONTEXTPATH/image/portal/gray/ed_properties_gray.gif' BORDER='0' /></a>&nbsp;&nbsp;"
		+ "<a href=\"javascript:;\"  title='���' ><img src='CONTEXTPATH/image/portal/gray/ed_max_gray.gif' BORDER='0'/></a>&nbsp;"
		+ "<a href=\"javascript:;\"  title='�ر�'   onclick=\"removeFDiv(this.parentNode);\"><img src='CONTEXTPATH/image/portal/ed_del.gif' BORDER='0'/></a>"
		+ "</div>";
	
	int _DIV_CONTAINER_HEAD_HEIGHT = 25;

	int _DIV_CONTAINER_HEAD_LEFT = 0;

	/**
	 * Portalչ��
	 * 
	 * @param groupEntity
	 *            portal����
	 * @param mode
	 *            ��ʾ���ģʽ(mode=1��ʾ��Ҫʵ�ֶ��ƹ�����,mode=2��ʾ����Ҫ���ƹ�����,mode=3��ʾ����Ҫ���ƹ�����,����ȥ��portal�����)
	 * @return
	 */
	String toPortal(String cellid, String mode,
			HttpServletRequest request);

}
