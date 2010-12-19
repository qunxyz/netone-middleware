package oe.cms.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsJppmidware;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.runtime.ruleparser.XHtmlParser;
import oe.frame.orm.OrmerEntry;

public class CmsServiceImpl extends UnicastRemoteObject implements CmsService {

	public CmsServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String fetchJppScript(String cellid) throws RemoteException {
		TCmsInfocell tjm = (TCmsInfocell) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsInfocell.class, cellid);
		return tjm.getBody();
	}

	public String executeJpp(String script) throws RemoteException

	{

		XHtmlParser xmparser = (XHtmlParser) CmsEntry
				.fetchBean(CmsBean._XHTML_PARSER);

		return xmparser.xHtmlParser(script, null);
	}

	public String fetchJppTemplate(String id) throws RemoteException {
		TCmsJppmidware tjm = (TCmsJppmidware) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsJppmidware.class, id);
		return tjm.getJppmidscriptapi();
	}

	public String addpage(String modelid, String name, String naturalname,
			String participant, String width, String height) throws RemoteException {

		boolean usecache = false;

		TCmsInfocell cell = new TCmsInfocell();
		cell.setBelongto(modelid);
		cell.setCellname(name);
		cell.setNaturalname(naturalname);
		cell.setParticipant(participant);
		cell.setWidth(new Long(width));
		cell.setHeight(new Long(height));
		if (usecache) {
			cell.setIntime(CellInfo._IN_TIME);
		}
		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");

		infocellDao.create(cell, CellInfo._JPP_HEAD, "");

		return cell.getCellid();
	}



}
