package com.jl.common.workflow.worklist;

/**
 * worklist�������
 * 
 * 
 */
public final class WlEntry {

	static WorklistViewIfc wl = null;

	public static WorklistViewIfc iv() {
		if (wl == null) {
			wl = new WorklistVIewImpl();
		}
		return wl;
	}

}
