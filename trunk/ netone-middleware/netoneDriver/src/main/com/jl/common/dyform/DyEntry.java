package com.jl.common.dyform;

/**
 * ���������
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class DyEntry {

	static DyFormConsoleIfc dy = null;

	public static DyFormConsoleIfc iv() {
		if (dy == null) {
			dy = new DyformConsoleImpl();
		}
		return dy;
	}

	public static void main(String[] args) {

	}
}
