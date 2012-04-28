package com.jl.common.netone;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class NetoneXmlTree {

	/**
	 * xuwei(2012-2-24) 遍历资源的数据构造成xml
	 * 
	 * @param args
	 */

	// 返回名字和 naturalname
	public String dataxml(String path, String cname) {
		String rs = null;
		String strcode = path + "." + path;
		Document document = DocumentHelper.createDocument();

		Element repfolder = document.addElement("repfolder").addAttribute(
				"name", cname).addAttribute("naturalname", strcode)
				.addAttribute("type", "gen");
		try {
			List quanbushuju = SecurityEntry.iv().listRsInDir(strcode, "01", 0,
					1000, "");
			List file = SecurityEntry.iv().listDirRs(strcode);
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource name = (Resource) iterator.next();
				String code1 = name.getResourcecode();
				Boolean fal = true;
				if (code1.equals(strcode)) {
					fal = false;
				}
				for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
					Resource resourcefile = (Resource) iterator2.next();
					String code = resourcefile.getResourcecode();
					if (code.equals(code1)) {
						if (strcode.equals(resourcefile.getResourcecode()
								.toString())) {
							break;
						} else {
							repfolder.add(XmlDate(resourcefile
									.getResourcename(), resourcefile
									.getResourcecode()));
						}
						fal = false;
					}
				}
				if (fal) {
					repfolder.addElement("repfile").addAttribute("name",
							name.getResourcename()).addAttribute("naturalname",
							name.getResourcecode()).addText("");
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			ByteOutputStream bytex = new ByteOutputStream();
			XMLWriter writer = new XMLWriter(bytex, format);
			writer.write(document);

			byte[] bytexxx = bytex.getBytes();
			rs = new String(bytexxx);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs.trim();
	}

	public Element XmlDate(String namea, String paht) {
		String strcode = paht;
		Document document = DocumentHelper.createDocument();
		// 获得根元素
		String[] arr = namea.split("/");
		Element repfolder = document.addElement("repfolder").addAttribute(
				"name", arr[arr.length - 1]).addAttribute("naturalname", paht)
				.addAttribute("type", "gen");
		try {
			List quanbushuju = SecurityEntry.iv().listRsInDir(strcode, "01", 0,
					1000, "");
			List file = SecurityEntry.iv().listDirRs(strcode);
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource name = (Resource) iterator.next();
				String code1 = name.getResourcecode();
				Boolean fal = true;
				if (code1.equals(strcode)) {
					fal = false;
				}
				for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
					Resource resourcefile = (Resource) iterator2.next();
					String code = resourcefile.getResourcecode();

					if (code.equals(code1)) {
						if (paht.equals(code)) {
							fal = false;
							break;
						} else {
							repfolder.add(XmlDate(resourcefile
									.getResourcename(), resourcefile
									.getResourcecode()));
						}
						fal = false;
					}
				}
				if (fal) {
					String[] namestr = name.getResourcename().split("/");
					repfolder.addElement("repfile").addAttribute("name",
							namestr[namestr.length - 1]).addAttribute(
							"naturalname", name.getResourcecode());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repfolder;
	}

	// 返回名字和formcode
	public String dyfromdataxml(String path, String cname) {
		String rs = null;
		String strcode = path + "." + path;
		Document document = DocumentHelper.createDocument();

		Element repfolder = document.addElement("repfolder").addAttribute(
				"name", cname).addAttribute("naturalname",
				formcodeString(strcode)).addAttribute("type", "gen");

		try {
			List quanbushuju = SecurityEntry.iv().listRsInDir(strcode, "01", 0,
					1000, "");
			List file = SecurityEntry.iv().listDirRs(strcode);
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource name = (Resource) iterator.next();
				String code1 = name.getResourcecode();
				Boolean fal = true;
				if (code1.equals(strcode)) {
					fal = false;
				}
				for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
					Resource resourcefile = (Resource) iterator2.next();
					String code = resourcefile.getResourcecode();
					if (code.equals(code1)) {
						if (strcode.equals(resourcefile.getResourcecode()
								.toString())) {
							break;
						} else {
							repfolder.add(dyfromXmlDate(resourcefile
									.getResourcename(), resourcefile
									.getResourcecode()));
						}
						fal = false;
					}
				}
				if (fal) {
					repfolder.addElement("repfile").addAttribute("name",
							name.getResourcename()).addAttribute("naturalname",
							formcodeString(name.getResourcecode())).addText("");
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			ByteOutputStream bytex = new ByteOutputStream();
			XMLWriter writer = new XMLWriter(bytex, format);
			writer.write(document);

			byte[] bytexxx = bytex.getBytes();
			rs = new String(bytexxx);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs.trim();
	}

	public Element dyfromXmlDate(String namea, String paht) {
		String strcode = paht;
		Document document = DocumentHelper.createDocument();
		// 获得根元素
		String[] arr = namea.split("/");
		Element repfolder = document.addElement("repfolder").addAttribute(
				"name", arr[arr.length - 1]).addAttribute("naturalname",
				formcodeString(paht)).addAttribute("type", "gen");
		try {
			List quanbushuju = SecurityEntry.iv().listRsInDir(strcode, "01", 0,
					1000, "");
			List file = SecurityEntry.iv().listDirRs(strcode);
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource name = (Resource) iterator.next();
				String code1 = name.getResourcecode();
				Boolean fal = true;
				if (code1.equals(strcode)) {
					fal = false;
				}
				for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
					Resource resourcefile = (Resource) iterator2.next();
					String code = resourcefile.getResourcecode();

					if (code.equals(code1)) {
						if (paht.equals(code)) {
							fal = false;
							break;
						} else {
							repfolder.add(dyfromXmlDate(resourcefile
									.getResourcename(), resourcefile
									.getResourcecode()));
						}
						fal = false;
					}
				}
				if (fal) {
					String[] namestr = name.getResourcename().split("/");
					repfolder.addElement("repfile").addAttribute("name",
							namestr[namestr.length - 1]).addAttribute(
							"naturalname",
							formcodeString(name.getResourcecode()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repfolder;
	}

	// naturalname 得到 formcode
	public String formcodeString(String naturalneme) {
		ResourceRmi resourceRmi = null;
		UmsProtectedobject upof = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			upof = resourceRmi.loadResourceByNatural(naturalneme);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upof.getExtendattribute();
	}
    // 根据路径和类型得到数据
	public String Reportdsxml(String path, String cname, String type) {
		String rs = null;
		String strcode = path + "." + path;
		Document document = DocumentHelper.createDocument();

		Element repfolder = document.addElement("repfolder").addAttribute(
				"name", cname).addAttribute("naturalname", strcode)
				.addAttribute("type", "gen");
		try {
			List quanbushuju = SecurityEntry.iv().listRsInDir(strcode, "01", 0,
					1000, "");
			List file = SecurityEntry.iv().listDirRs(strcode);
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource name = (Resource) iterator.next();
				String code1 = name.getResourcecode();
				Boolean fal = true;
				if (code1.equals(strcode)) {
					fal = false;
				}
				for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
					Resource resourcefile = (Resource) iterator2.next();
					String code = resourcefile.getResourcecode();
					if (code.equals(code1)) {
						if (strcode.equals(resourcefile.getResourcecode()
								.toString())) {
							break;
						} else {
							repfolder.add(ReportdsxmlDate(resourcefile
									.getResourcename(), resourcefile
									.getResourcecode(), type));
						}
						fal = false;
					}
				}
				if (fal) {
					if (name.getTypes()!= null) {
						if (name.getTypes().equals(type)) {
							repfolder.addElement("repfile").addAttribute(
									"name", name.getResourcename())
									.addAttribute("naturalname",
											name.getResourcecode()).addText("");
						}
					}
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gbk");
			ByteOutputStream bytex = new ByteOutputStream();
			XMLWriter writer = new XMLWriter(bytex, format);
			writer.write(document);

			byte[] bytexxx = bytex.getBytes();
			rs = new String(bytexxx);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs.trim();
	}

	public Element ReportdsxmlDate(String namea, String paht, String type) {
		String strcode = paht;
		Document document = DocumentHelper.createDocument();
		// 获得根元素
		String[] arr = namea.split("/");
		Element repfolder = document.addElement("repfolder").addAttribute(
				"name", arr[arr.length - 1]).addAttribute("naturalname", paht)
				.addAttribute("type", "gen");
		try {
			List quanbushuju = SecurityEntry.iv().listRsInDir(strcode, "01", 0,
					1000, "");
			List file = SecurityEntry.iv().listDirRs(strcode);
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource name = (Resource) iterator.next();
				String code1 = name.getResourcecode();
				Boolean fal = true;
				if (code1.equals(strcode)) {
					fal = false;
				}
				for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
					Resource resourcefile = (Resource) iterator2.next();
					String code = resourcefile.getResourcecode();

					if (code.equals(code1)) {
						if (paht.equals(code)) {
							fal = false;
							break;
						} else {
							repfolder.add(ReportdsxmlDate(resourcefile
									.getResourcename(), resourcefile
									.getResourcecode(), type));
						}
						fal = false;
					}
				}
				if (fal) {
					if (name.getTypes() != null) {
						if (name.getTypes().equals(type)) {
							String[] namestr = name.getResourcename()
									.split("/");
							repfolder.addElement("repfile").addAttribute(
									"name", namestr[namestr.length - 1])
									.addAttribute("naturalname",
											name.getResourcecode());
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repfolder;
	}
}
