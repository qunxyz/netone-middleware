package top.sample;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import oe.frame.bus.res.doc.common.obj.MultiDimData;
import oe.midware.workflow.engine.rule2.func.STools;


public class ExcelImport {
	
	public static void main(String[] args) throws Exception {
		//��Ҫ��soa�ű����������
		//import java.io.*;import oe.frame.bus.res.doc.ExcelHandler;import oe.frame.bus.res.doc.common.obj.MultiDimData;
		//import oe.midware.workflow.engine.rule2.func.STools;import oe.midware.doc.excel.*;
		
		//�ò��������ϴ���ʱ��ϵͳ�Զ����ȵ�,SOA�ű��в�Ҫ���
		Object []_param={"d:/aaa.xls"};
		
		String particiant="$(participant)";
		String parentid="$(parentid)";
		if("1".equals(parentid)||StringUtils.isEmpty(parentid)){
			//˵���ǵ�����
		}else{
			//���ӱ�
		}
		
		STools st=new STools();
		InputStream input = new FileInputStream((String)_param[0]);
		Map mapx=st.excel.readExcelAllSheet(input);
		for (Iterator iterator = mapx.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			MultiDimData kpp=(MultiDimData)mapx.get(key);
			System.out.println(key+","+kpp.getDataColumnName().size());
		}
	
	}
}
