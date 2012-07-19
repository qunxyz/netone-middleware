package oe.frame.bus.res.doc;

import java.io.OutputStream;
import java.util.List;

/**
 * Word文档(Excel文档) 的模板中的内嵌脚本元素
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WordHandler {
	// ////基础变量/////////
	String TYPE_VAR = "var";// 普通变量

	String TYPE_VARX = "varx";// 多份变量

	String TYPE_VARS = "vars";// 变量数组

	String TYPE_LOOPS = "loops";// 循环

	// ////////////////////

	// 普通变量
	String VAR = "$var(";

	// 多份变量
	String VARX = "$varx(";

	// 变量数组
	String VARS = "$vars(";

	// 循环
	String LOOPS = "$loops{";

	// 结束标志
	String _END_MARK = ")";

	// 循环结束标志
	String _LOOP_END_MARK = "}";

	int LOOPS_LEN = 7;

	String LOSE_VALUE = "%%@Error!!";

	String LOOP_SYMBOL_TMP = "$tmploops{";

	/**
	 * 基于模板写文书（多份）
	 * 
	 * @param bussObjs
	 *            业务对象List
	 * @param multilBussObjs
	 *            多份文档的业务值
	 * @param templatename
	 *            模板文件名称（通用用Word生成的.htm文件，参数只需要传入文件名，不带扩展名，不带全路径名，可带相对路径名。
	 *            因为程序会自动添加扩展名.htm,自动添加根路径）
	 * @param output
	 *            输出流 补充说明:如果 output来自WEB response
	 *            response.setContentType("application/msword;charset=GBK");//
	 *            设置ContentType String expfileName = System.currentTimeMillis() +
	 *            .zip; response.setHeader("Content-Disposition", "attachment;
	 *            filename=\"" + expfileName + "\""); <br>
	 *            <br>
	 *            如果模板中有varx变量该方法可以产生n份文书,每份文书都是zip格式,里面打包 着文档和对应的扩展文件夹
	 */
	public void writeDocUseMultiBussObj(List value, String templateId,
			OutputStream output);

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
}
