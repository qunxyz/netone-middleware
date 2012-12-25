package com.jl.common.app;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class DeBuger {
	
	static ResourceBundle rsx=ResourceBundle.getBundle("config",Locale.CHINESE);
	static boolean debug=false;
	static String debuger=",adminx[adminx]";
	

	static{
		try{
			String debugconfig=rsx.getString("debug");
			debug="yes".equals(debugconfig);
			if(debug){
				String debugerconfig=rsx.getString("debuger");
				if(StringUtils.isNotEmpty(debugerconfig)){
					debuger=debugerconfig;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean isDebug(){
		return debug;
	}
	
	public static String getDebuger(){
		return debuger;
	}

}
