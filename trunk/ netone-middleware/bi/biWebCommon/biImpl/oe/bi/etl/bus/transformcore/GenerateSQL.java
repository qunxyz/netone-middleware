package oe.bi.etl.bus.transformcore;

import oe.bi.etl.obj.MiddleCode;

public class GenerateSQL {

	public static String[] makeSQL(MiddleCode midd) {
		String[] dimensionCondition = midd.getDimensionConditions();
		String[] sqlGroup = new String[dimensionCondition.length];
		String uniformview = midd.getUniformview();
		String target = midd.getTargetColumnLink();
		for (int i = 0; i < dimensionCondition.length; i++) {
			sqlGroup[i] = "select " + target + uniformview
					+ dimensionCondition[i];
		}
		return sqlGroup;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
