package oe.mid.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import oe.frame.bus.res.doc.common.XmlObj;
import oe.midware.doc.excel.ExcelUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class PoiSample {
	
	public static void main(String[] args) throws Exception {
		File file = new File("d:/MyHtml.xls");
		InputStream input = new FileInputStream(file);
		 byte []bytearr=new byte[input.available()];
		 input.read(bytearr);
		 String info=new String(bytearr,"utf-8");
		 input.close();
		 info= StringUtils.replace(info, "$(name)", "mike");
		 String loop= StringUtils.substringBetween(info, "$(loop-)", "$(-loop)");
		 
		 StringBuffer but=new StringBuffer();
		 for(int i=0;i<10;i++){
			 String loopEach=loop;
			 loopEach=StringUtils.replace(loopEach, "$(loop.key1)", "10"+i);
			 loopEach=StringUtils.replace(loopEach, "$(loop.key2)", "10"+i+i);
			 loopEach=StringUtils.replace(loopEach, "$(loop.key3)", "10"+i+i+i+i);
			 
			 but.append(loopEach);
		 }
		 info=StringUtils.replace(info, "$(loop-)"+loop+"$(-loop)", but.toString());
		 OutputStream out=new FileOutputStream("d:\\test.xls");
		 out.write(info.getBytes());
		 out.close();
		
	}

}
