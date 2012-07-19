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
		//��Ҫ��soa�ű����������
		//import java.io.*;import oe.frame.bus.res.doc.ExcelHandler;import oe.frame.bus.res.doc.common.obj.MultiDimData;
		//import oe.midware.workflow.engine.rule2.func.STools;import oe.midware.doc.excel.*;
		//�ò��������ϴ���ʱ��ϵͳ�Զ����ȵ�
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
