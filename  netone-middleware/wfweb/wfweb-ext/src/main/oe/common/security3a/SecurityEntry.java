package oe.common.security3a;

/**
 * 安全访问入口
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class SecurityEntry {
	public static Security3AIfc se = null;

	public static Security3AIfc iv() {
		if (se == null) {
			se = new Security3AImpl();
		}
		return se;
	}

}
