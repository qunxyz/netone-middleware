package oe.frame.bus.res.doc.common;

/**
 * �ĵ�·������<br>
 * Ϊ����Ӧ����ģʽ�µ��ĵ��ֲ��洢,�ʽ��ĵ��ĵ�ַ��Ϣͨ����������д,ͨ���ö��� ����ӳ���������е��ĵ���Ϣ
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class FileRootInfo {
	String xmlpath;

	String docpath;

	String xpdlpath;

	String docwebtemplatepath;

	String commonPath;

	public String getCommonPath() {
		return commonPath;
	}

	public void setCommonPath(String commonPath) {
		this.commonPath = commonPath;
	}

	public String getDocpath() {
		return docpath;
	}

	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}

	public String getDocwebtemplatepath() {
		return docwebtemplatepath;
	}

	public void setDocwebtemplatepath(String docwebtemplatepath) {
		this.docwebtemplatepath = docwebtemplatepath;
	}

	public String getXmlpath() {
		return xmlpath;
	}

	public void setXmlpath(String xmlpath) {
		this.xmlpath = xmlpath;
	}

	public String getXpdlpath() {
		return xpdlpath;
	}

	public void setXpdlpath(String xpdlpath) {
		this.xpdlpath = xpdlpath;
	}
}
