package com.jl.common.dyform;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.derby.tools.sysinfo;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.jl.common.ScriptTools;
import com.jl.common.workflow.DbTools;
import com.jl.common.workflow.WfEntry;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class DyAnalysisXml {
	// �ű���ʹ��
	public List  script2(String formid, List param)throws Exception {
		String mxlstr = null;
		DyAnalysisXml dyxml = new DyAnalysisXml();
		String xml = dyxml.XmlDate(formid);
		boolean falconfig = dyxml.isExists(xml, "config");
		if (falconfig) {
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println("xml��������");
				e.printStackTrace();
			}
			Element rootElt = doc.getRootElement();
			Iterator iter = rootElt.elementIterator("config");
			while (iter.hasNext()) {
				Element configEle = (Element) iter.next();
				mxlstr = configEle.asXML();
			}
			if (!mxlstr.equals("") || mxlstr != null) {
				String script = dyxml.readXML(mxlstr, "OnList");

               if(StringUtils.isNotEmpty(script)){
            	   Object []obj={param};
				return (List)ScriptTools.todo(script,obj);
               }
			}
		}
		return param;
	}
	// �ű���ʹ��
	public Object script(String formid, String lsh, String fatherNode)
			throws Exception {
		String mxlstr = null;
		DyAnalysisXml dyxml = new DyAnalysisXml();
		String xml = dyxml.XmlDate(formid);
		boolean falconfig = dyxml.isExists(xml, "config");
		if (falconfig) {
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println("xml��������");
				e.printStackTrace();
			}
			Element rootElt = doc.getRootElement();
			Iterator iter = rootElt.elementIterator("config");
			while (iter.hasNext()) {
				Element configEle = (Element) iter.next();
				mxlstr = configEle.asXML();
			}
			if (!mxlstr.equals("") || mxlstr != null) {
				String script = dyxml.readXML(mxlstr, fatherNode);
				script=dealWithResourceScript(script,"SOASCRIPT");		
				
				if (StringUtils.isNotEmpty(lsh)) {
					DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
					TCsBus bus =null;
					try{
						bus=dy.loadData(lsh,formid);
					}catch(Exception e){
						e.printStackTrace();
					}
					script = dealWithScrpit(script, "lsh", lsh);
					if(bus!=null){
						Object obj = BeanUtils.getProperty(bus, "statusinfo");
						script = dealWithScrpit(script, "statusinfo", obj);					
						Object obj2 = BeanUtils.getProperty(bus, "participant");
						script = dealWithScrpit(script, "participant", obj2);
						Object obj3 = BeanUtils.getProperty(bus, "fatherlsh");
						script = dealWithScrpit(script, "fatherlsh", obj3);
						script = dealWithScrpit(script, "formcode", formid);
						List list=dy.fetchColumnList(bus.getFormcode());
						for (Iterator iterator = list.iterator(); iterator
								.hasNext();) {
							TCsColumn object = (TCsColumn) iterator.next();
							String idx=object.getColumnid().toLowerCase();
							Object obj5 = BeanUtils.getProperty(bus,idx );
							if(obj5!=null){
								script = dealWithScrpit(script,idx, obj5);
							}	
						}
					}
				}else{
			
					script = dealWithScrpit(script, "fatherlsh", fatherNode);
					script = dealWithScrpit(script, "formcode", formid);
				}
               if(StringUtils.isNotEmpty(script)){
				return ScriptTools.todo(script);
               }
			}
		}
		return "";
	}
		
		
		public String dealWithResourceScript(String script,String rsHead) throws  Exception{
			//��Խű���Դ������ȴ�����ͨ��naturalnameȥ���Ƚű�ͨ����key=value#key2=value2�ķ�ʽ�����ݲ���
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			if(StringUtils.isNotEmpty(script)&&script.trim().startsWith(rsHead)){
				String scriptContect=StringUtils.substringBefore(script, "?");
				String ext=rs.loadResourceByNatural(scriptContect).getExtendattribute();
				String scriptAft=StringUtils.substringAfter(script, "?");
				String []info=scriptAft.split("#");
				for (int i = 0; i < info.length; i++) {
					String []param=info[i].split("=");
					if(param.length==2){
						String key="$("+param[0]+")";
						ext=StringUtils.replace(ext, key, param[1]);
					}
				}
				return ext;
			}else{
				return script;
			}
		}
	// �ű���ʹ��
	public Object scriptPre(String formid, TCsBus bus,String mode)
			throws Exception {
		String mxlstr = null;
		DyAnalysisXml dyxml = new DyAnalysisXml();
		String xml = dyxml.XmlDate(formid);
		boolean falconfig = dyxml.isExists(xml, "config");
		if (falconfig) {
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println("xml��������");
				e.printStackTrace();
			}
			Element rootElt = doc.getRootElement();
			Iterator iter = rootElt.elementIterator("config");
			while (iter.hasNext()) {
				Element configEle = (Element) iter.next();
				mxlstr = configEle.asXML();
			}
			if (!mxlstr.equals("") || mxlstr != null) {
				String script = dyxml.readXML(mxlstr, mode);

					DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");

					script = dealWithScrpit(script, "lsh", bus.getLsh());
					Object obj = BeanUtils.getProperty(bus, "statusinfo");
					script = dealWithScrpit(script, "statusinfo", obj);					
					Object obj2 = BeanUtils.getProperty(bus, "participant");
					script = dealWithScrpit(script, "participant", obj2);
					Object obj3 = BeanUtils.getProperty(bus, "fatherlsh");
					script = dealWithScrpit(script, "fatherlsh", obj3);
					script = dealWithScrpit(script, "formcode", formid);
					List list=dy.fetchColumnList(bus.getFormcode());
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						TCsColumn object = (TCsColumn) iterator.next();
						String idx=object.getColumnid().toLowerCase();
						Object obj5 = BeanUtils.getProperty(bus,idx);
						if(obj5!=null){
							script = dealWithScrpit(script, idx, obj5);
						}	
					}

               if(StringUtils.isNotEmpty(script)){
            	   return ScriptTools.todo(script);
               }
			}
		}
		return "";
	}
	private String dealWithScrpit(String script, String column, Object value) {
		if (StringUtils.isEmpty(script))
			return "";
		String rp = "$(" + column + ")";
		String valuex = "";
		if (value != null) {
			valuex = value.toString();
		}
		return StringUtils.replace(script, rp, valuex);
	}

	// ���� xml������
	public String readXML(String mxlstr, String fatherNode) {
		String script = null;
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(mxlstr);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("xml��������");
			e.printStackTrace();
		}
		Element rootElt = doc.getRootElement();
		Iterator iter = rootElt.elementIterator(fatherNode);
		List list = new ArrayList();
		while (iter.hasNext()) {
			Element configEle = (Element) iter.next();
			script = configEle.getText();
		}
		return script;
	}

	// ����formcode �õ�Actionurl �����xml����
	public String XmlDate(String formcode) {
		String mxlstr = null;
		String parentid = null;
		String sqlString = "select PARENTDIR from netone.ums_protectedobject  where extendattribute='"
				+ formcode + "' and inclusion is null and  objectType='DYFROM'";
		List list = DbTools.queryData(sqlString);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			parentid = (String) object.get("PARENTDIR");
		}

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		upo.setParentdir(parentid);
		ResourceRmi rs = null;
		List listDate = null;
		try {
			rs = (ResourceRmi) RmiEntry.iv("resource");
			listDate = rs.queryObjectProtectedObj(upo, null, 0, 10, "");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UmsProtectedobject upjs = (UmsProtectedobject) listDate.get(0);
		return upjs.getActionurl();
	}

	// �жϽڵ��Ƿ����
	public static boolean isExists(String formcode, String fatherNode)
			throws DocumentException, MalformedURLException, RemoteException {
		// �õ���XML�ļ�
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(formcode);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		int count = 0;
		// �õ���XML�ļ��ĸ��ڵ�
		Element root = doc.getRootElement();
		// �õ���Ҫ���ҵĽڵ�
		Element log = root.element(fatherNode);
		// �õ��ýڵ�Ľڵ�ö�ټ��ϣ�
		Iterator<Element> i = null;
		try {
			i = log.elementIterator();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		while (i.hasNext()) {
			// �õ�ö�ټ����ڵĽڵ�
			Element chars = (Element) i.next();
			// �õ��ýڵ��ڵ���������
			List as = chars.attributes();
			for (int j = 0; j < as.size(); j++) {
				// ѭ���õ��ڵ��ڵ���������
				Attribute attribute = (Attribute) as.get(j);
				count++;
			}
		}
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
