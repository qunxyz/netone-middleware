package oe.cav.bean.logic.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.reference.XMLReference;
import oe.cav.bean.logic.tools.reference.DyReference;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 从XML文档中得到构造DyObj所需的对象来生成DyObj
 * 
 * @author admin
 * 
 */
public class DyObjFromXmlImpl implements DyObjFromXml {

	static Log log = LogFactory.getLog(DyObjFromXmlImpl.class);

	public DyObj parser(String url) {

		SAXReader reader = new SAXReader();
		DyObj dfo = new DyObj();
		String extendAttribute = "";
		Document xml;
		if (url == null || url.equals("")) {
			log.error("输入参数url为空");
			return null;
		} else {
			URL urls = null;
			try {
				urls = new URL(url);
			} catch (MalformedURLException ee) {
				log.error("url地址错误");
				ee.printStackTrace();
				return null;
			}
			if (urls == null) {
				log.error("url地址为空");
				return null;
			} else {
				try {
					xml = reader.read(urls);
				} catch (DocumentException ex) {
					log.error("无效url路径");
					ex.printStackTrace();
					return null;
				}
				// 获得根元素
				Element root = xml.getRootElement();
				// 获得第一层子元素
				for (Iterator i = root.elementIterator(); i.hasNext();) {
					Element element1 = (Element) i.next();
					// 获得第一层属性
					String levelColumn = element1
							.attributeValue(XMLReference.LEVELCOLUMN);
					// String timeColumn =
					// element1.attributeValue(XMLReference.TIMECOLUMN);
					String ds = element1.attributeValue(XMLReference.DS);
					String table = element1.attributeValue(XMLReference.TABLE);
					String name = element1.attributeValue(XMLReference.NAME);
					String uuid = element1.attributeValue(XMLReference.UUID);
					String dataSrc = element1
							.attributeValue(XMLReference.DATASRC);

					String dimLevel = element1
							.attributeValue(XMLReference.LEVEL);
					String timeLevel = element1
							.attributeValue(XMLReference.PERIOD);
					// String level =
					// element1.attributeValue(XMLReference.LEVEL);
					// String usable =
					// element1.attributeValue(XMLReference.USEABLE);
					// String period =
					// element1.attributeValue(XMLReference.PERIOD);

					String other = element1.attributeValue(XMLReference.OTHER);
					String subform = "";
					String listinfo = "";
					String orderinfo = "";
					String butinfo = "";
					String viewbutinfo = "";
					String styleinfo = "";
					// 分隔字段
					String[] x = StringUtils.split(other, "#");
					for (int k = 0; k < x.length; k++) {
						String s = x[k];
						String d = StringUtils.substringBetween(s, "[", "]");
						String f = StringUtils.substringAfter(s, "]");
						log.debug(d + ",,," + f);
						if (DyReference.SUBFORM.equals(d)) {
							subform = f;
						} else if (DyReference.LISTINFO.equals(d)) {
							listinfo = f;
						} else if (DyReference.ORDERINFO.equals(d)) {
							orderinfo = f;
						} else if (DyReference.BUTINFO.equals(d)) {
							butinfo = f;
						} else if (DyReference.VIEWBUTINFO.equals(d)) {
							viewbutinfo = f;
						} else if (DyReference.STYLEINFO.equals(d)) {
							styleinfo = f;
						}
					}
					// 封装TCsForm对象
					TCsForm tcf = new TCsForm();
					tcf.setButinfo(butinfo);
					tcf.setCreated("");
					tcf.setDescription(table);
					tcf.setDesigner(levelColumn);
					tcf.setExtendattribute("");
					tcf.setFormcode(uuid);
					tcf.setFormname(name);
					tcf.setListinfo(listinfo);
					tcf.setOrderinfo(orderinfo);
					tcf.setParticipant("");
					tcf.setStatusinfo("");
					tcf.setSubform(subform);
					tcf.setSystemid(ds);
					tcf.setTypeinfo(dataSrc);
					tcf.setViewbutinfo(viewbutinfo);
					tcf.setTimelevel(timeLevel);
					tcf.setDimlevel(dimLevel);
					tcf.setStyleinfo(styleinfo);

					List formcolumn = new ArrayList();
					long invexvalue = 1;

					Iterator j = element1.elementIterator();
					Element eleSQL = (Element) j.next();
					String sqlInfo = (String) eleSQL.getData();
					tcf.setSqlinfo(sqlInfo);
					// 获得第二层子元素
					for (; j.hasNext();) {
						Element element2 = (Element) j.next();
						// 获得第二层属性
						String name1 = element2
								.attributeValue(XMLReference.NAME);
						// String uuid1 =
						// element2.attributeValue(XMLReference.UUID);
						String attributeName = element2
								.attributeValue(XMLReference.ATTRIBUERNAME);
						String fieldName = element2
								.attributeValue(XMLReference.FIELDNAME);

						// String attributeType =
						// element2.attributeValue(XMLReference.ATTRIBUTETYPE);
						// String unit =
						// element2.attributeValue(XMLReference.UNIT);
						String usable1 = element2
								.attributeValue(XMLReference.USEABLE);
						// String period1 =
						// element2.attributeValue(XMLReference.PERIOD);
						String other1 = element2
								.attributeValue(XMLReference.OTHER);
						String viewtype = element2
								.attributeValue(XMLReference.VIEWTYPE);
						String valuelist = element2
								.attributeValue(XMLReference.VIRTUAL);

						String htmltype = "";
						String checktype = "";
						String musk = "";
						String opemode = "";

						String extend = "";
						// 分隔字段
						String[] xx = StringUtils.split(other1, "#");
						for (int l = 0; l < xx.length; l++) {
							String ss = xx[l];
							String dd = StringUtils.substringBetween(ss, "[",
									"]");
							String ff = StringUtils.substringAfter(ss, "]");
							if (dd.equals(DyReference.HTMLTYPE)) {
								htmltype = ff;
							} else if (dd.equals(DyReference.CHECKTYPE)) {
								checktype = ff;
							} else if (dd.equals(DyReference.MUSK)) {
								musk = ff;
							} else if (dd.equals(DyReference.OPEMODE)) {
								opemode = ff;
							} else if (dd.equals(DyReference.VALUELIST)) {
								valuelist = ff;
							} else if (dd.equals(DyReference.EXTEND)) {
								extend = ff;
							}
						}
						// 封装TCsColumn对象
						TCsColumn tcc = new TCsColumn();

						tcc.setChecktype(checktype);
						tcc.setColumname(name1);
						tcc.setColumncode(attributeName);
						tcc.setColumnid(fieldName);
						tcc.setExtendattribute(extend);
						tcc.setFormcode(uuid);
						tcc.setHtmltype(htmltype);
						tcc.setViewtype(viewtype);
						if (usable1.equals("true")) {
							tcc.setIndexvalue(new Long(invexvalue));
							invexvalue++;
						} else {
							tcc.setIndexvalue(new Long("0"));
						}
						tcc.setMusk(musk);
						tcc.setOpemode(opemode);
						tcc.setParticipant("");
						tcc.setStatusinfo("");
						tcc.setUseable(new Boolean(usable1).booleanValue());
						tcc.setValuelist(valuelist);
						formcolumn.add(tcc);

						if (attributeName.equalsIgnoreCase("belongx")) {
							tcf.setBelongx(valuelist);
						}
					}
					// 封装DyObj对象
					dfo.setExtendAttribute(extendAttribute);
					dfo.setFrom(tcf);
					dfo.setColumn(formcolumn);
					dfo.setSystemid(ds);
					break;
				}
			}
		}

//		List list = dfo.getColumn();
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			TCsColumn object = (TCsColumn) iterator.next();
//			String ext = object.getExtendattribute();
//			ext = StringUtils.replace(ext, "%X@", "#");
//			object.setExtendattribute(ext);
//		}
		return dfo;
	}
}
