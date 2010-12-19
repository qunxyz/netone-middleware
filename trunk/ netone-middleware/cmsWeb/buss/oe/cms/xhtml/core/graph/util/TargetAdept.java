package oe.cms.xhtml.core.graph.util;

public class TargetAdept {
	
	public static String[] adpetTarget(String target, int dimlen) {
		String[] targetInfo = target.split(",");
		if(targetInfo==null || targetInfo.length==0){
			targetInfo=new String[]{"0"};
		}
		String[] targetInfoReal = new String[dimlen];
		System.arraycopy(targetInfo, 0, targetInfoReal, 0, targetInfo.length);
		for (int i = targetInfo.length; i < dimlen; i++) {
			targetInfoReal[i] = "0";
		}
		return targetInfoReal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
