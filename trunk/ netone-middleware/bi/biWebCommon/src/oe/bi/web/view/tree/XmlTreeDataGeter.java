package oe.bi.web.view.tree;

import java.util.List;

public interface XmlTreeDataGeter {
	
	/**
	 * ��ȡ��ͼ�ڵ���ӽڵ�
	 * @param condition ���������ж���ṹ��һ��ýڵ��id�Ǳ�Ҫ������
	 * @return
	 */
	public List getChildrenById(String condition); 
	
	
	/**
	 * ���ݽڵ��id��ýڵ���Ӧ�Ķ��� 
	 * @param id
	 * @return
	 */
	public Object getNodeObjByid(String id);

}
