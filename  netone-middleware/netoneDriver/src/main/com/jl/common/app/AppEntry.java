package com.jl.common.app;

/**
 * Ӧ�ÿ�ܷ������
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class AppEntry {

	static AppHandleIfc app = null;
	static AppHandleIfc app2 = null;

	public static AppHandleIfc iv() {
		if (app == null) {
			app = new AppHandleImpl();
		}
		return app;
	}
	
	public static AppHandleIfc iv2() {
		if (app2 == null) {
			app2 = new AppHandleImpl2();
		}
		return app2;
	}

	public static void main(String[] args) throws Exception {
//		AppObj appo = AppEntry.iv().loadApp("APPFRAME.APPFRAME.MYAPP");
//		String[] dyurl = AppEntry.iv().dyCreateView(appo, "����Ա[adminx]");
//		System.out.println(dyurl[1]);

	}
}
