package oe.frame.bus.res.doc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Txt�ĵ�����
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface TxtHandler {

	/**
	 * д�ı�
	 * 
	 * @param value
	 *            ��д����
	 * @param column
	 *            ������ֶ�(�ֶ�������ʽ����)
	 * @param linesplitSymbol
	 *            �зָ����
	 * @param columnSplitSymbol
	 *            �зָ����
	 * @param outstream
	 *            �����
	 */
	public void writeTxt(List value, List column, OutputStream outstream);

	/**
	 * ���ı�
	 * 
	 * @param in
	 * @return
	 */
	public List readTxt(InputStream in);

	/**
	 * ����readTxt�����ķ���ֵ,����XMlObj list
	 * 
	 * @param value
	 * @return
	 */
	public List metaData(List value);
}
