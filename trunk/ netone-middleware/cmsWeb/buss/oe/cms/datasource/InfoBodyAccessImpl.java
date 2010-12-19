package oe.cms.datasource;

import oe.cms.CmsEntry;
import oe.cms.FileHandler;
import oe.cms.RootPath;

public class InfoBodyAccessImpl implements InfoBodyAccess {

	public String fetchBodyInfo(Long arg0) {
		String path = RootPath.getDatamodelpath();
		FileHandler fh = (FileHandler) CmsEntry.fetchBean("fileHandler");
		String bodyinfo = fh.readFileStr(path + arg0 + ".xhtml");
		bodyinfo = bodyinfo == null ? "" : bodyinfo.trim();
		return bodyinfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
