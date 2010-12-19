package oe.cms.datasource;


public interface WiDivInfoAccess {
	String _FILE_URL = "/fi/downloadAction.do?fileid=fileid";

	String _FILE_URL_PARAM = "fileid";

	String _EXT_KEY_FOFFSET = "foffset";

	String _EXT_KEY_TOFFSET = "toffset";

	String _EXT_KEY_URL = "url";

	/**
	 * 将wiinfo解析成WiDivInfo对象
	 * 
	 * wiinfo的格式如：foffset,100:100;toffset,300:300;url,http://www.sina.com;"
	 * 
	 * @param wiinfo
	 * @return
	 */
	WiDivInfo fetchDivInfo(String wiinfo);

}
