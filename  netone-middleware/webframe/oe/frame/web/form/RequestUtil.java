package oe.frame.web.form;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HttpServletRequest 处理表单参数工具类。
 * 
 * @author hls
 * 
 */
public class RequestUtil {

	static Log log = LogFactory.getLog(RequestUtil.class);

	/**
	 * 以Attribute形式返回请求的参数到页面，参数以key,value的形式保存在Map中。
	 * 
	 * @param req
	 * @param paramMap
	 */
	public static RequestParamMap setParamMapToRequest(HttpServletRequest req,
			RequestParamMap paramMap) {
		if(req.getAttribute("paramMap") == null){
			req.setAttribute("paramMap", paramMap);
			return paramMap;
		}
		else{
			RequestParamMap rpm = (RequestParamMap) req.getAttribute("paramMap");
			rpm.putAll(paramMap);
			return rpm;
		}
	}

	/**
	 * 以Attribute形式返回请求的参数到页面，参数以key,value的形式保存在Map中。
	 * 
	 * @param req
	 */
	public static RequestParamMap setParamMapToRequest(HttpServletRequest req) {
		RequestParamMap map = new RequestParamMap(req);
		return setParamMapToRequest(req,map);
	}
	
	
	/**
	 * 将表单的参数(RequestParamMap)映射成对象类， 支持的参数类型包括：
	 * String - 直接使用表单的参数，
	 * 所有的数字及数字对象类型 - 自动转换表单的参数为数字类型，
	 * String[]和Collection集会类 - 使用“，”分割表单参数， 
	 * Map<String,String>集合类 -“key1=value1,ke2=value2”格式的参数
	 * 
	 * @param classname
	 * @param req
	 * @return
	 */
	public static Object mappingReqParam(String classname,
			HttpServletRequest req) {
		RequestParamMap map = new RequestParamMap(req);
		Class objclass;
		try {
			objclass = Class.forName(classname);
			return mappingReqParam(objclass, map);
		} catch (ClassNotFoundException e) {
			log.error("",e);
		}
		return null ;
	}
	
	
	/**
	 * 将表单的参数(RequestParamMap)映射成对象类， 支持的参数类型包括：
	 * String - 直接使用表单的参数，
	 * 所有的数字及数字对象类型 - 自动转换表单的参数为数字类型，
	 * String[]和Collection集会类 - 使用“，”分割表单参数， 
	 * Map集合类 -“key1=value1,ke2=value2”格式的参数
	 * 
	 * @param classname
	 * @param req
	 * @return
	 */
	public static Object mappingReqParam(Class objclass,
			HttpServletRequest req) {
		RequestParamMap map = new RequestParamMap(req);
		return mappingReqParam(objclass, map);
	}

	/**
	 * 将表单的参数(RequestParamMap)映射成对象类， 支持的参数类型包括：
	 * String - 直接使用表单的参数，
	 * 所有的数字及数字对象类型 - 自动转换表单的参数为数字类型，
	 * String[]和Collection集会类 - 使用“，”分割表单参数， 
	 * Map集合类 -“key1=value1,ke2=value2”格式的参数
	 * 
	 * @param objclass
	 * @param paramMap
	 * @return
	 */
	public static Object mappingReqParam(Class objclass,
			RequestParamMap paramMap) {
		if (objclass != null && paramMap != null && paramMap.size() > 0) {
			try {
				HashSet baseClassSet = new HashSet();
				baseClassSet.add(int.class);
				baseClassSet.add(long.class);
				baseClassSet.add(float.class);
				baseClassSet.add(double.class);
				baseClassSet.add(short.class);
				baseClassSet.add(byte.class);
				
				Object obj = objclass.newInstance();
				Method[] methods = objclass.getMethods();
				for (int i = 0; i < methods.length; i++) {
					String mname = methods[i].getName();
					Class[] paramclasses = methods[i].getParameterTypes();
					
					if (mname.startsWith("set") && mname.length() > 3 && paramclasses.length == 1) {
						String fieldname = mname.substring(3, 4).toLowerCase() + mname.substring(4);
						Class paramclass = paramclasses[0];
						String value = paramMap.getParameter(fieldname);
						if(value == null || value.equals("")){
							continue;
						}
						if (paramclass.equals(String.class)) {
							methods[i].invoke(obj, new Object[] { paramMap.getParameter(fieldname) });
						} else if (baseClassSet.contains(paramclass)) {
							try {
								Double d = new Double(value);
								String invokemethod = paramclass.toString()+"Value";
								Method inkm = Double.class.getMethod(invokemethod,new Class[]{});
								Object reobj = inkm.invoke(d,new Object[]{});
								methods[i].invoke(obj, new Object[] { reobj });
							} catch (Exception e) {
								log.error("表单上的参数："+fieldname+"="+value+" 不是数字类型！");
							}
						} else if (Collection.class.isAssignableFrom(paramclass)) {
							String[] valuearr = value.split(",");
							if (paramclass.isInterface()) {
								paramclass = ArrayList.class;
							}
							Collection collection = (Collection) paramclass.newInstance();
							collection.addAll(Arrays.asList(valuearr));
							methods[i].invoke(obj, new Object[] { collection });
						} else if (paramclass.equals(String[].class)) {
							String[] valuearr = value.split(",");
							methods[i].invoke(obj, new Object[] { valuearr });
						} else if (Map.class.isAssignableFrom(paramclass)) {
							String[] valuearr = value.split(",");
							if (paramclass.isInterface()) {
								paramclass = HashMap.class;
							}
							Map map = (Map) paramclass.newInstance();
							for (int m = 0; m < valuearr.length; m++) {
								int index = valuearr[m].indexOf("=");
								if (index != -1) {
									map.put(valuearr[m].substring(0, index),
											valuearr[m].substring(index + 1));
								}
							}
							methods[i].invoke(obj, new Object[] { map });
						} else if(Number.class.isAssignableFrom(paramclass)){
							try {
								if(paramclass.equals(Number.class)){
									paramclass = Double.class;
								}
								Object numobj = paramclass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{value});
								methods[i].invoke(obj, new Object[] { numobj });
							}catch (Exception e) {
								log.error("表单上的参数："+fieldname+"="+value+" 类型不匹配！");
							}	
						} else {
							log.debug("方法：" + mname + "的参数类型：" + paramclass
									+ "无法自动设置值！");
						}
					}
				}

				return obj;
			} catch (Exception e) {
				log.error("表单参数映射到对象出错!", e);
			}
		}

