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
		//��Ҫ��soa�ű����������
		//import java.io.*;import oe.frame.bus.res.doc.ExcelHandler;import oe.frame.bus.res.doc.common.obj.MultiDimData;
		//import oe.midware.workflow.engine.rule2.func.STools;import oe.midware.doc.excel.*;
		
		//�ò��������ϴ���ʱ��ϵͳ�Զ����ȵ�,SOA�ű��в�Ҫ���
		Object []_param={"d:/�ƽ�ģ��.xls"};
		
		String particiant="$(participant)";
		String parentid="$(parentid)";
		if("1".equals(parentid)||parentid==null){
			//˵���ǵ�����
		}else{
			//���ӱ�
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
