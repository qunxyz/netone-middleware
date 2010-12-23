package oe.bi.etl.bus.transformcore;

import oe.bi.etl.obj.MiddleCode;

public class GenerateSQL2 {
	/**
	 * Ö»ÐèÒªSQlÆ¬¶Ï
	 * 
	 * @param midd
	 * @return
	 */
	public static String[] makeSQL(MiddleCode midd) {
		return midd.getDimensionConditions();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
