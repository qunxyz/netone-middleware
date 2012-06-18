package com.jl.common.dyform;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.jl.common.workflow.DbTools;

/**
 * ���ֶ� �򵥲�ѯmodel=0���߼���ѯmodel=1���ֶ��б�model=2���ֶ��б����model=3 xuwei��2012-4-7��
 * Constructor of the object.
 */
public class DyColumnQuery {
	public List<TCsColumn> QueryColumn(String formcode, String model)
			throws Exception {
		List<TCsColumn> listx = new ArrayList<TCsColumn>();
		String parentid = null;
		List listxml = null;
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
		try {
			rs = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List listDate = rs.queryObjectProtectedObj(upo, null, 0, 10, "");
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		DyFormDesignService dys = null;
		dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		List listmame = dys.queryObjects(busForm);
		List<TCsColumn> listremove=new ArrayList<TCsColumn>();
		//������belongx��TIMEX�ֶ�
		for (Iterator iterator = listmame.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
		 	if(object.getColumnid().toUpperCase().equals("BELONGX") || object.getColumnid().toUpperCase().equals("TIMEX") ){
		 	}else{
		 		listremove.add(object);
		 	}
		}
		listmame=listremove;
		UmsProtectedobject upjs = (UmsProtectedobject) listDate.get(0);
		if (upjs.getActionurl().equals("") || upjs.getActionurl() == ""
				|| upjs.getActionurl() == null) {
			listx = listmame;
		} else {
			DyColumnQuery dColumnQuery = new DyColumnQuery();
			boolean fal = dColumnQuery.isExists(upjs.getActionurl(), "config");
			if (fal) {
				listxml = readXML(model, upjs.getActionurl());
				if(listxml.size()>0){
				for (Iterator iterator = listmame.iterator(); iterator
						.hasNext();) {
					TCsColumn object = (TCsColumn) iterator.next();
					for (Iterator iteratorxml = listxml.iterator(); iteratorxml
							.hasNext();) {
						FullObj fullObj = (FullObj) iteratorxml.next();
						if (object.getColumnid().equals(fullObj.getColumnid())) {
							listx.add(object);
						}
					}
				}
				}else{
					listx = listmame;
				}
			} else {
				listx = listmame;
			}
		}
		if (model.equals("3")) {
			TCsColumn tcscolumn = new TCsColumn();
			tcscolumn.setColumname("ͳ��");
			tcscolumn.setColumncode("statistical");
			tcscolumn.setColumnid("statistical");
			tcscolumn.setFormcode(formcode);
			listx.add(tcscolumn);
		}
		return listx;
	}

	// ���� xml������
	public List readXML(String model, String mxlstr) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(mxlstr);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("xml��������");
		}
		Element rootElt = doc.getRootElement();
		Iterator iter = rootElt.elementIterator("config");
		List list = new ArrayList();
		try {
			while (iter.hasNext()) {
				Element configEle = (Element) iter.next();
				if (model.equals("0")) {
					Iterator typetiter = configEle
							.elementIterator("TDataGridType");
					while (typetiter.hasNext()) {
						Element configEletype = (Element) typetiter.next();
						Iterator titer = configEletype
								.elementIterator("TDataGrid"); // ��ȡ���ڵ��µ��ӽڵ�TDataGrid
						// ����TDataGrid�ڵ�
						while (titer.hasNext()) {
							Element recordEle = (Element) titer.next();
							FullObj fObj = new FullObj();

							fObj.setColumnid(recordEle.attributeValue("id"));
							fObj
									.setColumnname(recordEle
											.attributeValue("name"));
							list.add(fObj);
						}
					}
				} else if (model.equals("1")) {
					Iterator typetiter = configEle
							.elementIterator("ADataGridType");
					while (typetiter.hasNext()) {
						Element configEletype = (Element) typetiter.next();

						Iterator aiter = configEletype
								.elementIterator("ADataGrid"); // ��ȡ���ڵ��µ��ӽڵ�ADataGrid
						// ����TDataGrid�ڵ�
						while (aiter.hasNext()) {
							Element recordEle = (Element) aiter.next();
							FullObj fObj = new FullObj();
							fObj.setColumnid(recordEle.attributeValue("id"));
							fObj
									.setColumnname(recordEle
											.attributeValue("name"));
							list.add(fObj);
						}
					}
				} else if (model.equals("2")) {
					Iterator typetiter = configEle
							.elementIterator("DDataGridType");
					while (typetiter.hasNext()) {
						Element configEletype = (Element) typetiter.next();
						Iterator diter = configEletype
								.elementIterator("DDataGrid"); // ��ȡ���ڵ��µ��ӽڵ�ADataGrid
						// ����TDataGrid�ڵ�
						while (diter.hasNext()) {
							Element recordEle = (Element) diter.next();
							FullObj fObj = new FullObj();
							fObj.setColumnid(recordEle.attributeValue("id"));
							fObj
									.setColumnname(recordEle
											.attributeValue("name"));
							fObj.setColumnorder(recordEle
									.attributeValue("order"));
							list.add(fObj);
						}
					}
				}else if(model.equals("3")){
					Iterator typetiter = configEle
					.elementIterator("TJDataGridType");
					while (typetiter.hasNext()) {
						Element configEletype = (Element) typetiter.next();
						Iterator diter = configEletype
								.elementIterator("TJDataGrid"); // ��ȡ���ڵ��µ��ӽڵ�ADataGrid
						// ����TDataGrid�ڵ�
						while (diter.hasNext()) {
							Element recordEle = (Element) diter.next();
							FullObj fObj = new FullObj();
							fObj.setColumnid(recordEle.attributeValue("id"));
							fObj.setColumnname(recordEle.attributeValue("name"));
							list.add(fObj);
						}
						}
						}
			}
		} catch (Exception e) {
			System.out.println("xml������");
		}
		return list;
	}
   //�жϽڵ��Ƿ����
	public static boolean isExists(String mxlstr, String fatherNode)
			throws DocumentException {
		// �õ���XML�ļ�
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(mxlstr);
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
