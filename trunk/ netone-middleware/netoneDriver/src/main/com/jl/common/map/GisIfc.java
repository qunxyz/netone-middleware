package com.jl.common.map;

import java.util.List;

import com.jl.common.map.obj.Gis;

/**
 * װ�ص�ͼҵ���߼�
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface GisIfc {
	/**
	 * ��õ�ͼҵ���߼�����
	 * 
	 * @param url
	 *            ��ͼҵ���߼������ĵ���ַ
	 * @return
	 */
	Gis load(String url);

	/**
	 * �������õ���ȡ������ȡ��ͼ����һ������
	 * 
	 * @param gis
	 *            ��ͼҵ�����,������ȡ����
	 * @param flowId
	 *            ��ȡ���̽ڵ�
	 * @param parentstep
	 *            ��ǰ�ı���ȡ�ĵ�Ĳ��λ�� ��Step�� indexֵ
	 * @param areapointId
	 *            ��ǰ����ȡ�ĵ������ID����ֵ������Ϊ�գ���ʹ�û���ҵ��ڵ������һ������������ҵ��ڵ㱾���Ӵ�����id������Ҫ��������
	 * @param busspointId
	 *            ��ǰ����ȡ�ĵ��ҵ��ID����ֵ����Ϊ�գ�������������������һ��ʱ�����ֵΪ��
	 * @param extendbussinfo
	 *            ��չҵ�����ԣ����ڽ�һ������ҵ�������ã����磺�ᰴ���г��������������˾�����
	 * 
	 * @return
	 */
	List treeData(Gis gis, String flowId, int parentstep, String areapointId,
			String busspointId, String extendbussinfo);

	/**
	 * ƽ��չʾ���ݣ����ṩ��ȡ��������Ӧ�õļ�ֵ���ڷ���ͻ��ڵ�ͼ�Ͽ��ֲ������<br>
	 * 
	 * @param picid
	 *            ͼ��id
	 * @param busslevel
	 *            ��˾�ļ���
	 * @param busspointId
	 *            ��ǰ����ȡ�ĵ��ҵ��ID����ֵ����Ϊ�գ�������������������һ��ʱ�����ֵΪ��
	 * @param extendbussinfo
	 *            ��չҵ�����ԣ����ڽ�һ������ҵ�������ã����磺�ᰴ���г��������������˾�����
	 * @return
	 */
	List lineData(String picid, String busslevel,String busspointId, String extendbussinfo);

}
