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
 * ��ȡfck�е����ݣ��������ַ�
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
	    String htmlStr = inputString; //��html��ǩ���ַ���
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
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script>
	                                                                                                // }
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style>
	                                                                                            // }
	        String regEx_html = "<[^>]+>"; //����HTML��ǩ��������ʽ
	        
	        String regEx_other = "&[a-z]+;";//һЩ�����ַ�������Ҫ��&��ͷ;��β

	        p_script = java.util.regex.Pattern.compile(regEx_script,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); //����script��ǩ

	        p_style = java.util.regex.Pattern.compile(regEx_style,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); //����style��ǩ

	        p_html = java.util.regex.Pattern.compile(regEx_html,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); //����html��ǩ
	        
	        p_other = java.util.regex.Pattern.compile(regEx_other,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_other = p_other.matcher(htmlStr);
	        htmlStr = m_other.replaceAll(""); //���������ַ���ǩ

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
	    
	    textStr = textStr.replaceAll(" ","").replaceAll("��","").replaceAll("��",",").replaceAll("��",".");
	    textStr = StringUtils.substring(textStr, 0, 32);
	    return textStr;//�����ı��ַ���
	   } 

	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String inputString="";
		ResourceRmi rsrmi =  (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject up0 = rsrmi.loadResourceById("402882d9206b59cd01207e963f560007");
		System.out.println(up0.getExtendattribute());
		inputString = up0.getExtendattribute();
		
//		String html = "<h5><span style=\"FONT-SIZE: 9pt; COLOR: black; FONT-FAMILY: ����; mso-bidi-font-size: 12.0pt; mso-ascii-font-family: ; mso-hansi-font-family: \">��ǰ��������&nbsp; ������ҿ��� ʱ�����ⷭת��&nbsp; ������������ ǿ��ֱ�����&nbsp; ��ӰLCD״̬��<br />����ڸ��ٿ���&nbsp; �����ɫ�⽣&nbsp; �𺳸ֿ�M-BOX&nbsp; ���η������&nbsp; ������������ ���ü������<br />H1:������ʾ H2:����Ӧ H3:������</span></h5>";
//	    String str = filterHtml(html);
//	    System.out.println(str);
//	    
	    String str2 = Html2Text(inputString);
	    System.out.println(str2);
//		Regex.IsMatch("����","^[\u4e00-\u9fa5]+$");
//		String patternString="�����ַ���"; 
//		Pattern a=Pattern.compile("^[\u4e00-\u9fa5]+$"); 
//		Pattern a=Pattern.compile("[\u4e00-\u9fa5]+$"); 
//		Matcher b=a.matcher(patternString); 
//		        if(b.matches()) 
//		        { 
//		            System.out.println("is chinese"); 
//		        } else { 
//		        System.out.println("is NOT chinese"); 
//		} 
//		System.out.println("��ʼ��ȡ----------------------------------------------------------------");
//		Html2Text(inputString);

	}

}
