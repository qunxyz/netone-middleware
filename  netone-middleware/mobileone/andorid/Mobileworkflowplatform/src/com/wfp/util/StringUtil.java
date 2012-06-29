package com.wfp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil {

	// 获取输入流数据

	static byte[] readStream(InputStream inStream) throws Exception {

		byte[] buffer = new byte[1024];

		int len = -1;

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		while ((len = inStream.read(buffer)) != -1) {

			outStream.write(buffer, 0, len);

		}

		byte[] data = outStream.toByteArray();

		outStream.close();

		inStream.close();

		return data;

	}

	public static String decode(String codes) {
		ArrayList<String> code_list = new ArrayList<String>();
		StringBuffer period = new StringBuffer();
		// codes = codes.substring(1);
		codes += '\\';
		for (int i = 0; i < codes.length(); i++) { // 拆分字符
			if (codes.charAt(i) == '\\' && period.length() != 0) {
				code_list.add(period.toString());
				period = new StringBuffer();
			} else {
				period.append(codes.charAt(i));
			}
		}
		StringBuffer result = new StringBuffer();
		for (String code : code_list) { // 逐个转换
			result.append((char) Integer.valueOf(code.substring(1), 16)
					.intValue());
		}
		return result.toString();
	}
	
	//全角转半角
	public static final String SBCchange(String QJstr) { 
		String outStr=""; 
		String Tstr=""; 
		byte[] b=null; 
	
		for(int i=0;i<QJstr.length();i++) { 
			try { 
				Tstr=QJstr.substring(i,i+1); 
				b=Tstr.getBytes("unicode"); 
			} 
			catch(java.io.UnsupportedEncodingException e) { 
				e.printStackTrace(); 
			} 
			if (b[3]==-1) { 
				b[2]=(byte)(b[2]+32); 
				b[3]=0; 
	
				try { 
					outStr=outStr+new String(b,"unicode"); 
				} 
				catch(java.io.UnsupportedEncodingException e) { 
					e.printStackTrace(); 
				} 
			}else 
				outStr=outStr+Tstr; 
		} 

		return outStr; 
	} 
	
	public static ArrayList getFieldName(List targetList){
		ArrayList fieldName = new ArrayList();
		Map map = (HashMap)targetList.get(0);
		Set keyvalues = map.keySet();
		int i = 0;
		for (Iterator iterator2 = keyvalues.iterator() ; iterator2
				.hasNext(); i++) {

			fieldName.add((String) iterator2.next());
		}
		return fieldName;
	}
}
