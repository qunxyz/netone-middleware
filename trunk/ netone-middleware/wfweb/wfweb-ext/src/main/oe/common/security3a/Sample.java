package oe.common.security3a;

public class Sample {

	public static void main(String[] args) throws Exception {
		String rootDirId="35";
		String bussDirId="xxx";
		String bussDirId2="xxx2";
		String bussDirId3="xxx3";
		//SecurityEntry.iv().newOrganization(rootDirId, bussDirId3, "xxx", null);
		//SecurityEntry.iv().newOrganization(rootDirId, bussDirId, "xxx", null);
		//SecurityEntry.iv().newOrganization(bussDirId, bussDirId2, "xxx", null);
		//SecurityEntry.iv().deleteOrganization("xxx");
		//SecurityEntry.iv().editOrganization(bussDirId3, bussDirId, "xxxz", null);
		
		
		SecurityEntry.iv().newAccount(bussDirId3, "mikeee", "mikeee", null);
		//SecurityEntry.iv().deleteAccount("mikeee");
		SecurityEntry.iv().editAccount(bussDirId, "mikeee", "mikeeexxx", null);
	}

}
