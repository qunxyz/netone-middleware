package com.jl.common.security3a;

import com.jl.entity.Client;

/**
 * ��ȫ�������
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public final class SecurityEntry {
	public static Security3AIfc se = null;

	public static Security3AIfc iv() {
		if (se == null) {
			se = new Security3AImpl();
		}
		return se;
	}

	public static void main(String[] args) throws Exception {
	}

}
