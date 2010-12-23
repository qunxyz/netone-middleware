package oe.bi;

/**
 * BIչ����ģ��
 * 
 * @author chen.jia.xun
 * 
 */
public interface BiTree {
	String _TREE_XML = "<tree id=\"KEYID\" text=\"{cb}0{tt}KEYNAME\" action=\"javascript:nodeAction(&apos;KEYID&apos;,&apos;LEVELCOLUMN&apos;)\" src=\"/biWeb/servlet/EtlTreeSvl?datamodelid=KEYMODEL&amp;dimensionid=KEYCOLUMN&amp;dimensionvalue=KEYID&amp;analysis=0&amp;level=LEVELCOLUMN\" onload=\"treesonload();\" levelid=\"LEVELCOLUMN\"></tree>";

	String HEAD_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><tree>";

	String END_XML = "</tree>";

	/**
	 * ��Ԫ���еĹؼ��ֶ�
	 */
	String[] _TREE_XML_KEY = { "KEYID", "KEYNAME", "KEYCOLUMN", "KEYMODEL",
			"LEVELCOLUMN", "ORDERKEY" };

}
