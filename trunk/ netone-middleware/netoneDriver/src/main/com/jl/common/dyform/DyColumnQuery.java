package com.jl.common.dyform;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
		List listDate = rs.queryObjectProtectedObj(upo, null, 0, 100, "");
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		DyFormDesignService dys = null;
		dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		List listmame = dys.queryObjects(busForm);
		List<TCsColumn> listremove = new ArrayList<TCsColumn>();
		// ������belongx��TIMEX�ֶ�
		for (Iterator iterator = listmame.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			if (object.getColumnid().toUpperCase().equals("BELONGX")
					|| object.getColumnid().toUpperCase().equals("TIMEX")) {
			} else {
				listremove.add(object);
			}
		}
		listmame = listremove;
		UmsProtectedobject upjs = (UmsProtectedobject) listDate.get(0);
		if (upjs.getActionurl().equals("") || upjs.getActionurl() == ""
				|| upjs.getActionurl() == null) {
			listx = listmame;
		} else {

			boolean fal = isExists(upjs.getActionurl(), "config");
			if (fal) {
				listxml = readXML(model, upjs.getActionurl());
				if (listxml.size() > 0) {
					for (Iterator iterator = listmame.iterator(); iterator
							.hasNext();) {
						TCsColumn object = (TCsColumn) iterator.next();
						for (Iterator iteratorxml = listxml.iterator(); iteratorxml
								.hasNext();) {
							FullObj fullObj = (FullObj) iteratorxml.next();
							if (object.getColumnid().equals(
									fullObj.getColumnid())) {
								listx.add(object);
							}
						}
					}
				} else {
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

	public List<TCsColumn> QueryColumnQ(String formcode) throws Exception {
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
		List listDate = rs.queryObjectProtectedObj(upo, null, 0, 100, "");
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		List listmame = dys.queryObjects(busForm);
		List<TCsColumn> listremove = new ArrayList<TCsColumn>();
		Map columnKey = new HashMap();
		// ������belongx��TIMEX�ֶ�
		for (Iterator iterator = listmame.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			if (object.getColumnid().toUpperCase().equals("BELONGX")
					|| object.getColumnid().toUpperCase().equals("TIMEX")) {
			} else {
				DyFormColumn columnnew = new DyFormColumn();
				BeanUtils.copyProperties(columnnew, object);
				
				listremove.add(columnnew);
				columnKey.put(object.getColumnid().toUpperCase(), columnnew);
			}
		}

		UmsProtectedobject upjs = (UmsProtectedobject) listDate.get(0);
		if (StringUtils.isNotEmpty(upjs.getActionurl())&&isExists(upjs.getActionurl(), "config")) {

				listxml = readXML("0", upjs.getActionurl());
				if (listxml.size() > 0) {
					for (Iterator iteratorxml = listxml.iterator(); iteratorxml
							.hasNext();) {
						FullObj fullObj = (FullObj) iteratorxml.next();
						if (StringUtils.isEmpty(fullObj.getRef())) {
							// ��ѯ�ֶ��Ǳ���
							DyFormColumn columnx = (DyFormColumn) columnKey
									.get(fullObj.getColumnid().toUpperCase());
							if (StringUtils.isNotEmpty(fullObj.getXoffset())
									&& StringUtils.isNotEmpty(fullObj
											.getYoffset())) {
								// �������Ʋ��֣���ô���µĲ���
								columnx.setXoffset(Double.parseDouble(fullObj
										.getXoffset()));
								columnx.setYoffset(Double.parseDouble(fullObj
										.getYoffset()));
							} else {
								// ���û����Ʋ��֣���ô���Ĳ���
								String ext = columnx.getExtendattribute();
								// ����XY����
								String xyoffset = StringUtils.substringBetween(
										ext, DymaticFormCheck._CHECK_OFFSET,
										DymaticFormCheck._FINAL_CHECK);
								if (StringUtils.isNotEmpty(xyoffset)) {
									String xy[] = StringUtils.split(xyoffset,
											"-");
									if (xy.length == 2) {
										columnx.setYoffset(Double
												.parseDouble(xy[0]));
										columnx.setXoffset(Double
												.parseDouble(xy[1]));
									}
								}
							}
							columnx.setExtendattribute(fullObj.getExtinfo());
							columnx.setColumname(fullObj.getColumnname());
							listx.add(columnx);
						} else {
							// ��ѯ�ֶ�����������
							String forminfo[]=StringUtils.split(fullObj.getRef(),".");
							if(forminfo.length!=2){
								System.err.println("ref ���ø�ʽ����Ϊ formcode.columnid");
								throw new RuntimeException("ref ���ø�ʽ����Ϊ formcode.columnid");
							}
							TCsColumn columnx = dys.loadColumn(
									forminfo[0], forminfo[1]);
							columnx.setColumnid(fullObj.getColumnid());//Ҫ�޸�ԭ����columnid ������뵱ǰ���غ�
							columnx.setColumncode(fullObj.getColumnid()); //Ҫ�޸�ԭ����columnid ������뵱ǰ���غ�
							DyFormColumn columnnew = new DyFormColumn();
							BeanUtils.copyProperties(columnnew, columnx);
							if (StringUtils.isNotEmpty(fullObj.getXoffset())
									&& StringUtils.isNotEmpty(fullObj
											.getYoffset())) {
								// �������Ʋ��֣���ô���µĲ���
								columnnew.setXoffset(Double.parseDouble(fullObj
										.getXoffset()));
								columnnew.setYoffset(Double.parseDouble(fullObj
										.getYoffset()));
							}
							columnnew.setColumname(fullObj.getColumnname());
							columnnew.setExtendattribute(fullObj.getExtinfo());
							
							listx.add(columnnew);
						}
					}
				return listx;
				}
			
		}
		//û�����ò�ѯ�ֶ�Ĭ��ȫ���ֶβ�ѯ������ģʽ����Ҫ������ֶ���� extendattribute���ԣ���Ϊ��ѯģʽ����Ϊ �����ŵ��ǲ�ѯ����
		// ��Ĭ�ϵ�extendattribute���� ����λ����Ϣ
		for (Iterator iterator = listremove.iterator(); iterator.hasNext();) {
			DyFormColumn csColumn = (DyFormColumn) iterator.next();
			// ���û����Ʋ��֣���ô���Ĳ���
			String ext = csColumn.getExtendattribute();
			// ����XY����
			String xyoffset = StringUtils.substringBetween(
					ext, DymaticFormCheck._CHECK_OFFSET,
					DymaticFormCheck._FINAL_CHECK);
			if (StringUtils.isNotEmpty(xyoffset)) {
				String xy[] = StringUtils.split(xyoffset,
						"-");
				if (xy.length == 2) {
					csColumn.setYoffset(Double
							.parseDouble(xy[0]));
					csColumn.setXoffset(Double
							.parseDouble(xy[1]));
				}
			}
			csColumn.setExtendattribute("");
		}
		return listremove;
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
							fObj
									.setXoffset(recordEle
											.attributeValue("xoffset"));
							fObj
									.setYoffset(recordEle
											.attributeValue("yoffset"));
							fObj.setRef(recordEle
											.attributeValue("ref"));
							fObj.setExtinfo(recordEle.getData().toString());
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
				} else if (model.equals("3")) {
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
							fObj
									.setColumnname(recordEle
											.attributeValue("name"));
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

	// �жϽڵ��Ƿ����
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
