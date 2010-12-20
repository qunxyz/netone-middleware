package oe.midware.dyform.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormDesignServiceImpl;
import oe.midware.dyform.service.DyFormService;

public class Test {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		todo();

	}

	public static void todo() {
		DyFormService dfs = (DyFormService) FormEntry.fetchBean("dyhandle");
		DyFormDesignService dfs1 = (DyFormDesignService) FormEntry.fetchBean("dydesign");
	}
}
