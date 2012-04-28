package com.jl.common.map.obj;

import java.util.Map;

/**
 * 地图钻取流程对象
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class Flow {

	String id;
	String description;
	Map<String, Step> step;
	Display display;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Step> getStep() {
		return step;
	}

	public void setStep(Map<String, Step> step) {
		this.step = step;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

}
