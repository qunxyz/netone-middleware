package oe.cav.web.data.dyform.utils;

import org.apache.commons.lang.StringUtils;

public class UriMaker {
	static final String _RUI = "/downloadsvl?fileid=$%FILEID&filename=$%FILENAME&formcode=$%FORMCODE";

	public static String makeUri(String rootpath, String formcode,
			String fileid, String filename) {
		if (fileid == null) {
			fileid = "";
		}
		String repOne = StringUtils.replace(_RUI, "$%FILEID", fileid);
		String repTwo = StringUtils.replace(repOne, "$%FILENAME", filename);
		String repTre = StringUtils.replace(repTwo, "$%FORMCODE", formcode);
		return rootpath + repTre;
	}
}
