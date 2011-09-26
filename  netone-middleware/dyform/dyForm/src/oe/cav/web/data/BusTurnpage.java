package oe.cav.web.data;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;

import oe.cav.web.data.dyform.utils.ListView;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import com.rongji.webframework.event.page.Page;
import com.rongji.webframework.event.page.PageEvent;

public class BusTurnpage implements PageEvent {
	DyFormDesignService dys = null;

	DyFormService dysc = null;

	public BusTurnpage() {
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
		ListView search = (ListView) obj;
		if (search != null) {
			if (search.getCondition() != null
					&& search.getCondition().length() > 0) {
				try {
					total = dysc.queryDataNum((TCsBus) search.getSearchobj(),
							search.getCondition());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					total = dysc.queryDataNum((TCsBus) search.getSearchobj(),
							"");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		page.setTotal((int) total);
	}

	public Object page(Page page, Object obj) {
		List list = new ArrayList();
		ListView search = (ListView) obj;
		if (search != null) {
			if (search.getCondition() != null
					&& search.getCondition().length() > 0) {
				try {

					list = dysc.queryData((TCsBus) search.getSearchobj(), page
							.startRow(), page.getSize(), search.getCondition());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// UtilsBuss.reWrite(list, search.getFormcode());

			} else {
				try {
					list = dysc.queryData((TCsBus) search.getSearchobj(), page
							.startRow(), page.getSize(), "");

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// UtilsBuss.reWrite(list, search.getFormcode());
			}
		}
		// UserDao.userViewRichx(list);
		// 改造数据结果，在 fatherlsh中增加选择项，这个在Portal应用中选择 fatherlsh 有用到
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			object.setFatherlsh("<a href=\"javascript:selecthis('"
					+ object.getLsh() + "','" + object.getFormcode()
					+ "')\">选择</a>");
		}
		return list;
	}

}
