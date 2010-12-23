package oe.bi.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.bi.BiEntry;
import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.datasource.DimensionAct;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.TargetElement;
import oe.frame.orm.util.IdServer;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * ChoiceInfo的dao实现类
 * 
 * @author chen.jia.xun
 * 
 */
public class WizardDaoImpl extends UnicastRemoteObject implements WizardDao {

	public WizardDaoImpl() throws RemoteException {
		super();
	}

	public void create(ChoiceInfo cho, String natrualname)
			throws RemoteException {
		String pathinfo = System.getProperty("user.dir") + "/bi/";
		
		Document doc = toXmlCore(cho);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");

		OutputStream output = null;
		try {
			output = new FileOutputStream(pathinfo
					+ cho.getLsh() + ".xml");
			XMLWriter writer = new XMLWriter(output, format);
			writer.write(doc);

			// 注册入资源
			ResourceRmi rsrmi = null;
			try {
				// 读取名为resource的rmi服务
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(cho.getNaturalname());
			upo.setName(cho.getName());
			upo.setActionurl("");
			upo.setExtendattribute(cho.getLsh());
			if (natrualname == null) {
				natrualname = "ETL.ETL";
			}
			upo.setObjecttype("BUSSENV.BUSSENV.BI");
			rsrmi.addResource(upo, natrualname);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(String lsh) throws RemoteException {
		String pathinfo = System.getProperty("user.dir") + "/bi/";
		File file = new File(pathinfo + lsh + ".xml");
		file.delete();
	}

	public ChoiceInfo fromXml(String xml) throws RemoteException {
		ChoiceInfo ci = new ChoiceInfo();
		InputStream input = null;
		Document doc = null;
		try {
			String pathinfo = System.getProperty("user.dir") + "/bi/";
			xml = pathinfo + xml + ".xml";
			input = new FileInputStream(xml);
			SAXReader reader = new SAXReader();
			doc = reader.read(input);
			Element root = doc.getRootElement();
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element ele = (Element) i.next();
				if ("lsh".equals(ele.getName())) {
					String lsh = ele.attributeValue("value");
					ci.setLsh(lsh);
				} else if ("dataModelid".equals(ele.getName())) {
					String dataModelid = ele.attributeValue("value");
					ci.setDataModelid(dataModelid);
				} else if ("actcondition".equals(ele.getName())) {
					String actcondition = ele.attributeValue("value");
					ci.setActcondition(actcondition);
				} else if ("acturl".equals(ele.getName())) {
					String acturl = ele.attributeValue("value");
					ci.setActurl(acturl);
				} else if ("name".equals(ele.getName())) {
					String name = ele.attributeValue("value");
					ci.setName(name);
				} else if ("naturalname".equals(ele.getName())) {
					String naturalname = ele.attributeValue("value");
					ci.setNaturalname(naturalname);
				} else if ("tggroup".equals(ele.getName())) {
					String tggroup = ele.attributeValue("value");
					ci.setTggroup(tggroup);
				} else if ("xlevel".equals(ele.getName())) {
					String xlevel = ele.attributeValue("value");
					ci.setXlevel(xlevel);
				} else if ("otherinfo".equals(ele.getName())) {
					String otherinfo = ele.getData().toString();
					ci.setOtherinfo(otherinfo);
				} else if ("created".equals(ele.getName())) {
					String created = ele.attributeValue("value");
					ci.setCreated(created);
				} else if ("active".equals(ele.getName())) {
					String active = ele.attributeValue("value");
					ci.setActive(Boolean.parseBoolean(active));
				} else if ("showactive".equals(ele.getName())) {
					String showactive = ele.attributeValue("value");
					ci.setShowactive(showactive);
				} else if ("selcharttype".equals(ele.getName())) {
					String selcharttype = ele.attributeValue("value");
					ci.setSelcharttype(selcharttype);
				} else if ("seldatatype".equals(ele.getName())) {
					String seldatatype = ele.attributeValue("value");
					ci.setSeldatatype(seldatatype);
				} else if ("name_en".equals(ele.getName())) {
					String name_en = ele.attributeValue("value");
					ci.setName_en(name_en);
				} else if ("start_time".equals(ele.getName())) {
					String start_time = ele.attributeValue("value");
					ci.setStart_time(start_time);
				} else if ("seltg".equals(ele.getName())) {
					String seltgs = ele.attributeValue("value");
					String[] seltg = StringUtils.split(seltgs, ",");
					ci.setSeltg(seltg);
				} else if ("multichart".equals(ele.getName())) {
					String multichart = ele.attributeValue("value");
					ci.setMultichart(multichart);
				} else if ("maxvalue".equals(ele.getName())) {
					String maxvalue = ele.attributeValue("value");
					ci.setMaxvalue(maxvalue);
				} else if ("pngwidth".equals(ele.getName())) {
					String pngwidth = ele.attributeValue("value");
					ci.setPngwidth(pngwidth);
				} else if ("pictitle".equals(ele.getName())) {
					String pictitle = ele.attributeValue("value");
					ci.setPictitle(pictitle);
				} else if ("piccolor".equals(ele.getName())) {
					String piccolor = ele.attributeValue("value");
					ci.setPiccolor(piccolor);
				} else if ("xqingxie".equals(ele.getName())) {
					String xqingxie = ele.attributeValue("value");
					ci.setXqingxie(xqingxie);
				} else if ("showvalue".equals(ele.getName())) {
					String showvalue = ele.attributeValue("value");
					ci.setShowvalue(showvalue);
				} else if ("dynamicDim".equals(ele.getName())) {
					String dynamicDim = ele.attributeValue("value");
					ci.setDynamicDim(Boolean.parseBoolean(dynamicDim));
				} else if ("dimensionElements".equals(ele.getName())) {
					List<DimensionElement> dimensionElement = new ArrayList<DimensionElement>();
					for (Iterator j = ele.elementIterator(); j.hasNext();) {
						Element elem = (Element) j.next();
						DimensionElement de = new DimensionElement();
						String id = elem.attributeValue("id");
						de.setId(id);
						String name = elem.attributeValue("name");
						de.setName(name);
						String node = elem.attributeValue("choicenode");
						if (StringUtils.isNotEmpty(node)) {
							String[] choicenode = StringUtils.split(node, ",");
							de.setChoicenode(choicenode);
						}
						String nodename = elem.attributeValue("choicenodename");
						if (StringUtils.isNotEmpty(nodename)) {
							String[] choicenodename = StringUtils.split(
									nodename, ",");
							de.setChoicenodename(choicenodename);
						}
						String levelcolumnid = elem
								.attributeValue("levelcolumnid");
						de.setLevelcolumnid(levelcolumnid);
						String order = elem.attributeValue("order");
						de.setOrder(Boolean.parseBoolean(order));
						String desc = elem.attributeValue("desc");
						de.setDesc(Boolean.parseBoolean(desc));
						dimensionElement.add(de);
					}
					ci.setDimensionElement(dimensionElement);
				} else if ("targetElements".equals(ele.getName())) {
					List<TargetElement> targetElement = new ArrayList<TargetElement>();
					for (Iterator j = ele.elementIterator(); j.hasNext();) {
						Element elem = (Element) j.next();
						TargetElement te = new TargetElement();
						String id = elem.attributeValue("id");
						te.setId(id);
						String name = elem.attributeValue("name");
						te.setName(name);
						String nativeid = elem.attributeValue("nativeid");
						te.setNativeid(nativeid);
						String type = elem.attributeValue("type");
						te.setType(type);
						String extendattribute = elem
								.attributeValue("extendattribute");
						te.setExtendattribute(extendattribute);
						targetElement.add(te);
					}
					ci.setTargetElement(targetElement);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ci;
	}

	public List list() throws RemoteException {
		List<ChoiceInfo> list = new ArrayList<ChoiceInfo>();
		String pathinfo = System.getProperty("user.dir") + "/bi/";
		File fi = new File(pathinfo);
		if (!fi.isFile()) {
			File[] list1 = fi.listFiles();
			if (list1 != null && list1.length > 0) {
				for (int i = 0; i < list1.length; i++) {
					String path = pathinfo;
					if (list1[i].isFile()
							&& StringUtils.contains(list1[i].getName(), ".xml")) {
						path = path + list1[i].getName();
						ChoiceInfo ci = fromXml(path);
						list.add(ci);
					}
				}
			}
		}
		return list;
	}

	public void modify(ChoiceInfo cho) throws RemoteException {
		delete(cho.getLsh());
		Document doc = toXmlCore(cho);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		OutputStream output = null;
		try {
			String pathinfo = System.getProperty("user.dir") + "/bi/";
			output = new FileOutputStream(pathinfo
					+ cho.getLsh() + ".xml");
			XMLWriter writer = new XMLWriter(output, format);
			writer.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String toXml(ChoiceInfo cho) throws RemoteException {
		return toXmlCore(cho).asXML();
	}

	public Document toXmlCore(ChoiceInfo cho) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("ChoiceInfo");
		if (StringUtils.isEmpty(cho.getLsh())) {
			cho.setLsh(IdServer.uuid());
		}
		root.addElement("lsh").addAttribute("value", NullToEmpty(cho.getLsh()));
		root.addElement("actcondition").addAttribute("value",
				NullToEmpty(cho.getActcondition()));
		root.addElement("acturl").addAttribute("value",
				NullToEmpty(cho.getActurl()));
		root.addElement("dataModelid").addAttribute("value",
				NullToEmpty(cho.getDataModelid()));
		root.addElement("name").addAttribute("value",
				NullToEmpty(cho.getName()));
		root.addElement("naturalname").addAttribute("value",
				NullToEmpty(cho.getNaturalname()));
		root.addElement("tggroup").addAttribute("value",
				NullToEmpty(cho.getTggroup()));
		root.addElement("xlevel").addAttribute("value",
				NullToEmpty(cho.getXlevel()));
		root.addElement("active").addAttribute("value",
				NullToEmpty(String.valueOf(cho.isActive())));
		root.addElement("selcharttype").addAttribute("value",
				NullToEmpty(cho.getSelcharttype()));
		root.addElement("seldatatype").addAttribute("value",
				NullToEmpty(cho.getSeldatatype()));
		root.addElement("name_en").addAttribute("value",
				NullToEmpty(cho.getName_en()));
		root.addElement("start_time").addAttribute("value",
				NullToEmpty(cho.getStart_time()));
		String[] seltg = cho.getSeltg();
		String seltgs = "";
		if (seltg != null && seltg.length > 0) {
			for (int i = 0; i < seltg.length; i++) {
				seltgs += seltg[i] + ",";
			}
		}
		root.addElement("seltg").addAttribute("value", NullToEmpty(seltgs));
		root.addElement("multichart").addAttribute("value",
				NullToEmpty(cho.getMultichart()));
		root.addElement("showactive").addAttribute("value",
				NullToEmpty(cho.getShowactive()));
		root.addElement("maxvalue").addAttribute("value",
				NullToEmpty(cho.getMaxvalue()));
		root.addElement("pngwidth").addAttribute("value",
				NullToEmpty(cho.getPngwidth()));
		root.addElement("showvalue").addAttribute("value",
				NullToEmpty(cho.getShowvalue()));
		root.addElement("pictitle").addAttribute("value",
				NullToEmpty(cho.getPictitle()));
		root.addElement("piccolor").addAttribute("value",
				NullToEmpty(cho.getPiccolor()));
		root.addElement("xqingxie").addAttribute("value",
				NullToEmpty(cho.getXqingxie()));
		root.addElement("dynamicDim").addAttribute("value",
				NullToEmpty(String.valueOf(cho.isDynamicDim())));
		Element root1 = root.addElement("dimensionElements");
		Element root2 = root.addElement("targetElements");
		root.addElement("otherinfo").addCDATA(NullToEmpty(cho.getOtherinfo()));
		GregorianCalendar nowgc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",
				Locale.CHINA);
		String nowdatetime = sdf.format(nowgc.getTime());
		root.addElement("created").addAttribute("value",
				NullToEmpty(nowdatetime));
		List dimensionElement = cho.getDimensionElement();
		List targetElement = cho.getTargetElement();
		for (Iterator iter = dimensionElement.iterator(); iter.hasNext();) {
			DimensionElement de = (DimensionElement) iter.next();
			String id = de.getId();
			String name = de.getName();
			String[] choicenode = de.getChoicenode();
			String[] choicenodename = de.getChoicenodename();
			String levelcolumnid = de.getLevelcolumnid();
			boolean order = de.isOrder();
			boolean desc = de.isDesc();
			StringBuffer cn = new StringBuffer();
			if (choicenode != null && choicenode.length > 0) {
				for (int i = 0; i < choicenode.length; i++) {
					if (i == 0) {
						cn.append(choicenode[i]);
					} else {
						cn.append("," + choicenode[i]);
					}
				}
			}
			StringBuffer cnn = new StringBuffer();
			if (choicenodename != null && choicenodename.length > 0) {
				for (int i = 0; i < choicenodename.length; i++) {
					if (i == 0) {
						cnn.append(choicenodename[i]);
					} else {
						cnn.append("," + choicenodename[i]);
					}
				}
			}
			String orderflag = String.valueOf(order);
			String descflag = String.valueOf(desc);
			root1
					.addElement("dimensionElement")
					.addAttribute("id", NullToEmpty(id))
					.addAttribute("name", NullToEmpty(name))
					.addAttribute("choicenode", NullToEmpty(cn.toString()))
					.addAttribute("choicenodename", NullToEmpty(cnn.toString()))
					.addAttribute("levelcolumnid", NullToEmpty(levelcolumnid))
					.addAttribute("order", NullToEmpty(orderflag))
					.addAttribute("desc", NullToEmpty(descflag));
		}
		for (Iterator iter = targetElement.iterator(); iter.hasNext();) {
			TargetElement te = (TargetElement) iter.next();
			String id = te.getId();
			String name = te.getName();
			String nativeid = te.getNativeid();
			String type = te.getType();
			String extendattribute = te.getExtendattribute();
			root2.addElement("targetElement").addAttribute("id",
					NullToEmpty(id)).addAttribute("name", NullToEmpty(name))
					.addAttribute("nativeid", NullToEmpty(nativeid))
					.addAttribute("type", NullToEmpty(type)).addAttribute(
							"extendattribute", NullToEmpty(extendattribute));
		}
		return xml;
	}

	public String NullToEmpty(String s) {
		if (StringUtils.isEmpty(s)) {
			return "";
		} else {
			return s;
		}
	}

	public boolean checkExist(String natrualname) throws RemoteException {
		// 注册入资源
		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname("ETL.ETL." + natrualname.toUpperCase());

			List list = rsrmi.fetchResource(upo, null);
			if (list == null || list.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String[][] listTree() throws RemoteException {
		DimensionAct dimact = (DimensionAct) BiEntry.fetchBi("dimensionAct");
		return dimact.listTree();
	}

	public String subTree(String treeModelId, NodeObj curNode)
			throws RemoteException {
		DimensionAct dimact = (DimensionAct) BiEntry.fetchBi("dimensionAct");

		return dimact.subTree(treeModelId, curNode);
	}

	public String timeTree(NodeObj curNode) throws RemoteException {
		DimensionAct dimact = (DimensionAct) BiEntry.fetchBi("dimensionAct");
		return dimact.timeTree(curNode);
	}
}
