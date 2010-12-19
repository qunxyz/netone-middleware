package oe.cms.xhtml;

public class LinkMake {

	public static String makeLink(String[] link) {
		if (link == null || link.length == 0) {
			return "0";
		}
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < link.length; i++) {
			but.append("," + link[i]);
		}
		return but.toString().substring(1);
	}

	public static String[] makeLink(String[][] link) {
		if (link == null || link.length == 0) {
			return new String[0];
		}
		String[] linkAll = new String[link.length];
		for (int i = 0; i < link.length; i++) {
			linkAll[i] = makeLink(link[i]);
		}
		return linkAll;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
