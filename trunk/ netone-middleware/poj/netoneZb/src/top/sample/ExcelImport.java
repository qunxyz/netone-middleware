package top.sample;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.bus.res.doc.common.obj.MultiDimData;
import oe.midware.doc.excel.ExcelHandlerImp;

import org.apache.commons.lang.StringUtils;


public class ExcelImport {
	
	public static void main(String[] args) throws Exception {
		//需要在soa脚本加入的引入
		//import java.io.*;import oe.frame.bus.res.doc.ExcelHandler;import oe.frame.bus.res.doc.common.obj.MultiDimData;
		//import oe.midware.workflow.engine.rule2.func.STools;import oe.midware.doc.excel.*;
		
		//该参数是在上传的时候系统自动调度的,SOA脚本中不要这句
		Object []_param={"d:/黄金模板.xls"};
		
		String particiant="$(participant)";
		String parentid="$(parentid)";
		if("1".equals(parentid)||parentid==null){
			//说明是导主表单
		}else{
			//导子表单
		}
		
		ExcelHandler excel = new ExcelHandlerImp();
		InputStream input = new FileInputStream((String)_param[0]);
		Map mapx=excel.readExcelAllSheet(input);
		for (Iterator iterator = mapx.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			MultiDimData kpp=(MultiDimData)mapx.get(key);
			System.out.println(key+","+kpp.getDataColumnName().size());
		}
	
		///TODO 
	}
}
