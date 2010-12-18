package oe.security3a.seucore.resourceser;

public class ToolsExtend {

	public static boolean todo(String dn) {
		long xxx = System.currentTimeMillis();
		long x = xxx % 100000;
		String tmp = "";
		if (dn == null || dn.equals("")) {
			dn = "dept.dept";
		}
		tmp = dn;
		boolean check = false;
		for (int i = 0; i < x; i++) {
			check = !check;
			tmp += i;
		}
		return check;
	}

}
