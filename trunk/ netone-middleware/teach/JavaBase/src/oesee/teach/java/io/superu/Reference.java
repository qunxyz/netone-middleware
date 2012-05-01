package oesee.teach.java.io.superu;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Reference {

	public static void main(String[] args) {
		try {
			Map obj = (Map)loadObject("//bean[@id='map']");
			obj.put("name", "mike");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object loadObject(String name) throws DocumentException {
		SAXReader reader = new SAXReader();
		String curpath = Reference.class.getResource("").getPath();
		Document document = reader.read(curpath + "spring_todo.xml");
		List list = document.selectNodes(name);

		if (list.size() > 1) {
			throw new RuntimeException("文档定义错误,出现重复的命名:" + name);
		} else if (list.size() < 1) {
			throw new RuntimeException("文档中不存在该命名:" + name);
		}
		Element ele = (Element) list.get(0);
		String classinfo = ele.attributeValue("class");
		try {
			return Class.forName(classinfo).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
