package com.jl.common;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * 
 * oe.fjnusoft.exam.common.NullJsonValueProcessor.java
 * <p>
 * <li></li>
 * </p>
 * <description></description>
 * 
 * @author zhang.chao.yi
 * @version 1.0
 * @date 2009-4-21
 * @histroy 1.0 date 2009-4-21 modified by zhang.chao.yi
 */
public class NullJsonValueProcessor implements JsonValueProcessor {

	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if (arg1 == null) {
			return "";
		}
		return arg1;
	}

}
