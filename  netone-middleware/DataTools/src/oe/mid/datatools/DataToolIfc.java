package oe.mid.datatools;

import java.util.List;

import oe.mid.datatools.obj.DataTrans;

public interface DataToolIfc {
	/**
	 * 
	 * @param datameta
	 *            ����������ֲ��XML�ĵ�
	 * @return
	 */
	DataTrans parser(String datameta);

	/**
	 * ִ������Ǩ��
	 * 
	 * @param dt
	 *            Ǩ����Ч�ļ�¼����
	 * @return
	 */
	String todo(DataTrans dt,boolean printsql);

}
