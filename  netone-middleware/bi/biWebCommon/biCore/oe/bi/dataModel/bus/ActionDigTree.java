package oe.bi.dataModel.bus;

import oe.bi.exceptions.TreeModelException;
import oe.bi.view.obj.GraphModel;


public interface ActionDigTree {

	String _ROOT_NODE = "root";

	/**
	 * ���ݽڵ㶯̬չ��������WEB�ϵ����νṹ����ʹ��
	 * 
	 * @param datamodelId
	 * @param dimcolumnId
	 * @param nodeid
	 * @return
	 */
	String fetchTreeElement(String datamodelId, String dimcolumnId,
			String nodeid, String levels) throws TreeModelException;

	/**
	 * ���ݽڵ㶯̬չ����������Ԥ�������ѡ��ڵ�ʹ��
	 * 
	 * @param datamodelId
	 * @param dimcolumnId
	 * @param nodeid
	 * @return
	 */
	String fetchTreeElement(String datamodelId, String dimcolumnId,
			String dimensionlevle, String nodeid, String levels,
			GraphModel graphModel) throws TreeModelException;
}