package oe.cms.runtime.core;

import oe.cms.cfg.TCmsInfocell;



/**
 * web–≈œ¢Ω‚Œˆ∆˜
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WiParser {

	String _DIV_ID = "divid";

	String _WIDTH_INFO = "widthinfo";

	String _HEIGHT_INFO = "heightinfo";

	String _LEFT_INFO = "leftinfo";

	String _TOP_INFO = "topinfo";

	String _SRC_INFO = "srcinfo";

	String _DIV_LEFT = "divleft";

	String _DIV_TOP = "divtop";

	String _POTROL_DIV = "<div id=\"divid\" style=\"width:widthinfopx;height:heightinfopx;top:topinfopx;left:leftinfopx; position:static;overflow: hidden;border:1px;\" >"
			+ "<IFRAME src=\"srcinfo\" style=\"width:1024px;height:4000px;\"></IFRAME></div>"
			+ "<script>	document.getElementById(\"divid\").scrollLeft=divleft;document.getElementById(\"divid\").scrollTop=divtop; </script>";

	String performWiParser(String wiinfo);
}
