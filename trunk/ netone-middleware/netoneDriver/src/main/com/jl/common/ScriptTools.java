package com.jl.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;


public class ScriptTools {

	static public Object todo(String elogicExpress) {

		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			return console.exeScript(elogicExpress);
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
		return null;
	}
	
	static public Object todo(String elogicExpress,Object [] obj) {

		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			return console.exeScript(elogicExpress);
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
		return null;
	}

}
