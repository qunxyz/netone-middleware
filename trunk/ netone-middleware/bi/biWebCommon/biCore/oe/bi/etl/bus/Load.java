package oe.bi.etl.bus;

import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.view.obj.ViewModel;


public interface Load {
    /**
     * ×°ÔØÊý¾Ý
     * @param sql
     * @param runtimeDimColumn
     * @return
     */
	ViewModel performLoad(AimCode aim,ChoiceInfo info);
}
