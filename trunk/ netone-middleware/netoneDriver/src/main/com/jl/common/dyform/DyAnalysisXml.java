package com.jl.common.dyform;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
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
				System.out.println(script);
				if (StringUtils.isNotEmpty(lsh)) {
					DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
					TCsBus bus = dy.loadData(lsh,formid);
					Object obj = BeanUtils.getProperty(bus, "statusinfo");
					script = dealWithScrpit(script, "statusinfo", obj);
					Object obj1 = BeanUtils.getProperty(bus, "lsh");
					script = dealWithScrpit(script, "lsh", obj1);
					Object obj2 = BeanUtils.getProperty(bus, "participant");
					script = dealWithScrpit(script, "participant", obj2);
					Object obj3 = BeanUtils.getProperty(bus, "fatherlsh");
					script = dealWithScrpit(script, "fatherlsh", obj3);
					Object obj4 = BeanUtils.getProperty(bus, "formcode");
					script = dealWithScrpit(script, "formcode", obj4);
					for (int i = 1; i <= 50; i++) {
						Object obj5 = BeanUtils.getProperty(bus, "column" + i);
						script = dealWithScrpit(script, "column" + i, obj5);
					}
				}
				System.out.println("--"+script);
				return ScriptTools.todo(script);
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
