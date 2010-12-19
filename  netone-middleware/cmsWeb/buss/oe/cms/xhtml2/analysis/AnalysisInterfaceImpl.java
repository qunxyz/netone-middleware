package oe.cms.xhtml2.analysis;

import oe.cms.xhtml2.AnalysisInterface;

public final class AnalysisInterfaceImpl implements AnalysisInterface {

	public String[][] addDim(String[][] arg0, String[][] arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[][] adpetTargetDim(String[][] arg0, String[][] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[][] circleRelationCoordinate(String[][] info) {
		if (info.length < 1 || info[0] == null) {
			return null;
		}
		String[][] newInfo = new String[info[0].length][info.length];
		for (int i = 0; i < info.length; i++) {
			for (int j = 0; j < info[0].length; j++) {
				newInfo[j][i] = info[i][j];
			}
		}
		return newInfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
