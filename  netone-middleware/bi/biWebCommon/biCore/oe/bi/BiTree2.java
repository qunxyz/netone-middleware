package oe.bi;

public interface BiTree2 {
	String _TREE_XML = "<tree text=\"{cb}0{tt}KEYNAME\" action=\"javascript:nodeAction(&apos;KEYID&apos;,&apos;LEVELCOLUMN&apos;)\" src=\"/biWeb/servlet/EtlTreeSvl?treeModelId=KEYMODE&amp;columnname=KEYCOLUMN&amp;nodeid=KEYID&amp;nodename=KEYNAME&amp;analysis=0&amp;fathernodeid=0&amp;levelname=LEVELCOLUMN\" onload=\"treesonload();\" levelid=\"LEVELCOLUMN\"></tree>";

	String _TREETIME_XML = "<tree id=\"KEYID\" text=\"{cb}0{tt}KEYNAME\" action=\"javascript:timeTreeNodeAction(&apos;KEYID&apos;,&apos;LEVELCOLUMN&apos;)\" src=\"/biWeb/servlet/EtlTreeSvl?treeModelId=KEYCOLUMN&amp;nodeid=KEYID&amp;nodename=KEYNAME&amp;analysis=0&amp;fathernodeid=0&amp;levelname=LEVELCOLUMN\" onload=\"treesonload();\" levelid=\"LEVELCOLUMN\"></tree>";

	
	String HEAD_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><tree>";

	String END_XML = "</tree>";

	String[] _TREE_XML_KEY = { "KEYMODE", "KEYID", "KEYNAME", "LEVELCOLUMN","KEYCOLUMN" };

}
