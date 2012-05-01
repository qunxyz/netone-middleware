package oesee.teach.java.io.xml;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class XpathDemo {

	/**
	 * @param args
	 * @throws DocumentException
	 */
	public static void main(String[] args) throws DocumentException {

		// ·��ƥ��
		String condition1 = "/org/course";
		String condition2 = "//course";
		String condition3 = "//forum";
		String condition4 = "//*";
		String condition5 = "/*";

		// ����ƥ��
		String con1 = "//forum[1]";
		String con2 = "//forum[last()]";
		String con3 = "//forum[position()=1]";

		// ����ƥ��
		String conc1 = "//forum[@*]";// �߱����Ե�Ԫ��
		String conc2 = "//forum[not(@*)]";// ���߱����Ե�Ԫ��
		String conc3 = "//forum[@name]";// �߱�name���Ե�Ԫ��
		String conc4 = "//forum[@id='forum1']";// ����idΪforum1��Ԫ��

		// ��ι�ϵƥ��
		String cond1 = "//forum/parent::*";
		String cond2 = "//forum/ancestor::*";
		String cond3 = "//course/child::*";
		String cond4 = "/A/descendant::*";

		findInfo(cond2);
	}

	public static void findInfo(String queryConditon) throws DocumentException {
		SAXReader reader = new SAXReader();
		String curpath = ReadXML.class.getResource("").getPath();
		Document document = reader.read(curpath + "xpathdemo.xml");
		List list = document.selectNodes(queryConditon);

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String elementtype = obj.getClass().getName();
			if ("org.dom4j.tree.DefaultDocument".equals(elementtype)) {
				Document ele = (Document) obj;
				System.out.print(ele.asXML());

			} else {
				Element ele = (Element) obj;
				System.out.print(ele.asXML());
			}

		}

	}

}
