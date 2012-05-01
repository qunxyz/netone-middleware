package oe.env.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import oe.env.EnvEntry;
import oe.env.client.EnvService;

public class EnvServiceImpl extends UnicastRemoteObject implements EnvService {

	public EnvServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addEnv(String key, String value) throws RemoteException

	{
		EnvEntry.setEnvInfo(key, value);

	}

	public String[][] fetchAllJppScript() throws RemoteException

	{
		return EnvEntry.getJppArr();
	}

	public String fetchEnvValue(String key) throws RemoteException

	{
		// TODO Auto-generated method stub
		return EnvEntry.getEnvInfo(key);
	}

	public String fetchJppScript(String id) throws RemoteException

	{
		return EnvEntry.getJppScriptCore(id);
	}

	public String fetchJppScriptModel(String id) throws RemoteException

	{
		return EnvEntry.getJppScriptDemo(id);
	}

	public String[][] fetchAllEnvSystemInfo() throws RemoteException {
		// TODO Auto-generated method stub
		return EnvEntry.getEnvSystemInfo();
	}

}
