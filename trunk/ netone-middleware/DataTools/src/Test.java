import java.sql.Connection;
import java.sql.Timestamp;
import java.util.UUID;

import oe.mid.datatools.DataToolIfc;
import oe.mid.datatools.DataToolImplamle;
import oe.mid.datatools.obj.DataTrans;
import oe.mid.datatools.utils.DB;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(new Timestamp(System.currentTimeMillis()).toString());
		
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
		DataToolIfc dataToolIfc = new DataToolImplamle();

		String[] source = {"src/iss_product.xml","src/iss_dept.xml",
				"src/iss_buss.xml", "src/iss_payment.xml" };
		for (int i = 0; i < source.length; i++) {

			DB.setSource(source[i]);

			DataTrans dataTrans1 = dataToolIfc.parser(source[i]);
			dataToolIfc.todo(dataTrans1, false);
		}
		//tdo();
	}
	
	public static void tdo(){
		String parentid="";
		if(parentid.equals("")){
			parentid="402882e61d6fdb70011d7728fba10011";/*现在将根目录的父节点ID暂存在note字段里*/
			
		}
		DB.setSource("src/iss_dept.xml");
		Connection con = DB.conByDs("source0");
		String[] dataAll = DB.fetchOneData(
		"select naturalname NATURALNAME from ums_protectedobject where id='"+parentid+"'", "NATURALNAME", con);
		
		String datatmp = "";
		if (dataAll.length > 0) {
			datatmp = dataAll[0];
		}
		System.out.println(datatmp+".35") ;
	}



}
