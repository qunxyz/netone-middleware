package oe.bi.common;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {
	
	static Log log = LogFactory.getLog(StringUtil.class);
	
	public static String GBKTo8859(String str){
		
		
		if(str != null){
			try {
				return new String(str.getBytes("GBK"),"iso-8859-1");
			} catch (UnsupportedEncodingException e) {
				log.error("",e);
			}
		}
		return null ;
	}
	
	public static String iso8859ToGBK(String str){
		if(str != null){
			try {
				return new String(str.getBytes("iso-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				log.error("",e);
			}
		}
		return null ;
	}
	
	public static String iso8859ToUTF8(String str){
		if(str != null){
			try {
				return new String(str.getBytes("iso-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("",e);
			}
		}
		return null ;
	}
	
	/**
	 * 
	 * @param str
	 * @param index  
	 * @return
	 */
	public static String getThValue(String str, int index) {
		if(str == null){
			return null ;
		}
		int tmp = -1;
		for (int m = 0; m < index; m++) {
			tmp = str.indexOf("[", tmp + 1);
		}
		if (tmp != -1) {
			int i = tmp;
			int j = str.indexOf("]", i);
			return str.substring(i+1, j);
		} else {
			return null;
		}
	}
	
	 /**
	   * ��ȡ�����ļ���·��
	   * @return String
	   */
	  public static String getPropertyValue(String proprertyPath) {
		  
	    String str = "";
	    try {
	      ResourceBundle messages = ResourceBundle.getBundle("datasource",
	          Locale.CHINESE);
	      str = messages.getString(proprertyPath);
	  //   System.out.println("str======="+str);
	      return str;
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	      return "";
	    }

	  }
	  public static void main(String []args){
		
	  }
	
	
}
