package oe.cms.xhtml2.view.core;

import oe.cms.xhtml.LinkMake;
import oe.cms.xhtml.core.graph.Graph2SeveletAdpet;


public final class GraphInfo2Coordinate {
	/**
	 * À´÷·Õº±Ì¥¶¿Ì
	 * 
	 * @param dimvaluelist
	 * @param dimName
	 * @param targetvaluelistLeft
	 * @param targetvaluelistRight
	 * @param targetnameLeft
	 * @param targetnameLRight
	 * @param title
	 * @param is3D
	 * @param xoffset
	 * @param yoffset
	 * @return
	 */
	public static String fetchGraph2Common(String[] dimvaluelist, String dimName,
			String[][] targetvaluelistLeft, String[][] targetvaluelistRight,
			String[] targetnameLeft, String[] targetnameLRight, String title,
			boolean is3D, String xoffset, String yoffset) {
		String dimvalueListLink = LinkMake.makeLink(dimvaluelist);

		String[] targetValuelinkLeft = new String[targetvaluelistLeft.length];
		for (int i = 0; i < targetValuelinkLeft.length; i++) {
			targetValuelinkLeft[i] = LinkMake.makeLink(targetvaluelistLeft[i]);
		}

		String[] targetValuelinkRight = new String[targetvaluelistRight.length];
		for (int i = 0; i < targetValuelinkRight.length; i++) {
			targetValuelinkRight[i] = LinkMake
					.makeLink(targetvaluelistRight[i]);
		}
		String charType = is3D ? "3D" : "2D";

		return Graph2SeveletAdpet.fetchGraphX(dimvalueListLink,
				targetValuelinkLeft, targetValuelinkRight, dimName,
				targetnameLeft, targetnameLRight, charType, title, xoffset,
				yoffset);
	}

}
