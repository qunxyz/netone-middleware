package oe.teach.oescript;

import org.apache.commons.lang.StringUtils;

public class Test {
	
	public static void main(String[] args) {
		String elogicExpress="char a=��a��;System.out.println(a);";
		elogicExpress=StringUtils.replace(elogicExpress, "��", "'");
		System.out.println(elogicExpress);
	}

}
