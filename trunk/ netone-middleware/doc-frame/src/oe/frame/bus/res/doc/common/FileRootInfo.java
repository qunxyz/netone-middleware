package oe.frame.bus.res.doc.common;

/**
 * 文档路径管理<br>
 * 为了适应多种模式下的文档分布存储,故将文档的地址信息通过配置来填写,通过该对象 可以映射获得配置中的文档信息
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
