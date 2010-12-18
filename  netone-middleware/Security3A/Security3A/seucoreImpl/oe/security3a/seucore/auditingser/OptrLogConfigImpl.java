package oe.security3a.seucore.auditingser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.auditingser.OptrLogConfigDao;


public class OptrLogConfigImpl implements OptrLogConfigDao{

private Ormer ormer = OrmerEntry.fetchOrmer("ds_config_log.xml");
	
	public Object loadObject(Class objClass, Serializable key) {
		return ormer.fetchQuerister().loadObject(objClass, key);
	}

	public List queryObjects(Object obj, Map comparisonKey) {
		return ormer.fetchQuerister().queryObjects(obj, comparisonKey);
	}

	public List queryObjects(Object obj, Map comparisonKey, String conditionPre) {
		return ormer.fetchQuerister().queryObjects(obj, comparisonKey, conditionPre);
	}

	public List queryObjects(Object obj, Map comparisonKey, int from, int to) {
		return ormer.fetchQuerister().queryObjects(obj, comparisonKey, from, to);
	}

	public List queryObjects(Object obj, Map comparisonKey, int from, int to, String conditionPre) {
		return ormer.fetchQuerister().queryObjects(obj, comparisonKey, from, to , conditionPre);
	}

	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		return ormer.fetchQuerister().queryObjectsNumber(obj, comparisonKey);
	}

	public long queryObjectsNumber(Object obj, Map comparisonKey, String conditionPre) {
		return ormer.fetchQuerister().queryObjectsNumber(obj, comparisonKey, conditionPre);
	}

	public boolean checkSecrity(Object obj) {
		return ormer.fetchSerializer().checkSecrity(obj);
	}

	public boolean create(Object obj) {
		return ormer.fetchSerializer().create(obj);
	}

	public boolean creates(List objs) {
		return ormer.fetchSerializer().creates(objs);
	}

	public boolean drop(Object obj) {
		return ormer.fetchSerializer().drop(obj);
	}

	public boolean drops(List objs) {
		return ormer.fetchSerializer().drop(objs);
	}

	public void refreshObject(Object objClass, Serializable key) {
		ormer.fetchSerializer().refreshObject(objClass, key);
	}

	public boolean serial(Object obj) {
		return ormer.fetchSerializer().serial(obj);
	}

	public boolean update(Object obj) {
		return ormer.fetchSerializer().update(obj);
	}

	public boolean updates(List objs) {
		return ormer.fetchSerializer().updates(objs);
	}
	

}
