package oe.midware.dyform.service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.cav.bean.logic.column.ColumnDao;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;
import oe.cav.bean.logic.tools.DyObjFromDatabaseImpl;
import oe.cav.bean.logic.tools.DyObjFromXmlImpl;
import oe.cav.bean.logic.tools.DyObjToXMLImpl;
import oe.cav.bean.logic.tools.XmlPools;
import oe.frame.web.WebCache;

public class DyFormDesignServiceImpl extends UnicastRemoteObject implements
		DyFormDesignService {

	public DyFormDesignServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	static DyObjFromXmlImpl dyformxml = new DyObjFromXmlImpl();

	static DyObjToXMLImpl dyobjtoxml = new DyObjToXMLImpl();

	static DyObjFromDatabaseImpl dyobjformdb = new DyObjFromDatabaseImpl();

	private FormDao formDao;

	private ColumnDao columnDao;

	private void initCache(String formcode) {
		WebCache.removeCache("DYFORMTITLE$_" + formcode);
		WebCache.removeCache("DYFORM$_" + formcode);
		WebCache.removeCache("DYFORMCOLUMN$_" + formcode);
	}

	public String addColumn(TCsColumn column) throws RemoteException {
		
		initCache(column.getFormcode());
		String ext = column.getExtendattribute();
		ext = StringUtils.replace(ext, "#", "%X@");
		column.setExtendattribute(ext);
		return columnDao.create(column);
	}

	public String[] create(TCsForm form, String belongname)
			throws RemoteException {

		formDao.create(form, belongname);
		String tablename = form.getDescription();
		String formcode = form.getFormcode();
		return new String[] { tablename, formcode };
	}

	public String dropColumn(TCsColumn column) throws RemoteException {
		initCache(column.getFormcode());
		return columnDao.drop(column);
	}

	public String updateColumn(TCsColumn column) throws RemoteException {
		initCache(column.getFormcode());
		String ext = column.getExtendattribute();
		ext = StringUtils.replace(ext, "#", "%X@");
		column.setExtendattribute(ext);
		return columnDao.update(column);
	}

	public String updateColumnView(TCsColumn column) throws RemoteException {
		initCache(column.getFormcode());
		return columnDao.updateView(column);
	}

	public DyObj fromDy(String formcode) throws RemoteException {
		String url = XmlPools.fetchXML(formcode).toString();
		return dyformxml.parser(url);
	}

	public String fromDyObj(DyObj obj) throws RemoteException {

		return dyobjtoxml.parser(obj).asXML();
	}

	public DyObj fromSQL(String ds, String table) throws RemoteException {

		return dyobjformdb.parser(ds, table);
	}

	public List listDyObjFromLevel(String level) {
		return formDao.listByLevel(level);
	}

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	public FormDao getFormDao() {
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	public boolean updateForm(TCsForm form) throws RemoteException {
		initCache(form.getFormcode());
		return formDao.update(form);
	}

	public boolean dropForm(TCsForm form) throws RemoteException {
		initCache(form.getFormcode());
		return formDao.drop(form);
	}

	public List queryObjects(TCsForm obj, int from, int to) {
		// TODO Auto-generated method stub
		return formDao.queryObjects(obj, from, to);
	}

	public List queryObjects(TCsForm obj, int from, int to, String conditionPre) {
		// TODO Auto-generated method stub
		return formDao.queryObjects(obj, from, to, conditionPre);
	}

	public List queryObjects(TCsForm obj) {
		// TODO Auto-generated method stub
		return formDao.queryObjects(obj);
	}

	public List queryObjects(TCsForm obj, String conditionPre) {
		// TODO Auto-generated method stub
		return formDao.queryObjects(obj, conditionPre);
	}

	public long queryObjectsNumber(TCsForm obj) {
		// TODO Auto-generated method stub
		return formDao.queryObjectsNumber(obj);
	}

	public long queryObjectsNumber(TCsForm obj, String conditionPre) {
		// TODO Auto-generated method stub
		return formDao.queryObjectsNumber(obj, conditionPre);
	}

	public String getNextColumn(String formcode) throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.getNextColumn(formcode);
	}

	public long getNextIndexValue(String formcode) throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.getNextIndexValue(formcode);
	}

	public boolean movedownColumn(String formcode, String columnid,
			String participant) throws RemoteException {
		initCache(formcode);
		return columnDao.moveupColumn(formcode, columnid, participant);
	}

	public boolean moveupColumn(String formcode, String columnid,
			String participant) throws RemoteException {
		initCache(formcode);
		return columnDao.movedownColumn(formcode, columnid, participant);
	}

	public String[] parseViewType(String type) throws RemoteException {

		return columnDao.parseViewType(type);
	}

	public boolean resizeColumnIndexValue(String formcode, String participant)
			throws RemoteException {
		return columnDao.resizeColumnIndexValue(formcode, participant);
	}

	public TCsColumn loadColumn(String formcode, Serializable key) {

		return columnDao.loadObject(formcode, key);
	}

	public List queryObjects(TCsColumn obj, int from, int to)
			throws RemoteException {

		return columnDao.queryObjects(obj, from, to);
	}

	public List queryObjects(TCsColumn obj, int from, int to,
			String conditionPre) throws RemoteException {

		return columnDao.queryObjects(obj, from, to, conditionPre);
	}

	public List queryObjects(TCsColumn obj) throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.queryObjects(obj);
	}

	public List queryObjects(TCsColumn obj, String conditionPre)
			throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.queryObjects(obj, conditionPre);
	}

	public long queryObjectsNumber(TCsColumn obj) throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.queryObjectsNumber(obj);
	}

	public long queryObjectsNumber(TCsColumn obj, String conditionPre)
			throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.queryObjectsNumber(obj, conditionPre);
	}

	public List queryObjectsUrl(String url) throws RemoteException {
		// TODO Auto-generated method stub
		return columnDao.queryObjectsUrl(url);
	}

	public String formDescription(String formcode) throws RemoteException {
		// TODO Auto-generated method stub
		return formDao.formDescription(formcode);
	}

}
