package oe.cav.web.data;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;

import oe.cav.web.data.dyform.DynamicFormDisplay;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.ListView;
import oe.cav.web.data.dyform.utils.ParseDyform;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import com.rongji.webframework.event.page.Page;
import com.rongji.webframework.event.page.PageEvent;
import com.rongji.webframework.ui.html.Button;
import com.rongji.webframework.ui.html.Select;
import com.rongji.webframework.ui.html.Table;

public class BusTurnpageView implements PageEvent {
	DyFormDesignService dys = null;

	DyFormService dysc = null;

	public BusTurnpageView() {
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

		ListView search = (ListView) obj;
		if (search != null) {
			List list = null;
			if (search.getCondition() != null
					&& search.getCondition().length() > 0) {
				try {
					list = dysc.queryData((TCsBus) search.getSearchobj(), page
							.startRow(), page.endRow(), search.getCondition());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				try {
					list = dysc.queryData((TCsBus) search.getSearchobj(), page
							.startRow(), page.endRow(), "");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (search.getFormcode() == null) {
				return new ArrayList();
			}
			List columnList=null;
			try {
				columnList = dysc.fetchColumnList(search.getFormcode());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Table table = new Table();
			DefaultElementAdder.addHideColumn(search.getDyform(), "formcode",
					search.getFormcode());
			DefaultElementAdder.addHideColumn(search.getDyform(), "lsh", search
					.getLsh());

			// fetchFatherInfo(search.getLsh(), search, table);

			for (Iterator itr = list.iterator(); itr.hasNext();) {
				TCsBus busFomrPre = (TCsBus) itr.next();
				DynamicFormDisplay.generateDispView(search.getAe(),
						busFomrPre, columnList, search.getDyform(), null,
						table, search.getBut1(), search.getBut2(), search
								.getSubForm());
			}
			search.getDyform().addControl(table);
			String dyforminfo = ParseDyform.parseDyform(search.getDyform());
			TCsBus bus = new TCsBus();

			bus.setColumn1(dyforminfo.replaceAll("<td>&nbsp;</td>", ""));
			List listData = new ArrayList();
			listData.add(bus);
			return listData;
		} else {
			return new ArrayList();
		}
	}

}
