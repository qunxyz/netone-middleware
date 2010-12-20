package oe.cav.bean.logic.bus;

public interface BusExtendInfo {
	String _STATUS_NORMAL = "00";

	String _STATUS_DROP = "01";

	String[] _COLUMN_PARTICIPANT = { "participant", "参与者" };

	String[] _COLUMN_CREATED = { "created", "创建日期" };

	String[] _COLUMN_LSH = { "lsh", "流水号" };

	String[] _COLUMN_TASKCODE = { "taskcode", "隶属任务码" };

	String[] _COLUMN_FORMCODE = { "formcode", "隶属表单码" };

	String[][] _COLUMN_ALL = { { "participant", "参与者" }, { "created", "创建日期" },
			{ "lsh", "流水号" }, { "formcode", "隶属表单码" } };

	String _RES_PATH = "FORMRES\\";

	String _TYPE_EXCEL = "xls";

	String _TYPE_PDF = "pdf";

	String _TYPE_TXT = "txt";
}
