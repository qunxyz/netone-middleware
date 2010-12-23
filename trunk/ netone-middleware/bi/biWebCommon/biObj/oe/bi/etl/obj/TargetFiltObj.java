package oe.bi.etl.obj;

public class TargetFiltObj {
	// ////////TOPN相关///////////////
	public String _CHOICE_TOP_TOPN = "topn";

	public String _CHOICE_TOP_BOTN = "botn";

	public String[][] _CHOICE_TOP = { { "topn", "最前" }, { "botn", "最后" } };

	public String[][] _CHOICE_TOP_VALUE = { { "1", "1" }, { "2", "2" }, { "3", "3" }, { "4", "4" }, { "5", "5" },
			{ "6", "6" }, { "7", "7" }, { "8", "8" }, { "9", "9" }, { "10", "10" } };

	// ///////ORDER相关///////////////
	public String _ORDER_DESC = "desc";

	public String _ORDER_ASC = "asc";

	public String[][] _CHOICE_ORDER = { { "asc", "升序" }, { "desc", "降序" } };

	// /////Alarm相关////////////////

	public String _CHOICE_ALARM_UP = "alarmup";

	public String _CHOICE_ALARM_DW = "alarmdw";

	public String[][] _CHOICE_ALARM = { { "alarmup", "超过" }, { "alarmdw", "低于" } };

	public String[][] _CHOICE_ALARM2 = { { "like", "like" }, { "=", "=" } };

	public String[][] _CHOICE_ALARM3 = { { ">=", ">=" }, { "<=", "<=" }, { "!=", "!=" }, { ">", ">" },
			{ "<", "<" }, { "=", "=" } };

	/** 指标ID */
	private String targetid;

	/** 告警信息 */
	private String alarm;

	/** 是否递减 */
	private String desc;

	/** 前N个 */
	private String topn;

	/** 后N个 */
	private String botn;

	// 其他条件
	private String other;

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getBotn() {
		return botn;
	}

	public void setBotn(String botn) {
		this.botn = botn;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTargetid() {
		return targetid;
	}

	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	public String getTopn() {
		return topn;
	}

	public void setTopn(String topn) {
		this.topn = topn;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
