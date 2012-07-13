package oe.netone.buss.zb.baseinfo;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String errormsg="";
		try {
			todo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			errormsg=e.getMessage();
		}
		System.out.print(errormsg);
	}
	
	public static void todo()throws Exception{
		throw new Exception("放松东方水都");
	}

}
