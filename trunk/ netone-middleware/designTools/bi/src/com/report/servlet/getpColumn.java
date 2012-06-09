package  com.report.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormColumn;
import com.sun.xml.internal.bind.v2.model.core.ID;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.web.tag.resource.LoadClerkTag;

public class getpColumn {
	public List getc(String formid) throws Exception {
		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		List aList = dysc.fetchColumnList(formid);
		List rList= new ArrayList();
	 	for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
	   		TCsColumn name = (TCsColumn) iterator.next();
	   		if(name.getColumnid().toUpperCase().equals("BELONGX") || name.getColumnid().toUpperCase().equals("TIMEX") ){
		 	}else{
	   		rList.add(name);
		 	}
	   		}
		return rList;
	}
}
