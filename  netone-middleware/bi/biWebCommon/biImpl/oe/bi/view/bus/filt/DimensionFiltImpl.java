package oe.bi.view.bus.filt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.bus.filt.DimensionFilt;
import oe.bi.view.bus.filt.util.CopyAndSplitTargetViewModel;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ��Ƭ����,��ά�Ƚ�Ϊ1ά
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class DimensionFiltImpl implements DimensionFilt {
	private static Log _log = LogFactory.getLog(DimensionFiltImpl.class);

	public ViewModel filtvalue(ViewModel viewModel, GraphModel graphModel)
			throws MoreThenOneDimensionViewModel {
		// ����1, ���γ���Ƿ���� 3�����ݼ��
		try {
			if (graphModel.getXOffsetDimension() == null) {
				_log.error("XOffsetDimension����Ϊ��..");
				throw new RuntimeException("XOffsetDimension����Ϊ��..");
			}
			Map map = graphModel.getOtherDimension();
			if (map == null) {
				map = new HashMap();
			}
			// �Ƿ��N-1��ά���Ѿ�����ȷ��ֵ
			if ((map.size() + 1) != viewModel.getDimensionid().length) {
				_log.error("N-1��ά��ֵδȫ��ȷ��");
				throw new MoreThenOneDimensionViewModel("N-1��ά��ֵδȫ��ȷ��");
			}
			// ����ViewModel�е���Ϣ����
			ViewModel viewModeClone = new ViewModel();
			CopyAndSplitTargetViewModel.copyAndSplitTargetViewModel(
					viewModeClone, viewModel, graphModel.getChoicetarget());

			// ����2, ��N-1ά����Ϣ����
			String[][] dimensionvalue = viewModeClone.getDimensionvalue();
			double[][] targetvalue = viewModeClone.getTargetvalue();

			String[] dimensionid = viewModeClone.getDimensionid();
			for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
				String keys = (String) itr.next();
				String values = (String) map.get(keys);
				// �ҵ�Key��Indexλ��
				int indexKey = 0;
				for (int i = 0; i < dimensionid.length; i++) {
					if (dimensionid[i].equals(keys)) {
						indexKey = i;
						break;
					}
				}
				// �����к�key��ֵ��һ�µ�����ɾ��,��Ϊdelete
				for (int i = 0; i < dimensionvalue.length; i++) {
					if (!values.equals(dimensionvalue[i][indexKey])) {
						dimensionvalue[i][indexKey] = "delete";
					}
				}
			}
			// ����3���������е�Detele��Ϣ
			List listDimensionAvaible = new ArrayList();
			List listTargetAvaible = new ArrayList();
			for (int i = 0; i < dimensionvalue.length; i++) {
				boolean unDelete = true;
				for (int j = 0; j < dimensionvalue[0].length; j++) {
					if (dimensionvalue[i][j].equals("delete")) {
						unDelete = false;
						break;
					}
				}
				if (unDelete) {

					listDimensionAvaible.add(dimensionvalue[i]);
					listTargetAvaible.add(targetvalue[i]);
				}
			}
			if (dimensionvalue == null || dimensionvalue.length == 0) {
				viewModeClone.setTargetvalue(new double[0][0]);

			} else {
				String[][] reSetDimensionvalue = (String[][]) listDimensionAvaible
						.toArray(new String[0][dimensionvalue[0].length]);

				double[][] reSetTargetvalue = (double[][]) listTargetAvaible
						.toArray(new double[0][targetvalue[0].length]);

				viewModeClone.setDimensionvalue(reSetDimensionvalue);
				viewModeClone.setTargetvalue(reSetTargetvalue);

			}

			Map otherdim = new HashMap();
			for (Iterator itr = viewModel.getOtherDim().keySet().iterator(); itr
					.hasNext();) {
				Object key = itr.next();
				otherdim.put(key, viewModel.getOtherDim().get(key));
			}

			viewModeClone.setOtherDim(otherdim);
			return viewModeClone;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public String[][] allDimensionValueList(ViewModel viewModel) {
		// TODO Auto-generated method stub
		String[][] values = viewModel.getDimensionvalue();
		if (values.length == 0) {
			return null;
		}
		String[][] valueDimension = new String[values.length][values[0].length];
		System.arraycopy(values, 0, valueDimension, 0, values.length);
		String[] dimensionId = viewModel.getDimensionid();
		String[] dimensionName = viewModel.getDimensionname();

		List listValue = new ArrayList();
		for (int i = 0; i < dimensionId.length; i++) {
			StringBuffer buf = new StringBuffer();
			Map checkHave = new HashMap();
			for (int j = 0; j < valueDimension.length; j++) {
				if (!checkHave.containsKey(valueDimension[j][i])) {
					checkHave.put(valueDimension[j][i], valueDimension[j][i]);
					buf.append("," + valueDimension[j][i]);
				}
			}
			String[] preValue = new String[] { dimensionId[i],
					dimensionName[i], buf.substring(1).toString() };
			listValue.add(preValue);
		}
		return (String[][]) listValue.toArray(new String[0][3]);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphModel graphModel = new GraphModel();
		Map map = new Hashtable();
		map.put("key0", "1");
		map.put("key1", "2");
		map.put("key2", "3");
		graphModel.setOtherDimension(map);
		graphModel.setXOffsetDimension("key3");

		ViewModel viewModel = new ViewModel();
		String[] dimensionid = { "key0", "key1", "key2", "key3" };
		viewModel.setDimensionid(dimensionid);
		String[][] values = { { "1", "2", "3", "4" }, { "5", "6", "7", "8" },
				{ "9", "10", "11", "12" }, { "13", "2", "3", "4" } };
		viewModel.setDimensionvalue(values);

		DimensionFiltImpl dfImpl = new DimensionFiltImpl();
		try {
			ViewModel newviewModel = dfImpl.filtvalue(viewModel, graphModel);

			String[][] newValues = newviewModel.getDimensionvalue();
			for (int i = 0; i < newValues.length; i++) {
				for (int j = 0; j < newValues[0].length; j++) {
					System.out.print(newValues[i][j]);
				}
				System.out.println();
			}

		} catch (MoreThenOneDimensionViewModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
