package com.jl.common.resource;

import oe.security3a.client.rmi.ResourceRmi;

/**
 * ��Դ�������
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class RsEntry {

	static ResourceIfc rs = null;

	public static ResourceIfc iv() {
		if (rs == null) {
			rs = new ResourceImpl();
		}
		return rs;
	}

	public static void main(String[] args) {

	}
}
