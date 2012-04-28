package com.jl.common.workflow;

/**
 * 工作流访问入口
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class WfEntry {

	static TWfConsoleIfc wf = null;

	public static TWfConsoleIfc iv() {
		if (wf == null) {
			wf = new TWfConsoleImpl();
		}
		return wf;
	}

	public static void main(String[] args) {

	}
}
