package oe.bi.etl.bus3;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.datasource.DataChannel;
import oe.bi.etl.bus.Load;
import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.view.obj.ViewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 装载数据
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class LoadImpl4 implements Load {
	private Log _log = LogFactory.getLog(LoadImpl4.class);

	public ViewModel performLoad(AimCode aim,ChoiceInfo info) {

		// 视图模型
		ViewModel viewModel = new ViewModel();
		// 获得分析指标数据
		String[] sql = aim.getSql();

		if (_log.isDebugEnabled()) {
			for (int i = 0; i < sql.length; i++) {
				_log.debug(sql[i]);
			}
		}
		// 重点1：获得指标字段
		String[] targetColumn = aim.getTargetColumnIds();
		// 重点2：获得指标的值
		DataChannel datachannels = (DataChannel) BiEntry
				.fetchBi("datachannels");
		List[] values = datachannels.executeQuery(aim.getDatamodelid(), sql,
				targetColumn);
		List listOtherDim = new ArrayList();
		Map otherDimValue = new HashMap();
		double[][] targetReal = makeTarget(values, targetColumn, listOtherDim,
				otherDimValue);

		viewModel.setTargetvalue(targetReal);

		// 重点3：获得分析维度值
		String[][] dimensionValueRealArr = makeDimension(values, listOtherDim);
		viewModel.setDimensionvalue(dimensionValueRealArr);

		otherSetter(aim, viewModel, listOtherDim, otherDimValue);
		// 调试指标和维度
		if (_log.isDebugEnabled()) {
			for (int i = 0; i < dimensionValueRealArr.length; i++) {
				for (int k = 0; k < targetReal[i].length; k++) {
					System.out.print(targetReal[i][k] + ",");
				}
				for (int j = 0; j < dimensionValueRealArr[i].length; j++)

				{
					System.out.print(dimensionValueRealArr[i][j] +

					",");
				}
				System.out.println();
			}
		}

		return viewModel;
	}

	private void otherSetter(AimCode aim, ViewModel viewModel,
			List listOtherdim, Map listOtherdimValue) {
		List listOtherDimId = new ArrayList();
		List listOtherDimName = new ArrayList();
		List listOtherDimType = new ArrayList();
		List listOtherDimLevel = new ArrayList();
		for (int i = 0; i < aim.getDimensionIds().length; i++) {
			listOtherDimId.add(aim.getDimensionIds()[i]);
			listOtherDimName.add(aim.getDimensionNames()[i]);
			listOtherDimType.add(aim.getDimensionTypes()[i]);
			listOtherDimLevel.add(aim.getDimensionlevel()[i]);
		}
		viewModel.setTargetalarm(aim.getAlarms());

		String[] tarOld = aim.getTargetColumnIds();
		String[] tarNameOld = aim.getTargetColumnnames();
		// String[] tarNew = new String[tarOld.length - 2 -
		// listOtherdim.size()];
		// String[] tarNameNew = new String[tarOld.length - 2
		// - listOtherdim.size()];
		List listTargetId = new ArrayList();
		List listTargetName = new ArrayList();
		Map butinfo = new HashMap();
		for (int i = 2; i < tarOld.length; i++) {
			boolean isTarger = true;
			for (int j = 0; j < listOtherdim.size(); j++) {
				String intPreIndexstr = (String) listOtherdim.get(j);
				int intPreIndex = Integer.parseInt(intPreIndexstr);
				if (intPreIndex == i) {
					// listOtherDimId.add(tarOld[intPreIndex]);
					// listOtherDimName.add(tarNameOld[intPreIndex]);
					// listOtherDimType.add("string");
					// listOtherDimLevel.add("0");
					Map value = (Map) listOtherdimValue.get(intPreIndexstr);
					butinfo.put(tarNameOld[intPreIndex], value);
					isTarger = false;
					break;
				}
			}
			if (isTarger) {
				listTargetId.add(tarOld[i]);
				listTargetName.add(tarNameOld[i]);
			}
		}
		viewModel.setTargetid((String[]) listTargetId.toArray(new String[0]));
		viewModel.setTargetname((String[]) listTargetName
				.toArray(new String[0]));

		viewModel.setDimensionid((String[]) listOtherDimId
				.toArray(new String[0]));
		viewModel.setDimensionname((String[]) listOtherDimName
				.toArray(new String[0]));

		viewModel.setDimensionType((String[]) listOtherDimType
				.toArray(new String[0]));
		viewModel.setDimensionlevel((String[]) listOtherDimLevel
				.toArray(new String[0]));

		viewModel.setOtherDim(butinfo);
	}

	private String[][] makeDimension(List[] target, List listOtherDim) {

		String[][] dimmensioninfo = new String[target[0].size()][2];
		for (int i = 0; i < dimmensioninfo.length; i++) {
			// 获得网元纬度
			dimmensioninfo[i][0] = (String) target[0].get(i);
			// 获得日期纬度
			String timeLong = (String) target[1].get(i);
			String timeStr = timeLong;
			try {
				timeStr = new Timestamp(Long.parseLong

				(timeLong)).toString().substring(0, 13);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dimmensioninfo[i][1] = timeStr;
			// for (int j = 0; j < listOtherDim.size(); j++) {
			// int indexX = Integer.parseInt((String) listOtherDim.get(j));
			// dimmensioninfo[i][2 + j] = (String) target[indexX].get(i);
			// }
		}
		return dimmensioninfo;

	}

	private double[][] makeTarget(List[] target, String[] targetcolumn,
			List listOtherDim, Map otherDimValue) {
		if (target == null || target[0] == null) {
			return new double[0][0];
		}

		int indexFrom = 2;

		int targetColLen = targetcolumn.length;
		int targetRowLen = target[0].size();

		for (int i = 2; i < targetColLen; i++) {
			if (target.length > i) {
				if (target[i].size() > 0) {
					String value = (String) target[i].get(0);
					if (value == null || "".equals(value)) {
						int last = target[i].size();
						if (last > 0) {
							last--;
						}
						value = (String) target[i].get(last);
					}
					if (value != null && !"".equals(value)) {
						double valueCur = getDoubleStr(value);
						if (valueCur == -1) {
							listOtherDim.add(String.valueOf(i));
						}
					}
				}
			}
		}

		double[][] targetvalue = new double[targetRowLen][targetColLen
				- indexFrom - listOtherDim.size()];

		Map[] but = new HashMap[listOtherDim.size()];
		for (int i = 0; i < but.length; i++) {
			but[i] = new HashMap();
		}
		for (int i = 0; i < targetRowLen; i++) {
			int k = 0;
			int butLen = 0;
			for (int j = indexFrom; j < targetColLen; j++) {
				String value = (String) target[j].get(i);
				boolean isTarget = true;
				String key = (String) target[0].get(i)
						+ new Timestamp(Long.parseLong((String) target[1]
								.get(i))).toString().substring(0, 13);
				for (int m = 0; m < listOtherDim.size(); m++) {
					int mValue = Integer.parseInt((String) listOtherDim.get(m));
					if (mValue == j) {
						but[butLen++].put(key, value);
						isTarget = false;
						break;
					}
				}
				if (isTarget) {
					targetvalue[i][k++] = getDoubleStr(value);
				}
			}
		}

		for (int i = 0; i < listOtherDim.size(); i++) {
			otherDimValue.put((String) listOtherDim.get(i), but[i]);
		}
		return targetvalue;
	}

	private static double getDoubleStr(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			System.out.println("发现错误指标数据:" + value);
			return -1; // 返回-1
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
