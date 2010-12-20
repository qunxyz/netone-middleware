package oe.cav.bean.logic.bus;

public interface BusExtendInfo {
	String _STATUS_NORMAL = "00";

	String _STATUS_DROP = "01";

	String[] _COLUMN_PARTICIPANT = { "participant", "������" };

	String[] _COLUMN_CREATED = { "created", "��������" };

	String[] _COLUMN_LSH = { "lsh", "��ˮ��" };

	String[] _COLUMN_TASKCODE = { "taskcode", "����������" };

	String[] _COLUMN_FORMCODE = { "formcode", "��������" };

	String[][] _COLUMN_ALL = { { "participant", "������" }, { "created", "��������" },
			{ "lsh", "��ˮ��" }, { "formcode", "��������" } };

	String _RES_PATH = "FORMRES\\";

	String _TYPE_EXCEL = "xls";

	String _TYPE_PDF = "pdf";

	String _TYPE_TXT = "txt";
}
