package oe.midware.workflow.track.mode;

public class XOffsetTool {

	public static String xoffsetResize(String xoffset) {
		try {
			int x = Integer.parseInt(xoffset);
			int xreal = x - 200;
			if (xreal > 0) {
				return String.valueOf(xreal);
			} else {
				return "10";
			}
		} catch (Exception e) {
			return xoffset;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
