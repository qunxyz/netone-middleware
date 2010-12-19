package oe.cms.cfg;

public class CellInfo {

	public static final String _TYPE_PORTAL = "1";

	public static final String _TYPE_PORTAL_WITHOUT_TITLE = "2";

	public static final String _TYPE_PORTAL_WITHOUT_BORDER = "3";

	public static final String _TYPE_PORTAL_WITHOUT_TITLE_AND_BORDER = "4";

	public static final String _IN_TIME = "1";

	public static final String _IN_DESIGN_TIME = "2";

	public static final String _PIC_AHEAD = "PIC";

	public static final String _FILE_AHEAD = "FIL";

	public static final String _ARTICLE_AHEAD = "ARI";

	public static final String _JPP_HEAD = "JPP";

	public static final String _TEMPLATE_HEAD = "TPL";

	public static final String _CUT_HEAD = "CUT";

	public static final String _BLK_HEAD = "BLK";

	public static final String _URL_HEAD = "URL";

	static public String _TYPE_PUBLIC = "1";

	static public String _TYPE_PRIVATE = "0";

	private String id;

	private String infoCellid;

	private String xoffset;

	private String yoffset;

	private String extendattribute;

	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInfoCellid() {
		return infoCellid;
	}

	public void setInfoCellid(String infoCellid) {
		this.infoCellid = infoCellid;
	}

	public String getXoffset() {
		return xoffset;
	}

	public void setXoffset(String xoffset) {
		this.xoffset = xoffset;
	}

	public String getYoffset() {
		return yoffset;
	}

	public void setYoffset(String yoffset) {
		this.yoffset = yoffset;
	}

}
