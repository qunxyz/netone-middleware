package oe.cms.xhtml2;

/**
 * Oesee 业务整合(主要针对表单和流程的整合)
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface BusinessInterface {
	
	
	/**
	 * 解析Oec表单的信息
	 * 
	 * @param urlx
	 *            地址 Oesee论坛地址
	 *            /cavserweb/data/showdata/subListviewslink.do?lsh=1&formcode=
	 * @param limitrecordnum
	 *            显示的记录个数
	 * @param wordnum
	 *            显示的词个数
	 * @return
	 */
	public String oecForum(String urlBase, String limitrecordnum, String wordnum);

	/**
	 * 统计句子中的字的个数 注意到，在java中无论英文还是中文都认为是一个字，但是两个在html上显示的
	 * 时候中文却是英文的两倍，为了能更好控制web上的句子的长度，我们需要这个方法 来统计句子的字数 其中，发现英文单词加1,其他单词加2
	 * 
	 * @param senstance
	 * @param limit
	 * @return
	 * 
	 * 注意：该方法还可以处理样式（样式实际上并不占用显示位置，所以在分析的时候要能识别过滤 目前可识别过滤得样式只有一下2种 font 模式 和
	 * strong）
	 * 
	 */
	String _fontStyle = "<font\\s+color='#?[A-Za-z0-9]+'>";

	String _fontStyleE = "</font>";

	String _strongStyle = "<strong>";

	String _strongStyleE = "</strong>";

	public String limitDispSenc(String senstance, int limit);

}