		return null;
	}
	
	
	/**
	 * 将pojo对象的值插入到RequestParamMap中，
	 * 支持String,基本类型(int等),数据对象类型(Integer等),Object[]类型（,分割），
	 * 集合类型（,分割），Map类型（,分割,值为key=value格式）
	 * @param obj
	 * @return
	 */
	public static RequestParamMap loadObjectParam(Object obj){
		return loadObjectParam(null,obj);
	}
	
	
	/**
	 * 将pojo对象的值插入到RequestParamMap中，
	 * 支持String,基本类型(int等),数据对象类型(Integer等),Object[]类型（,分割），
	 * 集合类型（,分割），Map类型（,分割,值为key=value格式）
	 * @param paramMap
	 * @param obj
	 * @return
	 */
	public static RequestParamMap loadObjectParam(RequestParamMap paramMap , Object obj){
		if(paramMap == null){
			paramMap = new RequestParamMap();
		}
		if(obj != null){
			
			try {
				Class cl = obj.getClass();
				HashSet baseClassSet = new HashSet();
				baseClassSet.add(int.class);
				baseClassSet.add(long.class);
				baseClassSet.add(float.class);
				baseClassSet.add(double.class);
				baseClassSet.add(short.class);
				baseClassSet.add(byte.class);
				Method[] methods = cl.getMethods();
				for(int i=0 ;i<methods.length ; i++){
					String mname = methods[i].getName();
					Class reclass = methods[i].getReturnType();
					Class[] paramcl = methods[i].getParameterTypes();
					if(mname.startsWith("get") && !mname.equals("getClass") && mname.length() > 3 && paramcl.length==0 ){
						String fieldname = mname.substring(3,4).toLowerCase()+mname.substring(4);
						Object value = methods[i].invoke(obj,new Object[]{});
						if(value != null){
							if(reclass.equals(String.class) ){
								paramMap.put(fieldname,value.toString());
							}
							else if (baseClassSet.contains(reclass)) {
								paramMap.put(fieldname,value.toString());
							}
							else if (Number.class.isAssignableFrom(reclass)){
								paramMap.put(fieldname,value.toString());
							}
							else if (reclass.equals(Object[].class)){
								Object[] objs = (Object[])value;
								StringBuffer sb = new StringBuffer();
								for(int l=0 ; l<objs.length ; l++){
									if(l!=0){
										sb.append(",");
									}
									sb.append(stringValueOf(objs[i]));
								}
								paramMap.put(fieldname,sb.toString());
							}
							else if (Collection.class.isAssignableFrom(reclass)) {
								Collection c = (Collection)value;
								StringBuffer sb = new StringBuffer();
								Iterator iter = c.iterator();
								while(iter.hasNext()){
									sb.append(stringValueOf(iter.next())+",");
								}
								String str = sb.toString();
								if(str.length()>0){
									str = str.substring(0,str.length()-1);
								}
								paramMap.put(fieldname,str);
							}
							else if( Map.class.isAssignableFrom(reclass)){
								Map map = (Map)value;
								StringBuffer sb = new StringBuffer();
								Iterator iter = map.keySet().iterator();
								while(iter.hasNext()){
									String key = iter.next().toString();
									sb.append(key+"="+stringValueOf(map.get(key))+",");
								}
								String str = sb.toString();
								if(str.length()>0){
									str = str.substring(0,str.length()-1);
								}
								paramMap.put(fieldname,str);
							}
							else {
								log.debug("方法：" + mname + "的返回参数类型：" + reclass
										+ "无法自动添加到RequestParamMap！");
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("对象映射成表单参数出错!", e);
			}
		}
		return paramMap ;
	}
	
	/**
	 * 对象转成字符串，当对象为null时返回"";
	 * @param obj
	 * @return
	 */
    private static String stringValueOf(Object obj) {
    	return (obj == null) ? "" : obj.toString();
    }
    
}

