package com.jl.common.map.obj;

import java.util.Map;

/**
 * 地图中用到的展示图层和标识点
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class Display {

	Map<String, Pic> pic;

	Map<String, Mark> Mark;

	public Map<String, Pic> getPic() {
		return pic;
	}

	public void setPic(Map<String, Pic> pic) {
		this.pic = pic;
	}

	public Map<String, Mark> getMark() {
		return Mark;
	}

	public void setMark(Map<String, Mark> mark) {
		Mark = mark;
	}

}
