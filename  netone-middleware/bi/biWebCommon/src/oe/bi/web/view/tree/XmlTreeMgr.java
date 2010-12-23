package oe.bi.web.view.tree;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlTreeMgr {
	
	static Log log = LogFactory.getLog(XmlTreeMgr.class);

	private static XmlTreeMgr xtm = new XmlTreeMgr();
	
	private Map getermap = new HashMap();
	
	private Map documentMap = new HashMap();
	
	private XmlTreeMgr(){
		initmap();
	}
	
	public void initmap(){
		XmlTreeDataGeter menugeter = new MenuTreeDataGeter();
		getermap.put("menutree",menugeter);
	}
	
	
	public static XmlTreeMgr getInstance(){
		return xtm;
	}
	
	public XmlTreeDataGeter getXmlTreeDataGeter(String filename){
		Object obj = getermap.get(filename);
		if(obj != null){
			return (XmlTreeDataGeter)obj;
		}
		else{
			return null ;
		}
	}
	
	public Document getDocument(String filename) throws Exception
	{
		Object obj = documentMap.get(filename);
		if(obj == null){
			obj = readFile(filename);
			if (obj != null) {
				documentMap.put(filename, obj);
			} else {
				throw new Exception("文件不存在！");
			}
		}
		return (Document)obj;
	}
	
	
	
	public Document readFile(String filename) {
		String xmlfile = filename + ".xml";
		InputStream inputs = XmlTreeSvl.class.getClassLoader()
				.getResourceAsStream(xmlfile);
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(inputs);
			return doc;
		} catch (DocumentException e) {
			log.error("树图对应的xml文件：" + xmlfile + "不存在!", e);
		}
		return null;
	}
	
	
	
	public XmlTreeObj getXmlTreeRoot(String filename){
		XmlTreeObj xtobj = new XmlTreeObj();
		try {
			Document doc = getDocument(filename);
			Element root = doc.getRootElement();
			xtobj.setId(root.attributeValue("id"));
			xtobj.setText(root.attributeValue("text"));
			xtobj.setChildren(root.attributeValue("children"));
			return xtobj ;
		} catch (Exception e) {
			log.error("",e);
		}
		return null ;
	}
	

}
