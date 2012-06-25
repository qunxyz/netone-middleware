package oe.midware.workflow.engine.rule2.func;



import oe.env.client.EnvService;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;

public class STools {
	public ResourceRmi rs_=null;
	public DyFormService dy_ =null;
	public CupmRmi cupm_ = null;
	public WorkflowConsole wf_ =null;
	public EnvService env_ = null;
	public STools(){
		 try {
			 rs_=(ResourceRmi)RmiEntry.iv("resource");
			 dy_ = (DyFormService) RmiEntry.iv("dyhandle");
			 cupm_ = (CupmRmi) RmiEntry.iv("cupm");
			 wf_ = (WorkflowConsole) RmiEntry.iv("wfhandle");
			 env_ = (EnvService) RmiEntry.iv("envinfo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
