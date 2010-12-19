package oe.midware.workflow.service.soatools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.midware.workflow.client.ActiveBind;
import oe.midware.workflow.client.Paramobj;
import oe.midware.workflow.client.ScriptObject;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.client.TaskObject;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;


public class ActiveBindDaoImpl implements ActiveBindDao {

	public void create(String id, ActiveBind ab, TaskObject task, ScriptObject script) {
		Document doc = toXmlCore(ab, task, script);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(id);
			upo.setExtendattribute(doc.asXML());
			resourceRmi.updateResource(upo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public Document toXmlCore(ActiveBind ab, TaskObject task, ScriptObject script) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("soa");
		if (ab != null) {
			Element root1 = root.addElement("activity").addAttribute("name", ab.getName()).addAttribute("bind",
					ab.getBindsource()).addAttribute("sync", "" + ab.isSync());
			List list = ab.getParams();
			if (list != null && !list.isEmpty()) {
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Paramobj po = (Paramobj) iter.next();
					root1.addElement("relation").addAttribute("datamode", po.getDatamode()).addAttribute("name",
							po.getName()).addAttribute("source", po.getSource()).addAttribute("node", po.getNode())
							.addAttribute("value", po.getValue());
				}
			}
		}
		if (script != null) {
			root.addElement("script").addAttribute("name", script.getName()).addAttribute("activityname",
					script.getActivityname()).addCDATA(script.getCdata());
		}
		if (task != null) {
			root.addElement("task").addAttribute("name", task.getName()).addAttribute("activityname",
					task.getActivityname()).addAttribute("starttime", task.getStarttime()).addAttribute("choicemode",
					task.getChoicemode()).addAttribute("loopmode", task.getLoopmode()).addAttribute("endtime",
					task.getEndtime());
		}
		return xml;
	}

	public SoaObj fromXml(String xml) {
		SoaObj soa = new SoaObj();
		ActiveBind ab = new ActiveBind();
		TaskObject task = new TaskObject();
		ScriptObject script = new ScriptObject();
		InputStream input = null;
		Document doc = null;
		try {
			input = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXReader reader = new SAXReader();
			doc = reader.read(input);
			Element root = doc.getRootElement();
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element ele = (Element) i.next();
				if ("activity".equals(ele.getName())) {
					String name = ele.attributeValue("name");
					ab.setName(name);
					String bindsource = ele.attributeValue("bind");
					ab.setBindsource(bindsource);
					String sync = ele.attributeValue("sync");
					ab.setSync(new Boolean(sync));

					List<Paramobj> params = new ArrayList<Paramobj>();
					for (Iterator j = ele.elementIterator(); j.hasNext();) {
						Element elem = (Element) j.next();
						Paramobj po = new Paramobj();
						String datamode = elem.attributeValue("datamode");
						String names = elem.attributeValue("name");
						String source = elem.attributeValue("source");
						String node = elem.attributeValue("node");
						String value = elem.attributeValue("value");
						po.setDatamode(datamode);
						po.setName(names);
						po.setNode(node);
						po.setSource(source);
						po.setValue(value);
						params.add(po);
					}
					ab.setParams(params);
					soa.setActivity(ab);
				} else if ("script".equals(ele.getName())) {
					String name = ele.attributeValue("name");
					script.setName(name);
					String activityname = ele.attributeValue("activityname");
					script.setActivityname(activityname);
					String cdata = ele.getData().toString();
					script.setCdata(cdata);
					soa.setScript(script);
				} else if ("task".equals(ele.getName())) {
					String name = ele.attributeValue("name");
					task.setName(name);
					String activityname = ele.attributeValue("activityname");
					task.setActivityname(activityname);
					String starttime = ele.attributeValue("starttime");
					task.setStarttime(starttime);
					String choicemode = ele.attributeValue("choicemode");
					task.setChoicemode(choicemode);
					String loopmode = ele.attributeValue("loopmode");
					task.setLoopmode(loopmode);
					String endtime = ele.attributeValue("endtime");
					task.setEndtime(endtime);
					soa.setTask(task);
				}
			}
			return soa;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return null;
			}
		}
		return null;
	}
}
