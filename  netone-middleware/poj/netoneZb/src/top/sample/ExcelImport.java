package top.sample;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.bus.res.doc.common.obj.MultiDimData;
import oe.midware.workflow.engine.rule2.func.STools;
import oe.midware.doc.excel.*;

public class ExcelImport {
	
	public static void main(String[] args) throws Exception {
		//需要在soa脚本加入的引入
		//import java.io.*;import oe.frame.bus.res.doc.ExcelHandler;import oe.frame.bus.res.doc.common.obj.MultiDimData;
		//import oe.midware.workflow.engine.rule2.func.STools;import oe.midware.doc.excel.*;
		//该参数是在上传的时候系统自动调度的
		Object []_param={"d:/aaa.xls"};
		
		String particiant="$(participant)";
		
		STools st=new STools();
		InputStream input = new FileInputStream((String)_param[0]);
		Map mapx=st.excel.readExcelAllSheet(input);
		for (Iterator iterator = mapx.keySet().iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			MultiDimData kpp=(MultiDimData)mapx.get(object);
			System.out.println(object+","+kpp.getDataColumnName().size());
		}
	
	}
}
