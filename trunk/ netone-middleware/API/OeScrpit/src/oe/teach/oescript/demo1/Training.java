package oe.teach.oescript.demo1;

import oescript.parent.OeScript;

public class Training extends OeScript {

	static String runtimeid = null;

	public static void main(String[] args) {
//		String dyformnaturalname = "BUSSFORM.BUSSFORM.XYCIT.RCZP.DY_901227410298808";
//		String lsh = dy.newInstance(dyformnaturalname);
//		String position = wf.get(runtimeid, "position");
//		dy.set(lsh, "belongx", position);
//		wf.set(runtimeid, "applyInfo", lsh);
		
		
//		String dyformnaturalname = "BUSSFORM.BUSSFORM.FLOWFORM.DY_251234231749142";
//		String lsh = dy.newInstance(dyformnaturalname);
//        dy.set(lsh, "column3", "Robanco"); 
//		wf.set(runtimeid, "formbinds", lsh);
		
		/*获得流程中绑定的表单实例*/
		String lshinfo=wf.get("402882821f69a800011f69aa8d370019", "formbinds");
		String checkinfo=dy.get(lshinfo, "column4");
		System.out.println(lshinfo+","+checkinfo);
		
		
		String formnatrualname="BUSSFORM.BUSSFORM.DY_321248053007297";
		String formruntimeid=dy.newInstance(formnatrualname);
		wf.set(runtimeid, "formai", formruntimeid);
		
		String formid=wf.get(runtimeid, "formai");
		String varinfo=dy.get(formid, "column7");
		
		boolean rs="new".equals(varinfo);
		
		wf.set(runtimeid, "isnew", rs?1:0);
		
		

	}
}
       