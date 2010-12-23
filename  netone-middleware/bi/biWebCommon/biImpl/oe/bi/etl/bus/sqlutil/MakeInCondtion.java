package oe.bi.etl.bus.sqlutil;

public class MakeInCondtion {
	
	
	public String inCondition(String condtion,String columnid){
		return columnid+ " in("+condtion+") and ";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
