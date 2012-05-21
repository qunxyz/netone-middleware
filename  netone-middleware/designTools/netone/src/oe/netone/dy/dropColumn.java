package oe.netone.dy;

import java.awt.List;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.web.tag.resource.LoadClerkTag;

public class dropColumn {
	public void dropcolumn(String formcode, String columnid) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		TCsColumn csColumn = dys.loadColumn(formcode,columnid);		
		String fal=dys.dropColumn(csColumn);
	}
      
	public String updateColumn(String formcode, String columnid,String name,String index){
		DyFormDesignService dys;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			TCsColumn csColumn = dys.loadColumn(formcode,columnid);	
			csColumn.setColumname(name);
			String fal=dys.updateColumnView(csColumn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return index;
	}
	
}
