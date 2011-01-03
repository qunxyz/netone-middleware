package oe.product.fck.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 提取fck中的内容，并过滤字符
 *
 * Apr 19, 2009  10:52:09 AM<br>
 *
 * @author wu.shang.zhan<br>
 */
public class REGUtil {
	
	
	
	
	public  String todo(UmsProtectedobject upo){
		String  textContent="";
			String htmlContent = upo.getExtendattribute();
			textContent = Html2Text(htmlContent);
			textContent = StringUtils.substring(textContent, 0, 64);
			
		return textContent;
	}
	
	public static String filterHtml(String str) {

	    if (StringUtils.isEmpty(str)) {
	      return "";
	    }

	    Pattern pattern = Pattern.compile("<[^<|>]*>");
	    Pattern pattern1 = Pattern.compile("&nbsp;");
	    Matcher matcher = pattern.matcher(str);

	    String returnStr = matcher.replaceAll("");

	    Matcher matcher1 = pattern.matcher(returnStr);
	    return returnStr;
	  }

	  
	  

	/**
	 * @param args
	 */
	
	public static String Html2Text(String inputString) {
	    String htmlStr = inputString; //含html标签的字符串
	    String textStr = "";
	    java.util.regex.Pattern p_script;
	    java.util.regex.Matcher m_script;
	    java.util.regex.Pattern p_style;
	    java.util.regex.Matcher m_style;
	    java.util.regex.Pattern p_html;
	    java.util.regex.Matcher m_html;
	    java.util.regex.Pattern p_other;
	    java.util.regex.Matcher m_other;

	    try {
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	                                                                                                // }
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	                                                                                            // }
	        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
	        
	        String regEx_other = "&[a-z]+;";//一些特殊字符处理，主要是&开头;结尾

	        p_script = java.util.regex.Pattern.compile(regEx_script,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); //过滤script标签

	        p_style = java.util.regex.Pattern.compile(regEx_style,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); //过滤style标签

	        p_html = java.util.regex.Pattern.compile(regEx_html,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); //过滤html标签
	        
	        p_other = java.util.regex.Pattern.compile(regEx_other,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_other = p_other.matcher(htmlStr);
	        htmlStr = m_other.replaceAll(""); //过滤特殊字符标签

	        textStr = htmlStr;
	        
	        try{
	            byte[] bytes = textStr.getBytes("GBK");
	            for(int i = 0;i < bytes.length;i++) {
	                if (bytes[i] > 0 && bytes[i] < 32) 
	                    bytes[i] = 32;
	            }
	            textStr = new String(bytes,"GBK");
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	        }

	    } catch (Exception e) {
	        System.err.println("Html2Text: " + e.getMessage());
	    }
	    
	    textStr = textStr.replaceAll(" ","").replaceAll("　","").replaceAll("，",",").replaceAll("。",".");
	    textStr = StringUtils.substring(textStr, 0, 32);
	    return textStr;//返回文本字符串
	   } 

	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String inputString="";
		ResourceRmi rsrmi =  (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject up0 = rsrmi.loadResourceById("402882d9206b59cd01207e963f560007");
		System.out.println(up0.getExtendattribute());
		inputString = up0.getExtendattribute();
		
//		String html = "<h5><span style=\"FONT-SIZE: 9pt; COLOR: black; FONT-FAMILY: 宋体; mso-bidi-font-size: 12.0pt; mso-ascii-font-family: ; mso-hansi-font-family: \">超前静音操作&nbsp; 娱乐玩家酷鼠 时尚随意翻转门&nbsp; 健康环保机箱 强力直冷风扇&nbsp; 幻影LCD状态窗<br />镜面黑钢琴烤漆&nbsp; 冷酷蓝色光剑&nbsp; 震撼钢盔M-BOX&nbsp; 弧形飞翼设计&nbsp; 六键快速启动 内置键盘鼠标<br />H1:高清显示 H2:高响应 H3:高寿命</span></h5>";
//	    String str = filterHtml(html);
//	    System.out.println(str);
//	    
	    String str2 = Html2Text(inputString);
	    System.out.println(str2);
//		Regex.IsMatch("中文","^[\u4e00-\u9fa5]+$");
//		String patternString="中文字符串"; 
//		Pattern a=Pattern.compile("^[\u4e00-\u9fa5]+$"); 
//		Pattern a=Pattern.compile("[\u4e00-\u9fa5]+$"); 
//		Matcher b=a.matcher(patternString); 
//		        if(b.matches()) 
//		        { 
//		            System.out.println("is chinese"); 
//		        } else { 
//		        System.out.println("is NOT chinese"); 
//		} 
//		System.out.println("开始提取----------------------------------------------------------------");
//		Html2Text(inputString);

	}

}
