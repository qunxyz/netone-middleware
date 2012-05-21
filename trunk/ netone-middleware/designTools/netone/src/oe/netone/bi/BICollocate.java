package oe.netone.bi;

public class BICollocate {
    private String _bFormcode;
    private String _bCutDimColumn;
    private String _bCutDimColumnValue;
    private String _bExtendCondition ;
    private String _bXDimColumn ;
    private String _bTargetColumns ;
    private String _bNextConnectColumn ;
    private String  _bDisplayMode;
	public String get_bDisplayMode() {
		return _bDisplayMode;
	}
	public void set_bDisplayMode(String displayMode) {
		_bDisplayMode = displayMode;
	}
	public String get_bFormcode() {
		return _bFormcode;
	}
	public void set_bFormcode(String formcode) {
		_bFormcode = formcode;
	}
	public String get_bCutDimColumn() {
		return _bCutDimColumn;
	}
	public void set_bCutDimColumn(String cutDimColumn) {
		_bCutDimColumn = cutDimColumn;
	}
	public String get_bCutDimColumnValue() {
		return _bCutDimColumnValue;
	}
	public void set_bCutDimColumnValue(String cutDimColumnValue) {
		_bCutDimColumnValue = cutDimColumnValue;
	}
	public String get_bExtendCondition() {
		return _bExtendCondition;
	}
	public void set_bExtendCondition(String extendCondition) {
		_bExtendCondition = extendCondition;
	}
	public String get_bXDimColumn() {
		return _bXDimColumn;
	}
	public void set_bXDimColumn(String dimColumn) {
		_bXDimColumn = dimColumn;
	}
	public String get_bTargetColumns() {
		return _bTargetColumns;
	}
	public void set_bTargetColumns(String targetColumns) {
		_bTargetColumns = targetColumns;
	}
	public String get_bNextConnectColumn() {
		return _bNextConnectColumn;
	}
	public void set_bNextConnectColumn(String nextConnectColumn) {
		_bNextConnectColumn = nextConnectColumn;
	}
}
