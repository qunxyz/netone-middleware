package oe.bi.etl.bus.extractcore.util;

import oe.bi.BiEntry;
import oe.bi.TimeColumnAdepter;
import oe.bi.dataModel.obj.ext.SqlTypes;


public class MakeInExpression {

	public static String makeInExpression(String[] incondition, String dimId,
			String dimType) {
		if (incondition == null || incondition.length == 0) {
			return "";
		}
		TimeColumnAdepter timeColumnAdepter = (TimeColumnAdepter) BiEntry
				.fetchBi("timeColumnAdepter");
		if (!SqlTypes._DIM_TYPE_DATE[0].equals(dimType)) {
			StringBuffer buf = new StringBuffer();

			for (int i = 0; i < incondition.length; i++) {
				String valueReal = timeColumnAdepter
						.adaptStringNumberDimensionTypes(incondition[i],
								dimType);
				buf.append(valueReal);
			}
			// String newDimId = DimensionColumnModify.modifyColumn(dimId,
			// dimType,
			// incondition[0]);
			return dimId + " in(" + buf.substring(1) + ")";
		} else {
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < incondition.length; i++) {
				String valueReal = timeColumnAdepter.adaptDateDimensionTypes(
						incondition[i], dimId);
				buf.append(" and " + valueReal);
			}
			// String newDimId = DimensionColumnModify.modifyColumn(dimId,
			// dimType,
			// incondition[0]);
			return buf.substring(5);

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
