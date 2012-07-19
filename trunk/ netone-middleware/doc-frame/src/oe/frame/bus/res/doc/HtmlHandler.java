package oe.frame.bus.res.doc;

import java.util.List;
import java.util.Map;

/**
 * 通用文档处理程序(无论是处理Word还是excel都可以通过该程序作为中介完成处理)
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface HtmlHandler {

	/**
	 * 写文书模板
	 * 
	 * @param bussObjs
	 *            业务对象Map数组
	 * @param templatename
	 *            文书模板名
	 * @return
	 */
	public String[] generateMultiDocByTemplate(List bussObjs,
			String templatename);

	/**
	 * 写文书模板
	 * 
	 * @param bussObj
	 *            业务对象Map
	 * @param templatename
	 *            文书模板名
	 * @param needpagesplit
	 *            是否需要分页显示
	 * @return
	 */
	public String generateDocByTemplate(Map bussObj, String templatename);

	/**
	 * 读取XML参考模板（注意：如果先前未执行过fetchColumnInfo，那么读取的结果为空）
	 * 
	 * @param templateId
	 * @return
	 */
	public List fetchColumnInfo(String templateId);

	/**
	 * 创建XML参考信息
	 * 
	 * @param templateId
	 * @return
	 */
	public void generateXmlReference(String templateId);

	/**
	 * 获得模板的扩展文件,当模板中有图片等附件的时候,会有一个扩展文件夹
	 * 
	 * @param templateId
	 * @return
	 */
	public String templateModelExtFile(String templateId);
}
