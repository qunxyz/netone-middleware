package oe.mid.datatools;

import java.util.List;

import oe.mid.datatools.obj.DataTrans;

public interface DataToolIfc {
	/**
	 * 
	 * @param datameta
	 *            描述数据移植的XML文档
	 * @return
	 */
	DataTrans parser(String datameta);

	/**
	 * 执行数据迁移
	 * 
	 * @param dt
	 *            迁移生效的记录个数
	 * @return
	 */
	String todo(DataTrans dt,boolean printsql);

}
