/**
 * 类说明 数据集和数据总条数 转换成数据jsonStore 可以解析的数据格式
 * @author 丘桂昌 
 * @version  1.0 
   @time 创建时间：2009-03-18
 * 
 */

package com.jl.common;

public class StoreUtil {
   
	private static final String RESULT = "total"; //标示数据总记录数
	
	private static final String ROWS = "rows";//标示数据源
	
	public StoreUtil(){}
	
	/**
	 * 
	 * @param result 数据中记录条数
	 * @param json  List数据转换成json数据字符串
	 * @return
	 */
	public static String StoreString(int result,String json){
		StringBuilder store = new StringBuilder();
		store.append("{");
		store.append(RESULT);
		store.append(" : ");
		store.append(result);
		store.append(",");
		store.append(ROWS);
		store.append(":");
		store.append(json);
		store.append("}");
		return store.toString();
	}
	
	
}
