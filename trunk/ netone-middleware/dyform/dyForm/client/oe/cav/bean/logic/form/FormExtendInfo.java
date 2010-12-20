package oe.cav.bean.logic.form;

public interface FormExtendInfo {
	public String[][] _STATUS = { { "00", "正常" }, { "01", "失效" } };

	public String _DEFAULT_LIST_COLUMN = "1,2,3,4,5,6,7,8";

	public String _DEFAULT_LIST_BUT = ",backIc:1:回退,dispIc:1:显示,queIc:1:查询,expIc:1:导出,createIc:1:创建\r,modifyIc:1:修改,deleteIc:1:删除,sublistIc:1:子表单列表";

	public String _DEFAULT_VIEW_BUT = ",viewmanIc:1:视图,viewbut1Ic:1:进入明细,viewbut2Ic:1:返回上一级";

	public String _DEFAULT_ORDER = "1";

	public String _STATUS_NORMAL = "00";

	public String _STATUS_LOSE = "01";

	public String[][] _PART = { { "00", "公开" }, { "01", "内部" }, { "02", "指定" } };

	public String _PART_PUBLIC = "00";

	public String _PART_PROTECTED = "01";

	public String _PART_POINT = "02";

	public String[] uicontrol = { "queryu", "dropu", "editu", "superqueu",
			"exportu", "createu" };
}
