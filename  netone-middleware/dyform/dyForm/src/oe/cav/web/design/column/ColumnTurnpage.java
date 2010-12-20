package oe.cav.web.design.column;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.column.ColumnDao;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.util.SearchObj;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import com.rongji.webframework.event.page.Page;
import com.rongji.webframework.event.page.PageEvent;

public class ColumnTurnpage implements PageEvent {
	DyFormDesignService dys = null;

	DyFormService dysc = null;

	public ColumnTurnpage() {
		if (dys == null) {
			try {
				dys = (DyFormDesignService) RmiEntry.iv("dydesign");
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
		if (dysc == null) {
			try {
				dysc = (DyFormService) RmiEntry.iv("dyhandle");
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

	public void total(Page page, Object obj) {

		long total = 0;
		SearchObj search = (SearchObj) obj;
		if (search != null) {
			if (search.getSql() != null && search.getSql().length() > 0) {
				try {
					total = dys.queryObjectsNumber((TCsColumn) search
							.getSearchobj(), search.getSql());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					total = dys.queryObjectsNumber((TCsColumn) search
							.getSearchobj());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		page.setTotal((int) total);
	}

	public Object page(Page page, Object obj) {

		SearchObj search = (SearchObj) obj;
		List list = null;
		if (search != null) {
			if (search.getSql() != null && search.getSql().length() > 0) {
				try {
					list = dys.queryObjects((TCsColumn) search.getSearchobj(),
							page.startRow(), page.endRow(), search.getSql());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					list = dys.queryObjects((TCsColumn) search.getSearchobj(),
							page.startRow(), page.endRow(), "");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			return new ArrayList();
		}
		return list;
	}
}
