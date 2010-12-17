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
 * HttpServletRequest ��������������ࡣ
 * 
 * @author hls
 * 
 */
public class RequestUtil {

	static Log log = LogFactory.getLog(RequestUtil.class);

	/**
	 * ��Attribute��ʽ��������Ĳ�����ҳ�棬������key,value����ʽ������Map�С�
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
	 * ��Attribute��ʽ��������Ĳ�����ҳ�棬������key,value����ʽ������Map�С�
	 * 
	 * @param req
	 */
	public static RequestParamMap setParamMapToRequest(HttpServletRequest req) {
		RequestParamMap map = new RequestParamMap(req);
		return setParamMapToRequest(req,map);
	}
	
	
	/**
	 * �����Ĳ���(RequestParamMap)ӳ��ɶ����࣬ ֧�ֵĲ������Ͱ�����
	 * String - ֱ��ʹ�ñ��Ĳ�����
	 * ���е����ּ����ֶ������� - �Զ�ת�����Ĳ���Ϊ�������ͣ�
	 * String[]��Collection������ - ʹ�á������ָ�������� 
	 * Map<String,String>������ -��key1=value1,ke2=value2����ʽ�Ĳ���
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
	 * �����Ĳ���(RequestParamMap)ӳ��ɶ����࣬ ֧�ֵĲ������Ͱ�����
	 * String - ֱ��ʹ�ñ��Ĳ�����
	 * ���е����ּ����ֶ������� - �Զ�ת�����Ĳ���Ϊ�������ͣ�
	 * String[]��Collection������ - ʹ�á������ָ�������� 
	 * Map������ -��key1=value1,ke2=value2����ʽ�Ĳ���
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
	 * �����Ĳ���(RequestParamMap)ӳ��ɶ����࣬ ֧�ֵĲ������Ͱ�����
	 * String - ֱ��ʹ�ñ��Ĳ�����
	 * ���е����ּ����ֶ������� - �Զ�ת�����Ĳ���Ϊ�������ͣ�
	 * String[]��Collection������ - ʹ�á������ָ�������� 
	 * Map������ -��key1=value1,ke2=value2����ʽ�Ĳ���
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
								log.error("���ϵĲ�����"+fieldname+"="+value+" �����������ͣ�");
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
								log.error("���ϵĲ�����"+fieldname+"="+value+" ���Ͳ�ƥ�䣡");
							}	
						} else {
							log.debug("������" + mname + "�Ĳ������ͣ�" + paramclass
									+ "�޷��Զ�����ֵ��");
						}
					}
				}

				return obj;
			} catch (Exception e) {
				log.error("������ӳ�䵽�������!", e);
			}
		}

		return null;
	}
	
	
	/**
	 * ��pojo�����ֵ���뵽RequestParamMap�У�
	 * ֧��String,��������(int��),���ݶ�������(Integer��),Object[]���ͣ�,�ָ��
	 * �������ͣ�,�ָ��Map���ͣ�,�ָ�,ֵΪkey=value��ʽ��
	 * @param obj
	 * @return
	 */
	public static RequestParamMap loadObjectParam(Object obj){
		return loadObjectParam(null,obj);
	}
	
	
	/**
	 * ��pojo�����ֵ���뵽RequestParamMap�У�
	 * ֧��String,��������(int��),���ݶ�������(Integer��),Object[]���ͣ�,�ָ��
	 * �������ͣ�,�ָ��Map���ͣ�,�ָ�,ֵΪkey=value��ʽ��
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
								log.debug("������" + mname + "�ķ��ز������ͣ�" + reclass
										+ "�޷��Զ���ӵ�RequestParamMap��");
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("����ӳ��ɱ���������!", e);
			}
		}
		return paramMap ;
	}
	
	/**
	 * ����ת���ַ�����������Ϊnullʱ����"";
	 * @param obj
	 * @return
	 */
    private static String stringValueOf(Object obj) {
    	return (obj == null) ? "" : obj.toString();
    }
    
}

