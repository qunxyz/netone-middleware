import java.net.URL;

import oe.mid.io.CommonIoTools;
import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

public class Test {

	public static void main(String[] args) throws Exception {
		CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
		boolean rs = cupm.checkUserPermission("0000", "adminx", "DEPT.DEPT",
				"7");
		System.out.println(rs);

		WorkflowConsole wfc = (WorkflowConsole) RmiEntry.iv("wfhandle");
		Object obj = wfc.exeScript(
				"float sell=100;float plan=200;return sell/plan;", "");
		System.out.println(obj);

		String url = "http://netone.oesee.org:81/WebSpeci/DownloadSvl?fileid=4028f89e2bf70bbc012bf70d7bea0004";
		URL urlx=new URL(url);
		CommonIoTools.inputDo(urlx.openStream(), System.out);
	}
}
