/**
 * 
 */
package com.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 报表数据处理实体类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-20 下午03:06:33
 * @history
 */
public class ReportGatherEntity {

	/**
	 * 数据容器<BR>
	 * KEY说明<BR>
	 * 1.ReportExt._REPORT_ENTITY 当前行实体 <BR>
	 * 2.ReportExt._REPORT_KEYWORDS 关键字 <BR>
	 * 3.ReportExt._REPORT_GATHER_DOUBLE_ARRAY double[] 某分析项的汇总集合
	 * 例:某个经销商的所有货款、差价集合累计值<BR>
	 * 4.交互参数 包含汇总变量 ReportExt._REPORT_GATHER_COL_*
	 */
	private ArrayList<Map> data = null;

	/** 临时变量扩展 */
	private Map tmpVar = null;

	public ReportGatherEntity() {
		tmpVar = new HashMap();
		data = null;
		data = new ArrayList<Map>();
	}

	/**
	 * 添加
	 * 
	 * @param Map
	 *            数据实体。
	 * @throws Exception
	 */
	public void add(Map map) throws Exception {
		data.add(map);
	}

	/**
	 * 更新变量
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
						throw new Exception("累加下标值不一致");
					}
					for (int i = 0; i < new_value.length; i++) {
						gatherArr[i] += new_value[i];
					}
					tmpVar.put(key, gatherArr);
				}
			} else {
				throw new Exception("非双精度数值");
			}
		}
	}

	/**
	 * 获得单元数。
	 * 
	 * @return int
	 */
	public int getCount() {
		return data.size();
	}

	/**
	 * 取得当前位置
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
	 * 取得前一个位置
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
	 * 获取当前指定数据
	 * 
	 * @param index
	 *            单元的位置。
	 * @throws Exception
	 */
	public ReportEntity getReportEntity(int index) throws Exception {
		if (index < 0 || index > (data.size() - 1)) {
			throw new Exception("在行中没有这个" + index + "单元");
		}
		Map row = data.get(index);
		return (ReportEntity) row.get(ReportExt._REPORT_ENTITY);
	}

	/**
	 * 获取当前指定关键字
	 * 
	 * @param index
	 *            单元的位置。
	 * @throws Exception
	 */
	public String getKeyword(int index) throws Exception {
		if (index < 0 || index > (data.size() - 1)) {
			throw new Exception("在行中没有这个" + index + "单元");
		}
		Map row = data.get(index);
		return (String) row.get(ReportExt._REPORT_KEYWORDS);
	}

	/**
	 * 获取当前指定关键字
	 * 
	 * @param index
	 *            单元的位置。
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
	 * 获取当前指定所有关键字 及 报表行实体数据 供汇总期初、期末、累计数据使用
	 * 
	 * @param index
	 *            单元的位置。
	 * @throws Exception
	 */
	public Map getAllInfo(int index) throws Exception {
		if (index < 0 || index > (data.size() - 1)) {
			throw new Exception("在行中没有这个" + index + "单元");
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
