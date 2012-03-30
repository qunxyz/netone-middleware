package test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;

public class XXXX {

	public static void main(String[] args) {
		try {
			WorkflowConsole wfhandle = (WorkflowConsole) RmiEntry
					.iv("wfhandle");
			String xxx = "String beanid = bean.newInstance(\"BUSSBEAN.BUSSBEAN.SAMPLE1\");bean.set(beanid, \"age\", 10);bean.set(beanid, \"name\", \"mike\");bean.run(beanid);System.out.println(bean.get(beanid, \"types\"));";

			wfhandle.exeScript(xxx, null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
