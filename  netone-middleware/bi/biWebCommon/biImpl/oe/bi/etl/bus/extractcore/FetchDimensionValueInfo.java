package oe.bi.etl.bus.extractcore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.TimeTree;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.ext.SqlTypes;
import oe.bi.etl.bus.arithmetic.NDimension;
import oe.bi.etl.obj.DimensionElement;


public class FetchDimensionValueInfo {
	/**
	 * 创建维度字段值
	 * 
	 * @param dimensionElement
	 * @return
	 */
	public static Map dimenstionColumnValueName(List dimensionElement,
			DataModel dataModel) {
		Map value = new HashMap();
		if (dimensionElement == null || dimensionElement.size() == 0) {
			return value;
		}
		Map mapDim = dataModel.getDimColumns();
		
		int maxLength = 0;
		List listColumnValue = new ArrayList();
		List listColumnValueName = new ArrayList();

		for (Iterator itr = dimensionElement.iterator(); itr.hasNext();) {
			DimensionElement elem = (DimensionElement) itr.next();
			DimColumn dimColumn = (DimColumn) mapDim.get(elem.getId());
			String[] choiceNoteName = null;
			String[] choiceNoteValue = null;
			if (dimColumn.getSqltype().equals(SqlTypes._DIM_TYPE_DATE[0])) {
				String[] ids = elem.getChoicenode();// 时间类型是用ID做名字
				choiceNoteName = new String[ids.length];
				System.arraycopy(ids, 0, choiceNoteName, 0, ids.length);
				if (choiceNoteName[0] != null
						&& choiceNoteName[0].matches(TimeTree._RULE_HOUR)) {
					for (int i = 0; i < choiceNoteName.length; i++) {
						choiceNoteName[i] = choiceNoteName[i] + ":00:00";
					}
				}
				if (choiceNoteName[0] != null
						&& choiceNoteName[0].matches(TimeTree._RULE_MIN)) {
					for (int i = 0; i < choiceNoteName.length; i++) {
						choiceNoteName[i] = choiceNoteName[i] + ":00";
					}
				}

				Arrays.sort(choiceNoteName);
				choiceNoteValue = choiceNoteName;
			} else {
				choiceNoteName = elem.getChoicenodename();
				choiceNoteValue = elem.getChoicenode();
			}

			listColumnValueName.add(choiceNoteName);
			listColumnValue.add(choiceNoteValue);

			if (choiceNoteName.length > maxLength) {
				maxLength = choiceNoteName.length;
			}
		}
		String[][] dimensionvalue = (String[][]) listColumnValue
				.toArray(new String[0][maxLength]);
		String[][] dimensionvalueName = (String[][]) listColumnValueName
				.toArray(new String[0][maxLength]);
		// 获得N维度空间构建器
		NDimension nDimensionCondition = (NDimension) BiEntry
				.fetchBi("nDimension");
		String[] dimValue = nDimensionCondition.nDimension(dimensionvalue, ",");
		String[] dimValueName = nDimensionCondition.nDimension(
				dimensionvalueName, ",");
		value.put("dimV", dimValue);
		value.put("dimVN", dimValueName);
		return value;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
