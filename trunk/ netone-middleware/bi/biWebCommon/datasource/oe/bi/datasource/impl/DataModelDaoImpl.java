package oe.bi.datasource.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.TargetColumn;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.datasource.DataModelDao;
import oe.bi.etl.obj.ChoiceInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.tools.DyObj;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;


/**
 * 
 * @author wang-ting-jie
 * 
 */
public class DataModelDaoImpl implements DataModelDao {

	/**
	 * 
	 * @param dataModelObj
	 *            数据源对象
	 */
	public void create(DataModel dataModelObj) throws NullDataSetException {

	}

	/**
	 * 更新多维业务数据模型
	 * 
	 * @param dataModelObj
	 */
	public void update(DataModel dataModelObj) {

	}

	/**
	 * 删除多维业务数据模型
	 * 
	 * @param dataModelObj
	 */
	public void drop(DataModel dataModelObj) {

	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String[][] listAll(String condtion) {
		throw new RuntimeException();
	}

	/**
	 * 装载
	 * 
	 * @param id
	 * @return
	 */
	public DataModel fetchDataModel(String formcode) throws UnableLoadDataModel {

		DataModel datamodel = new DataModel();
		Map columnMap = new HashMap();
		Map dimMap = new HashMap();

		DyFormDesignService dfd = null;
		try {
			dfd = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DyObj dy = dfd.fromDy(formcode);
			List listColumn = dy.getColumn();
			for (Iterator iter = listColumn.iterator(); iter.hasNext();) {
				TCsColumn element = (TCsColumn) iter.next();

				TargetColumn tcolumn1 = new TargetColumn();
				tcolumn1.setId(element.getColumncode());
				tcolumn1.setName(element.getColumname());
				tcolumn1.setSqltype(element.fetchType());

				DimColumn dimcolumn1 = new DimColumn();
				dimcolumn1.setId(element.getColumncode());
				dimcolumn1.setName(element.getColumname());
				dimcolumn1.setSqltype(element.fetchType());
				dimcolumn1.setTreeModel(TreeModel._DATA_LIST[0][0]);

				columnMap.put(element.getColumncode(), tcolumn1);
				dimMap.put(element.getColumncode(), dimcolumn1);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3
		TargetColumn tcolumn3 = new TargetColumn();
		tcolumn3.setId("start_time");
		tcolumn3.setName("start_time");
		tcolumn3.setSqltype("date");
		DimColumn dimcolumn3 = new DimColumn();
		dimcolumn3.setId("start_time");
		dimcolumn3.setName("start_time");
		dimcolumn3.setSqltype("date");
		dimcolumn3.setTreeModel(TreeModel._TIME_TREE[0][0]);

		columnMap.put("start_time", tcolumn3);
		dimMap.put("start_time", dimcolumn3);
		// 4
		TargetColumn tcolumn4 = new TargetColumn();
		tcolumn4.setId("sys_int_id");
		tcolumn4.setName("sys_int_id");
		tcolumn4.setSqltype("string");
		columnMap.put("sys_int_id", tcolumn4);

		DimColumn dimcolumn4 = new DimColumn();
		dimcolumn4.setId("sys_int_id");
		dimcolumn4.setName("sys_int_id");
		dimcolumn4.setSqltype("string");
		dimcolumn4.setTreeModel(TreeModel._DATA_LIST[0][0]);

		columnMap.put("sys_int_id", tcolumn4);
		dimMap.put("sys_int_id", dimcolumn4);

		datamodel.setTargetColumns(columnMap);
		datamodel.setDimColumns(dimMap);

		return datamodel;
	}

}
