package oe.cav.bean.logic.form;

public interface FormExtendInfo {
	public String[][] _STATUS = { { "00", "����" }, { "01", "ʧЧ" } };

	public String _DEFAULT_LIST_COLUMN = "1,2,3,4,5,6,7,8";

	public String _DEFAULT_LIST_BUT = ",backIc:1:����,dispIc:1:��ʾ,queIc:1:��ѯ,expIc:1:����,createIc:1:����\r,modifyIc:1:�޸�,deleteIc:1:ɾ��,sublistIc:1:�ӱ��б�";

	public String _DEFAULT_VIEW_BUT = ",viewmanIc:1:��ͼ,viewbut1Ic:1:������ϸ,viewbut2Ic:1:������һ��";

	public String _DEFAULT_ORDER = "1";

	public String _STATUS_NORMAL = "00";

	public String _STATUS_LOSE = "01";

	public String[][] _PART = { { "00", "����" }, { "01", "�ڲ�" }, { "02", "ָ��" } };

	public String _PART_PUBLIC = "00";

	public String _PART_PROTECTED = "01";

	public String _PART_POINT = "02";

	public String[] uicontrol = { "queryu", "dropu", "editu", "superqueu",
			"exportu", "createu" };
}
