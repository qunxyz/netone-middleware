package oe.mid.web.rspage.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.mid.io.CommonIoTools;

import org.apache.commons.lang.StringUtils;

public class WriteIoInfo {

	private static ResourceBundle webrs = ResourceBundle.getBundle("resource",
			Locale.CHINESE);

	public static void todo(String objecttype, Serializable idcreated,
			String extendattribute, String realpath, boolean security) {
		String path = webrs.getString("BinarySavePath");
		String contextpath = webrs.getString("BinarySaveContextPath");
		if (contextpath == null || contextpath.equals("")) {
			contextpath = realpath;
		}
		// Binary�Ĵ洢·��
		String rspath = contextpath + path;
		String objType1 = StringUtils.substringAfterLast(objecttype, ".");
		objType1 = StringUtils.substringBefore(objType1, "]");
		String filefullname = null;
		String filefullnameOld=null;
		if (!security) {// �����Ҫ��ȫ�Ļ�����ô�Զ�����ǰ����� _seu9 ������Զ�����ȫ�Ĺ�����ʶ��
			filefullname = rspath + "_seu9" + idcreated + "."
					+ objType1.toLowerCase();
			filefullnameOld=filefullname = rspath + idcreated + "." + objType1.toLowerCase();
		} else {
			filefullname = rspath + idcreated + "." + objType1.toLowerCase();
			filefullnameOld=rspath + "_seu9" + idcreated + "."
			+ objType1.toLowerCase();
		}

		File file = new File(filefullname);
		File fileOld=new File(filefullnameOld);
		try {
			if(fileOld.exists()){
				fileOld.delete();
			}
			
			if(file.exists()){
				file.deleteOnExit();
			}
			
			file.createNewFile();
			OutputStream out = new FileOutputStream(file);
			InputStream input = new ByteArrayInputStream(extendattribute
					.getBytes());
			CommonIoTools.inputDo(input, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
