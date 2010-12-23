package oe.bi.etl.bus.extractcore;

import java.util.Iterator;
import java.util.Map;

import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.Linker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UniformView {
	private static Log _log = LogFactory.getLog(UniformView.class);

	public static String _DAFUALT_LINK_FROM = "1=1";

	public static String _DAFUALT_LINK_TO = "2=2";

	public static String _DAFUALT_TABLE = "TMP998TABLE";

	/**
	 * ����ͳһ��ͼ������ģ���еı��������� from t1,t2....tn where t1.c1=t2.c1 and ...... ����Ϣ
	 * 
	 * @param choice
	 * @return
	 * @throws NullDataSetException
	 * @throws UnableLoadDataModel
	 */
	public static String makeUniformView(DataModel dataModel)
			throws NullDataSetException, UnableLoadDataModel {
		// ���ģ���е����ݼ���
		Map dataSetlist = dataModel.getDataSets();
		// ���ģ���е����ݼ������Ӷ���
		Map linkerList = dataModel.getLinkers();
		// ͳһ��ͼ���ʽ
		StringBuffer bufTable = new StringBuffer();
		for (Iterator itr = dataSetlist.keySet().iterator(); itr.hasNext();) {
			String dataSetKey = (String) itr.next();
			bufTable.append("," + dataSetKey);
			_log.debug("pre dataSet:" + dataSetKey);
		}

		if (bufTable.length() == 0) {
			throw new NullDataSetException();
		}

		StringBuffer bufLinkCondition = new StringBuffer();
		for (Iterator itr = linkerList.keySet().iterator(); itr.hasNext();) {
			String linkKey = (String) itr.next();
			Linker linker = (Linker) linkerList.get(linkKey);
			String preValue = " and " + linker.getFormDataSet() + "="
					+ linker.getToDataSet();
			bufLinkCondition.append(preValue);
			_log.debug("pre Linker:" + preValue);
		}

		bufLinkCondition.append(" and " + _DAFUALT_LINK_TO);

		return " from " + bufTable.toString().substring(1) + " where "
				+ _DAFUALT_LINK_FROM + bufLinkCondition.toString() + " and ";

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
