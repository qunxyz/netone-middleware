/**
 * 
 */
package com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * �������ݴ���ʵ����
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-20 ����03:06:33
 * @history
 */
public class ReportGatherEntity {

	/**
	 * ��������<BR>
	 * KEY˵��<BR>
	 * 1.ReportExt._REPORT_ENTITY ��ǰ��ʵ�� <BR>
	 * 2.ReportExt._REPORT_KEYWORDS �ؼ��� <BR>
	 * 3.ReportExt._REPORT_GATHER_DOUBLE_ARRAY double[] ĳ������Ļ��ܼ���
	 * ��:ĳ�������̵����л����ۼ����ۼ�ֵ<BR>
	 * 4.�������� �������ܱ��� ReportExt._REPORT_GATHER_COL_*
	 */
	private ArrayList<Map> data = null;

	/** ��ʱ������չ */
	private Map tmpVar = null;

	public ReportGatherEntity() {
		tmpVar = new HashMap();
		data = null;
		data = new ArrayList<Map>();
	}

	/**
	 * ���
	 * 
	 * @param Map
	 *            ����ʵ�塣
	 * @throws Exception
	 */
	public void add(Map map) throws Exception {
		data.add(map);
	}

	/**
	 * ���±���
	 * 
	 * @param index
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void updateVar(String key, Object value) throws Exception {
		if (key.contains("_" + ReportExt._REPORT_ENTITY)) {
			tmpVar.put(key, value);
		} else if (key.contains("_" + ReportExt._REPORT_GATHER_DOUBLE_ARRAY)) {
			if (value instanceof double[]) {
				double[] new_value = (double[]) value;
				double[] gatherArr = (double[]) tmpVar.get(key);
				if (gatherArr == null || gatherArr.length == 0) {
					tmpVar.put(key, value);
				} else {
					if (new_value.length != gatherArr.length) {
						throw new Exception("�ۼ��±�ֵ��һ��");
					}
					for (int i = 0; i < new_value.length; i++) {
						gatherArr[i] += new_value[i];
					}
					tmpVar.put(key, gatherArr);
				}
			} else {
				throw new Exception("��˫������ֵ");
			}
		}
	}

	/**
	 * ��õ�Ԫ����
	 * 
	 * @return int
	 */
	public int getCount() {
		return data.size();
	}

	/**
	 * ȡ�õ�ǰλ��
	 * 
	 * @return
	 */
	public int getIndex() {
		int index = this.getCount();
		if (index == 0) {
			return 0;
		}
		return index - 1;
	}

	/**
	 * ȡ��ǰһ��λ��
	 * 
	 * @return
	 */
	public int getPreIndex() {
		int index = this.getCount();
		if (index == 0) {
			return 0;
		}
		return index - 2;
	}

	/**
	 * ��ȡ��ǰָ������
	 * 
	 * @param index
	 *            ��Ԫ��λ�á�
	 * @throws Exception
	 */
	public ReportEntity getReportEntity(int index) throws Exception {
		if (index < 0 || index > (data.size() - 1)) {
			throw new Exception("������û�����" + index + "��Ԫ");
		}
		Map row = data.get(index);
		return (ReportEntity) row.get(ReportExt._REPORT_ENTITY);
	}

	/**
	 * ��ȡ��ǰָ���ؼ���
	 * 
	 * @param index
	 *            ��Ԫ��λ�á�
	 * @throws Exception
	 */
	public String getKeyword(int index) throws Exception {
		if (index < 0 || index > (data.size() - 1)) {
			throw new Exception("������û�����" + index + "��Ԫ");
		}
		Map row = data.get(index);
		return (String) row.get(ReportExt._REPORT_KEYWORDS);
	}

	/**
	 * ��ȡ��ǰָ���ؼ���
	 * 
	 * @param index
	 *            ��Ԫ��λ�á�
	 * @throws Exception
	 */
	public double[] getGatherArrays(String keyword) throws Exception {
		if (tmpVar.containsKey(keyword)) {
			double[] gatherArr = (double[]) tmpVar.get(keyword);
			return gatherArr;
		} else {
			return null;
		}
	}

	/**
	 * ��ȡ��ǰָ�����йؼ��� �� ������ʵ������ �������ڳ�����ĩ���ۼ�����ʹ��
	 * 
	 * @param index
	 *            ��Ԫ��λ�á�
	 * @throws Exception
	 */
	public Map getAllInfo(int index) throws Exception {
		if (index < 0 || index > (data.size() - 1)) {
			throw new Exception("������û�����" + index + "��Ԫ");
		}
		Map row = data.get(index);
		return row;
	}

	public boolean isStart() throws Exception {
		int index = this.getIndex();
		int preIndex = this.getPreIndex();
		if (index == 0) {
			return true;
		} else {
			return !getKeyword(index).equals(getKeyword(preIndex));
		}
	}

	public boolean isPreEnd() throws Exception {
		int index = this.getIndex();
		int preIndex = this.getPreIndex();
		if (index == 0) {
			return false;
		} else {
			boolean isPreEnd = !getKeyword(index).equals(getKeyword(preIndex));
			return isPreEnd;
		}
	}

}
