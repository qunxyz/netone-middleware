package oe.security3a.seupublic.authentication.obj;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oe.security3a.seupublic.authentication.obj.core.ActionCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.ResourceCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;
import oe.security3a.tools.XMLReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 策略对象<br>
 * 策略对象与相关的策略文件相互对应,策略文件通常是一些静态的xml文件,这些文件会自动 与权限资源的变更保持一致
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class ResponseCtx implements Serializable {

	static Log log = LogFactory.getLog(ResponseCtx.class);

	private TargetCtx target;

	private Set<PermissionCtx> permissions;

	public TargetCtx newTarget() {
		if (target == null) {
			target = new TargetCtx();
			return target;
		} else {
			throw new RuntimeException("Response中已经存在target");
		}
	}

	public void newPermission(PermissionCtx permission) {
		if (permissions == null) {
			permissions = new HashSet<PermissionCtx>();

		} else {
			permissions.add(permission);
		}
	}

	public static ResponseCtx getInstance(InputStream input) {
		ResponseCtx rc = new ResponseCtx();
		RequestCtx request = rc.newTarget().newRequest();
		SubjectCtx sub = request.newSubject();
		ActionCtx act = request.newAction();
		ResourceCtx rs = request.newResource();
		SAXReader reader = new SAXReader();
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
			if (element1.getName().equals(XMLReference.TARGET)) {
				for (Iterator j = element1.elementIterator(); j.hasNext();) {
					Element element2 = (Element) j.next();
					for (Iterator k = element2.elementIterator(); k.hasNext();) {
						Element element3 = (Element) k.next();
						String name = element3.attributeValue(XMLReference.NAME);
						String value = element3.attributeValue(XMLReference.VALUE);

						if (element2.getName().equals(XMLReference.SUBJECT)) {
							sub.newAttribute(name, value);
						}
						if (element2.getName().equals(XMLReference.RESOURCE)) {
							rs.newAttribute(name, value);
						}
						if (element2.getName().equals(XMLReference.ACTION)) {
							act.newAttribute(name, value);
						}
					}
				}
			}
			if (element1.getName().equals(XMLReference.PERMISSIONS)) {
				rc.newPermission(null);
				for (Iterator j = element1.elementIterator(); j.hasNext();) {
					Element element2 = (Element) j.next();
					PermissionCtx per = new PermissionCtx();
					Set<AttributeCtx> set = new HashSet<AttributeCtx>();
					for (Iterator k = element2.elementIterator(); k.hasNext();) {
						Element element3 = (Element) k.next();
						String name = element3.attributeValue(XMLReference.NAME);
						String value = element3.attributeValue(XMLReference.VALUE);
						AttributeCtx ac = new AttributeCtx(name, value);
						if (name.equals(XMLReference.RESOURCE2)) {
							per.setResource(ac);
						} else if (name.equals(XMLReference.ACTION2)) {
							per.setAction(ac);
						} else if (name.equals(XMLReference.STATUS)) {
							per.setStatus(ac);
						} else {
							set.add(ac);
						}
					}
					per.setSet(set);
					rc.newPermission(per);
				}
			}
		}
		return rc;
	}

	public static ResponseCtx getInstance(String s) {
		try {
			InputStream input = new ByteArrayInputStream(s.getBytes("utf-8"));
			return getInstance(input);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void encode(OutputStream output) {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(XMLReference.RESPONSE);
		Element root1 = root.addElement(XMLReference.TARGET);
		Element root11 = root1.addElement(XMLReference.SUBJECT);
		Element root12 = root1.addElement(XMLReference.RESOURCE);
		Element root13 = root1.addElement(XMLReference.ACTION);
		if(target!=null){
			RequestCtx request = target.getRequest();
			if(request!=null){
				SubjectCtx subject = request.getSubject();
				if(subject!=null){
					Set<AttributeCtx> subjects = subject.getAttribute();
					if (subjects != null) {
						for (Iterator iter = subjects.iterator(); iter.hasNext();) {
							AttributeCtx ac = (AttributeCtx) iter.next();
							String name = ac.getName();
							String value = ac.getValue();
							root11.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
									XMLReference.VALUE, value);
						}
					}
				}
				ResourceCtx resource = request.getResource();
				if(resource!=null){
					Set<AttributeCtx> resources = resource.getAttribute();
					if (resources != null) {
						for (Iterator iter = resources.iterator(); iter.hasNext();) {
							AttributeCtx ac = (AttributeCtx) iter.next();
							String name = ac.getName();
							String value = ac.getValue();
							root12.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
									XMLReference.VALUE, value);
						}
					}
				}
				ActionCtx action = request.getAction();
				if(action!=null){
					Set<AttributeCtx> actions = action.getAttribute();
					if (actions != null) {
						for (Iterator iter = actions.iterator(); iter.hasNext();) {
							AttributeCtx ac = (AttributeCtx) iter.next();
							String name = ac.getName();
							String value = ac.getValue();
							root13.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
									XMLReference.VALUE, value);
						}
					}
				}
			}	
		}
		if (permissions != null) {
			Element root2 = root.addElement(XMLReference.PERMISSIONS);
			for (Iterator iter = permissions.iterator(); iter.hasNext();) {
				PermissionCtx pctx = (PermissionCtx) iter.next();
				Element root2x = root2.addElement(XMLReference.PERMISSION);
				AttributeCtx resourcectx = pctx.getResource();
				if (resourcectx != null) {
					root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, resourcectx.getName())
							.addAttribute(XMLReference.VALUE, resourcectx.getValue());
				}
				AttributeCtx actionctx = pctx.getAction();
				if (actionctx != null) {
					root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, actionctx.getName())
							.addAttribute(XMLReference.VALUE, actionctx.getValue());
				}
				AttributeCtx statusctx = pctx.getStatus();
				if (statusctx != null) {
					root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, statusctx.getName())
							.addAttribute(XMLReference.VALUE, statusctx.getValue());
				}
				Set<AttributeCtx> set = pctx.getSet();
				if (set != null) {
					for (Iterator iterator = set.iterator(); iterator.hasNext();) {
						AttributeCtx attributectx = (AttributeCtx) iterator.next();
						root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME,
								attributectx.getName()).addAttribute(XMLReference.VALUE, attributectx.getValue());
					}
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

	public TargetCtx getTarget() {
		return target;
	}

	public void setTarget(TargetCtx target) {
		this.target = target;
	}

	public String toString() {
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement(XMLReference.RESPONSE);
		Element root1 = root.addElement(XMLReference.TARGET);
		Element root11 = root1.addElement(XMLReference.SUBJECT);
		Element root12 = root1.addElement(XMLReference.RESOURCE);
		Element root13 = root1.addElement(XMLReference.ACTION);
		if(target!=null){
			RequestCtx request = target.getRequest();
			if(request!=null){
				SubjectCtx subject = request.getSubject();
				if(subject!=null){
					Set<AttributeCtx> subjects = subject.getAttribute();
					if (subjects != null) {
						for (Iterator iter = subjects.iterator(); iter.hasNext();) {
							AttributeCtx ac = (AttributeCtx) iter.next();
							String name = ac.getName();
							String value = ac.getValue();
							root11.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
									XMLReference.VALUE, value);
						}
					}
				}
				ResourceCtx resource = request.getResource();
				if(resource!=null){
					Set<AttributeCtx> resources = resource.getAttribute();
					if (resources != null) {
						for (Iterator iter = resources.iterator(); iter.hasNext();) {
							AttributeCtx ac = (AttributeCtx) iter.next();
							String name = ac.getName();
							String value = ac.getValue();
							root12.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
									XMLReference.VALUE, value);
						}
					}
				}
				ActionCtx action = request.getAction();
				if(action!=null){
					Set<AttributeCtx> actions = action.getAttribute();
					if (actions != null) {
						for (Iterator iter = actions.iterator(); iter.hasNext();) {
							AttributeCtx ac = (AttributeCtx) iter.next();
							String name = ac.getName();
							String value = ac.getValue();
							root13.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, name).addAttribute(
									XMLReference.VALUE, value);
						}
					}
				}
			}	
		}
		if (permissions != null) {
			Element root2 = root.addElement(XMLReference.PERMISSIONS);
			for (Iterator iter = permissions.iterator(); iter.hasNext();) {
				PermissionCtx pctx = (PermissionCtx) iter.next();
				Element root2x = root2.addElement(XMLReference.PERMISSION);
				AttributeCtx resourcectx = pctx.getResource();
				if (resourcectx != null) {
					root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, resourcectx.getName())
							.addAttribute(XMLReference.VALUE, resourcectx.getValue());
				}
				AttributeCtx actionctx = pctx.getAction();
				if (actionctx != null) {
					root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, actionctx.getName())
							.addAttribute(XMLReference.VALUE, actionctx.getValue());
				}
				AttributeCtx statusctx = pctx.getStatus();
				if (statusctx != null) {
					root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME, statusctx.getName())
							.addAttribute(XMLReference.VALUE, statusctx.getValue());
				}
				Set<AttributeCtx> set = pctx.getSet();
				if (set != null) {
					for (Iterator iterator = set.iterator(); iterator.hasNext();) {
						AttributeCtx attributectx = (AttributeCtx) iterator.next();
						root2x.addElement(XMLReference.ATTRIBUTE).addAttribute(XMLReference.NAME,
								attributectx.getName()).addAttribute(XMLReference.VALUE, attributectx.getValue());
					}
				}
			}
		}
		return xml.asXML();
	}

	public Set<PermissionCtx> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<PermissionCtx> permissions) {
		this.permissions = permissions;
	}

	public static void main(String[] args) {
		try {
			ResponseCtx rc = ResponseCtx.getInstance(new FileInputStream(
					"D:/project/newLand/project/Newland3A/RESOURCE/SAMPLE/response.xml"));
			rc.encode(new FileOutputStream("E:/responsectx.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
