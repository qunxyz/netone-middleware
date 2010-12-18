package oe.security3a.seupublic.authentication.obj;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oe.security3a.seupublic.authentication.obj.core.ActionCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.ResourceCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.tools.XMLReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 请求对象
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class RequestCtx implements Serializable {

	static Log log = LogFactory.getLog(RequestCtx.class);

	private SubjectCtx subject;

	private ResourceCtx resource;

	private ActionCtx action;

	public SubjectCtx newSubject() {
		if (subject == null) {
			subject = new SubjectCtx();
			return subject;
		} else {
			throw new RuntimeException("Request中的Subject已经创建过");
		}
	}

	public ResourceCtx newResource() {
		if (resource == null) {
			resource = new ResourceCtx();
			return resource;
		} else {
			throw new RuntimeException("Request中的resource已经创建过");
		}
	}

	public ActionCtx newAction() {
		if (action == null) {
			action = new ActionCtx();
			return action;
		} else {
			throw new RuntimeException("Request中的action已经创建过");
		}
	}

	public ActionCtx getAction() {
		return action;
	}

	public void setAction(ActionCtx action) {
		this.action = action;
	}

	public ResourceCtx getResource() {
		return resource;
	}

	public void setResource(ResourceCtx resource) {
		this.resource = resource;
	}

	public SubjectCtx getSubject() {
		return subject;
	}

	public void setSubject(SubjectCtx subject) {
		this.subject = subject;
	}

	public static RequestCtx getInstance(InputStream input) {
		RequestCtx rc = new RequestCtx();
		SAXReader reader = new SAXReader();
		rc.subject = new SubjectCtx();
		rc.resource = new ResourceCtx();
		rc.action = new ActionCtx();
		Document xml = null;
		try {
			xml = reader.read(input);
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error("input为空");
			return null;
		}
		// 获得根元素
		Element root = xml.getRootElement();
		// 获得第一层子元素
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element element1 = (Element) i.next();
			Set<AttributeCtx> set = new HashSet<AttributeCtx>();
			for (Iterator j = element1.elementIterator(); j.hasNext();) {
				Element element2 = (Element) j.next();
				String name = element2.attributeValue(XMLReference.NAME);
				String value = element2.attributeValue(XMLReference.VALUE);
				AttributeCtx ac = new AttributeCtx(name, value);
				set.add(ac);
			}
			if (element1.getName().equals(XMLReference.SUBJECT)) {
				rc.subject.setAttribute(set);
			}
			if (element1.getName().equals(XMLReference.RESOURCE)) {
				rc.resource.setAttribute(set);
			}
			if (element1.getName().equals(XMLReference.ACTION)) {
				rc.action.setAttribute(set);
			}
		}
		rc.setSubject(rc.subject);
		rc.setResource(rc.resource);
		rc.setAction(rc.action);
		return rc;
	}

	public void encode(OutputStream output) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(XMLReference.REQUEST);
		Element root1 = root.addElement(XMLReference.SUBJECT);
		Element root2 = root.addElement(XMLReference.RESOURCE);
		Element root3 = root.addElement(XMLReference.ACTION);
		if(subject!=null){
			Set<AttributeCtx> subjects = subject.getAttribute();
			if (subjects != null) {
				for (Iterator iter = subjects.iterator(); iter.hasNext();) {
					AttributeCtx ac = (AttributeCtx) iter.next();
					String name = ac.getName();
					String value = ac.getValue();
					root1.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
							XMLReference.VALUE, value);
				}
			}
		}
		if(resource!=null){
			Set<AttributeCtx> resources = resource.getAttribute();
			if (resources != null) {
				for (Iterator iter = resources.iterator(); iter.hasNext();) {
					AttributeCtx ac = (AttributeCtx) iter.next();
					String name = ac.getName();
					String value = ac.getValue();
					root2.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
							XMLReference.VALUE, value);
				}
			}
		}
		if(action!=null){
			Set<AttributeCtx> actions = action.getAttribute();
			if (actions != null) {
				for (Iterator iter = actions.iterator(); iter.hasNext();) {
					AttributeCtx ac = (AttributeCtx) iter.next();
					String name = ac.getName();
					String value = ac.getValue();
					root3.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
							XMLReference.VALUE, value);
				}
			}
		}
		log.debug(xml.asXML());
		try {
			output.write(xml.asXML().getBytes());
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(XMLReference.REQUEST);
		Element root1 = root.addElement(XMLReference.SUBJECT);
		Element root2 = root.addElement(XMLReference.RESOURCE);
		Element root3 = root.addElement(XMLReference.ACTION);
		if(subject!=null){
			Set<AttributeCtx> subjects = subject.getAttribute();
			if (subjects != null) {
				for (Iterator iter = subjects.iterator(); iter.hasNext();) {
					AttributeCtx ac = (AttributeCtx) iter.next();
					String name = ac.getName();
					String value = ac.getValue();
					root1.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
							XMLReference.VALUE, value);
				}
			}
		}
		if(resource!=null){
			Set<AttributeCtx> resources = resource.getAttribute();
			if (resources != null) {
				for (Iterator iter = resources.iterator(); iter.hasNext();) {
					AttributeCtx ac = (AttributeCtx) iter.next();
					String name = ac.getName();
					String value = ac.getValue();
					root2.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
							XMLReference.VALUE, value);
				}
			}
		}
		if(action!=null){
			Set<AttributeCtx> actions = action.getAttribute();
			if (actions != null) {
				for (Iterator iter = actions.iterator(); iter.hasNext();) {
					AttributeCtx ac = (AttributeCtx) iter.next();
					String name = ac.getName();
					String value = ac.getValue();
					root3.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
							XMLReference.VALUE, value);
				}
			}
		}
		return xml.asXML();
	}

	public static void main(String[] args) {
		try {
			RequestCtx rc = RequestCtx.getInstance(new FileInputStream(
					"D:/project/newLand/project/Newland3A/RESOURCE/SAMPLE/request.xml"));
			rc.encode(new FileOutputStream("E:/requestctx.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
