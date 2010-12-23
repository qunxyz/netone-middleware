package oe.bi.dataModel.bus.tree;

import oe.bi.BiTree;
import oe.bi.TimeTree;
import oe.bi.dataModel.bus.ActionDigTree;
import oe.bi.dataModel.bus.tree.util.MakeXMLTree;


public class TimeTreeImpl implements TimeTree, BiTree {

	private String yeardim;

	/**
	 * 获得年信息
	 * 
	 * @return
	 */
	public String fetchYearInfo(String datamodelid, String columnid) {
		if (yeardim == null || yeardim.length() == 0) {
			return HEAD_XML + END_XML;
		}
		String[] yeardims = yeardim.split(",");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < yeardims.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid,
					yeardims[i], yeardims[i] + "年", "1");
			buf.append(preInfo);
		}
		return HEAD_XML + buf.toString() + END_XML;
	}

	/**
	 * 获得月信息
	 * 
	 * @param yeardim
	 * @return
	 */
	public String fetchMonthInfo(String datamodelid, String columnid,
			String yeardimValue) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < monthInfo.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid,
					yeardimValue + "-" + monthInfo[i][0], monthInfo[i][1], "2");
			buf.append(preInfo);
		}
		return HEAD_XML + buf.toString() + END_XML;
	}

	public String fetchDayInfo(String datamodelid, String columnid,
			String monthDim) {
		String[][] dayInfos = fetchDay(monthDim);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < dayInfos.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid,
					dayInfos[i][0], dayInfos[i][1], "3");
			buf.append(preInfo);
		}
		return HEAD_XML + buf.toString() + END_XML;
	}

	/**
	 * 获得天信息
	 * 
	 * @param monthDim
	 * @return
	 */
	public String[][] fetchDay(String monthDim) {
		if (monthDim != null) {
			if (monthDim.matches(_RULE_MONTH)) {
				int year = Integer.parseInt(monthDim.substring(0, 4));
				int month = Integer.parseInt(monthDim.substring(5, 7));
				if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					return fetchDayCore(31, monthDim);
				} else {
					if (month == 2) {
						if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
							return fetchDayCore(29, monthDim);
						} else {
							return fetchDayCore(28, monthDim);
						}
					} else {
						return fetchDayCore(30, monthDim);
					}
				}
			}
		}
		return new String[0][2];
	}

	private String[][] fetchDayCore(int number, String month) {
		String[][] newDay = new String[number][2];
		for (int i = 0; i < number; i++) {
			newDay[i][0] = month + "-" + dayInfo[i][0];
			newDay[i][1] = dayInfo[i][1];
		}
		return newDay;
	}

	public String fetchHour(String datamodelid, String columnid, String day) {

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < hourInfo.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid, day
					+ " " + hourInfo[i][0], hourInfo[i][1], "4");
			buf.append(preInfo);
		}
		return HEAD_XML + buf.toString() + END_XML;
	}

	public String fetchMin(String datamodelid, String columnid, String hour) {

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < minInfo.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid,
					hour + ":" + minInfo[i][0], minInfo[i][1], "5");
			buf.append(preInfo);
		}
		return HEAD_XML + buf.toString() + END_XML;
	}

	public String fetchSec(String datamodelid, String columnid, String min) {

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < secInfo.length; i++) {
			String preInfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid, min
					+ ":" + secInfo[i][0], secInfo[i][1], "6");
			buf.append(preInfo);
		}
		return HEAD_XML + buf.toString() + END_XML;

	}

	public String getYeardim() {
		return yeardim;
	}

	public void setYeardim(String yeardim) {
		this.yeardim = yeardim;
	}

	public String fetchTimeInfo(String datamodelid, String columnid,
			String node, String level) {
		if (node == null) {
			return HEAD_XML + END_XML;
		}
		if (ActionDigTree._ROOT_NODE.equals(node)) {
			return this.fetchYearInfo(datamodelid, columnid);
		} else if (node.matches(_RULE_YEAR)) {
			return this.fetchMonthInfo(datamodelid, columnid, node);
		} else if (node.matches(_RULE_MONTH)) {
			return this.fetchDayInfo(datamodelid, columnid, node);
		} else if (node.matches(_RULE_DAY)) {
			return this.fetchHour(datamodelid, columnid, node);
		} else if (node.matches(_RULE_HOUR)) {
			return this.fetchMin(datamodelid, columnid, node);
		} else if (node.matches(_RULE_MIN)) {
			return this.fetchSec(datamodelid, columnid, node);
		}
		// TODO Auto-generated method stub
		return HEAD_XML + END_XML;
	}

}
